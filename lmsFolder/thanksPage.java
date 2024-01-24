package lmsPack;
    import javax.swing.*;
import java.awt.*;

    public class thanksPage extends JFrame {

        public thanksPage() {
            setTitle("GIF Display Frame");
            //setSize(300, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Create an ImageIcon from the GIF file
            ImageIcon gifIcon = new ImageIcon("src/lmsPack/lib5.gif");

            // Create a JLabel and set the ImageIcon
            JLabel gifLabel = new JLabel(gifIcon);

            // Set layout for the frame
            setLayout(new BorderLayout());

            // Add the JLabel to the center of the frame
            add(gifLabel, BorderLayout.CENTER);

            setVisible(true);
        }
}
