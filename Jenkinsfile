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
          input(
            id: 'SonarSkipApproval',
            message: '🔐 Confirm SonarQube skip (Admins only)',
            submitter: 'admin,sksohel01',  // Allowed usernames
            ok: 'Yes, skip'
          )
        }
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression { return !params.SKIP_SONAR }
      }
      steps {
        echo "🔍 Running SonarQube Analysis..."
        // Add your sonar-scanner command here
      }
    }

    stage('Post Build') {
      steps {
        echo "📦 Post build actions..."
      }
    }
  }
}
