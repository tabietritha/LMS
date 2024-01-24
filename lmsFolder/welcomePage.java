package lmsPack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class welcomePage extends JFrame {

    private JLabel animationLabel;

    public welcomePage() {
        // Set up JFrame
        setTitle("Welcome Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Create animation label
        ImageIcon gifIcon = new ImageIcon("src/lmsPack/lib9.gif");
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(100,50,1200,600);

        // Set layout for the frame
        setLayout(new BorderLayout());

        // Add the JLabel to the center of the frame
       // add(gifLabel, BorderLayout.CENTER);

        animationLabel = new JLabel("Welcome To Our Library Management System!");
        animationLabel.setFont(new Font("Arial", Font.ITALIC, 28));
        animationLabel.setBounds(20,20,700,45);
        animationLabel.setForeground(Color.BLUE);
        add(animationLabel);

        // Create buttons
        JButton button1 = new JButton("Contiune");
        button1.setBounds(300,500,100,35);
        button1.setForeground(Color.white);
        button1.setBackground(Color.black);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpPage();
            }
        });

        JButton button2 = new JButton("Users Login");
        button2.setBounds(500,500,100,35);
        button2.setForeground(Color.white);
        button2.setBackground(Color.black);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
            }
        });

        JButton button3 = new JButton("Librarian");
        button3.setBounds(700,500,100,35);
        button3.setForeground(Color.white);
        button3.setBackground(Color.black);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Librarian();
            }
        });

        /*JButton button4 = new JButton("Administration");
        button4.setBounds(900,500,100,35);
        button4.setForeground(Color.white);
        button4.setBackground(Color.black);*/


        // Set up button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        //buttonPanel.add(button4);
        buttonPanel.add(gifLabel);

        // Add button panel to JFrame
        add(buttonPanel);

        // Set up animation timer
        Timer timer = new Timer(20, new ActionListener() {
            int x = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Move the label to create a simple animation
                x += 5;
                if (x > getWidth()) {
                    x = -animationLabel.getWidth();
                }
                animationLabel.setLocation(x, 0);
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            welcomePage welcomePage = new welcomePage();
            welcomePage.setVisible(true);
        });
    }
}

