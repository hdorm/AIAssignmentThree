import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Test extends JPanel {

    public static BufferedImage originalImageOne;
    public static BufferedImage denoisedImageOne;
    public static BufferedImage edgeImageOne;

    public static BufferedImage originalImageTwo;
    public static BufferedImage denoisedImageTwo;
    public static BufferedImage edgeImageTwo;
    public static BufferedImage originalImageThree;
    public static BufferedImage denoisedImageThree;
    public static BufferedImage edgeImageThree;
    static {
        try {
            originalImageOne = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/resources/images/BAJIp.png")));
            denoisedImageOne = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/resources/images/BAJIp.png")));
            originalImageTwo = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/resources/images/BAJIp.png")));
            denoisedImageTwo = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/resources/images/BAJIp.png")));
            originalImageThree = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/resources/images/BAJIp.png")));
            denoisedImageThree = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/resources/images/BAJIp.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage GaussianFilter(BufferedImage currentImage) {

        return denoisedImageOne;
    }

    public void paint(Graphics g) {
        File denoisedImage = new File("src/resources/images/denoised.png");
        try {
            ImageIO.write(originalImageOne, "png", denoisedImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(originalImageOne, 0, 0, 250, 250, this);
        g.drawImage(originalImageTwo, 250, 0, 250, 250, this);
        g.drawImage(originalImageThree, 500, 0, 250, 250, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Noisy Image");
        frame.getContentPane().add(new Test());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
    }
}
