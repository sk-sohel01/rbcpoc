import com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy
import jenkins.model.Jenkins

def call() {
    def userId = currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.userId?.toLowerCase()
    echo "👤 Triggered by: ${userId ?: 'UNKNOWN'}"

    if (!userId) return false

    def authStrategy = Jenkins.instance.getAuthorizationStrategy()
    if (!(authStrategy instanceof RoleBasedAuthorizationStrategy)) {
        echo "❌ Not using RoleBasedAuthorizationStrategy"
        return false
    }

    def roleMap = authStrategy.getRoleMap(RoleBasedAuthorizationStrategy.GLOBAL)
    def adminRole = roleMap.getRole('admin')

    if (!adminRole) {
        echo "❌ 'admin' role not defined"
        return false
    }

    def assignedSids = roleMap.getSids(adminRole)*.toLowerCase()
    echo "🔐 Admin Role Assigned To: ${assignedSids}"

    def result = assignedSids.contains(userId)
    echo "✅ isAdmin result for '${userId}': ${result}"
    return result
}
