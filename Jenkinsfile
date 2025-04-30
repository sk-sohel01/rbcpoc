pipeline {
  agent any

  parameters {
    booleanParam(name: 'SKIP_SONAR', defaultValue: false, description: 'Skip SonarQube analysis (Admins Only)')
  }

  stages {
    stage('Build') {
      steps {
        echo "Building application..."
      }
    }

    stage('Authorize Sonar Skip') {
      when {
        expression { return params.SKIP_SONAR }
      }
      steps {
        script {
          def userInput = input(
            id: 'SonarSkipApproval',
            message: 'Do you want to skip SonarQube analysis?',
            parameters: [],
            submitter: 'admin,sksohel01'  // List of allowed Jenkins usernames
          )
        }
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression { return !params.SKIP_SONAR }
      }
      steps {
        echo "Running SonarQube analysis..."
        // sonar-scanner ...
      }
    }

    stage('Post Build') {
      steps {
        echo "Post build actions..."
      }
    }
  }
}
