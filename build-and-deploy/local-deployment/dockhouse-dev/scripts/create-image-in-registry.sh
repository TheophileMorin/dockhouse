#!/usr/bin/env bash

# PARAMETERS
# $1 : IP of the private registry
# $2 : Port to the private registry
# $3 : image name and version to push

# Arguments verification
if [ $# -ne 3 ]; then
    echo "ERROR : 3 arguments expected  !!"
    echo "**********************************"
    echo "--> IP of the private registry"
    echo "--> Listen port of the private registry"
    echo "--> Image name {namespace}/{imageName}:{version} (e.q mycorp/myname:1.2.0, latest if version is empty, ex mycorp/myname)"
    echo
    echo "Usage example : ./create-image-in-registry.sh 192.168.12.3 5000 john/myapp:1.0"
    exit 65
fi

# Pull a light base image
echo "STEP 1 ==> Pull the busybox image"
docker pull busybox

# Create a Tag for a new image
echo "STEP 2 ==> Tag the new image {docker tag busybox $1:$2/$3}"
docker tag -f busybox $1:$2/$3

# Push the image to the regsitry
echo "STEP 3 ==> Push the new image to the private registry"
docker push $1:$2/$3
