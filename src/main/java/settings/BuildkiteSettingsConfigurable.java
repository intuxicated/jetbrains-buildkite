package settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;

public class BuildkiteSettingsConfigurable implements SearchableConfigurable {

    private BuildkiteSettingsForm settingForm;
    private final BuildkiteSettings buildkiteSettingsProject;
    private final BuildkiteSettingsCache buildkiteSettingsCache;
    private Project project;

    public BuildkiteSettingsConfigurable(@NotNull Project project) {
        this.project = project;
        buildkiteSettingsCache = BuildkiteSettingsCache.getInstance(project);
        buildkiteSettingsProject = BuildkiteSettings.getInstance(project);
    }

    @NotNull
    @Override
    public String getId() {
        return "buildkite.settings";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Buildkite Plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingForm = new BuildkiteSettingsForm(this.project);
        loadConfigurationIntoForm();
        return settingForm.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return !Comparing.equal(settingForm.getAccessTokenAPI(), buildkiteSettingsProject.getAccessTokenAPI()) ||
                !Comparing.equal(settingForm.getOrganization(), buildkiteSettingsProject.getOrganization()) ||
                !Comparing.equal(settingForm.getPipeline(), buildkiteSettingsProject.getPipeline());
    }

    @Override
    public void apply() throws ConfigurationException {
        buildkiteSettingsProject.setAccessTokenAPI(settingForm.getAccessTokenAPI());
        buildkiteSettingsProject.setOrganization(settingForm.getOrganization());
        buildkiteSettingsProject.setPipeline(settingForm.getPipeline());
    }

    @Override
    public void reset() {
        loadConfigurationIntoForm();
    }

    @Override
    public void disposeUIResources() {
        settingForm = null;
    }

    private void loadConfigurationIntoForm()
    {
        settingForm.setAccessTokenAPI(buildkiteSettingsProject.getAccessTokenAPI());
        settingForm.setOrganization(buildkiteSettingsProject.getOrganization());
        settingForm.setPipeline(buildkiteSettingsProject.getPipeline());
        settingForm.setOrganizationList(buildkiteSettingsCache.getOrganizationResponseList());
        settingForm.setPipelineList(buildkiteSettingsCache.getPipelineResponsesList());
    }
}
