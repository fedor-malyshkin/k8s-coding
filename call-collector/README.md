# call-collector

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/call-collector-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Related Guides

- Consul Config ([guide](https://quarkiverse.github.io/quarkiverse-docs/quarkus-config-extensions/dev/consul.html)):
  Read runtime configuration from Consul Key - Value store
- SmallRye Reactive Messaging - RabbitMQ Connector ([guide](https://quarkus.io/guides/rabbitmq)): Connect to RabbitMQ
  with Reactive Messaging
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time
  processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions
  that depend on it.
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin
- Kubernetes ([guide](https://quarkus.io/guides/kubernetes)): Generate Kubernetes resources from annotations

### Helm
To create new:
```shell
helm create helm
```

Check
```shell
helm lint ./helm
helm template ./helm
```

Install
```shell
helm install q-c ./helm
```

Uninstall
```shell
helm uninstall q-c
```

### Create docker image and push into local docker image repo
```shell
./gradlew clean build -x test
docker build -t call-collector:0.0.1 -f src/main/docker/Dockerfile.jvm .
docker tag call-collector:0.0.1 k3d-registry.localhost:5111/call-collector:0.0.1
docker push k3d-registry.localhost:5111/call-collector:0.0.1
```

