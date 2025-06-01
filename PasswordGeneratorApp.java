package PasswordGenerator;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.util.Random;

public class PasswordGeneratorApp extends Frame implements ActionListener {

    Label headingLabel, nameLabel, colorLabel, numberLabel, lengthLabel, passwordLabel, strengthLabel;
    TextField nameField, colorField, numberField, lengthField;
    TextField passwordField1, passwordField2, passwordField3;
    Button generateButton, clearButton;
    Button copyButton1, copyButton2, copyButton3;

    public PasswordGeneratorApp() {
        // Frame properties
        setTitle("Password Generator Pro Max - v2");
        setSize(600, 650);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setBackground(new Color(230, 245, 255));

        // Fonts
        Font headingFont = new Font("Arial", Font.BOLD, 22);
        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        // Heading
        headingLabel = new Label("Secure Password Generator");
        headingLabel.setFont(headingFont);
        headingLabel.setForeground(new Color(0, 51, 102));

        // Initialize components
        nameLabel = new Label("Name:");
        colorLabel = new Label("Favorite Color:");
        numberLabel = new Label("Lucky Number:");
        lengthLabel = new Label("Password Length:");
        passwordLabel = new Label("Generated Passwords:");
        strengthLabel = new Label("");

        nameLabel.setFont(labelFont);
        colorLabel.setFont(labelFont);
        numberLabel.setFont(labelFont);
        lengthLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        strengthLabel.setFont(new Font("Arial", Font.BOLD, 14));

        nameField = new TextField();
        colorField = new TextField();
        numberField = new TextField();
        lengthField = new TextField();

        passwordField1 = new TextField();
        passwordField2 = new TextField();
        passwordField3 = new TextField();
        passwordField1.setEditable(false);
        passwordField2.setEditable(false);
        passwordField3.setEditable(false);

        generateButton = new Button("Generate Passwords");
        clearButton = new Button("Clear");

        copyButton1 = new Button("Copy 1");
        copyButton2 = new Button("Copy 2");
        copyButton3 = new Button("Copy 3");

        generateButton.setFont(labelFont);
        clearButton.setFont(labelFont);
        copyButton1.setFont(labelFont);
        copyButton2.setFont(labelFont);
        copyButton3.setFont(labelFont);

        // Set bounds
        headingLabel.setBounds(140, 40, 320, 30);

        nameLabel.setBounds(50, 100, 120, 20);
        nameField.setBounds(200, 100, 300, 20);

        colorLabel.setBounds(50, 140, 120, 20);
        colorField.setBounds(200, 140, 300, 20);

        numberLabel.setBounds(50, 180, 120, 20);
        numberField.setBounds(200, 180, 300, 20);

        lengthLabel.setBounds(50, 220, 120, 20);
        lengthField.setBounds(200, 220, 300, 20);

        generateButton.setBounds(120, 270, 170, 30);
        clearButton.setBounds(320, 270, 80, 30);

        passwordLabel.setBounds(50, 320, 200, 20);

        passwordField1.setBounds(50, 360, 400, 25);
        copyButton1.setBounds(470, 360, 80, 25);

        passwordField2.setBounds(50, 400, 400, 25);
        copyButton2.setBounds(470, 400, 80, 25);

        passwordField3.setBounds(50, 440, 400, 25);
        copyButton3.setBounds(470, 440, 80, 25);

        strengthLabel.setBounds(50, 500, 400, 25);
        strengthLabel.setForeground(Color.BLUE);

        // Add components
        add(headingLabel);
        add(nameLabel); add(nameField);
        add(colorLabel); add(colorField);
        add(numberLabel); add(numberField);
        add(lengthLabel); add(lengthField);
        add(generateButton); add(clearButton);
        add(passwordLabel);

        add(passwordField1); add(copyButton1);
        add(passwordField2); add(copyButton2);
        add(passwordField3); add(copyButton3);

        add(strengthLabel);

        // Add action listeners
        generateButton.addActionListener(this);
        clearButton.addActionListener(this);

        copyButton1.addActionListener(this);
        copyButton2.addActionListener(this);
        copyButton3.addActionListener(this);

        // Window close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            generatePasswords();
        } else if (e.getSource() == clearButton) {
            clearFields();
        } else if (e.getSource() == copyButton1) {
            copyToClipboard(passwordField1.getText());
        } else if (e.getSource() == copyButton2) {
            copyToClipboard(passwordField2.getText());
        } else if (e.getSource() == copyButton3) {
            copyToClipboard(passwordField3.getText());
        }
    }

    private void generatePasswords() {
        String name = nameField.getText().trim();
        String color = colorField.getText().trim();
        String number = numberField.getText().trim();
        String lengthText = lengthField.getText().trim();

        // Check for empty fields
        if (name.isEmpty() || color.isEmpty() || number.isEmpty() || lengthText.isEmpty()) {
            strengthLabel.setText("Please fill all fields!");
            return;
        }

        // Validate name (alphabets only)
        if (!name.matches("[a-zA-Z]+")) {
            strengthLabel.setText("Name must contain only alphabets!");
            return;
        }

        // Validate color (alphabets only)
        if (!color.matches("[a-zA-Z]+")) {
            strengthLabel.setText("Favorite Color must contain only alphabets!");
            return;
        }

        // Validate number (digits only)
        if (!number.matches("\\d+")) {
            strengthLabel.setText("Lucky Number must contain only digits!");
            return;
        }

        // Validate password length
        int length;
        try {
            length = Integer.parseInt(lengthText);
            if (length < 6) {
                strengthLabel.setText("Password length should be at least 6!");
                return;
            }
        } catch (NumberFormatException ex) {
            strengthLabel.setText("Password length must be a number!");
            return;
        }

        // Generate passwords
        String password1 = createPassword(name, color, number, length);
        String password2 = createPassword(name, color, number, length);
        String password3 = createPassword(name, color, number, length);

        passwordField1.setText(password1);
        passwordField2.setText(password2);
        passwordField3.setText(password3);

        checkStrength(password1);  // Check the strength of the first password
    }

    private String createPassword(String name, String color, String number, int targetLength) {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(color);
        sb.append(number);

        sb.reverse();

        Random rand = new Random();
        char[] specialChars = {'@', '#', '$', '%', '^', '&', '*'};

        for (int i = 0; i < 4; i++) {
            int index = rand.nextInt(sb.length());
            char specialChar = specialChars[rand.nextInt(specialChars.length)];
            sb.insert(index, specialChar);
        }

        while (sb.length() < targetLength) {
            char randomChar = (char) (rand.nextInt(26) + 'a');
            sb.append(randomChar);
        }

        if (sb.length() > targetLength) {
            sb.setLength(targetLength);
        }

        return sb.toString();
    }

    private void checkStrength(String password) {
        int length = password.length();
        boolean hasSymbol = password.matches(".*[@#$%^&*].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");

        if (length >= 14 && hasSymbol && hasDigit && hasUppercase && hasLowercase) {
            strengthLabel.setText("Strength: Very Strong 💪");
        } else if (length >= 10 && hasSymbol && (hasUppercase || hasLowercase)) {
            strengthLabel.setText("Strength: Strong 👍");
        } else if (length >= 8) {
            strengthLabel.setText("Strength: Medium ⚡");
        } else {
            strengthLabel.setText("Strength: Weak ❗");
        }
    }

    private void clearFields() {
        nameField.setText("");
        colorField.setText("");
        numberField.setText("");
        lengthField.setText("");
        passwordField1.setText("");
        passwordField2.setText("");
        passwordField3.setText("");
        strengthLabel.setText("");
    }

    private void copyToClipboard(String text) {
        if (!text.isEmpty()) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(text);
            clipboard.setContents(selection, null);
            strengthLabel.setText("Copied to Clipboard ✅");
        }
    }

    public static void main(String[] args) {
        new PasswordGeneratorApp();
    }
}