pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'ls'
                sh 'mvn test'
            }
        }
        stage('Build'){
            steps {
                sh 'mvn clean package'
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