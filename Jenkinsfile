pipeline {
  agent any

  parameters {
    string(name: 'JOB_ID', defaultValue: 'demo-job', description: 'Unique Job ID for state tracking and locking')
  }

  stages {
    stage('Checkout') {
      steps {
        git branch: 'stateful-pipeline', url: 'https://github.com/sk-sohel01/rbcpoc.git'
      }
    }

    stage('Run Unit Tests') {
      steps {
        sh '''
        apt-get update
        apt-get install -y python3 python3-pip
        python3 -m pip install pytest
        pytest tests/ --maxfail=1 --disable-warnings -q
       '''
    }
  }


    stage('Run Stateful Pipeline') {
      steps {
        sh 'python3 scripts/state_aware_pipeline.py --job-id $JOB_ID'
      }
    }

    stage('Show Final State') {
      steps {
        sh 'cat state/state.json || echo "No state file found."'
      }
    }
  }
}

