package research.dresden.htw.moderationapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "members"
})
public class AppConfig {

    @JsonProperty("web_socke_uri")
    private String webSocketURI;

    public AppConfig() {
        // Dummy constructor or json parser
    }

    public AppConfig(String webSocketURI) {
        this.webSocketURI = webSocketURI;
    }

    /**
     *
     * @return
     * The web socket uri
     */
    @JsonProperty("web_socke_uri")
    public String getWebSocketURI() {
        return webSocketURI;
    }

    /**
     *
     * @param webSocketURI
     * The web socket uri
     */
    public void setWebSocketURI(String webSocketURI) {
        this.webSocketURI = webSocketURI;
    }
}
