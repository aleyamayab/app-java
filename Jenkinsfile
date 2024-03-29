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
        AWS_ACCOUNT_ID = '654654145084'
    }

    stages {
        stage('Clean work area') {
            steps {
                cleanWs()
            }
        }

        stage('Search SCM') {
            steps {
                git branch: 'main', credentialsId: 'github', url: 'https://github.com/aleyamayab/app-java'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir('my-app') {
                    sh 'rm -rf target'
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
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Quality Gate failure: ${qg.status}"
                        }
                    }
                }
            }
        }

        stage('Clean, Build and Test') {
            steps {
                dir('/home/ec2-user/workspace/testing/my-app') {
                    script {
                        sh 'mvn clean package'
                        sh 'mvn test'
                    }
                }
            }
        }

        stage('Build and Upload Docker Image') {
            steps {
                dir('/home/ec2-user/workspace/testing/my-app') {
                    script {
                        def IMAGE_TAG = "v1.0.${BUILD_NUMBER}"
                        def REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"

                        sh "docker build -t $IMAGE_REPO_NAME:$IMAGE_TAG ."

                        withCredentials([aws(credentialsId: 'jenkis_tst', region: AWS_DEFAULT_REGION)]) {
                            sh "aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin $REPOSITORY_URI"
                        }

                        sh "docker tag $IMAGE_REPO_NAME:$IMAGE_TAG $REPOSITORY_URI:$IMAGE_TAG"
                        sh "docker push $REPOSITORY_URI:$IMAGE_TAG"
                    }
                }
            }
        }

        stage('Update image in deployment.yaml') {
            steps {
                dir('/home/ec2-user/workspace/testing/Java-hello') {
                    script {
                        // Definir las variables
                        def directory = '/home/ec2-user/workspace/testing/Java-hello'
                        def REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
                        def IMAGE_TAG = "v1.0.${BUILD_NUMBER}"

                        // Ejecutar el comando sed para actualizar la línea en el archivo deployment.yaml
                        sh "sed -i 's|image: .*|image: ${REPOSITORY_URI}:${IMAGE_TAG}|' ${directory}/deployment.yaml"
                    }
                }
            }
        }

        stage('Obtener cambios remotos') {
            steps {
                dir('/home/ec2-user/workspace/testing/Java-hello') {
                 sh 'git pull origin main'
            }
        }
    }
        
        stage('Update Repository') {
            steps {
               dir('/home/ec2-user/workspace/testing/Java-hello') {
                sh '''
                    git config --global user.name "aleyamayab"
                    git config --global user.email "kingdom_ale@hotmail.com"
                    git add deployment.yaml
                    git commit -m "Update Deployment version"
                '''
                withCredentials([gitUsernamePassword(credentialsId: 'github', gitToolName: 'Default')]) {
                    sh "git push 'https://github.com/aleyamayab/app-java' main"
                }
            }
        }
        }  

        stage('Deploy') {
            steps {
                echo 'Se ha subbido el cambio correctamente'
            }
        }
    }
}

