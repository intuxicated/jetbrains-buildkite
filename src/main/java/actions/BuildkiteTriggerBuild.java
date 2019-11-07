package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.options.ShowSettingsUtil;
import settings.BuildkiteSettingsConfigurable;
import settings.BuildkiteSettings;

public class BuildkiteTriggerBuild extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        BuildkiteSettings buildkiteSettings = BuildkiteSettings.getInstance(event.getProject());

        if (buildkiteSettings.getAccessTokenAPI().equals("")) {
            ShowSettingsUtil showSettingsUtil = ShowSettingsUtil.getInstance();
            showSettingsUtil.showSettingsDialog(null, BuildkiteSettingsConfigurable.class);
            return;
        }

        Messages.showMessageDialog("message", "Information", Messages.getInformationIcon());
    }
}
