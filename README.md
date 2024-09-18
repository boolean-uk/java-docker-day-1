# Java Docker Day 1 Exercise

## Learning Objectives

- Create a Spring Application with the usual end points
- Practice deploying the Spring Application to a Docker Container

## Instructions

1. Fork this repository
2. Clone your fork to your machine
3. Open the project in IntelliJ

## Core

Create an API with the usual GET/PUT/POST/DELETE endpoints.

The API should serve course data for students attending a bootcamp.

The data for students should include:

* Unique ID
* First Name
* Last Name
* Date of Birth
* Course Title
* Start Date for Course
* Average StudentCourse

Your API should connect to an ElephantSQL database instance that can be used for storing the data.

Create a `jar` file for the completed application make sure it is excluded from your GitHub solution!!!!!

Create a `Dockerfile` and any other associated files to allow you to deploy the application using a Docker Container save it and the jar file to a sensible folder in your project (make sure the JAR file does not get pushed to GitHub).

Exclude your `application.yml` file from GitHub as normal. 

To assess this we will clone your repository, add our own credentials to the cloned repository and use those to test that the application works as expected.

## Extension

In addition to the Student details add in details of different Courses and reference them from the Student details as a Foreign Key relationship. The endpoints for Courses should be completed as expected.

