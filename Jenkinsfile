pipeline {
    agent any

    tools {
        jdk 'JDK21'      
        maven 'Maven3'   
    }

    environment {
        MVN_OPTS = '-B -DskipTests=false'  
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out the source code..."
                git branch: 'main', url: 'https://github.com/JoshuaNath/Capstone_Project_BStackDemo.git'
            }
        }

        stage('Clean & Build') {
            steps {
                echo "Cleaning and building the project..."
                sh 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running TestNG and Cucumber tests..."
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo "Packaging the application..."
                sh 'mvn package'
            }
        }

        stage('Archive Artifacts & Reports') {
            steps {
                echo "Archiving build artifacts and test reports..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }

    post {
        success {
            echo 'Build, test, and packaging completed successfully!'
        }
        failure {
            echo 'Build failed. Please check the logs.'
        }
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
    }
}
