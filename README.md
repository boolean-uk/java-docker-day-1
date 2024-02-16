# Java Docker Day 1 Exercise

## Learning Objectives

- Create a Spring Application with the usual end points
- Practice deploying the Spring Application to a Docker Container

## Instructions

1. Fork this repository
2. Clone your fork to your machine
3. Open the project in IntelliJ

## Core

Create an API with the usual GET/PUT/POST/DELETE endpoints for students and a linked table containing details of courses the students are taking.

The API should serve user data for students attending a bootcamp, they will only ever be on a single course at a time.

The data should include:

* Unique ID for students
* First Name
* Last Name
* Date of Birth
* Course Title
* Start Date for Course

Your API should connect to an ElephantSQL database instance that can be used for storing the data.

Create a `jar` file for the completed application.

Create a `Dockerfile` and any other associated files to allow you to deploy the application using a Docker Container.

Do not share your credentials in the `application.yml` file. 

To assess this we will clone your repository, add our own credentials to the cloned repository and use those to test that the application works as expected.

## Extension

Extend the course details by adding another set of endpoints for modules, that make up courses. Modules can be part of multiple courses at the same time (EG they might complete a GitHub module in various courses, but the module itself has the same content across courses.) Link these to the students and courses in whatever way logically works for you. What extra endpoints does it make sense for a student t be able to view, that will give them extra insights and an overview of their studies? Add them to your solution.

## TO RUN THE APPLICATION

1. Open terminal and paste the following commands to run the application
```bash
docker run -dp 4000:4000 -e DB_USERNAME={REPLACE_WITH_DB_USERNAME} -e DB_PASSWORD={REPLACE_WITH_DB_PASSWORD} -e DB_URL={REPLACE_WITH_DB_URL} docker-day-1
docker build -t docker-day-1 .
```