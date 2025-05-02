def call() {
    def userId = currentBuild.rawBuild.getCauses().find { it.userId }?.userId
    println "🔍 Detected userId: ${userId}"

    if (!userId) return false

    def authStrategy = Jenkins.get().getAuthorizationStrategy()
    if (!(authStrategy instanceof RoleBasedAuthorizationStrategy)) return false

    def roleMap = authStrategy.getRoleMap(RoleBasedAuthorizationStrategy.GLOBAL)
    def adminRole = roleMap.getRole('admin')
    if (!adminRole) return false

    def assignedSids = roleMap.getSids(adminRole)
    println "👥 Admin role assigned to: ${assignedSids}"

    return assignedSids.contains(userId)
}
