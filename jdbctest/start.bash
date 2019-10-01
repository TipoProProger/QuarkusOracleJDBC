./mvnw -o -X clean
./mvnw -o -X package -Pnative -Dnative-image.docker-build=true 2> errorLogs.txt >& logs.txt
gedit logs.txt
