package settings;

import buildkite.BuildkiteService;
import buildkite.response.AccessTokenResponse;
import com.intellij.openapi.diagnostic.Logger;
import jdk.internal.jline.internal.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuildkiteSettingsForm {
    private JPanel rootPanel;
    private JTextField accessTokenAPI;
    private JButton verifyAccessTokenButton;
    private JLabel verifyAccessTokenResultTextPane;
    private Logger logger = Logger.getInstance(BuildkiteSettingsForm.class);

    public BuildkiteSettingsForm() {
        verifyAccessTokenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                verifyAccessTokenButton.setEnabled(false);
                verifyAccessTokenResultTextPane.setText("");
                BuildkiteService buildkiteService = BuildkiteService.getInstance(accessTokenAPI.getText());
                buildkiteService.getAccessToken().enqueue(new Callback<AccessTokenResponse>() {
                    @Override
                    public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                        if (response.isSuccessful()) {
                            verifyAccessTokenResultTextPane.setText("Access Token API is VALID");
                            verifyAccessTokenResultTextPane.setForeground(Color.GREEN);
                        } else {
                            logger.error(response.body());
                            verifyAccessTokenResultTextPane.setText("Access Token API is INVALID");
                            verifyAccessTokenResultTextPane.setForeground(Color.RED);
                        }
                        verifyAccessTokenButton.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                        verifyAccessTokenButton.setEnabled(true);
                        logger.error(t);
                    }
                });
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public String getAccessTokenAPIText() {
        return accessTokenAPI.getText();
    }

    public void setAccessTokenAPIText(String accessTokenAPIText) {
        accessTokenAPI.setText(accessTokenAPIText);
    }
}
