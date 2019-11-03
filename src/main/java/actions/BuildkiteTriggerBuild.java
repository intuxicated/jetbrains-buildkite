package actions;

import buildkite.BuildkiteClient;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import java.io.IOException;

public class BuildkiteTriggerBuild extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
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
