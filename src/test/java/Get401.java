import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

public class Get401 extends BaseClass {
    CloseableHttpClient client;
    CloseableHttpResponse response;

    @DataProvider
    private Object[][] endpoints() {
        return new Object[][]{
                {"/user"},
                {"/user/followers"},
                {"/notifications"}
        };
    }

    @BeforeMethod
    public void setup() {
        client = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void closeResources() throws IOException {
        client.close();
        response.close();
    }

    @Test(dataProvider = "endpoints")
    public void userUrlReturn401(String endpoint) throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + endpoint);
        response = client.execute(get);
        int actualStatus = response.getStatusLine().getStatusCode();
        assertEquals(actualStatus, 401);
    }
//
//    @Test
//    public void userFollowersUrlReturn401() throws IOException {
//        HttpGet get = new HttpGet(BASE_ENDPOINT + "/user/followers");
//        response = client.execute(get);
//        int actualStatus = response.getStatusLine().getStatusCode();
//        assertEquals(actualStatus, 401);
//    }
//
//    @Test
//    public void notificationsUrlReturn401() throws IOException {
//        HttpGet get = new HttpGet(BASE_ENDPOINT + "/notifications");
//        response = client.execute(get);
//        int actualStatus = response.getStatusLine().getStatusCode();
//        assertEquals(actualStatus, 401);
//    }
}
