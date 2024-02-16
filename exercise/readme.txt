To run spring application:

1. Change variables in the Dockerfile
2. Open CMD and cd into this folder
3. Run "docker build -t my-spring-app ."
4. Run "docker run -dp 4000:4000 --name my-spring-app my-spring-app:latest"

You can change the my-spring-app to whatever but remember to change the docker run to match (also change the ports if needed)