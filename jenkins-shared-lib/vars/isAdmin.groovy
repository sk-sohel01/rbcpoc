import com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy
import jenkins.model.Jenkins

def call() {
    def userId = currentBuild.rawBuild.getCauses().find { it.userId }?.userId?.toLowerCase()
    echo "👤 Triggered by: ${userId}"

    if (!userId) {
        echo "❌ No userId found from build cause"
        return false
    }

    def authStrategy = Jenkins.instance.getAuthorizationStrategy()
    if (!(authStrategy instanceof RoleBasedAuthorizationStrategy)) {
        echo "❌ Not using RoleBasedAuthorizationStrategy"
        return false
    }

    def roleMap = authStrategy.getRoleMap(RoleBasedAuthorizationStrategy.GLOBAL)
    def adminRole = roleMap.getRole('admin')
    if (!adminRole) {
        echo "❌ Admin role not found"
        return false
    }

    def assignedSids = roleMap.getSids(adminRole)
    echo "🔐 Raw assigned SIDs: ${assignedSids}"
    def normalizedSids = assignedSids*.toLowerCase()
    echo "🔐 Normalized assigned SIDs: ${normalizedSids}"

    def result = normalizedSids.contains(userId)
    echo result ? "✅ User '${userId}' has admin access" : "⚠️ User '${userId}' does NOT have admin access"

    return result
}
