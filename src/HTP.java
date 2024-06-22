import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HTP extends JPanel {
    public HTP() {
        setLayout(new BorderLayout());

        String htpText = "<html><h1>How to Play</h1>"
                + "<ul>"
                + "<li><b>Left Arrow Key:</b> Move left</li>"
                + "<li><b>Right Arrow Key:</b> Move right</li>"
                + "<li><b>Up Arrow Key:</b> Jump</li>"
                + "<li><b>Space Bar:</b> Go back to main menu</li>"
                + "</ul>"
                + "<p>Survive as long as you can.</p> <p>Gain score by stepping on the obstacles.</p><p>Good luck and Have Fun!</p>"
                + "</html>";

        JLabel htpLabel = new JLabel(htpText, JLabel.CENTER);
        add(htpLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) getParent().getLayout();
                cl.show(getParent(), "MainMenu");
            }
        });
        add(backButton, BorderLayout.SOUTH);
    }
}
