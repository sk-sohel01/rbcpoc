pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        echo "🚧 Building application..."
      }
    }

    stage('Authorize Sonar Skip') {
      when {
        expression { return params.SKIP_SONAR }
      }
      steps {
        script {
          def userId = currentBuild.rawBuild.getCause(hudson.model.Cause.UserIdCause)?.getUserId()
          echo "🔍 Build triggered by user: ${userId}"
          
          if (userId != 'sohel') {
            error("❌ Unauthorized: Only admin (sohel) can skip SonarQube analysis.")
          } else {
            echo "✅ Admin authorization confirmed. Skipping SonarQube."
          }
        }
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression { return !params.SKIP_SONAR }
      }
      steps {
        echo "🔍 Running SonarQube Analysis..."
        // Add sonar-scanner command here
      }
    }

    stage('Post Build') {
      steps {
        echo "📦 Post build actions..."
      }
    }
  }
}
