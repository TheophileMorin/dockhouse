How to deploy the dev environment in local
====

## Prerequisites
* For MacOS & Windows
    1. Install VirtualBox and Vagrant
* For Linux
    1. Install Docker
    2. Install docker-compose  (see ../install-docker-compose.sh)

## Run the environment
* For MacOS & Window
    1. In a terminal, go under this current directory
    2. Launch the virtual machine `vagrant up` (need to wait during the first launch)
    3. Go to the machine  
        a. `vagrant ssh`  
        b. Go to the scripts directory `cd scripts`
        c. Launch `docker-compose up -d` (need to wait during the first launch)   
    4. Now you have a MongoDB and a private Docker registry launched (respectively with port 27017 and 5000)
* For Linux
    1. In a terminal, go under "scripts" in this current directory
    2. Launch `docker-compose up -d`

## How to create image in the private docker registry
* For MacOS & Windows
    1. Assert that the VM is started `vagrant up`
    2. Connect to it `vagrant ssh`
    3. Go to the "scripts" directory
    4. Assert that the env is launched `docker-compose up -d` (`docker-compose ps` to see the status of the env)
    5. Use the script `create-image-in-registry.sh` to create image in the private registry  
* For Linux
    1. Assert the environment is launched
    2. Launch the `create-image-in-registry.sh`  

> **NOTE**: If the files under the scripts directory, Windows and MacOs users need
> to update their VM by launching `vagrant provision` after the `vagrant up` and before the `vagrant ssh`  
> **You must delete the script directory inside VM before doing this operation**



