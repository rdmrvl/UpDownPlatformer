import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Credits extends JPanel {
    public Credits() {
        setLayout(new BorderLayout());

        // Credits text
        JLabel creditsLabel = new JLabel("<html><h1>Credits</h1><p>Developed by Marvel Ravindra Dioputra.</p>" +
                "<p><strong>Game Assets:</strong><br>" +
                "- Background: PixelChoice<br>" +
                "- Character: LunarCore Games<br>" +
                "- Explosion: PNG-EGG Sprite Explosion<br>" +
                "- Obstacle (Up): Lilkin<br>" +
                "- Obstacle (Down): Magicuz, MaryValery<br><br>" +
                "<strong>SFX & BGM:</strong><br>" +
                "- Jump: EdR - 8bit Jump<br>" +
                "- Explosion: Pixabay - Explosion1<br>" +
                "- BGM: David Renda - Funny Bit</p></html>", JLabel.CENTER);
        add(creditsLabel, BorderLayout.CENTER);

        // Back button
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
