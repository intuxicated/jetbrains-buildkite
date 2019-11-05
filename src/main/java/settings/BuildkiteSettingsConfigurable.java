package settings;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.util.Comparing;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;

public class BuildkiteSettingsConfigurable implements SearchableConfigurable {

    private BuildkiteSettingsForm settingForm;
    private final BuildkiteSettings buildkiteSettings = ServiceManager.getService(BuildkiteSettings.class);
    private final BuildkiteSettingsCache buildkiteSettingsCache = BuildkiteSettingsCache.getInstance();

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
        settingForm = new BuildkiteSettingsForm();
        loadConfigurationIntoForm();
        return settingForm.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return !Comparing.equal(settingForm.getAccessTokenAPI(), buildkiteSettings.getAccessTokenAPI()) ||
                !Comparing.equal(settingForm.getOrganization(), buildkiteSettings.getOrganization());
    }

    @Override
    public void apply() throws ConfigurationException {
        buildkiteSettings.setAccessTokenAPI(settingForm.getAccessTokenAPI());
        buildkiteSettings.setOrganization(settingForm.getOrganization());
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
        settingForm.setOrganization(buildkiteSettings.getOrganization());
        settingForm.setAccessTokenAPI(buildkiteSettings.getAccessTokenAPI());
        settingForm.setOrganizationList(buildkiteSettingsCache.getOrganizationResponseList());
    }
}
