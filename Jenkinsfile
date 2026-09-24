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

        stage('Build Application') {
            steps {
                echo 'Building Spring Boot application...'
                bat 'mvn clean install'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image ${DOCKER_IMAGE}:${IMAGE_TAG}"

                bat "docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "Pushing Docker image ${DOCKER_IMAGE}:${IMAGE_TAG}"

                bat "docker push ${DOCKER_IMAGE}:${IMAGE_TAG}"
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
                echo "Updating Kubernetes deployment image..."

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
            echo 'Deployment completed successfully!'
            echo '========================================'
        }

        failure {
            echo '========================================'
            echo 'Deployment failed!'
            echo '========================================'
        }
    }
}