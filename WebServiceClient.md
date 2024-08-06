要在 IntelliJ IDEA 中使用 SpringBoot3 和远程的 WSDL 来生成客户端 WebService 代码，你可以按照以下步骤进行：

1. **添加依赖**：
   在你的 `build.gradle` 文件中，添加以下依赖来支持 JAX-WS：

    ```groovy
    dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-web-services'
        implementation 'jakarta.xml.ws:jakarta.xml.ws-api:3.0.1'
        implementation 'com.sun.xml.ws:jaxws-ri:3.0.1'
    }
    ```

2. **配置插件**：
   添加 `gradle-jaxb-plugin` 插件，用于生成客户端代码：

    ```groovy
    plugins {
        id 'org.unbroken-dome.xjc' version '2.0.0'
    }

    xjc {
        destinationDir = file("$buildDir/generated-sources/xjc")
        source = files("src/main/resources/wsdl")
        options {
            encoding = 'UTF-8'
        }
    }

    sourceSets {
        main {
            java {
                srcDir "$buildDir/generated-sources/xjc"
            }
        }
    }
    ```

3. **下载 WSDL 文件**：
   将远程的 WSDL 文件下载到项目的 `src/main/resources/wsdl` 目录中。

4. **运行生成代码任务**：
   在 IntelliJ IDEA 的终端中运行 `./gradlew xjc` 命令生成客户端代码。生成的代码将会位于 `build/generated-sources/xjc` 目录下。

5. **创建配置类**：
   创建一个 Spring 配置类来配置 WebService 客户端：

    ```java
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.ws.client.core.WebServiceTemplate;
    import org.springframework.ws.client.support.interceptor.ClientInterceptor;
    import org.springframework.ws.transport.http.HttpComponentsMessageSender;

    @Configuration
    public class WebServiceConfig {

        @Bean
        public WebServiceTemplate webServiceTemplate() {
            WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
            webServiceTemplate.setMessageSender(httpComponentsMessageSender());
            return webServiceTemplate;
        }

        @Bean
        public HttpComponentsMessageSender httpComponentsMessageSender() {
            HttpComponentsMessageSender sender = new HttpComponentsMessageSender();
            // 配置超时等属性
            return sender;
        }
    }
    ```

6. **调用 WebService**：
   使用生成的客户端代码调用 WebService。例如：

    ```java
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.ws.client.core.WebServiceTemplate;

    @Service
    public class MyWebServiceClient {

        @Autowired
        private WebServiceTemplate webServiceTemplate;

        public MyResponse callWebService(MyRequest request) {
            return (MyResponse) webServiceTemplate.marshalSendAndReceive(request);
        }
    }
    ```

通过这些步骤，你可以在 IntelliJ IDEA 中使用 SpringBoot3 和远程的 WSDL 来生成和调用客户端 WebService 代码。如果你在任何步骤中遇到问题，可以进一步提供详细信息，我可以提供更具体的帮助。