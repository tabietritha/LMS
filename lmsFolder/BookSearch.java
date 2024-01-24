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


public class BookSearch extends JFrame {

    private JTextField searchField;
    private JTextArea resultArea;

    private JComboBox bookComboBox;

    private JScrollPane scrollPane;

    public BookSearch() {
        setTitle("book search and books available");
        //setSize(1000, 500);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        setVisible(true);

    }
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        panel.setBackground(Color.cyan);


        JLabel searchLabel = new JLabel("Search Books or Authors:");
        searchLabel.setBounds(300, 100, 300, 25);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(searchLabel);

        searchField = new JTextField(20);
        searchField.setBounds(600, 100, 300, 25);
        panel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(950, 100, 180, 25);
        searchButton.setFont(new Font("Arial", Font.BOLD, 18));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBooks();
            }
        });
        panel.add(searchButton);

       resultArea = new JTextArea();
        resultArea.setBounds(600, 150, 500, 300);
        resultArea.setEditable(true);

        scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(600, 150, 500, 300);
        panel.add(scrollPane);

        JButton retrieveButton = new JButton("Available books");
        retrieveButton.setBounds(550, 480, 200, 25);
        retrieveButton.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(retrieveButton);

        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                retrieveBooks();
            }
        });

       JButton borrow = new JButton("Borrow book..");
        borrow.setBounds(800, 480, 200, 25);
        borrow.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(borrow);

        borrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new borrow();
            }
        });

        JButton back = new JButton("Back");
        back.setBounds(1050, 480, 100, 25);
        back.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignUpPage();
            }
        });


    }


    private void searchBooks() {
        String searchTerm = searchField.getText().trim();

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/lmsDatabase";
        String user = "root";
        String dbPassword = "Passage@1111";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String query;
            PreparedStatement preparedStatement;

            if (searchTerm.isEmpty()) {
                // If search field is empty, retrieve all books
                query = "SELECT title, author, description_, isbn FROM books";
                preparedStatement = connection.prepareStatement(query);
            } else {
                // If search field has a value, search for books with matching title or author
                query = "SELECT title, author, description_, isbn FROM books WHERE title LIKE ? OR author LIKE ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "%" + searchTerm + "%");
                preparedStatement.setString(2, "%" + searchTerm + "%");
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            StringBuilder resultText = new StringBuilder();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description_");
                String isbn = resultSet.getString("isbn");

                resultText.append("Title: ").append(title).append("\n");
                resultText.append("Author: ").append(author).append("\n");
                resultText.append("Description: ").append(description).append("\n");
                resultText.append("ISBN: ").append(isbn).append("\n\n");
            }

            if (resultText.length() == 0) {
                resultText.append("No books found.");
            }

            resultArea.setText(resultText.toString());

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during book search");
        }
    }
    private void retrieveBooks(){
            String jdbcUrl = "jdbc:mysql://localhost:3306/lmsDatabase";
            String username = "root";
            String password = "Passage@1111";

            // SQL query to retrieve all data from a table (replace 'your_table_name' with the actual table name)
            String query = "SELECT * FROM books";

            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the connection
                try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                     PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Process the result set and append data to the text area
                    StringBuilder resultText = new StringBuilder();
                    while (resultSet.next()) {
                        String title = resultSet.getString("title");
                        String author = resultSet.getString("author");
                        String description = resultSet.getString("description_");
                        String isbn = resultSet.getString("isbn");

                        resultText.append("Title: ").append(title).append("\n");
                        resultText.append("Author: ").append(author).append("\n");
                        resultText.append("Description: ").append(description).append("\n");
                        resultText.append("ISBN: ").append(isbn).append("\n\n");

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




