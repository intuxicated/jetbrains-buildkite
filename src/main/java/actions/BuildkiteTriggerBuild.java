package actions;

import buildkite.BuildkiteClient;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.options.ShowSettingsUtil;
import settings.BuildkiteSettings;
import settings.BuildkiteSettingsConfigurable;

import java.io.IOException;

public class BuildkiteTriggerBuild extends AnAction {

    private static BuildkiteSettings buildkiteSettings = BuildkiteSettings.getInstance();

    @Override
    public void actionPerformed(AnActionEvent event) {
        if (buildkiteSettings.getAccessTokenAPI().equals("")) {
            ShowSettingsUtil showSettingsUtil = ShowSettingsUtil.getInstance();
            showSettingsUtil.showSettingsDialog(null, BuildkiteSettingsConfigurable.class);
            return;
        }
        BuildkiteClient buildkiteClient = BuildkiteClient.getInstance();
        String message = "";
        try {
            message = buildkiteClient.getHelloWorld();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Messages.showMessageDialog( message, "Information", Messages.getInformationIcon());
    }
}
