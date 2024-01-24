package lmsPack;
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

        public class addBooks extends JFrame implements ActionListener {

            private JTextField titleField;
            private JTextField authorField;
            private JTextField descriptionField;
            private JTextField isbnField;
            private JButton insertButton;

            public addBooks() {
                // Set layout manager to null for absolute positioning
                setLayout(null);

                // Set the size of the JFrame
                setSize(400, 400);
                getContentPane().setBackground( Color.cyan );

                // Create components
                JLabel titleLabel = new JLabel("Book Insert System");
                titleLabel.setBounds(100, 20, 200, 20);
                add(titleLabel);

                JLabel titleLabelField = new JLabel("Title:");
                titleLabelField.setBounds(20, 60, 150, 20);
                add(titleLabelField);

                titleField = new JTextField();
                titleField.setBounds(180, 60, 150, 20);
                add(titleField);

                JLabel authorLabel = new JLabel("Author:");
                authorLabel.setBounds(20, 100, 150, 20);
                add(authorLabel);

                authorField = new JTextField();
                authorField.setBounds(180, 100, 150, 20);
                add(authorField);

                JLabel descriptionLabel = new JLabel("Description:");
                descriptionLabel.setBounds(20, 140, 150, 20);
                add(descriptionLabel);

                descriptionField = new JTextField();
                descriptionField.setBounds(180, 140, 150, 20);
                add(descriptionField);

                JLabel isbnLabel = new JLabel("ISBN:");
                isbnLabel.setBounds(20, 180, 150, 20);
                add(isbnLabel);

                isbnField = new JTextField();
                isbnField.setBounds(180, 180, 150, 20);
                add(isbnField);

                insertButton = new JButton("Insert Book");
                insertButton.setBounds(80, 230, 120, 30);
                insertButton.addActionListener(this);
                add(insertButton);

                JButton borrowedBooks =new JButton("Borrowed Books");
                borrowedBooks.setBounds(220, 230, 150, 30);
                add(borrowedBooks);
                borrowedBooks.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      dispose();
                        new RetrieveData();
                    }
                });
                JButton finish =new JButton("Finish");
                finish.setBounds(170, 280, 100, 30);
                finish.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new thanksPage();
                    }
                });
                add(finish);

                // Set default close operation and make the JFrame visible
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setResizable(false);
                setVisible(true);
            }

            private void insertBook(String title, String author, String description, String isbn) {
                // Connect to the database and insert new book details

                String url = "jdbc:mysql://localhost:3306/lmsDatabase";
                String user = "root";
                String dbPassword = "Passage@1111";
                try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
                    String query = "INSERT INTO books (title, author, description_, isbn) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, title);
                        preparedStatement.setString(2, author);
                        preparedStatement.setString(3, description);
                        preparedStatement.setString(4, isbn);

                        int rowsInserted = preparedStatement.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(this, "Book inserted successfully");
                        } else {
                            JOptionPane.showMessageDialog(this, "Error inserting book");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error inserting book: " + e.getMessage());
                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == insertButton) {
                    // Retrieve user input
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String description = descriptionField.getText();
                    String isbn = isbnField.getText();

                    // Perform book insertion logic
                    insertBook(title, author, description, isbn);
                }
            }

        }
