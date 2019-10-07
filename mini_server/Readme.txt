##########################
###### SYSTEM SPECS ######
##########################
> java -version
openjdk version "1.8.0_222"
OpenJDK Runtime Environment (build 1.8.0_222-8u222-b10-1ubuntu1~16.04.1-b10)
OpenJDK 64-Bit Server VM (build 25.222-b10, mixed mode)

> lsb_release -a
No LSB modules are available.
Distributor ID:	Ubuntu
Description:	Ubuntu 16.04.4 LTS
Release:	16.04
Codename:	xenial

##########################
###### WHAT IT DOES ######
##########################
The mini server to run and listen on the port (default: 8081)
if the query for the folder comes, it will generate the html page to list all the files and folders
If the query for the file comes, it will let you download the file.

########################
###### HOW TO RUN ######
########################
1. Edit the application.properties and change the config to match your desire.
    > cat src/main/resources/application.properties 
    server.port=8081
    shared.dir=/shared
    --> the server will run on port 8081 and load from /shared folder
2. Run "mvn clean install" to build, it will produce the jar file
3. Run "java -jar [path_to_jar]" to run the server
4. Using this command to check:
    > curl localhost:8081

##############################
###### SOURCE STRUCTURE ######
##############################
mini_server
    src
        main
            java
                com
                    webapp
                        controller
                            Controller.java -> the source code of controller
                        main
                            Application.java -> the source code of the application
                        model
                            Model.java -> the source code of the model to generate html page or transfer file