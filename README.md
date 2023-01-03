# Json4Img REST API
Some modern languages still don't have (cross-platform) graphics even for simple tasks like to display a model to an image.   
This is a simple and lightweight **REST API** application on **Java 8** for local development (sandbox) which does simple things:
* writes a **PNG** image file from **<** a **JSON**
* reads a **PNG/JPG** image to **>** a **JSON**
* **display a PNG** image in a JFrame from **<** a **@POST**'ed **JSON** via REST API
* <s>**display a PNG** image in a JFrame from **<** a **JSON** file</s>

It is not complex. It follows KISS principle: keep it simple and stupid.  
<small>*Note:* a CSV file may be easily converted to a JSON file (in many ways by many tools).</small>

REST API is used for cross-platform compatibility: just run as a standalone service on a host and a port defined (by default: `localhost` and `8080`).  
Because of that there is a useful option to be a supporting backend microservice (a Docker container for instance) to produce and/or read image(s).

## Used technologies
This Java app runs [Bootique](https://bootique.io) framework. It is lightweight and this framework supports Environment variables and YAML config out of the box.

## File storage
It is assumed there are IN and OUT:
* IN is `sharedDir`
* OUT is `outputDir`

An application reads images from IN (`sharedDir`) by a relative path and respond a json for them.  
An application writes image files into OUT (`outputDir`).

IN and OUT may be configured to point the same directory.

## How is to run on Java
### Build:
Build
```shell
mvn clean package
```
### First Run
#### Configure file storage 
Do run the following shell script for a demo run: a project directory will be properly configured as file storage for the current Terminal session.
```shell
source ./use-pwdfs.sh
```
Or export IN and OUT into environment variables in Terminal on your own (read a manual further how).
#### Run an application
then run a Java app:
```shell
java -Dbq.trace -jar json4img-rest/target/json4img-rest-1.0.jar --server --local
```
### Open a browser
Check in a browser that an application is up and works:
```
http://127.0.0.1:8080/json4img
```
### Make first requests
POST to recieve a json response for an image file placed on a demo `IN` relative path `./samples/sample.png`:
```shell
curl -X POST -v -H "Content-Type: application/json" --data "{\"path\":\"/samples/sample.png\"}" http://127.0.0.1:8080/json4img/json
```
POST a json to produce an output image file (in case of a demo run it will be wrotten into `./output/example.png`):
```shell
curl -X POST -H "Content-Type: application/json" -d @./samples/image.json http://127.0.0.1:8080/json4img/image
```

# How is to run with Docker
## initialize
`m2` is a local volume for Maven repository purposes.
```shell
docker volume create m2
```
## fill vars in .env file
There is a template which from is good to start.
```shell
cp env.template .env
```
Mount volumes for file storage IN=${IMAGES_DIR} and OUT=${OUTPUT_DIR}.
# How is to build
### compile & build Java code
```shell
docker-compose run build-code
```
### build Docker images:
```shell
docker-compose build
```

## Run REST API service:

### Run
```shell
docker-compose up json4img-rest
```
or in detached mode
```shell
docker-compose up -d json4img-rest
```
# Preferences
Have a look at `./json4img-rest/src/main/resources/com/github/vitalz/jrest/json4img/server.yml` YAML as an example for a custom config.
### Jetty context and port
Jetty prefs are under `jetty` prefix.
### File storage
File storage is in a YAML file under `fs`.
### Environment variables
Such options may be declared for a Bootique app via environment variables by exporting them in a shell like in an example:
```shell
export JETTY_CONTEXT=/json4img/rest
export FS_SHAREDDIR=/opt/json4img/images
export FS_OUTPUTDIR=/opt/json4img/output
```
## Run with custom preferences declared in its own YAML config:
```shell
java -Dbq.trace -jar json4img-rest/target/json4img-rest-1.0.jar --server --config=myjson4img.yml
```