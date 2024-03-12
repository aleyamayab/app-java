pipeline {
    agent {
        label 'jenkis-slave'
    }

    tools {
        jdk 'java17'
        maven 'maven'
    }

    environment {
        AWS_DEFAULT_REGION = 'us-east-1'
        IMAGE_REPO_NAME = 'my-app-java'
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

        stage('Limpiar, Construir y Testear') {
            steps {
                dir('/home/ec2-user/workspace/Despliegue/my-app') {
                    script {
                        // Limpia, construye y prueba el proyecto Maven
                        sh 'mvn clean package'
                        sh 'mvn test'
                    }
                }
            }
        }

        stage('Construir y Subir Imagen Docker') {
            steps {
                dir('/home/ec2-user/workspace/Despliegue/my-app') {
                    script {
                        // Define las variables
                        def AWS_ACCOUNT_ID = '654654145084'
                        def IMAGE_TAG = "v1.0.${BUILD_NUMBER}"
                        def REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"

                        // Construye la imagen Docker
                        sh "docker build -t $IMAGE_REPO_NAME:$IMAGE_TAG ."

                        // Autentica con AWS ECR
                        withCredentials([aws(credentialsId: 'jenkis_tst', region: AWS_DEFAULT_REGION)]) {
                            sh "aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin $REPOSITORY_URI"
                        }

                        // Etiqueta la imagen
                        sh "docker tag $IMAGE_REPO_NAME:$IMAGE_TAG $REPOSITORY_URI:$IMAGE_TAG"

                        // Sube la imagen a ECR
                        sh "docker push $REPOSITORY_URI:$IMAGE_TAG"
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
