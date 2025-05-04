pipeline {
  agent any

  environment {
    GIT_REPO_URL = 'https://github.com/sk-sohel01/rbcpoc.git'
    SKIP_LIST_FILE = 'pipeline-policy/sonar-skip-repos.txt'
  }

  stages {
    stage('Build') {
      steps {
        echo "🚧 Building application..."
      }
    }

    stage('Check Skip List') {
      steps {
        script {
          def skipList = readFile(env.SKIP_LIST_FILE).readLines().collect { it.trim() }
          if (skipList.contains(env.GIT_REPO_URL)) {
            currentBuild.description = "✅ Skipped SonarQube (Repo Whitelisted)"
            env.SKIP_SONAR = "true"
          } else {
            env.SKIP_SONAR = "false"
          }
        }
      }
    }

    stage('SonarQube Analysis') {
      when {
        expression { return env.SKIP_SONAR != "true" }
      }
      steps {
        echo "🔍 Running SonarQube Analysis..."
        // sonar-scanner command goes here
      }
    }

    stage('Post Build') {
      steps {
        echo "📦 Post build actions..."
      }
    }
  }
}
