# Helidon Issue with ProxyFactory createProxyName

Simple project that shows an issue with Helidon 2.5.0 (also reproduced 2.4.2) with _ProxyFactory createProxyName_. 

Main things to look out for.

- JUnit5 test is used for running the application, the test will fail - but that is not relevant for the issue
- The project in the checked-in state will produce output similar to this upon `mvn clean verify`

```
[INFO] Running io.helidon.mapstruct.mp.MainTest
2022.04.13 13:12:35 INFO io.helidon.common.LogConfig Thread[main,5,main]: Logging at initialization configured using /home/jvissers/Projects/experiments/helidon-mapstruct-mp/target/classes/logging.properties
2022.04.13 13:12:35 INFO io.helidon.tracing.tracerresolver.TracerResolverBuilder Thread[main,5,main]: TracerResolver not configured, tracing is disabled
2022.04.13 13:12:35 WARNING io.helidon.microprofile.tracing.TracingCdiExtension Thread[main,5,main]: helidon-microprofile-tracing is on the classpath, yet there is no tracer implementation library. Tracing uses a no-op tracer. As a result, no tracing will be configured for WebServer and JAX-RS
2022.04.13 13:12:35 INFO io.helidon.microprofile.security.SecurityCdiExtension Thread[main,5,main]: Authentication provider is missing from security configuration, but security extension for microprofile is enabled (requires providers configuration at key security.providers). Security will not have any valid authentication provider
2022.04.13 13:12:35 INFO io.helidon.microprofile.security.SecurityCdiExtension Thread[main,5,main]: Authorization provider is missing from security configuration, but security extension for microprofile is enabled (requires providers configuration at key security.providers). ABAC provider is configured for authorization.
2022.04.13 13:12:36 INFO io.helidon.microprofile.server.ServerCdiExtension Thread[main,5,main]: Registering JAX-RS Application: HelidonMP
2022.04.13 13:12:36 INFO io.helidon.webserver.NettyWebServer Thread[nioEventLoopGroup-2-1,10,main]: Channel '@default' started: [id: 0x0dddef51, L:/[0:0:0:0:0:0:0:0]:35365]
2022.04.13 13:12:36 INFO io.helidon.microprofile.server.ServerCdiExtension Thread[main,5,main]: Server started on http://localhost:35365 (and all other host addresses) in 2533 milliseconds (since JVM startup).
2022.04.13 13:12:36 INFO io.helidon.common.HelidonFeatures Thread[features-thread,5,main]: Helidon MP 2.4.2 features: [CDI, Config, Fault Tolerance, Health, JAX-RS, Metrics, Open API, REST Client, Security, Server, Tracing]
___Decorating___ enclosing type
```

- Now go into the `beans.xml` and enable the line that was originally commented:
```
<!--        <class>io.helidon.mapstruct.mp.SourceTargetMapperDecorator</class>-->
```
- Additionally, uncommented the commented `@Decorator` annotation in the toplevel `SourceTargetMapperDecorator` class:
```
//@Decorator
abstract class SourceTargetMapperDecorator implements SourceTargetMapper {

```

- Rerun `mvn clean verify` which should now produce something like this:

```
[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 0.889 s <<< FAILURE! - in io.helidon.mapstruct.mp.MainTest
[ERROR] io.helidon.mapstruct.mp.MainTest  Time elapsed: 0.889 s  <<< ERROR!
java.lang.NullPointerException
Caused by: java.lang.NullPointerException: Cannot invoke "java.lang.Class.getName()" because "superInterface" is null
```