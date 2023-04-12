import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class AIAssignmentThree extends JPanel {

    public static BufferedImage originalImageOne;
    public static BufferedImage originalImageTwo;
    public static BufferedImage originalImageThree;

    static {
        try {
            originalImageOne = ImageIO.read(Objects.requireNonNull
                    (AIAssignmentThree.class.getResource("/resources/images/NoisyImageOne.png")));
            originalImageTwo = ImageIO.read(Objects.requireNonNull
                    (AIAssignmentThree.class.getResource("/resources/images/NoisyImageTwo.png")));
            originalImageThree = ImageIO.read(Objects.requireNonNull
                    (AIAssignmentThree.class.getResource("/resources/images/NoisyImageThree.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int findGrayScale(Color rgb) {
        int r = rgb.getRed();
        int g = rgb.getGreen();
        int b = rgb.getBlue();

        return ((r + g + b) / 3);
    }

    static BufferedImage bufferedImageDeepCopy(BufferedImage bufferedImage) {
        ColorModel colorModel = bufferedImage.getColorModel();
        boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        WritableRaster raster = bufferedImage.copyData(null);
        return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage meanFilter(BufferedImage sentImage) {
        BufferedImage currentImage = bufferedImageDeepCopy(sentImage);
        int entireX = currentImage.getWidth();
        int entireY = currentImage.getHeight();
        int avgRed = 0;
        int avgGreen = 0;
        int avgBlue = 0;
        Color currentColor;
        Color newColor;
        LinkedList<Color> colors = new LinkedList<>();
        Color one;
        Color two;
        Color three;
        Color four;
        Color five;
        Color six;
        Color seven;
        Color eight;
        Color nine;
        for (int i = 1; i < entireX - 1; i++) {
            for (int j = 1; j < entireY - 1; j++) {
                five = new Color(currentImage.getRGB(i, j));
                one = new Color(currentImage.getRGB(i - 1, j + 1));
                two = new Color(currentImage.getRGB(i, j + 1));
                three = new Color(currentImage.getRGB(i + 1, j + 1));
                four = new Color(currentImage.getRGB(i - 1, j));
                six = new Color(currentImage.getRGB(i + 1, j));
                seven = new Color(currentImage.getRGB(i - 1, j - 1));
                eight = new Color(currentImage.getRGB(i, j - 1));
                nine = new Color(currentImage.getRGB(i - 1, j - 1));
                colors.add(0, one);
                colors.add(1, two);
                colors.add(2, three);
                colors.add(3, four);
                colors.add(4, five);
                colors.add(5, six);
                colors.add(6, seven);
                colors.add(7, eight);
                colors.add(8, nine);
                for (int h = 0; h < 9; h++) {
                    currentColor = colors.get(h);
                    avgRed += currentColor.getRed();
                    avgGreen += currentColor.getGreen();
                    avgBlue += currentColor.getBlue();
                }
                avgRed = avgRed / 9;
                avgGreen = avgGreen / 9;
                avgBlue = avgBlue / 9;
                newColor = new Color(avgRed, avgGreen, avgBlue);
                currentImage.setRGB(i, j, newColor.getRGB());
                colors.clear();
                avgRed = 0;
                avgBlue = 0;
                avgGreen = 0;
            }
        }
        return currentImage;
    }

    public static BufferedImage medianFilter(BufferedImage sentImage) {
        BufferedImage currentImage = bufferedImageDeepCopy(sentImage);
        int entireX = currentImage.getWidth();
        int entireY = currentImage.getHeight();
        int currentAddition;
        LinkedList<Color> colors = new LinkedList<>();
        LinkedList<Integer> median = new LinkedList<>();
        Color one;
        Color two;
        Color three;
        Color four;
        Color five;
        Color six;
        Color seven;
        Color eight;
        Color nine;
        for (int i = 1; i < entireX - 1; i++) {
            for (int j = 1; j < entireY - 1; j++) {
                five = new Color(currentImage.getRGB(i, j));
                one = new Color(currentImage.getRGB(i - 1, j + 1));
                two = new Color(currentImage.getRGB(i, j + 1));
                three = new Color(currentImage.getRGB(i + 1, j + 1));
                four = new Color(currentImage.getRGB(i - 1, j));
                six = new Color(currentImage.getRGB(i + 1, j));
                seven = new Color(currentImage.getRGB(i - 1, j - 1));
                eight = new Color(currentImage.getRGB(i, j - 1));
                nine = new Color(currentImage.getRGB(i - 1, j - 1));
                colors.add(0, one);
                colors.add(1, two);
                colors.add(2, three);
                colors.add(3, four);
                colors.add(4, five);
                colors.add(5, six);
                colors.add(6, seven);
                colors.add(7, eight);
                colors.add(8, nine);
                for (int h = 0; h < 9; h++) {
                    currentAddition = 0;
                    Color current = colors.get(h);
                    for (int k = 0; k < 9; k++) {
                        currentAddition += Math.pow((current.getRed() - colors.get(k).getRed()), 2)
                                + Math.pow((current.getGreen() - colors.get(k).getGreen()), 2)
                                + Math.pow((current.getBlue() - colors.get(k).getBlue()), 2);
                    }
                    median.add(h, currentAddition);
                }
                int minColor = median.indexOf(Collections.min(median));
                currentImage.setRGB(i, j, colors.get(minColor).getRGB());
                median.clear();
                colors.clear();
            }
        }
        return currentImage;
    }

    public static BufferedImage sobelEdgeDetection(BufferedImage sentImage) {
        BufferedImage currentImage = bufferedImageDeepCopy(sentImage);
        int entireX = currentImage.getWidth();
        int entireY = currentImage.getHeight();
        int[][] colorsOfEdges = new int[entireX][entireY];
        int maxGradient = -1;
        for (int i = 1; i < entireX - 1; i++) {
            for (int j = 1; j < entireY - 1; j++) {
                Color color00 = new Color(currentImage.getRGB(i - 1, j - 1));
                Color color01 = new Color(currentImage.getRGB(i - 1, j));
                Color color02 = new Color(currentImage.getRGB(i - 1, j + 1));
                Color color10 = new Color(currentImage.getRGB(i, j - 1));
                Color color12 = new Color(currentImage.getRGB(i, j + 1));
                Color color20 = new Color(currentImage.getRGB(i + 1, j - 1));
                Color color21 = new Color(currentImage.getRGB(i + 1, j));
                Color color22 = new Color(currentImage.getRGB(i + 1, j + 1));
                int val00 = findGrayScale(color00);
                int val01 = findGrayScale(color01);
                int val02 = findGrayScale(color02);
                int val10 = findGrayScale(color10);
                int val12 = findGrayScale(color12);
                int val20 = findGrayScale(color20);
                int val21 = findGrayScale(color21);
                int val22 = findGrayScale(color22);
                int gx = ((-1 * val00) + (val02))
                        + ((-2 * val10) + (2 * val12))
                        + ((-1 * val20) + (val22));
                int gy = ((-1 * val00) + (-2 * val01) + (-1 * val02)) + ((val20) + (2 * val21) + (val22));
                double gVal = Math.sqrt((gx * gx) + (gy * gy));
                int g = (int) gVal;
                if (maxGradient < g) {
                    maxGradient = g;
                }
                colorsOfEdges[i][j] = g;
            }
        }

        double scale = 255.0 / maxGradient;

        for (int i = 1; i < entireX - 1; i++) {
            for (int j = 1; j < entireY - 1; j++) {
                int colorOfEdge = colorsOfEdges[i][j];
                colorOfEdge = (int) (colorOfEdge * scale);
                colorOfEdge = 0xff000000 | (colorOfEdge << 16) | (colorOfEdge << 8) | colorOfEdge;
                currentImage.setRGB(i, j, colorOfEdge);
            }
        }
        return currentImage;
    }

    public static BufferedImage robertsEdgeDetection(BufferedImage sentImage) {
        BufferedImage currentImage = bufferedImageDeepCopy(sentImage);
        int entireX = currentImage.getWidth();
        int entireY = currentImage.getHeight();
        int avgRed = 0;
        int avgGreen = 0;
        int avgBlue = 0;
        Color currentColor;
        Color newColor;
        LinkedList<Color> colors = new LinkedList<>();
        Color one;
        Color two;
        Color three;
        Color four;
        Color five;
        Color six;
        Color seven;
        Color eight;
        Color nine;
        int X = 3;
        int Y = 3;
        boolean cont = true;
        while (cont) {
            five = new Color(currentImage.getRGB(X, Y));
            one = new Color(currentImage.getRGB(X - 1, Y + 1));
            two = new Color(currentImage.getRGB(X, Y + 1));
            three = new Color(currentImage.getRGB(X + 1, Y + 1));
            four = new Color(currentImage.getRGB(X - 1, Y));
            six = new Color(currentImage.getRGB(X + 1, Y));
            seven = new Color(currentImage.getRGB(X - 1, Y - 1));
            eight = new Color(currentImage.getRGB(X, Y - 1));
            nine = new Color(currentImage.getRGB(X - 1, Y - 1));
            colors.add(0, one);
            colors.add(1, two);
            colors.add(2, three);
            colors.add(3, four);
            colors.add(4, five);
            colors.add(5, six);
            colors.add(6, seven);
            colors.add(7, eight);
            colors.add(8, nine);
            for (int i = 0; i < 9; i++) {
                currentColor = colors.get(i);
                avgRed += currentColor.getRed();
                avgGreen += currentColor.getGreen();
                avgBlue += currentColor.getBlue();
            }
            avgRed = avgRed / 9;
            avgGreen = avgGreen / 9;
            avgBlue = avgBlue / 9;
            newColor = new Color(avgRed, avgGreen, avgBlue);
            currentImage.setRGB(X, Y, newColor.getRGB());
            if (X == entireX - 3) {
                X = 3;
                Y++;
            }
            X++;
            if ((X + Y) == ((entireX - 3) + (entireY) - 3)) {
                cont = false;
            }
            colors.clear();
            avgRed = 0;
            avgBlue = 0;
            avgGreen = 0;
        }
        return currentImage;
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
        g.drawImage(medianFilter(originalImageOne), 0, 250, 250, 250, this);
        g.drawImage(medianFilter(originalImageTwo), 250, 250, 250, 250, this);
        g.drawImage(meanFilter(originalImageThree), 500, 250, 250, 250, this);
        g.drawImage(sobelEdgeDetection(medianFilter(originalImageOne)), 0, 500, 250, 250, this);
        g.drawImage(sobelEdgeDetection(medianFilter(originalImageTwo)), 250, 500, 250, 250, this);
        g.drawImage(robertsEdgeDetection(meanFilter(originalImageThree)), 500, 500, 250, 250, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Image Denoiser and Edge Detector");
        frame.getContentPane().add(new AIAssignmentThree());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
    }
}
