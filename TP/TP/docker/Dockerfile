### First we build the application in docker in a controlled env
FROM maven:3.8.4-eclipse-temurin-17 as build
ARG BRANCH
ARG GITHUBLOGIN
ARG GITHUBPASSWORD
ARG SONAR_URL
ARG SONAR_TOKEN

WORKDIR /app

#see https://www.jrebel.com/blog/how-to-speed-up-your-maven-build
# see https://www.baeldung.com/jvm-tiered-compilation
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
#ENV MAVEN_CLI_OPTS="-T 1C --settings /usr/share/maven/ref/settings-docker.xml -B"
ENV MAVEN_CLI_OPTS="--settings /usr/share/maven/ref/settings-docker.xml -B"

COPY docker/settings-docker.xml /usr/share/maven/ref/

# COPY pom.xml /app/pom.xml
COPY .. /app/

RUN --mount=type=cache,id=mvncache,target=/root/.m2/repository,rw \
	mvn $MAVEN_CLI_OPTS verify

#RUN --mount=type=cache,id=mvncache,target=/root/.m2/repository,rw \
#    mvn $MAVEN_CLI_OPTS verify
#    if [ "$SONAR_TOKEN" ] ; then \
#      mvn $MAVEN_CLI_OPTS -D sonar.branch.name=${BRANCH} \
#        --activate-profiles sonar sonar:sonar && \
#      mvn $MAVEN_CLI_OPTS --activate-profiles sonar -pl . sonar-quality-gate:check ; \
#    fi

FROM eclipse-temurin:17.0.2_8-jre-alpine
COPY --from=build /app/target/*one-jar.jar /app/myapp.jar
CMD java -jar /app/myapp.jar