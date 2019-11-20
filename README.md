# jersey-servlet-weld-log4j2 [![Build Status](https://travis-ci.org/daggerok/jersey-servlet-weld-log4j2.svg?branch=master)](https://travis-ci.org/daggerok/jersey-servlet-weld-log4j2)
Using Grizzly Jersey, Weld SE CDI 2.0, Filters from Servlet API and Log4j2

## classpath

```bash
./mvnw clean compile
java -Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager -cp "target/dependency/*:target/classes" daggerok.App
#./mvnw clean dependency:copy-dependencies compile jar:jar
#java -Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager -cp "target/dependency/*:target/*" daggerok.App
http :8080/api/hello
http :8080/index.html
http :8080/not-found
http :8080/
```

## java

```bash
./mvnw clean package
java -Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager -jar target/*-all.jar
http :8080/api/hello
http :8080/index.html
http :8080/not-found
http :8080/
```

## resources

* [MVC templates](https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/user-guide.html#mvc)
* [Jersey reference](https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/user-guide.html)
* [Posting HTML form](https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/user-guide.html#d0e2417)
* https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/modules-and-dependencies.html
* https://github.com/eclipse-ee4j/jersey/blob/master/examples/cdi-webapp/pom.xml
* https://projects.eclipse.org/projects/ee4j.grizzly/developer
