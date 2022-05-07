<p align="center">
  <img src="https://renanfranca.github.io/img/mamazinha-baby-care/github-mamazinha-baby-image_readme.png" />
</p>
<p align="center">
<a href="https://github.com/renanfranca/mamazinha-baby/blob/main/LICENSE.txt" target="blank">
<img src="https://img.shields.io/github/license/renanfranca/mamazinha-baby?style=flat-square" alt="mamazinha-baby license" />
</a>
<a href="https://github.com/renanfranca/mamazinha-baby/fork" target="blank">
<img src="https://img.shields.io/github/forks/renanfranca/mamazinha-baby?style=flat-square" alt="mamazinha-baby forks"" alt="github-profile-readme-generator forks"/>
</a>
<a href="https://github.com/renanfranca/mamazinha-baby/stargazers" target="blank">
<img src="https://img.shields.io/github/stars/renanfranca/mamazinha-baby?style=flat-square" alt="mamazinha-baby stars"/>
</a>
<a href="https://github.com/renanfranca/mamazinha-baby/issues" target="blank">
<img src="https://img.shields.io/github/issues/renanfranca/mamazinha-baby?style=flat-square" alt="mamazinha-baby issues"/>
</a>
<a href="https://github.com/renanfranca/mamazinha-baby/pulls" target="blank">
<img src="https://img.shields.io/github/issues-pr/renanfranca/mamazinha-baby?style=flat-square" alt="mamazinha-baby pull-requests"/>
</a>
</p>

# ⭐
**If you like this project, please 🌟 this repository! For feedback, feel free to create an issue on this repository.**

# 📖 About
![cover image](https://renanfranca.github.io/img/postbanners/2022-04-23-cover-built-baby-care-web-app.jpeg)
[**I built a Baby Care web app using JHipster (open source from now on 🥰)**](https://renanfranca.github.io/i-built-a-baby-care-web-app-using-jhipster.html)

# baby

This application was generated using JHipster 7.2.0, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v7.2.0](https://www.jhipster.tech/documentation-archive/v7.2.0).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.
This application is configured for Service Discovery and Configuration with the JHipster-Registry. On launch, it will refuse to start if it is not able to connect to the JHipster-Registry at [http://localhost:8761](http://localhost:8761). For more information, read our documentation on [Service Discovery and Configuration with the JHipster-Registry][].

## Development

To start your application in the dev profile, run:

```
./mvnw
```

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### JHipster Control Center

JHipster Control Center can help you manage and control your application(s). You can start a local control center server (accessible on http://localhost:7419) with:

```
docker-compose -f src/main/docker/jhipster-control-center.yml up
```

## Building for production

### Packaging as jar

To build the final jar and optimize the baby application for production, run:

```
./mvnw -Pprod clean verify
```

To ensure everything worked, run:

```
java -jar target/*.jar
```

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off authentication in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod verify jib:dockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 7.2.0 archive]: https://www.jhipster.tech/documentation-archive/v7.2.0
[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v7.2.0/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v7.2.0/development/
[service discovery and configuration with the jhipster-registry]: https://www.jhipster.tech/documentation-archive/v7.2.0/microservices-architecture/#jhipster-registry
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v7.2.0/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v7.2.0/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v7.2.0/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v7.2.0/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v7.2.0/setting-up-ci/
[node.js]: https://nodejs.org/
[npm]: https://www.npmjs.com/
