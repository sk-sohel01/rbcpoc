pipeline {
  agent any

  parameters {
    booleanParam(name: 'SKIP_SONAR', defaultValue: false, description: 'Skip SonarQube analysis (Admins Only)')
  }

  environment {
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
          def isAdmin = userAuth.any { it.toString().toLowerCase().contains("admin") }

          if (params.SKIP_SONAR && !isAdmin) {
            error "Skipping SonarQube is restricted to Jenkins Admins only!"
          }

          return !params.SKIP_SONAR
        }
      }
      steps {
        echo "Running SonarQube analysis..."
        // Your sonar analysis logic
      }
    }

    stage('Post Build') {
      steps {
        echo "Post build tasks complete."
      }
    }
  }
}
