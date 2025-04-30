pipeline {
    agent any

    parameters {
        booleanParam(name: 'SKIP_SONAR', defaultValue: true, description: 'Skip SonarQube analysis?')
    }

    environment {
        SKIP_SONAR = "${params.SKIP_SONAR}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/sk-sohel01/rbcpoc.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the app...'
                sh 'echo Build completed.'
            }
        }

        stage('SonarQube Analysis') {
            when {
                expression { return SKIP_SONAR != 'true' }
            }
            steps {
                echo 'Running SonarQube scan...'
                // You can enable actual scanning here later
                // withSonarQubeEnv('sonarqube') {
                //     sh 'sonar-scanner'
                // }
            }
        }

        stage('Post Build') {
            steps {
                echo 'Post build steps here'
            }
        }
    }
}
