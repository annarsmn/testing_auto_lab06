package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RateLimit {
    public int coreLimit = 0;
    public String searchLimit = "";

    public int getCoreLimit() {
        return coreLimit;
    }

    public String getSearchLimit() {
        return searchLimit;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("resources")
    public void unmarshallNested(Map<String, Object> resources){

        Map<String, Integer> core = (Map<String, Integer>) resources.get("core");
        coreLimit = core.get("limit");

        Map<String, String> search = (Map<String, String>) resources.get("search");
        searchLimit = String.valueOf(search.get("limit"));
    }
}
