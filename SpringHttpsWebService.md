在 Spring 项目中使用 `WebServiceTemplate` 调用基于 HTTPS 的 SOAP Web Service，并使用 PFX 文件进行客户端认证，可以通过以下步骤实现：

### 1. 配置 HTTPS 证书
首先，需要将 PFX 文件导入到 Java 的信任库（TrustStore）或密钥库（KeyStore），并在 Spring Boot 中配置 SSL 相关的属性。

假设你的 PFX 文件位于 `src/main/resources/certs/` 目录下，文件名为 `client-cert.pfx`，密码为 `password`。

### 2. 配置 `WebServiceTemplate` 与 SSL
在 Spring Boot 项目中，可以通过配置 SSL 上下文来加载 PFX 文件，并将其应用于 `WebServiceTemplate`。

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class WebServiceConfig {

    @Bean
    public WebServiceTemplate webServiceTemplate() throws Exception {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setDefaultUri("https://example.com/service");

        // Load the PFX file as a KeyStore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (InputStream keyStoreStream = getClass().getResourceAsStream("/certs/client-cert.pfx")) {
            keyStore.load(keyStoreStream, "password".toCharArray());
        }

        // Initialize SSL context with the KeyStore
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "password".toCharArray())
                .build();

        // Create an HttpClient with the SSL context
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        webServiceTemplate.setRequestFactory(requestFactory);

        return webServiceTemplate;
    }
}
```

### 3. 调用 SOAP Web Service
配置完成后，你可以使用配置好的 `WebServiceTemplate` 进行 HTTPS 调用：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoapClientService {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public Object callWebService(Object request) {
        return webServiceTemplate.marshalSendAndReceive(request);
    }
}
```

### 关键点
- `KeyStore`：将 PFX 文件加载到 Java 的 `KeyStore` 中，以便客户端认证。
- `SSLContext`：使用加载的 `KeyStore` 创建 SSL 上下文，用于 HTTPS 连接。
- `HttpClient`：将 SSL 上下文应用到 `HttpClient`，并将其配置到 `WebServiceTemplate`。

通过这种配置，你可以安全地使用 HTTPS 调用远程 SOAP Web Service。