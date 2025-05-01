properties([
  parameters([
    booleanParam(
      name: 'SKIP_SONAR',
      defaultValue: false,
      description: 'Skip SonarQube Analysis (Admins Only)'
    )
  ])
])

pipeline {
  agent any

  environment {
    // default false, will be set true only if user is in admin group
    IS_ADMIN = 'false'
  }

  stages {
    stage('Determine Admin') {
      steps {
        script {
          def userId = currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')[0]?.userId
          def user = Jenkins.instance.getUser(userId)
          if (user?.getAuthorities()?.contains('admin')) {
            env.IS_ADMIN = 'true'
            echo "User '${userId}' is admin."
          } else {
            echo "User '${userId}' is NOT admin."
          }
        }
      }
    }

    stage('Build') {
      steps {
        echo 'Building application...'
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression {
          // Only run if SKIP_SONAR is false OR user is not admin
          return !(params.SKIP_SONAR && env.IS_ADMIN == 'true')
        }
      }
      steps {
        echo 'Running SonarQube Analysis...'
      }
    }

    stage('Post Build') {
      steps {
        echo 'Post build steps here...'
      }
    }
  }
}
