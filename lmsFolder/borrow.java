package lmsPack;

    import org.jdatepicker.impl.JDatePanelImpl;
    import org.jdatepicker.impl.JDatePickerImpl;
    import org.jdatepicker.impl.UtilDateModel;

    import java.awt.*;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import javax.swing.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.Properties;

public class borrow extends JFrame {

            private JTextField usernameField;
            private JComboBox<String> bookComboBox;
            private JButton borrowButton;
    String datee;

            public <DateFormat, DatePicker> borrow() {
                // Set layout manager to null for absolute positioning
                setLayout(null);
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //setSize(400, 300);

                // Create components
                JLabel titleLabel = new JLabel("Sign for the book borrowed");
                titleLabel.setFont(new Font("Georgia", Font.BOLD, 28));
                titleLabel.setBounds(400, 150, 480, 45);
                add(titleLabel);

                JLabel usernameLabel = new JLabel("Username:");
                usernameLabel.setBounds(400, 250, 180, 45);
                usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
                add(usernameLabel);

                usernameField = new JTextField();
                usernameField.setBounds(600, 250, 300, 45);
                add(usernameField);

                JLabel bookLabel = new JLabel("Select a Book:");
                bookLabel.setBounds(400, 350, 180, 45);
                bookLabel.setFont(new Font("Arial", Font.BOLD, 18));
                add(bookLabel);

                // Retrieve book details from the database
                String[] bookOptions = getBookOptions();
                bookComboBox = new JComboBox<>(bookOptions);
                bookComboBox.setBounds(600, 350, 300, 45);;
                add(bookComboBox);

                JLabel date1 = new JLabel("Date returned:");
                date1.setBounds(400, 445, 180, 45);
                date1.setFont(new Font("Arial", Font.BOLD, 18));
                add(date1);

                UtilDateModel model = new UtilDateModel();
                Properties properties = new Properties();
                properties.put("text.today", "Today");
                properties.put("text.month", "Month");
                properties.put("text.year", "Year");
                JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
                JDatePickerImpl datePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
                datePicker.setBounds(600,450,350,25);
                Date selectedDate = (Date) datePicker.getModel().getValue();
                datee = selectedDate + "";
                add(datePicker);

                JLabel jLabel =new JLabel("NOTE: Books should be returned after one month or below that!");
                jLabel.setBounds(450, 500, 600, 35);
                jLabel.setFont(new Font("Arial", Font.BOLD, 12));
                add(jLabel);

                borrowButton = new JButton("Borrow Book");
                borrowButton.setBounds(400, 600, 200, 35);
                borrowButton.setFont(new Font("Arial", Font.BOLD, 18));
                borrowButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        try {
                            borrowBook();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                add(borrowButton);

                JButton back =new JButton("Back");
                back.setBounds(700, 600, 100, 35);
                back.setFont(new Font("Arial", Font.BOLD, 18));
                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new BookSearch();
                        dispose();
                    }
                });
                add(back);

                JButton finish =new JButton("Finish");
                finish.setBounds(900, 600, 100, 35);
                finish.setFont(new Font("Arial", Font.BOLD, 18));
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
                getContentPane().setBackground(Color.CYAN);
            }

            private String[] getBookOptions() {
                // Connect to the database and retrieve book details
                String url = "jdbc:mysql://localhost:3306/lmsDatabase";
                String user = "root";
                String dbPassword = "Passage@1111";
                try (Connection connection = DriverManager.getConnection(url,user,dbPassword)) {
                    String query = "SELECT title FROM books";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        ResultSet resultSet = preparedStatement.executeQuery();
                        int rowCount = 0;
                        while (resultSet.next()) {
                            rowCount++;
                        }
                        resultSet.close();

                        String[] bookOptions = new String[rowCount];
                        resultSet = preparedStatement.executeQuery();
                        int index = 0;
                        while (resultSet.next()) {
                            bookOptions[index++] = resultSet.getString("title");
                        }
                        return bookOptions;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new String[0];
                }
            }

            private void borrowBook() throws SQLException {
                String url = "jdbc:mysql://localhost:3306/lmsDatabase";
                String user = "root";
                String dbPassword = "Passage@1111";
                // Perform book borrowing logic here (e.g., update database, show confirmation)
                String username = usernameField.getText();
                String selectedBook = (String) bookComboBox.getSelectedItem();
                    try (Connection connection = DriverManager.getConnection(url,user,dbPassword)) {
                        // Check if the book is available for borrowing
                        String availabilityQuery = "SELECT available FROM books WHERE title = ?";
                        try (PreparedStatement availabilityStatement = connection.prepareStatement(availabilityQuery)) {
                            availabilityStatement.setString(1, selectedBook);

                            ResultSet resultSet = availabilityStatement.executeQuery();
                            if (resultSet.next()) {
                                boolean isAvailable = resultSet.getBoolean("available");
                                if (!isAvailable) {
                                    JOptionPane.showMessageDialog(this, "Sorry, the book \"" + selectedBook + "\" is already borrowed.");
                                    return;
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Book \"" + selectedBook + "\" not found.");
                                return;
                            }
                        }

                        // Update the book's availability status and record the borrower's username
                        String borrowUpdateQuery = "UPDATE books SET available = ? WHERE title = ?";
                        try (PreparedStatement borrowUpdateStatement = connection.prepareStatement(borrowUpdateQuery)) {
                            borrowUpdateStatement.setBoolean(1, false);  // Book is no longer available
                           // borrowUpdateStatement.setString(2, username); // Record the borrower's username
                            borrowUpdateStatement.setString(2, selectedBook);

                            int rowsUpdated = borrowUpdateStatement.executeUpdate();
                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(this, "Book \"" + selectedBook + "\" borrowed by " + username);
                            } else {
                                JOptionPane.showMessageDialog(this, "Failed to borrow the book \"" + selectedBook + "\".");
                            }
                        }
                    }
                // String title = usernameField.getText();
                    try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
                    String query = "INSERT INTO borrowBook (username, title) VALUES (?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, selectedBook);
                        preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Borrowed book successfully registered");
                    }

                }
                    catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error borrowing book: " + e.getMessage());
                    }

                }
            }



