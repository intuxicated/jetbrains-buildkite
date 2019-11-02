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
        loadAccessTokenAPIFromSetting();
        return settingForm.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return !Comparing.equal(settingForm.getAccessTokenAPIText(), buildkiteSettings.getAccessTokenAPI());
    }

    @Override
    public void apply() throws ConfigurationException {
        buildkiteSettings.setAccessTokenAPI(settingForm.getAccessTokenAPIText());
    }

    @Override
    public void reset() {
        loadAccessTokenAPIFromSetting();
    }

    @Override
    public void disposeUIResources() {
        settingForm = null;
    }

    private void loadAccessTokenAPIFromSetting()
    {
        settingForm.setAccessTokenAPIText(buildkiteSettings.getAccessTokenAPI());
    }
}
