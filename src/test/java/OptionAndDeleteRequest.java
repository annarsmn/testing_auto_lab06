import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.example.ResponseUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class OptionAndDeleteRequest extends BaseClass {
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
    public void optionsReturnsCorrectMethodsList() throws IOException {

        String header = "Access-Control-Allow-Methods";
        String expectedReply = "GET, POST, PATCH, PUT, DELETE";

        HttpOptions request = new HttpOptions(BASE_ENDPOINT);
        response = client.execute(request);

        String actualValue = ResponseUtils.getHeader(response, header);
        assertEquals(actualValue, expectedReply);
    }

// delete - https://github.com/annarsmn/deleteMe2
    @Test
    public void deleteRepoWithAuth() throws Exception {

        HttpDelete request = new HttpDelete("https://api.github.com/repos/annarsmn/test_rep_to_delete");

        request.setHeader(HttpHeaders.AUTHORIZATION, "token {token}");
        response = client.execute(request);

        int actualStatusCode = response.getStatusLine().getStatusCode();
        assertEquals(actualStatusCode, 204);
    }
}
