package settings;

import buildkite.BuildkiteService;
import buildkite.response.AccessTokenResponse;
import com.intellij.openapi.diagnostic.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuildkiteSettingsForm {
    private JPanel rootPanel;
    private JTextField accessTokenAPIField;
    private JButton verifyAccessTokenButton;
    private JLabel verifyAccessTokenResultTextPane;
    private JTextField organizationField;
    private Logger logger = Logger.getInstance(BuildkiteSettingsForm.class);

    public BuildkiteSettingsForm() {
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

    public String getOrganization() {
        return organizationField.getText();
    }

    public void setOrganization(String organization) {
        organizationField.setText(organization);
    }
}
