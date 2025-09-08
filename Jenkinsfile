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

    stage('Publish Reports') {
      steps {
        echo 'Publishing Extent reports...'

        // Extent HTML -> HTML Publisher (requires HTML Publisher plugin)
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