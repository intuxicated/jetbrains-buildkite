package settings;

import buildkite.response.OrganizationResponse;
import buildkite.response.PipelineResponse;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "BuildkiteConfig",
        storages = {@Storage("BuildkiteConfig.xml")}
)
public class BuildkiteSettings implements PersistentStateComponent<BuildkiteSettings> {
    private String accessTokenAPI = "";
    private OrganizationResponse organization;
    private PipelineResponse pipeline;

    public String getAccessTokenAPI() {
        return accessTokenAPI;
    }
    public void setAccessTokenAPI(String accessTokenAPI) {
        this.accessTokenAPI = accessTokenAPI;
    }

    public OrganizationResponse getOrganization() { return organization; }
    public void setOrganization(OrganizationResponse organization) { this.organization = organization; }

    public PipelineResponse getPipeline() { return pipeline; }
    public void setPipeline(PipelineResponse pipeline) { this.pipeline = pipeline; }

    public Boolean canRequestCreateBuild() {
        return !(getAccessTokenAPI().equals("")) &&
                (getOrganization() != null) &&
                (getPipeline() != null);
    }

    public static BuildkiteSettings getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, BuildkiteSettings.class);
    }

    @Nullable
    @Override
    public BuildkiteSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull BuildkiteSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
