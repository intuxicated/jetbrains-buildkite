package settings;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collections;
import java.util.List;

import buildkite.response.OrganizationResponse;

@State(
        name = "BuildkiteConfigCache",
        storages = {@Storage("BuildkitePluginCache.xml")}
)
public class BuildkiteSettingsCache implements PersistentStateComponent<BuildkiteSettingsCache> {
    private List<OrganizationResponse> organizationResponseList = Collections.<OrganizationResponse>emptyList();;

    public List<OrganizationResponse> getOrganizationResponseList() {
        return organizationResponseList;
    }

    public void setOrganizationResponseList(List<OrganizationResponse> organizationResponseList) {
        this.organizationResponseList = organizationResponseList;
    }

    @Nullable
    @Override
    public BuildkiteSettingsCache getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull BuildkiteSettingsCache buildkiteSettingsCache) {
        XmlSerializerUtil.copyBean(buildkiteSettingsCache, this);
    }

    public static BuildkiteSettingsCache getInstance() {
        return ServiceManager.getService(BuildkiteSettingsCache.class);
    }
}
