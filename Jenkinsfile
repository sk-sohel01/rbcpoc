pipeline {
  agent any

  parameters {
    string(name: 'JOB_ID', defaultValue: 'demo-job', description: 'Unique Job ID for this pipeline run')
  }

  environment {
    PYTHONPATH = "${env.WORKSPACE}/scripts"
  }

  stages {
    stage('Install Dependencies') {
      steps {
        sh 'which python3 || sudo apt-get install python3 -y'
      }
    }

    stage('Run State-Aware Pipeline') {
      steps {
        dir('stateful-pipeline-demo') {
          sh 'python3 scripts/state_aware_pipeline.py --job-id $JOB_ID'
        }
      }
    }

    stage('Show Final State') {
      steps {
        dir('stateful-pipeline-demo') {
          sh 'cat state/state.json'
        }
      }
    }
  }
}
