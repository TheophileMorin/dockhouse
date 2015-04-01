Build and deploy the application
=======

### Description of the following directories

* ```image``` : Docker image creation for the application
* ```local-deployment``` : Scripts for deploy the context of the application (dev and integration)
    * ```dev``` : Deploy the dev environnement (for devs)
    * ```integ```: Deploy the application in the integration environment (for jenkins)
* ```pic/pic-context-build``` : Docker image for the compilation of the application (isolated context use by Jenkins) 
