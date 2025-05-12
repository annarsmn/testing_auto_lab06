package entities;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

public class User {
    private String login;
    private int id;

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

}
