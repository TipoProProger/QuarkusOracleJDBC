docker build -f src/main/docker/Dockerfile.native -t test .
docker run --name test -i --rm -p 8080:8080 test
