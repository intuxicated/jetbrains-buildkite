package buildkite.response;

public class AccessTokenResponse {
    private String uuid;
    private String[] scopes;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }
}
