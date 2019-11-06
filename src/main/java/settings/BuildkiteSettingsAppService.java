package settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import buildkite.response.OrganizationResponse;

@State(
        name = "BuildkiteConfig",
        storages = {@Storage("BuildkitePluginApp.xml")}
)
public class BuildkiteSettingsAppService implements PersistentStateComponent<BuildkiteSettingsAppService> {
    private String accessTokenAPI = "";

    public String getAccessTokenAPI() {
        return accessTokenAPI;
    }
    public void setAccessTokenAPI(String accessTokenAPI) {
        this.accessTokenAPI = accessTokenAPI;
    }

    @Nullable
    @Override
    public BuildkiteSettingsAppService getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull BuildkiteSettingsAppService buildkiteSettings) {
        XmlSerializerUtil.copyBean(buildkiteSettings, this);
    }

    public static BuildkiteSettingsAppService getInstance() {
        return ServiceManager.getService(BuildkiteSettingsAppService.class);
    }
}
