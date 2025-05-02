import com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy
import jenkins.model.Jenkins

def call() {
  def userId = currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.userId
  if (!userId) return false

  def authStrategy = Jenkins.instance.getAuthorizationStrategy()
  if (!(authStrategy instanceof RoleBasedAuthorizationStrategy)) return false

  def roleMap = authStrategy.getGrantedRoles(RoleBasedAuthorizationStrategy.GLOBAL).get('admin')
  return roleMap?.grantedRoles?.contains(userId)
}
