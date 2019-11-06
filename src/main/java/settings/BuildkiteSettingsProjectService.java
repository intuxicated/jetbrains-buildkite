package settings;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class BuildkiteSettingsProjectService {
    private final Project project;

    public BuildkiteSettingsProjectService(Project project) {
        this.project = project;
    }

    public static BuildkiteSettingsProjectService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, BuildkiteSettingsProjectService.class);
    }
}
