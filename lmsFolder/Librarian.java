package lmsPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public class Librarian extends JFrame {

        private JTextField usernameField, idField;
        private JPasswordField passwordField;
        private JButton loginButton;

        public Librarian() {
            setTitle("librarian's  Login Page");
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

            JLabel jLabel = new JLabel("Librarian's Login details");
            jLabel.setFont(new Font("Arial", Font.BOLD, 28));
            jLabel.setBounds(500, 150, 480, 45);
            panel.add(jLabel);

            JLabel userLabel = new JLabel("Username:");
            userLabel.setBounds(400, 250, 180, 45);
            userLabel.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(userLabel);

            usernameField = new JTextField(50);
            usernameField.setBounds(600, 250, 300, 25);
            panel.add(usernameField);

            JLabel idLabel = new JLabel("id:");
            idLabel.setBounds(400, 350, 80, 25);
            idLabel.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(idLabel);

            idField = new JTextField(50);
            idField.setBounds(600, 350, 300, 25);
            panel.add(idField);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(400, 450, 150, 25);
            passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(passwordLabel);

            passwordField = new JPasswordField(50);
            passwordField.setBounds(600, 450, 300, 25);
            panel.add(passwordField);

            loginButton = new JButton("Login");
            loginButton.setBounds(650, 550, 150, 25);
            loginButton.setFont(new Font("Arial", Font.BOLD, 15));
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    login();
                    new addBooks();
                }
            });
            panel.add(loginButton);
        }

        private void login() {
            String enteredUsername = usernameField.getText();
            String enteredid =idField.getText();
            char[] enteredPasswordChars = passwordField.getPassword();
            String enteredPassword = new String(enteredPasswordChars);

            // Database connection details
            String url = "jdbc:mysql://localhost:3306/lmsDatabase";
            String user = "root";
            String dbPassword = "Passage@1111";

            try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
                String query = "SELECT * FROM librarian WHERE username = ? AND id = ? AND password_ = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, enteredUsername);
                    preparedStatement.setString(2,enteredid);
                    preparedStatement.setString(2, enteredPassword);

                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(this, "Login successful");
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid username,id or password");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during login");
            }
        }
    }
