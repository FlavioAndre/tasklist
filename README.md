# Java EE 7 - TaskList #

## How to run ? ##

## Codenvy ##

Codenvy (https://codenvy.com) IDE Nuvem, criar/compilar/distruir.

Verificar: [Java EE 7 TaskList](https://codenvy.com/f?id=)


## Localhost ##

* You need JDK 7 or higher, Maven 3 and Wildfly 8 or Glassfish 4.1 to run the application.
* Build the code using Maven with the command: `mvn clean install`.

### Deploy in Wildfly 10 ###

  * Copy the file tasklist.war from target directory to your Wildfly installation folder
  `standalone/deployments`

  * You can also deploy the app using the Maven Wildfly Plugin with the following command: `mvn wildfly:deploy`. You need to have Wildfly running.

  * Start Wildfly and go to http://localhost:8080/tasklist/ (http://localhost:8080/tasklist/)
  
### Deploy in Embedded Wilffy ###

  * Just run `mvn wildfly:run`
  
  * Go to http://localhost:8080/tasklist/ (http://localhost:8080/tasklist/)

### Deploy in Glassfish 4.1 ###

  * Open Admin Console (http://localhost:8484/)
  
  * Go to menu "Application" 
  
  * In the button "Deploy..." select the file tasklist.war
   
  * Go to http://localhost:8080/tasklist/ (http://localhost:8080/tasklist/)
  
### Deploy in Embedded-Glassfish 4.1 ###

  * Just run `mvn embedded-glassfish:run`
  
  * Go to http://localhost:8080/tasklist/ (http://localhost:8080/tasklist/)
  
### Run with TomEE ###

  * Just run `mvn tomee:run`
  
  * Go to http://localhost:8080/tasklist/ (http://localhost:8080/tasklist/)
  
## Javascript Package Management (optional) ##

The required JS libraries are included in the project, but it also possible to manage them following the next steps:

* You need NPM. Please go to http://nodejs.org/download/ to get a copy.

* Once NPM is installed run the command `npm install`.

* Install Grunt `npm install -g grunt-cli`  for more information please go to http://gruntjs.com/getting-started.

* Run the command 'grunt' to download all the web dependencies and build an optimized version of the project.
