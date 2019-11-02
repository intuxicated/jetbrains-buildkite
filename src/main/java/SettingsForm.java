import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class SettingsForm {
    private JPanel rootPanel;
    private JTextField accessTokenAPI;
    private JButton verifyAccessTokenButton;
    private JTextPane verifyAccessTokenResultTextPane;

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
