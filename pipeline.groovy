pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "your-registry/your-api:${BUILD_NUMBER}"
        KUBE_CONFIG = "path/to/kubeconfig"
    }

    stages {
        stage('Build') {
            steps {
                script {
                    // Build Docker image
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }
        
        stage('Push') {
            steps {
                script {
                    // Login to Docker registry
                    sh "docker login -u your-username -p your-password your-registry"
                    // Push image to Docker registry
                    sh "docker push ${DOCKER_IMAGE}"
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    // Set Kubernetes config
                    sh "kubectl config use-context your-kube-context --kubeconfig=${KUBE_CONFIG}"
                    // Update Kubernetes deployment to use the new image
                    sh "kubectl set image deployment/api-service api-service=${DOCKER_IMAGE} --kubeconfig=${KUBE_CONFIG}"
                }
            }
        }
    }
    
    post {
        always {
            // Add steps to clean up, notify, etc.
        }
    }
}
