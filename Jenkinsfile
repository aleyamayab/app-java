pipeline {
    agent {
        label 'jenkins-slave'
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

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Establecer el directorio de trabajo en la carpeta que contiene pom.xml
                    dir('my-app') {
                        // Ejecutar el análisis de SonarQube
                        withSonarQubeEnv(installationName: 'sq1') {
                            sh "mvn sonar:sonar"
                        }
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    script {
                        // Utiliza waitForQualityGate y almacena el resultado en la variable qg
                        def qg = waitForQualityGate()

                        // Verifica el estado del Quality Gate
                        if (qg.status != 'OK') {
                            error "Quality Gate failure: ${qg.status}"
                        }
                    }
                }
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

        stage('Deploy') {
            steps {
                echo 'Deploying....'
                // Agrega aquí los pasos de implementación si es necesario
            }
        }
    }
}

