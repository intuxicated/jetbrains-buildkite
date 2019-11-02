import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SettingsConfigurable implements SearchableConfigurable {

    private SettingsForm settingForm;

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
        settingForm = new SettingsForm();
        return settingForm.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {
        settingForm = null;
    }
}
