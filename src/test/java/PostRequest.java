import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

import static org.testng.Assert.assertEquals;

public class PostRequest extends BaseClass {
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
    public void createRepoReturns201() throws IOException {
        HttpPost request = new HttpPost("https://api.github.com/user/repos");

        String auth = "annarsmn:token"; // Replace with actual credentials
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);

        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"name\": \"deleteMe\"}";
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        response = client.execute(request);

        int actualStatusCode = response.getStatusLine().getStatusCode();
        assertEquals(actualStatusCode, 201);
    }
}
