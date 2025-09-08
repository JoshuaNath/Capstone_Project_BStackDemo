pipeline {
  agent any
  environment { APP_ENV = 'dev' }

  stages {
    stage('Clone') {
      steps {
        git branch: 'main', url: 'https://github.com/JoshuaNath/Capstone_Project_BStackDemo.git'
      }
    }

    stage('Build & Test') {
      steps {
        echo 'Running TestNG via Maven...'
        bat 'mvn -B clean test'
      }
    }
    
    
    
    stage('Commit & Push Changes') {
    steps {
        script {
            echo 'Checking for changes to push...'
            bat """
                git config --global user.email "jenkins@pipeline.com"
                git config --global user.name "Jenkins CI"
                git config --global --add safe.directory %CD%

                git fetch origin

                git status
                git add .

                REM Commit only if there are changes
                git diff --cached --quiet || git commit -m "Jenkins: Auto-commit after build"

                REM Push to main branch
                git pull origin main
                git push origin main
            """
        }
    }
}


    stage('Publish Reports') {
      steps {
        echo 'Publishing Extent reports...'

        cucumber fileIncludePattern: 'target/*.json',
                 buildStatus: 'UNSTABLE',
                 classifications: [[key: 'Env', value: "${env.APP_ENV}"]]


        
        publishHTML(target: [
          reportDir: 'test-output',
          reportFiles: 'ExtentReport.html',
          reportName: 'Extent Report',
          keepAll: true,
          allowMissing: false,
          alwaysLinkToLastBuild: true
        ])
      }
    }

    stage('Deploy') {
      steps {
        echo "Deploying to ${env.APP_ENV}..."
      }
    }
  }

  post {
    always { echo "Build result: ${currentBuild.currentResult}" }
    success { echo ' Pipeline succeeded!' }
    failure { echo ' Pipeline failed! Check Jenkins logs and reports.' }
  }
}