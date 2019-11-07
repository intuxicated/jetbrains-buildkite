package settings;

import buildkite.response.PipelineResponse;
import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
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
    private List<OrganizationResponse> organizationResponseList = Collections.emptyList();
    private List<PipelineResponse> pipelineResponsesList = Collections.emptyList();

    public List<OrganizationResponse> getOrganizationResponseList() {
        return organizationResponseList;
    }

    public void setOrganizationResponseList(List<OrganizationResponse> organizationResponseList) {
        this.organizationResponseList = organizationResponseList;
    }

    public List<PipelineResponse> getPipelineResponsesList() {
        return pipelineResponsesList;
    }

    public void setPipelineResponsesList(List<PipelineResponse> pipelineResponsesList) {
        this.pipelineResponsesList = pipelineResponsesList;
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

    public static BuildkiteSettingsCache getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, BuildkiteSettingsCache.class);
    }
}
