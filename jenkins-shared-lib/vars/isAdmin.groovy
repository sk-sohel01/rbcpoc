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
    def normalizedSids = assignedSids*.toLowerCase()

    echo "🔐 Raw Admin Role SIDs: ${assignedSids}"
    echo "🔐 Normalized Admin Role SIDs: ${normalizedSids}"

    def result = normalizedSids.contains(userId)
    echo result ? "✅ '${userId}' is admin." : "❌ '${userId}' is NOT admin."

    return result
}
