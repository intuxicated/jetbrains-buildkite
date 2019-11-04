package settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "BuildkiteConfig",
        storages = {@Storage("BuildkitePlugin.xml")}
)
public class BuildkiteSettings implements PersistentStateComponent<BuildkiteSettings> {

    private String accessTokenAPI = "";
    private String organization = "";

    public String getOrganization() { return organization; }

    public void setOrganization(String organization) { this.organization = organization; }

    public String getAccessTokenAPI() {
        return accessTokenAPI;
    }

    public void setAccessTokenAPI(String accessTokenAPI) {
        this.accessTokenAPI = accessTokenAPI;
    }

    @Nullable
    @Override
    public BuildkiteSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull BuildkiteSettings buildkiteSettings) {
        XmlSerializerUtil.copyBean(buildkiteSettings, this);
    }
}
