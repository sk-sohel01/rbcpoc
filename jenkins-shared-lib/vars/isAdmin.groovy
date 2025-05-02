import com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy
import jenkins.model.Jenkins

def call() {
    def userId = currentBuild.rawBuild.getCauses().find { it.userId }?.userId?.toLowerCase()
    echo "👤 Triggered by: ${userId}"

    if (!userId) return false

    def authStrategy = Jenkins.instance.getAuthorizationStrategy()
    if (!(authStrategy instanceof RoleBasedAuthorizationStrategy)) return false

    def roleMap = authStrategy.getRoleMap(RoleBasedAuthorizationStrategy.GLOBAL)
    def adminRole = roleMap.getRole('admin')
    if (!adminRole) return false

    def assignedSids = roleMap.getSids(adminRole)*.toLowerCase()
    echo "🔐 Admin Role Assigned To: ${assignedSids}"

    return assignedSids.contains(userId)
}
