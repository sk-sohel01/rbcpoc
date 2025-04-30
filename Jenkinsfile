@Library('your-shared-lib-if-any') _

pipeline {
  agent any

  // Show parameter for all, but enforce logic inside
  parameters {
    booleanParam(name: 'SKIP_SONAR', defaultValue: false, description: 'Skip SonarQube analysis (Admins Only)')
  }

  environment {
    // Will be injected by plugin if installed
    BUILD_USER_ID = "${env.BUILD_USER_ID}"
  }

  stages {
    stage('Build') {
      steps {
        echo "Building application..."
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression {
          def auth = jenkins.model.Jenkins.instance.securityRealm.loadUser(env.BUILD_USER_ID)
          def userAuth = auth.getAuthorities()
          def isAdmin = userAuth.any { it.toString().contains("admin") || it.toString().contains("Admin") }

          if (params.SKIP_SONAR && !isAdmin) {
            error "Skipping SonarQube is restricted to Jenkins Admins only!"
          }

          return !params.SKIP_SONAR // run sonar if not skipped
        }
      }
      steps {
        echo "Running SonarQube analysis..."
        // sonar analysis step
      }
    }

    stage('Post Build') {
      steps {
        echo "Post build tasks complete."
      }
    }
  }
}
