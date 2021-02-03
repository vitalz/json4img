# Json4Img REST API
This is simple and lightweight REST API application wrotten on Java for local development (sandbox) which makes a PNG/JPG image from JSON and read a PNG/JPG image to JSON.

It is not complicated. It follows KISS principle: keep it simple and stupid:
* read json for an image
* make an image file for json

Following concept is to be backend microservice (for example, Docker container) to produce and/or read image(s).  
REST API is used for cross-platform compatibility: just run as standalone Docker service and call it on port like 8080.

## initialize
`m2` is local volume for Maven repository purposes.
```bash
docker volume create m2
```
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
### Open browser
Check in browser it works:
```
http://127.0.0.1:8080/json4img/api
```
### Make your requests
GET json for an image file:  
<small>will be re-design to POST soon</small>
```
http://127.0.0.1:8080/json4img/api/json?path=/usr/images/samples/sample.png
```
POST json for an image file:
```
curl -X POST -H "Content-Type: application/json" -d @./samples/image.json http://127.0.0.1:8080/json4img/api/image
```
