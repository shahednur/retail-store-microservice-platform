#!/bin/bash

# Enable Docker BuildKit
export DOCKER_BUILDKIT=1

# All service names (must match folder names exactly)
services=(
  auth-service
  configserver
  discoveryservice
  gatewayservice
  inventoryservice
  notificationservice
  orderservice
  paymentservice
  productservice
)

echo "🔄 Cleaning up old Docker resources..."
docker image prune -af
docker builder prune -af

for service in "${services[@]}"
do
  echo "🚀 Building $service..."
  docker build -t $service:latest ./$service

  echo "📦 Loading $service image into Minikube..."
  minikube image load $service:latest

  echo "📁 Applying Kubernetes configs for $service..."
  kubectl apply -f ./k8s/$service/
done

echo "✅ All services built, loaded into Minikube, and deployed!"
