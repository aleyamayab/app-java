pipeline {
    agent {
        label 'jenkis-slave'
    }

    tools {
        jdk 'java17'
        maven 'maven'
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
                script {
                    // Establecer el directorio de trabajo en la carpeta que contiene pom.xml
                    dir('my-app') {
                        sh "mvn clean package"
                    }
                }
            }
        }

        stage('Test app') {
            steps {
                script {
                    dir('my-app') {
                        sh "mvn test"
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Establecer el directorio de trabajo en la carpeta que contiene pom.xml
                    dir('my-app') {
                        // Limpiar el directorio de trabajo
                        deleteDir()
                        
                        // Ejecutar el análisis de SonarQube
                        withSonarQubeEnv(installationName: 'sq1') {
                            sh "mvn sonar:sonar"
                        }
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying....'
                // Agrega aquí los pasos de implementación si es necesario
            }
        }
    }
}

