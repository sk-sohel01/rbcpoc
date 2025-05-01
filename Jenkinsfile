properties([
  parameters([
    booleanParam(
      name: 'SKIP_SONAR',
      defaultValue: false,
      description: 'Skip SonarQube Analysis (Admin Only)',
      // No conditional access in UI yet, but this is the hook for RBAC
    )
  ])
])

pipeline {
  agent any

  environment {
    IS_ADMIN = "${currentBuild.rawBuild.getCauses()[0].userId == 'sk-sohel01'}"
  }

  stages {
    stage('Build') {
      steps {
        echo 'Building application...'
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression { return !params.SKIP_SONAR }
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
