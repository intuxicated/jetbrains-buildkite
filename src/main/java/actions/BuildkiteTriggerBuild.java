package actions;

import buildkite.BuildkiteService;
import buildkite.request.BuildRequest;
import buildkite.response.BuildResponse;
import com.intellij.notification.Notification;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.options.ShowSettingsUtil;
import notification.BuildkiteNotification;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import settings.BuildkiteSettingsConfigurable;
import settings.BuildkiteSettings;

public class BuildkiteTriggerBuild extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        BuildkiteSettings buildkiteSettings = BuildkiteSettings.getInstance(project);
        Logger logger = Logger.getInstance(this.getClass());

        if (!buildkiteSettings.canRequestCreateBuild()) {
            ShowSettingsUtil showSettingsUtil = ShowSettingsUtil.getInstance();
            showSettingsUtil.showSettingsDialog(project, BuildkiteSettingsConfigurable.class);
            return;
        }


        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Creating a Build") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                indicator.setIndeterminate(false);
                BuildkiteService buildkiteService = BuildkiteService.getInstance(buildkiteSettings.getAccessTokenAPI());
                indicator.setFraction(0.20);
                indicator.setText("Prepare Build Request");
                BuildRequest buildRequest = new BuildRequest();
                buildRequest.setBranch("master");
                buildRequest.setCommit("HEAD");
                indicator.setFraction(0.40);
                indicator.setText("Sending Build Request");
                buildkiteService.createBuild(
                        buildkiteSettings.getOrganization().getSlug(),
                        buildkiteSettings.getPipeline().getSlug(),
                        buildRequest
                ).enqueue(new Callback<BuildResponse>() {
                    @Override
                    public void onResponse(Call<BuildResponse> call, Response<BuildResponse> response) {
                        indicator.setFraction(0.80);
                        indicator.setText("Processing Response");

                        if (!response.isSuccessful()) {
                            indicator.setFraction(1.0);
                            indicator.setText("Finished");
                            logger.error(response.raw());
                        }

                        BuildResponse buildResponse = response.body();
                        Notification notification = BuildkiteNotification.info(
                                "Build Created!\n" +
                                "Build Id: " + buildResponse.getNumber().toString()
                        );
                        Notifications.Bus.notify(notification, project);
                        indicator.setFraction(1.0);
                        indicator.setText("Finished");
                    }

                    @Override
                    public void onFailure(Call<BuildResponse> call, Throwable t) {
                        indicator.setFraction(1.0);
                        indicator.setText("Finished");
                        logger.error(t);
                    }
                });
            }
        });
    }
}
