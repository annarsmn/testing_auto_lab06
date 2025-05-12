import org.apache.http.entity.ContentType;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.example.ResponseUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.example.ResponseUtils.getHeader;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ResponseHeaders extends BaseClass {
    CloseableHttpClient client;
    CloseableHttpResponse response;

    @BeforeMethod
    public void setup() {
        client = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void closeResources() throws IOException {
        client.close();
        response.close();
    }

    @Test
    public void contentTypeAndEncoding() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);
        Header contentType = response.getEntity().getContentType();
        assertEquals(contentType.getValue(), "application/json; charset=utf-8");
    }

    @Test
    public void contentType() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);
        ContentType ct = ContentType.getOrDefault(response.getEntity());
        assertEquals(ct.getMimeType(), "application/json");
    }

    @Test
    public void serverHeader() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);
        String headerValue = getHeader(response, "Server");
        assertEquals(headerValue, "github.com");
    }

    @Test
    public void xRateLimitIsSixty() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);

        String limitVal = ResponseUtils.getHeaderJava8Way(response, "X-RateLimit-Limit");
        assertEquals(limitVal, "60");
    }

    @Test
    public void ETagPresence() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);

        Boolean headerIsPresent = ResponseUtils.headerIsPresent(response, "ETag");
        assertTrue(headerIsPresent);
    }

}
