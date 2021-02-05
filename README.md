# Json4Img REST API
This is simple and lightweight Java REST API application for local development (sandbox) which does simple things:
* writes PNG image file from JSON
* reads a PNG/JPG image to JSON  

It is not complicated. It will follow KISS principle: keep it simple and stupid.

REST API is used for cross-platform compatibility: just run as standalone service on a any host and call it on port like 8080.  
Because of that there is useful option to be supporting backend microservice (Docker container for instance) to produce and/or read image(s).

## used technologies
This Java app runs [Bootique](https://bootique.io) framework. This framework supports Environment variables and YML config files as well.

## file storage
It is assumed there are IN and OUT:
* in is `sharedDir`
* out is `outputDir`

Application will read images from `sharedDir` (IN) by a relative path and respond json for them.  
And application will write image files into `outputDir` (OUT).  
IN and OUT may be configured to point the same directory.

## How is to run on Java
### Build:
Build
```
mvn clean package
```
### Run
in Terminal export into environment variables IN and OUT based on project directory (as demo file storage in these steps):
```shell
source ./use-pwd-as-fs.sh
```
then run Java app:
```
java -Dbq.trace -jar json4img-rest/target/json4img-rest-1.0.jar --server --local
```
### Open browser
Check in browser it works:
```
http://127.0.0.1:8080/json4img/api
```
### Make your first requests
GET json for an image file on `IN` relative path `/samples/sample.png`:  
<sub><sup>will be re-design to POST soon</sup></sub>
```
http://127.0.0.1:8080/json4img/api/json?path=/samples/sample.png
```
POST json for an image file (it will be wrotten into `/output/example.png`):
```
curl -X POST -H "Content-Type: application/json" -d @./samples/image.json http://127.0.0.1:8080/json4img/api/image
```

# How is to run with Docker
## initialize
`m2` is local volume for Maven repository purposes.
```bash
docker volume create m2
```
## fill vars in .env file
There is an example which from is good to start.
```
cp env.template .env
```
Mount volumes for file storage IN=${IMAGES_DIR} and OUT=${OUTPUT_DIR}.
# How is to build
### compile & build Java code
```bash
docker run build-code
```
### build Docker images:
```bash
docker-compose build
```

## Run REST API service:

### Run
```
docker-compose up json4img-rest
```
or in detached mode
```
docker-compose up -d json4img-rest
```
# Preferences
Have a look at `json4img-rest/src/main/resources/com/github/vitalz/jrest/json4img/server.yml` YML as example for you config:
## Jetty context and port
Jetty prefs are under `jetty` prefix:
```
jetty:
  context: /json4img
  connectors:
    - port: 8080
```
## File storage
File storage is in YML file under `fs` prefix:
```
fs:
  sharedDir: "/opt/json4img/images"
  outputDir: "/opt/json4img/output"
```  
Or these options may be declared for Bootique app via environment variables by names FS_SHAREDDIR, FS_OUTPUTDIR.
## Run with custom prefs declared in its own YML
```
java -Dbq.trace -jar json4img-rest/target/json4img-rest-1.0.jar --server --config=/Users/vital2e/myjson4img.yml
```
