pipeline {
    agent any

    environment {
        SKIP_SONAR = "${params.SKIP_SONAR ?: 'false'}"
    }

    parameters {
        booleanParam(name: 'SKIP_SONAR', defaultValue: false, description: 'Skip SonarQube Analysis?')
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/sk-sohel01/rbcpoc.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                echo "Building the app..."
                // Replace this with your real build commands if needed
                sh 'echo Build completed.'
            }
        }

        stage('SonarQube Analysis') {
            when {
                expression { return params.SKIP_SONAR == false }
            }
            steps {
                echo "Running SonarQube scan..."
                // Replace with your real sonar-scanner or mvn sonar:sonar command
                sh 'echo SonarQube scan done.'
            }
        }

        stage('Post Build') {
            steps {
                echo "Post build steps here"
            }
        }
    }
}
