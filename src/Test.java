import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Test extends JPanel {

    public static BufferedImage newIMG;

    static {
        try {
            newIMG = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/resources/images/BAJIp.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void paint(Graphics g) {
        g.drawImage(newIMG, 0, 0, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Noisy Image");
        frame.getContentPane().add(new Test());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(newIMG.getWidth(), newIMG.getHeight() + 100);
        frame.setVisible(true);
    }
}
