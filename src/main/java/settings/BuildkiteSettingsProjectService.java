package settings;

import buildkite.response.OrganizationResponse;
import buildkite.response.PipelineResponse;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class BuildkiteSettingsProjectService {
    private OrganizationResponse organization;
    private PipelineResponse pipeline;

    public OrganizationResponse getOrganization() { return organization; }
    public void setOrganization(OrganizationResponse organization) { this.organization = organization; }

    public PipelineResponse getPipeline() { return pipeline; }
    public void setPipeline(PipelineResponse pipeline) { this.pipeline = pipeline; }

    public static BuildkiteSettingsProjectService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, BuildkiteSettingsProjectService.class);
    }
}
