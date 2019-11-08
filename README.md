# quarkus-vlille-demo
**This is a small quakus demo that will display VLille bike stations using OpenData-MEL (Metropole Européenne Lilloise) <a href="https://opendata.lillemetropole.fr/explore/dataset/vlille-realtime/api/">Vlille-API</a> <br/>** <br/>
<img src="/src/main/resources/META-INF/resources/images/Annotation.png" width="400" height="300" /> <br>

Currently I have setted it to only display 2 stations, their name and available bike number 
  
### [centos-quarkus-maven](https://github.com/quarkusio/quarkus-images "https://github.com/quarkusio/quarkus-images"):   
- Contains all the necessary and pre-configured tools to build and develop the project. 
- Contains Graalvm which will later help me produce the native executable (*.sh)

Project current packaged weight:
124MB


### Prerequisite:
- A docker host container ready to use.<br>



### Run step by step: 

- Run with docker:<br/>
	- `docker pull jcsim/quarkus-vlille:latest`
	- `docker run -i --rm -p 8082:8080 jcsim/quarkus-vlille` <br/>   
	- Go to http://localhost:8082/ <br/>

- Run with kubernetes:<br/>
	- `docker pull jcsim/quarkus-vlille:latest`
	- `kubectl create deployment quarkus-deploy --image=jcsim/quarkus-vlille`
	- expose service and port XXXX->8080<br/>
	- Go to http://localhost:XXXX/ <br/>

### Intall step by step: 
- Install with docker:<br/>
	- Pull centos-quarkus-maven with docker.<br/>
	- git clone the project here its _d:/SharedFolder/projet_vlille/vlille-jc_
	- Run and share volume(if using windows) with your host the newly pulled centos image. `tail -f /dev/null` is here so the container does'nt automatically shuts down.
		- `docker run --rm --name gvm-mvn-tb  -v d:/SharedFolder/projet_vlille/vlille-jc:/mnt/vlille-jc -p 8081:8080 -d quay.io/quarkus/centos-quarkus-maven:19.2.1 tail -f /dev/null`
	- Docker exe bash into it , cd to the volume shared  `/mnt/vlille-jc`
	- Intall dependencies and run the app 
		- `./mvnw compile quarkus:dev`
- Package the app:
	- Then mvn package with TestSkip (I'm not using them yet).<br/>
	- `docker build -f src/main/docker/Dockerfile.jvm -t quarkus/getting-started-jvm .`   <br/>
