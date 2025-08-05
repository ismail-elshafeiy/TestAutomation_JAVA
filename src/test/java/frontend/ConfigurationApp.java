package frontend;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public class ConfigurationApp {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox enableFeatureCheckbox;
    private JComboBox<String> themeComboBox;
    private JSpinner timeoutSpinner;
    private Properties configProperties;
    private final String CONFIG_FILE = "app_config.properties";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ConfigurationApp().initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        configProperties = new Properties();

        // Initialize UI components first
        createUI();

        // Then load configuration
        loadConfig();
    }

    private void createUI() {
        // Create main frame
        frame = new JFrame("Application Configuration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout(10, 10));

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username field
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        // Password field
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Enable feature checkbox
        formPanel.add(new JLabel("Enable Feature:"));
        enableFeatureCheckbox = new JCheckBox();
        formPanel.add(enableFeatureCheckbox);

        // Theme selection
        formPanel.add(new JLabel("Theme:"));
        String[] themes = {"Light", "Dark", "System Default"};
        themeComboBox = new JComboBox<>(themes);
        formPanel.add(themeComboBox);

        // Timeout setting
        formPanel.add(new JLabel("Timeout (seconds):"));
        SpinnerModel spinnerModel = new SpinnerNumberModel(30, 1, 300, 1);
        timeoutSpinner = new JSpinner(spinnerModel);
        formPanel.add(timeoutSpinner);

        frame.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton saveButton = new JButton("Save Configuration");
        saveButton.addActionListener(e -> {
            saveConfig();
            JOptionPane.showMessageDialog(frame, "Configuration saved successfully!");
        });

        JButton loadButton = new JButton("Load Configuration");
        loadButton.addActionListener(e -> {
            loadConfig();
            JOptionPane.showMessageDialog(frame, "Configuration loaded successfully!");
        });

        JButton resetButton = new JButton("Reset to Defaults");
        resetButton.addActionListener(e -> {
            resetToDefaults();
            JOptionPane.showMessageDialog(frame, "Reset to default values!");
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(resetButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void saveConfig() {
        configProperties.setProperty("username", usernameField.getText());
        configProperties.setProperty("password", new String(passwordField.getPassword()));
        configProperties.setProperty("enableFeature", String.valueOf(enableFeatureCheckbox.isSelected()));
        configProperties.setProperty("theme", (String) themeComboBox.getSelectedItem());
        configProperties.setProperty("timeout", timeoutSpinner.getValue().toString());
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            configProperties.store(output, "Application Configuration");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving configuration: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            configProperties.load(input);
            updateFieldsFromProperties();
        } catch (IOException e) {
            // File doesn't exist or can't be read - use defaults
            resetToDefaults();
        }
    }

    private void updateFieldsFromProperties() {
        usernameField.setText(configProperties.getProperty("username", ""));
        passwordField.setText(configProperties.getProperty("password", ""));
        enableFeatureCheckbox.setSelected(Boolean.parseBoolean(
                configProperties.getProperty("enableFeature", "false")));
        themeComboBox.setSelectedItem(configProperties.getProperty("theme", "Light"));
        timeoutSpinner.setValue(Integer.parseInt(configProperties.getProperty("timeout", "30")));
    }

    private void resetToDefaults() {
        configProperties.setProperty("username", "");
        configProperties.setProperty("password", "");
        configProperties.setProperty("enableFeature", "false");
        configProperties.setProperty("theme", "Light");
        configProperties.setProperty("timeout", "30");
        updateFieldsFromProperties();
    }
}