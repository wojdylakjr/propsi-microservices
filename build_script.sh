#!/bin/bash

services=("user-service" "property-service" "payment-service" "rental-service" "api-gateway")

for service in "${services[@]}"; do
    echo "Building $service..."
    cd "$service" || { echo "Directory $service does not exist"; exit 1; }
    ./gradlew clean build || { echo "Failed to build $service"; exit 1; }
    cd ..
done

echo "All services built successfully"