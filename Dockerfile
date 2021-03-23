FROM openjdk:11

WORKDIR /gradleComponentDefiner

COPY gradle-modules-component-definer.jar ./gradle-component-definer.jar

ENTRYPOINT [ "java", "-jar", "gradle-component-definer.jar" ]