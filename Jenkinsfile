pipeline {
    agent any

    tools {
        jdk 'java17'
        maven 'maven_script'
    }

    stages {
        stage('Limpiar Area de trabajo') {
            steps {
                cleanWs()
            }
        }

        stage('Buscar SCM') {
            steps {
                git branch: 'main', credentialsId: 'github', url: 'https://github.com/aleyamayab/app-java'
            }
        }

        stage('Build app') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('Test app') {
            steps {
                sh "mvn test"
            }
        }

        stage('Analisis Sonarqube') {
            steps {
                Script{
                    withSonarQubeEnv('credentialsId: jenkins-sonarquebe') {
                    sh "mv sonar:sonar"
                     }
                 }
            }
        }
        
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}


