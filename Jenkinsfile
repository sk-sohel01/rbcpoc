pipeline {
  agent any

  parameters {
    booleanParam(name: 'SKIP_SONAR', defaultValue: false, description: 'Skip SonarQube Analysis (Admin Only)')
  }

  stages {
    stage('Build') {
      steps {
        echo 'Building application...'
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression {
          def userId = currentBuild.rawBuild.getCause(hudson.model.Cause.UserIdCause)?.userId
          def isAdmin = Jenkins.instance.getAuthorizationStrategy()
            .getGrantedAuthorities(userId)
            .contains('admin')
          return !(params.SKIP_SONAR && isAdmin)
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
