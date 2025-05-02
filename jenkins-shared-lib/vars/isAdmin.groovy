def call() {
  def userId = env.BUILD_USER_ID?.trim()
  if (!userId) return false

  def authStrategy = Jenkins.get().getAuthorizationStrategy()
  if (!(authStrategy instanceof RoleBasedAuthorizationStrategy)) return false

  def roleMap = authStrategy.getRoleMap(RoleBasedAuthorizationStrategy.GLOBAL)
  def adminRole = roleMap.getRole('admin')
  if (!adminRole) return false

  def assignedSids = roleMap.getSids(adminRole)*.toLowerCase()
  return assignedSids.contains(userId.toLowerCase())
}
