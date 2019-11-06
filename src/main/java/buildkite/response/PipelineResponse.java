package buildkite.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"url", "web_url", "description", "repository", "branch_configuration", "default_branch",
        "provider", "skip_queued_branch_builds", "skip_queued_branch_builds_filter", "cancel_running_branch_builds",
        "cancel_running_branch_builds_filter", "builds_url", "badge_url", "created_at", "scheduled_builds_count",
        "running_builds_count", "scheduled_jobs_count", "running_jobs_count", "waiting_jobs_count", "visibility", "env",
        "steps"
})
public class PipelineResponse {
    private String id;
    private String name;
    private String slug;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return this.name;
    }
}