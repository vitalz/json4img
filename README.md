# Java REST API Json4Img
This is simple and lightweight Java REST API application for local development (sandbox) which makes an PNG/JPG image from JSON and read a PNG/JPG image to JSON.

Following concept is to be backend Docker service to produce and/or read image(s).  
REST API is used for cross-platform compatibility (just run as standalone Docker service and call it on port like 8080).
Nothing is more complicated.

## initialize
`m2` is volume for Maven repository purposes.
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
```
docker-compose up json4img-rest
```
or in detached mode
```
docker-compose up -d json4img-rest
```