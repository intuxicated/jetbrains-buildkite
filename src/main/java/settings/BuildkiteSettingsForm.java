package settings;

import javax.swing.*;

public class BuildkiteSettingsForm {
    private JPanel rootPanel;
    private JTextField accessTokenAPI;
    private JButton verifyAccessTokenButton;
    private JTextPane verifyAccessTokenResultTextPane;

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
