pipeline {
    agent {
        label 'jenkis-slave'
    }
     environment {
         AWS_ACCOUNT_ID="654654145084"
         AWS_DEFAULT_REGION="us-east-1" 
         IMAGE_REPO_NAME="my-app-java"
         IMAGE_TAG="latest"
         REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
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
                // Establecer el directorio de trabajo en la carpeta que contiene pom.xml
                dir('my-app') {
                    // Eliminar el directorio target para evitar conflictos
                    sh 'rm -rf target'
                    // Ejecutar el análisis de SonarQube
                    withSonarQubeEnv(installationName: 'sq1') {
                        sh "mvn clean verify sonar:sonar -Dsonar.sources=src/main/java -Dsonar.java.binaries=target"
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
                // Establecer el directorio de trabajo en la carpeta que contiene pom.xml
                dir('my-app') {
                    sh "mvn clean package"
                }
            }
        }

        stage('Test app') {
            steps {
                dir('my-app') {
                    sh "mvn test"
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
