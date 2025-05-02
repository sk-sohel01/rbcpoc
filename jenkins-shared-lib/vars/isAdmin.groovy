import com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy
import jenkins.model.Jenkins

def call() {
  def userId = currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.userId
  if (!userId) return false

  def authStrategy = Jenkins.instance.getAuthorizationStrategy()
  if (!(authStrategy instanceof RoleBasedAuthorizationStrategy)) return false

  def grantedRoles = authStrategy.getGrantedRoles(RoleBasedAuthorizationStrategy.GLOBAL)
  def adminRole = grantedRoles.get('admin')  // role name must match exactly
  return adminRole?.containsKey(userId)
}
