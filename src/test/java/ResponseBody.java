import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import entities.NotFound;
import entities.RateLimit;
import entities.User;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static org.example.ResponseUtils.unmarshallGeneric;
import static org.testng.Assert.assertEquals;

public class ResponseBody extends BaseClass {
    CloseableHttpClient client;
    CloseableHttpResponse response;

    public ResponseBody() throws IOException {
    }

    private User unmarshall(CloseableHttpResponse response, Class<User> clazz) throws IOException {
        String jsonBody = EntityUtils.toString(response.getEntity());
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(jsonBody, clazz);
    }

    private Object getValueFor(JSONObject jsonObject, String key){
        return jsonObject.get(key);
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

    @Test
    public void bodyJson() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/users/annarsmn");
        response = client.execute(get);
        String jsonBody = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = new JSONObject(jsonBody);
        String loginValue = (String) getValueFor(jsonObject, "login");
    }

    @Test
    public void returnsCorrectId() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/users/annarsmn");

        response = client.execute(get);

        String jsonBody = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = new JSONObject(jsonBody);

        Integer idValue = (Integer) getValueFor(jsonObject, "id");

        assertEquals(idValue, Integer.valueOf(158623014));
    }

    @Test
    public void returnsLoginUnmarshall() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/users/annarsmn");
        response = client.execute(get);

        User user = unmarshall(response, User.class);
        assertEquals(user.getLogin(), "annarsmn");
    }

    @Test
    public void returnsIdUnmarshall() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/users/annarsmn");
        response = client.execute(get);

        User user = unmarshall(response, User.class);
        assertEquals(user.getId(), 158623014);
    }

    @Test
    public void notFoundBody() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/nonex");
        response = client.execute(get);

        NotFound user = unmarshallGeneric(response, NotFound.class);
        assertEquals(user.getMessage(), "Not Found");
    }


//    @Test
//    public void nestedJson() throws IOException {
//        HttpGet get = new HttpGet(BASE_ENDPOINT + "/nonex");
//        response = client.execute(get);
//        System.out.println(EntityUtils.toString(response.getEntity()));
//        Map<String, Object> responseMap = unmarshallGeneric(response, Map.class);
//        RateLimit rateLimit = new RateLimit();
//        rateLimit.unmarshallNested(responseMap);
//
//        assertEquals(rateLimit.getCoreLimit(), 60);
//    }
}
