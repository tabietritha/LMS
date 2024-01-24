package lmsPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

    public class SignUpPage extends JFrame {

        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton signUpButton, back;

        public SignUpPage() {
            setTitle("Sign Up Page");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            add(panel);
            placeComponents(panel);

            setVisible(true);
        }

        private void placeComponents(JPanel panel) {
            panel.setLayout(null);
            panel.setBackground(Color.cyan);

            JLabel jLabel = new JLabel("Enter your SignUp details");
            jLabel.setFont(new Font("Arial", Font.BOLD, 28));
            jLabel.setBounds(500, 150, 480, 45);
            panel.add(jLabel);

            JLabel userLabel = new JLabel("Username:");
            userLabel.setBounds(400, 250, 180, 45);
            userLabel.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(userLabel);

            usernameField = new JTextField(50);
            usernameField.setBounds(600, 250, 300, 45);
            panel.add(usernameField);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(400, 350, 180, 45);
            passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(passwordLabel);

            passwordField = new JPasswordField(20);
            passwordField.setBounds(600, 350, 300, 45);
            panel.add(passwordField);

            back = new JButton("Back");
            back.setBounds(500, 450, 100, 35);
            back.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(back);

            signUpButton = new JButton("Sign Up");
            signUpButton.setBounds(750, 450, 150, 35);
            signUpButton.setFont(new Font("Arial", Font.BOLD, 18));
            signUpButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    signUp();
                   new LoginPage();
                }
            });
            panel.add(signUpButton);
        }

        private void signUp() {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password_ = new String(passwordChars);

            // Database connection details
            String url = "jdbc:mysql://localhost:3306/lmsDatabase";
            String user = "root";
            String dbPassword = "Passage@1111";

            try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
                String query = "INSERT INTO signUp (username, password_) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password_);
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(this, "User successfully registered");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error registering user");
            }
        }
    }


