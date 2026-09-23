pipeline {

    agent any

    environment {
        DOCKER_IMAGE = "assadburiro30/ledger-core-system"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build & Unit Test') {
            steps {
                echo 'Building application and running unit tests...'

                bat 'mvn clean install'
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building Docker image ${DOCKER_IMAGE}:${IMAGE_TAG}"

                bat "docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} ."
            }
        }

        stage('Push Docker Image') {
            steps {

                echo "Pushing Docker image ${DOCKER_IMAGE}:${IMAGE_TAG}"

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {

                    bat 'docker login -u "%DOCKER_USERNAME%" -p "%DOCKER_PASSWORD%"'

                    bat "docker push ${DOCKER_IMAGE}:${IMAGE_TAG}"

                    bat 'docker logout'
                }
            }
        }

        stage('Kubernetes Deploy') {
            steps {

                echo 'Applying Kubernetes configuration...'

                bat 'kubectl apply -f k8s/deployment.yaml'
                bat 'kubectl apply -f k8s/service.yaml'
            }
        }

        stage('Update Image') {
            steps {

                echo "Updating Kubernetes image..."

                bat "kubectl set image deployment/ledger-core-system ledger-core-container=${DOCKER_IMAGE}:${IMAGE_TAG}"
            }
        }

        stage('Rollout') {
            steps {

                echo 'Waiting for deployment rollout...'

                bat 'kubectl rollout status deployment/ledger-core-system --timeout=120s'
            }
        }

        stage('Verify') {
            steps {

                echo 'Checking Kubernetes deployment...'

                bat 'kubectl get deployment ledger-core-system'

                bat 'kubectl get pods'

                bat 'kubectl get services'

                bat 'kubectl rollout status deployment/ledger-core-system --timeout=120s'
            }
        }
    }

    post {

        success {
            echo '========================================'
            echo 'Build, Tests and Deployment SUCCESSFUL!'
            echo '========================================'
        }

        failure {
            echo '========================================'
            echo 'Pipeline FAILED!'
            echo 'Application was NOT deployed.'
            echo '========================================'
        }
    }
}