package lmsPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public class LoginPage extends JFrame {

        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton loginButton, back,signup;

        public LoginPage() {
            setTitle("Login Page");
            setSize(300, 150);
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
            ImageIcon sideImage = new ImageIcon("src/lmsPack/");
            JLabel imageLabel = new JLabel(sideImage);
            panel.add(imageLabel, BorderLayout.WEST);

            JLabel jLabel = new JLabel("User's Login details");
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

            passwordField = new JPasswordField(50);
            passwordField.setBounds(600, 350, 300, 45);
            panel.add(passwordField);

            back = new JButton("Back");
            back.setBounds(500, 450, 100, 35);
            back.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(back);
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   dispose();
                    new SignUpPage();
                }
            });
            JLabel signuplabel =new JLabel("First time check in here?");
            signuplabel.setBounds(500, 500, 350, 35);
            signuplabel.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(signuplabel);

            signup = new JButton("SignUp");
            signup.setBounds(780, 500, 100, 35);
            signuplabel.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(signup);

            signup.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new SignUpPage();
                }
            });
            loginButton = new JButton("Login");
            loginButton.setBounds(750, 450, 150, 35);
            loginButton.setFont(new Font("Arial", Font.BOLD, 18));
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    login();
                    new BookSearch();
                }
            });
            panel.add(loginButton);
        }

        private void login() {
            String enteredUsername = usernameField.getText();
            char[] enteredPasswordChars = passwordField.getPassword();
            String enteredPassword = new String(enteredPasswordChars);

            // Database connection details
            String url = "jdbc:mysql://localhost:3306/lmsDatabase";
            String user = "root";
            String dbPassword = "Passage@1111";

            try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
                String query = "SELECT * FROM signUp WHERE username = ? AND password_ = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, enteredUsername);
                    preparedStatement.setString(2, enteredPassword);

                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(this, "Login successful");
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid username or password");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during login");
            }
        }

       /* public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new LoginPage();
                }
            });
        }*/
    }

