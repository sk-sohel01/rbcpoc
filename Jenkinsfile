@Library('pipeline-policies') _

pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        echo "🚧 Building app..."
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression {
          return !shouldSkipSonar('rbcpoc') // replace with your actual repo name
        }
      }
      steps {
        echo "🔍 Running SonarQube..."
      }
    }

    stage('Post Build') {
      steps {
        echo "✅ Post build complete."
      }
    }
  }
}
