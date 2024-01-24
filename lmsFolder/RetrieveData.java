package lmsPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RetrieveData {
    public RetrieveData() {

        JFrame page = new JFrame();
        page.setVisible(true);
        page.setSize(600,600);
        page.setExtendedState(JFrame.MAXIMIZED_BOTH);
        page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        page.getContentPane().setBackground(Color.cyan);

        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(450, 100, 500, 500);
        resultArea.setEditable(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(450, 100, 500, 500);
        page.add(scrollPane);

        JButton ok =new JButton("OK");
        ok.setBounds(1050,60,60,20);
        page.add(ok);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page.dispose();
                new thanksPage();
            }
        });
        String jdbcUrl = "jdbc:mysql://localhost:3306/lmsDatabase";
        String user = "root";
        String password = "Passage@1111";

        // SQL query to retrieve all data from a table (replace 'your_table_name' with the actual table name)
        String query = "SELECT * FROM borrowBook";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process the result set and append data to the text area
                StringBuilder resultText = new StringBuilder();
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String username = resultSet.getString("username");

                    resultText.append("Title: ").append(title).append("\n");
                    resultText.append("Username: ").append(username).append("\n\n");

                }

                // Display the retrieved data in the text area
                resultArea.setText(resultText.toString());
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            resultArea.setText("Error retrieving data from the database.");
        }
    }
}

