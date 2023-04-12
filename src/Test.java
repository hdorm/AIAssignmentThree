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

public class Test extends JPanel {

    public static BufferedImage originalImageOne;
    public static BufferedImage originalImageTwo;
    public static BufferedImage originalImageThree;

    static {
        try {
            originalImageOne = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/resources/images/NoisyImageOne.png")));
            originalImageTwo = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/resources/images/NoisyImageTwo.png")));
            originalImageThree = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/resources/images/NoisyImageThree.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        int X = 3;
        int Y = 3;
        boolean cont = true;
        while(cont){
            five = new Color(currentImage.getRGB(X, Y));
            one = new Color(currentImage.getRGB(X-1, Y+1));
            two = new Color(currentImage.getRGB(X, Y+1));
            three = new Color(currentImage.getRGB(X+1, Y+1));
            four = new Color(currentImage.getRGB(X-1, Y));
            six = new Color(currentImage.getRGB(X+1, Y));
            seven = new Color(currentImage.getRGB(X-1, Y-1));
            eight = new Color(currentImage.getRGB(X, Y-1));
            nine = new Color(currentImage.getRGB(X-1, Y-1));
            colors.add(0, one);
            colors.add(1, two);
            colors.add(2, three);
            colors.add(3, four);
            colors.add(4, five);
            colors.add(5, six);
            colors.add(6, seven);
            colors.add(7, eight);
            colors.add(8, nine);
            for(int i = 0; i < 9; i++){
                currentColor = colors.get(i);
                avgRed += currentColor.getRed();
                avgGreen += currentColor.getGreen();
                avgBlue += currentColor.getBlue();
            }
            avgRed = avgRed/9;
            avgGreen = avgGreen/9;
            avgBlue = avgBlue/9;
            newColor = new Color(avgRed, avgGreen, avgBlue);
            currentImage.setRGB(X, Y, newColor.getRGB());
            if(X == entireX - 3){
                X = 3;
                Y++;
            }
            X++;
            if((X+Y) == ((entireX - 3) + (entireY) - 3)){
                cont = false;
            }
            colors.clear();
            avgRed = 0;
            avgBlue = 0;
            avgGreen = 0;
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
        int X = 3;
        int Y = 3;
        boolean cont = true;
        while(cont){
            five = new Color(currentImage.getRGB(X, Y));
            one = new Color(currentImage.getRGB(X-1, Y+1));
            two = new Color(currentImage.getRGB(X, Y+1));
            three = new Color(currentImage.getRGB(X+1, Y+1));
            four = new Color(currentImage.getRGB(X-1, Y));
            six = new Color(currentImage.getRGB(X+1, Y));
            seven = new Color(currentImage.getRGB(X-1, Y-1));
            eight = new Color(currentImage.getRGB(X, Y-1));
            nine = new Color(currentImage.getRGB(X-1, Y-1));
            colors.add(0, one);
            colors.add(1, two);
            colors.add(2, three);
            colors.add(3, four);
            colors.add(4, five);
            colors.add(5, six);
            colors.add(6, seven);
            colors.add(7, eight);
            colors.add(8, nine);
            for(int i = 0; i < 9; i++){
                currentAddition = 0;
                Color current = colors.get(i);
                for(int j = 0; j < 9; j++){
                    currentAddition += Math.pow((current.getRed()-colors.get(j).getRed()), 2) + Math.pow((current.getGreen()-colors.get(j).getGreen()), 2) + Math.pow((current.getBlue()-colors.get(j).getBlue()), 2);
                }
                median.add(i, currentAddition);
            }
            int minColor = median.indexOf(Collections.min(median));
            currentImage.setRGB(X, Y, colors.get(minColor).getRGB());
            if(X == entireX - 3){
                X = 3;
                Y++;
            }
            X++;
            if((X+Y) == ((entireX - 3) + (entireY) - 3)){
                cont = false;
            }
            median.clear();
            colors.clear();
        }
        return currentImage;
    }

    public static BufferedImage sobelEdgeDetection(BufferedImage sentImage) {
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
        while(cont){
            five = new Color(currentImage.getRGB(X, Y));
            one = new Color(currentImage.getRGB(X-1, Y+1));
            two = new Color(currentImage.getRGB(X, Y+1));
            three = new Color(currentImage.getRGB(X+1, Y+1));
            four = new Color(currentImage.getRGB(X-1, Y));
            six = new Color(currentImage.getRGB(X+1, Y));
            seven = new Color(currentImage.getRGB(X-1, Y-1));
            eight = new Color(currentImage.getRGB(X, Y-1));
            nine = new Color(currentImage.getRGB(X-1, Y-1));
            colors.add(0, one);
            colors.add(1, two);
            colors.add(2, three);
            colors.add(3, four);
            colors.add(4, five);
            colors.add(5, six);
            colors.add(6, seven);
            colors.add(7, eight);
            colors.add(8, nine);
            for(int i = 0; i < 9; i++){
                currentColor = colors.get(i);
                avgRed += currentColor.getRed();
                avgGreen += currentColor.getGreen();
                avgBlue += currentColor.getBlue();
            }
            avgRed = avgRed/9;
            avgGreen = avgGreen/9;
            avgBlue = avgBlue/9;
            newColor = new Color(avgRed, avgGreen, avgBlue);
            currentImage.setRGB(X, Y, newColor.getRGB());
            if(X == entireX - 3){
                X = 3;
                Y++;
            }
            X++;
            if((X+Y) == ((entireX - 3) + (entireY) - 3)){
                cont = false;
            }
            colors.clear();
            avgRed = 0;
            avgBlue = 0;
            avgGreen = 0;
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
        while(cont){
            five = new Color(currentImage.getRGB(X, Y));
            one = new Color(currentImage.getRGB(X-1, Y+1));
            two = new Color(currentImage.getRGB(X, Y+1));
            three = new Color(currentImage.getRGB(X+1, Y+1));
            four = new Color(currentImage.getRGB(X-1, Y));
            six = new Color(currentImage.getRGB(X+1, Y));
            seven = new Color(currentImage.getRGB(X-1, Y-1));
            eight = new Color(currentImage.getRGB(X, Y-1));
            nine = new Color(currentImage.getRGB(X-1, Y-1));
            colors.add(0, one);
            colors.add(1, two);
            colors.add(2, three);
            colors.add(3, four);
            colors.add(4, five);
            colors.add(5, six);
            colors.add(6, seven);
            colors.add(7, eight);
            colors.add(8, nine);
            for(int i = 0; i < 9; i++){
                currentColor = colors.get(i);
                avgRed += currentColor.getRed();
                avgGreen += currentColor.getGreen();
                avgBlue += currentColor.getBlue();
            }
            avgRed = avgRed/9;
            avgGreen = avgGreen/9;
            avgBlue = avgBlue/9;
            newColor = new Color(avgRed, avgGreen, avgBlue);
            currentImage.setRGB(X, Y, newColor.getRGB());
            if(X == entireX - 3){
                X = 3;
                Y++;
            }
            X++;
            if((X+Y) == ((entireX - 3) + (entireY) - 3)){
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
        g.drawImage(meanFilter(originalImageOne), 0, 250, 250, 250, this);
        g.drawImage(medianFilter(originalImageTwo), 250, 250, 250, 250, this);
        g.drawImage(meanFilter(originalImageThree), 500, 250, 250, 250, this);
        g.drawImage(sobelEdgeDetection(meanFilter(originalImageOne)), 0, 500, 250, 250, this);
        g.drawImage(robertsEdgeDetection(medianFilter(originalImageTwo)), 250, 500, 250, 250, this);
        g.drawImage(sobelEdgeDetection(meanFilter(originalImageThree)), 500, 500, 250, 250, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Image Denoiser and Edge Detector");
        frame.getContentPane().add(new Test());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
    }
}
