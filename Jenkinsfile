@Library('jenkins-shared-lib') _

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
    IS_ADMIN = 'false'  // Default value, will be set in script
  }

  stages {
    stage('Verify Admin Role') {
      steps {
        script {
          if (isAdmin()) {
            env.IS_ADMIN = 'true'
            echo "✅ User is admin. SKIP_SONAR honored if set."
          } else {
            echo "⚠️ User is NOT admin. SKIP_SONAR will be ignored."
          }
        }
      }
    }

    stage('Build') {
      steps {
        echo '🚧 Building application...'
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression {
          // Run SonarQube stage if:
          // - SKIP_SONAR is false, OR
          // - SKIP_SONAR is true but user is not admin
          return !(params.SKIP_SONAR && env.IS_ADMIN == 'true')
        }
      }
      steps {
        echo '🔍 Running SonarQube Analysis...'
      }
    }

    stage('Post Build') {
      steps {
        echo '📦 Post build steps here...'
      }
    }
  }
}
