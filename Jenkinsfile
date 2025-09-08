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
    
    
    
    stage('Push Changes') {
            steps {
                echo 'Pushing local changes to GitHub...'
                withCredentials([usernamePassword(credentialsId: 'CapstoneProject', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_TOKEN')]) {
                    bat """
                    git config user.email "nathjoshua1502@gmail.com"
                    git config user.name "Admin"

                    git add .
                    git commit -m "Automated commit from Jenkins" || echo "No changes to commit"

                    git push https://${GIT_USER}:${GIT_TOKEN}@github.com/JoshuaNath/Capstone_Project_BStackDemo.git HEAD:main
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