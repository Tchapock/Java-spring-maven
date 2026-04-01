pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'chmod +x ./mvnw'
                sh './mvnw test'
            }
        }
        stage('Build'){
            steps {
                sh './mvnw clean package'
            }
        }
        stage('Build Docker Image'){
            steps {
                sh 'docker build -t my-registry/java-spring-maven:$BUILD_NUMBER .'
            }
        }
        stage('Push Docker Image') {
            steps {
                sh 'docker push my-registry/java-spring-maven:$BUILD_NUMBER'
            }
        }
    }
}