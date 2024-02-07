resource "kubernetes_deployment" "api_service" {
  metadata {
    name = "api-service"
  }

  spec {
    replicas = 2

    selector {
      match_labels = {
        app = "api-service"
      }
    }

    template {
      metadata {
        labels = {
          app = "api-service"
        }
      }

      spec {
        container {
          image = "your-api-image:latest"
          name  = "api-service"

          env = [
            { name = "DATABASE_HOST", value = "postgres" },
            { name = "DATABASE_NAME", value = "mydatabase" },
            { name = "DATABASE_USER", value = "user" },
            {
              name  = "DATABASE_PASSWORD",
              value_from {
                secret_key_ref {
                  name = "postgres-secret"
                  key  = "password"
                }
              }
            }
          ]

          port {
            container_port = 80
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "api_service" {
  metadata {
    name = "api-service"
  }

  spec {
    selector = {
      app = "api-service"
    }

    port {
      port        = 80
      target_port = 80
    }

    type = "LoadBalancer"
  }
}