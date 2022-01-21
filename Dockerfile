FROM maven:3.6.1-amazoncorretto-11 as maven-builder

WORKDIR /src

ARG DB_USER
ARG DB_PASSWORD
ARG DB_URL

ENV DB_USER=${DB_USER}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_URL=${DB_URL}

RUN curl -LJO https://github.com/liquibase/liquibase/releases/download/v4.2.0/liquibase-4.2.0.tar.gz && \
    tar -xzf liquibase-4.2.0.tar.gz && \
	curl -LJO https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.22.tar.gz && \
	tar -xzf mysql-connector-java-8.0.22.tar.gz

COPY . ./

RUN set -ex; ./liquibase \
    --changeLogFile=src/main/resources/db/changelog/changelog-master.yaml  \
	--url=$DB_URL \
	--username=$DB_USER \
	--password=$DB_PASSWORD \
	--driver=com.mysql.cj.jdbc.Driver \
	--classpath=mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar \
	update

WORKDIR /app

COPY . ./

RUN mvn dependency:go-offline
RUN mvn clean package
RUN cp target/span-0.0.1-SNAPSHOT.jar /app/league.jar


FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app

COPY --from=maven-builder /app/bin/start.sh /app/bin/start.sh
COPY --from=maven-builder /app/league.jar /app/league.jar

CMD ["/bin/bash", "bin/start.sh"]
