package settings;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import buildkite.BuildkiteService;
import buildkite.response.AccessTokenResponse;
import buildkite.response.OrganizationResponse;

public class BuildkiteSettingsForm {
    private JPanel rootPanel;
    private JTextField accessTokenAPIField;
    private JButton verifyAccessTokenButton;
    private JLabel verifyAccessTokenResultTextPane;
    private JComboBox organizationComboBox;
    private JButton organizationRefreshButton;
    private List<OrganizationResponse> organizationResponseList;
    private Logger logger = Logger.getInstance(BuildkiteSettingsForm.class);
    private final BuildkiteSettingsProjectCacheService buildkiteSettingsCache;

    public BuildkiteSettingsForm(Project project) {
        buildkiteSettingsCache = BuildkiteSettingsProjectCacheService.getInstance(project);
        verifyAccessTokenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                verifyAccessTokenButton.setEnabled(false);
                verifyAccessTokenResultTextPane.setText("");
                BuildkiteService buildkiteService = BuildkiteService.getInstance(accessTokenAPIField.getText());
                buildkiteService.getAccessToken().enqueue(new Callback<AccessTokenResponse>() {
                    @Override
                    public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                        if (response.isSuccessful()) {
                            verifyAccessTokenResultTextPane.setText("Access Token API is VALID");
                            verifyAccessTokenResultTextPane.setForeground(Color.GREEN);
                        } else {
                            verifyAccessTokenResultTextPane.setText("Access Token API is INVALID");
                            verifyAccessTokenResultTextPane.setForeground(Color.RED);
                        }
                        verifyAccessTokenButton.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                        logger.error(t);
                        verifyAccessTokenResultTextPane.setText("Unable to verify Access Token API");
                        verifyAccessTokenButton.setEnabled(true);
                    }
                });
            }
        });

        organizationRefreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                organizationRefreshButton.setEnabled(false);
                organizationComboBox.removeAllItems();
                BuildkiteService buildkiteService = BuildkiteService.getInstance(accessTokenAPIField.getText());
                buildkiteService.getOrganizations().enqueue(new Callback<List<OrganizationResponse>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<OrganizationResponse>> call, @NotNull Response<List<OrganizationResponse>> response) {
                        organizationComboBox.removeAllItems();
                        if (response.isSuccessful()) {
                            List<OrganizationResponse> organizationResponseList = response.body();
                            setOrganizationList(organizationResponseList);
                            buildkiteSettingsCache.setOrganizationResponseList(organizationResponseList);
                        } else {
                            logger.error(response.raw());
                        }
                        organizationRefreshButton.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<List<OrganizationResponse>> call, Throwable t) {
                        logger.error(t);
                        organizationComboBox.removeAllItems();
                        organizationRefreshButton.setEnabled(true);
                    }
                });
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public String getAccessTokenAPI() {
        return accessTokenAPIField.getText();
    }

    public void setAccessTokenAPI(String accessTokenAPI) {
        accessTokenAPIField.setText(accessTokenAPI);
    }

    public OrganizationResponse getOrganization() {
        return (OrganizationResponse)organizationComboBox.getSelectedItem();
    }

    public void setOrganization(OrganizationResponse organization) {
        organizationComboBox.setSelectedItem(organization);
    }

    public List<OrganizationResponse> getOrganizationResponseList() {
        return organizationResponseList;
    }

    public void setOrganizationList(List<OrganizationResponse> organizationResponseList) {
        if (!organizationResponseList.isEmpty()) {
            organizationComboBox.removeAllItems();
            organizationResponseList.forEach(organizationResponse -> organizationComboBox.addItem(organizationResponse));
            this.organizationResponseList = organizationResponseList;
        }
    }
}
