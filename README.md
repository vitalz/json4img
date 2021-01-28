# jrest-json4img
This is a simple and lightweight Java REST API application for local development (sandbox) which makes an PNG/JPG image from JSON and read a PNG/JPG image to JSON.

Following concept is to be backend Docker service to produce and/or read image(s).  
REST API is used for cross-platform compatibility (just run as standalone Docker service and call it on port like 8080).

`m2`is considered common Docker volume for Maven repository.
```bash
docker volume create m2
```
compile & build Java code
```bash
docker run build-code
```
