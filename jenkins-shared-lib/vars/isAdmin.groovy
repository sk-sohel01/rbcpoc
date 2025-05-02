def call() {
    def userId = currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.userId
    if (!userId) return false

    def user = jenkins.model.Jenkins.instance.getUser(userId)
    if (!user) return false

    return user.getAuthorities().contains('admin')
}
