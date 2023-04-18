import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class AIAssignmentThree extends JPanel {

    // Global variables for storing the noisy images as buffered images
    public static BufferedImage OGImageOne;
    public static BufferedImage OGImageTwo;
    public static BufferedImage OGImageThree;

    // Sets the buffered images as the images in the resources folder
    static {
        try {
            OGImageOne = ImageIO.read(Objects.requireNonNull
                    (AIAssignmentThree.class.getResource("/resources/images/NoisyImageOne.png")));
            OGImageTwo = ImageIO.read(Objects.requireNonNull
                    (AIAssignmentThree.class.getResource("/resources/images/NoisyImageTwo.png")));
            OGImageThree = ImageIO.read(Objects.requireNonNull
                    (AIAssignmentThree.class.getResource("/resources/images/NoisyImageThree.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Takes the rgb int, translates it into a Color variable, gets the individual rgb values, and converts that into
    // grayscale by diving their addition by 3
    public static int getGrayScale(int color) {
        Color currentColor = new Color(color);
        int r = currentColor.getRed();
        int g = currentColor.getGreen();
        int b = currentColor.getBlue();
        return ((r + g + b) / 3);
    }

    // Creates a deep copy of a buffered image, so they can be used multiple times without change
    static BufferedImage bufferedImageDeepCopy(BufferedImage bufferedImage) {
        return new BufferedImage(bufferedImage.getColorModel(), bufferedImage.copyData(null), bufferedImage.isAlphaPremultiplied(), null);
    }

    // Function for an averaging filter
    public static BufferedImage averageFilter(BufferedImage sentImage) {
        // Creates a deep copy of the buffered image
        BufferedImage currentImage = bufferedImageDeepCopy(sentImage);
        // Sets x and y variables that are the size of the image being denoised
        int entireX = currentImage.getWidth();
        int entireY = currentImage.getHeight();
        // Creates rgb values to be used in the averaging
        int avgRed = 0;
        int avgGreen = 0;
        int avgBlue = 0;
        // Creates Color variables for the current color and newly calculated color
        Color currentColor;
        Color newColor;
        // Creates a linked list to hold the colors currently being evaluated
        LinkedList<Color> colors = new LinkedList<>();
        // Double for loop so that each x and y coordinate are evaluated
        for (int i = 1; i < entireX - 1; i++) {
            for (int j = 1; j < entireY - 1; j++) {
                // Creates a Color variable for the center pixel and all the surrounding pixels in a 3x3 square
                Color five = new Color(currentImage.getRGB(i, j));
                Color one = new Color(currentImage.getRGB(i - 1, j + 1));
                Color two = new Color(currentImage.getRGB(i, j + 1));
                Color three = new Color(currentImage.getRGB(i + 1, j + 1));
                Color four = new Color(currentImage.getRGB(i - 1, j));
                Color six = new Color(currentImage.getRGB(i + 1, j));
                Color seven = new Color(currentImage.getRGB(i - 1, j - 1));
                Color eight = new Color(currentImage.getRGB(i, j - 1));
                Color nine = new Color(currentImage.getRGB(i - 1, j - 1));
                // Adds the Color variables to the linked list
                colors.add(0, one);
                colors.add(1, two);
                colors.add(2, three);
                colors.add(3, four);
                colors.add(4, five);
                colors.add(5, six);
                colors.add(6, seven);
                colors.add(7, eight);
                colors.add(8, nine);
                // For loop that goes through each of the nine colors and calculates the total of their rgb values
                for (int h = 0; h < 9; h++) {
                    currentColor = colors.get(h);
                    avgRed += currentColor.getRed();
                    avgGreen += currentColor.getGreen();
                    avgBlue += currentColor.getBlue();
                }
                // Divides each of the rgb values by 9 to get their average
                avgRed = avgRed / 9;
                avgGreen = avgGreen / 9;
                avgBlue = avgBlue / 9;
                // Sets the new color variable as the average of the three rgb values
                newColor = new Color(avgRed, avgGreen, avgBlue);
                // Sets the center pixel as the new average color
                currentImage.setRGB(i, j, newColor.getRGB());
                // Clears the color linked list so a new one can be made
                colors.clear();
                // Clears out the rbg values
                avgRed = 0;
                avgBlue = 0;
                avgGreen = 0;
            }
        }
        // Sends the newly made image back
        return currentImage;
    }

    // Median filter that works very similarly to the averaging filter
    // Only going to go over the changes from the other filter
    public static BufferedImage medianFilter(BufferedImage sentImage) {
        BufferedImage currentImage = bufferedImageDeepCopy(sentImage);
        int entireX = currentImage.getWidth();
        int entireY = currentImage.getHeight();
        int currentAddition;
        LinkedList<Color> colors = new LinkedList<>();
        // Linked list to hold the possible median values
        LinkedList<Integer> median = new LinkedList<>();
        for (int i = 1; i < entireX - 1; i++) {
            for (int j = 1; j < entireY - 1; j++) {
                Color five = new Color(currentImage.getRGB(i, j));
                Color one = new Color(currentImage.getRGB(i - 1, j + 1));
                Color two = new Color(currentImage.getRGB(i, j + 1));
                Color three = new Color(currentImage.getRGB(i + 1, j + 1));
                Color four = new Color(currentImage.getRGB(i - 1, j));
                Color six = new Color(currentImage.getRGB(i + 1, j));
                Color seven = new Color(currentImage.getRGB(i - 1, j - 1));
                Color eight = new Color(currentImage.getRGB(i, j - 1));
                Color nine = new Color(currentImage.getRGB(i - 1, j - 1));
                colors.add(0, one);
                colors.add(1, two);
                colors.add(2, three);
                colors.add(3, four);
                colors.add(4, five);
                colors.add(5, six);
                colors.add(6, seven);
                colors.add(7, eight);
                colors.add(8, nine);
                // Double for loop which calculates the absolute (squared) difference between the color of each pixel
                // and the color of every other pixel in a 3x3 square
                for (int h = 0; h < 9; h++) {
                    // Current addition is set to 0 after each color is gone through
                    currentAddition = 0;
                    Color current = colors.get(h);
                    // This loop calculates the sum of the squared difference between each pixel
                    // and every other pixel
                    for (int k = 0; k < 9; k++) {
                        currentAddition += Math.pow((current.getRed() - colors.get(k).getRed()), 2)
                                + Math.pow((current.getGreen() - colors.get(k).getGreen()), 2)
                                + Math.pow((current.getBlue() - colors.get(k).getBlue()), 2);
                    }
                    // The calculated value is added to the list of possible median values
                    median.add(h, currentAddition);
                }
                // The index of the lowest value within the median list is found
                int minColor = median.indexOf(Collections.min(median));
                // This index is used to set the center pixel as the median pixel color value as they share an index
                currentImage.setRGB(i, j, colors.get(minColor).getRGB());
                // Both lists are cleared for a new pixel to be calculated
                median.clear();
                colors.clear();
            }
        }
        return currentImage;
    }

    // Function for calculating the edges of an image using the Roberts method
    public static BufferedImage robertsEdgeDetection(BufferedImage sentImage) {
        BufferedImage currentImage = bufferedImageDeepCopy(sentImage);
        int entireX = currentImage.getWidth();
        int entireY = currentImage.getHeight();
        // Creates a 2-d array to hold x and y values
        int[][] colorsOfEdges = new int[entireX][entireY];
        // Sets a variable to be used as the initial gradient value
        int imageGradient = -1;
        // Double for loop to go through each pixel of the image
        for (int i = 1; i < entireX - 1; i++) {
            for (int j = 1; j < entireY - 1; j++) {
                // Sets variables to equal the gray scale of a 2x2 square of pixels including the current pixel
                int pixelOne = getGrayScale(currentImage.getRGB(i, j));
                int pixelTwo = getGrayScale(currentImage.getRGB(i + 1, j));
                int pixelThree = getGrayScale(currentImage.getRGB(i, j - 1));
                int pixelFour = getGrayScale(currentImage.getRGB(i + 1, j - 1));
                // Finds the gx and gy of the current 2x2 square
                int gX = Math.abs((pixelOne) - (pixelFour));
                int gY = Math.abs((pixelTwo) - (pixelThree));
                // Finds the absolute value of the sum of gx and gy
                double gVal = Math.abs((gX) + (gY));
                int g = (int) gVal;
                // Checks whether the gradient needs to be adjusted based on the current pixel
                if (imageGradient < g) {
                    imageGradient = g;
                }
                // Sets the current pixel location as the value of the g function
                colorsOfEdges[i][j] = g;
            }
        }
        // Determines the scale based on the max rgb value and the adjusted gradient value to keep the value
        // from going out of the bounds of rgb
        double scale = 255.0 / imageGradient;
        // Double for loop that goes through each pixel again with the new scale value
        for (int i = 1; i < entireX - 1; i++) {
            for (int j = 1; j < entireY - 1; j++) {
                // Sets the int value as the g value of the current pixel from the 2-d array
                int colorOfEdge = colorsOfEdges[i][j];
                // Sets the int value as the product of it and the scale to keep it within the bounds of rgb
                colorOfEdge = (int) (colorOfEdge * scale);
                // Converts the value back into rgb
                colorOfEdge = 0xff000000 | (colorOfEdge << 16) | (colorOfEdge << 8) | colorOfEdge;
                // Sets the value of the current pixel as the newly calculated rgb value
                currentImage.setRGB(i, j, colorOfEdge);
            }
        }
        return currentImage;
    }

    //Function for calculating the edges of an image using the Sobel method
    public static BufferedImage sobelEdgeDetection(BufferedImage sentImage) {
        // Works in the exact same way as the Roberts edge detection method except for using a 3x3 square
        // instead of a 2x2 square
        BufferedImage currentImage = bufferedImageDeepCopy(sentImage);
        int entireX = currentImage.getWidth();
        int entireY = currentImage.getHeight();
        int[][] colorsOfEdges = new int[entireX][entireY];
        int imageGradient = -1;
        for (int i = 1; i < entireX - 1; i++) {
            for (int j = 1; j < entireY - 1; j++) {
                int pixelOne = getGrayScale(currentImage.getRGB(i - 1, j - 1));
                int pixelTwo = getGrayScale(currentImage.getRGB(i - 1, j));
                int pixelThree = getGrayScale(currentImage.getRGB(i - 1, j + 1));
                int pixelFour = getGrayScale(currentImage.getRGB(i, j - 1));
                int pixelFive = getGrayScale(currentImage.getRGB(i, j + 1));
                int pixelSix = getGrayScale(currentImage.getRGB(i + 1, j - 1));
                int pixelSeven = getGrayScale(currentImage.getRGB(i + 1, j));
                int pixelEight = getGrayScale(currentImage.getRGB(i + 1, j + 1));
                int gX = Math.abs(((-1 * pixelOne) + (pixelThree))
                        + ((-2 * pixelFour) + (2 * pixelFive))
                        + ((-1 * pixelSix) + (pixelEight)));
                int gY = Math.abs(((-1 * pixelOne) + (-2 * pixelTwo) + (-1 * pixelThree))
                        + ((pixelSix) + (2 * pixelSeven) + (pixelEight)));
                double gVal = Math.abs((gX) + (gY));
                int g = (int) gVal;
                if (imageGradient < g) {
                    imageGradient = g;
                }
                colorsOfEdges[i][j] = g;
            }
        }
        double scale = 255.0 / imageGradient;
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

    // Method which calls paint to create a window, paint the images on it, and call the filters and edge detection
    public void paint(Graphics graphics) {
        // Draws the original noisy images and labels
        graphics.drawString("Original image", 0, 15);
        graphics.drawString("Original image", 250, 15);
        graphics.drawString("Original image", 500, 15);
        graphics.drawString("Original image", 750, 15);
        graphics.drawImage(OGImageOne, 0, 20, 250, 250, this);
        graphics.drawImage(OGImageTwo, 250, 20, 250, 250, this);
        graphics.drawImage(OGImageThree, 500, 20, 250, 250, this);
        graphics.drawImage(OGImageOne, 750, 20, 250, 250, this);
        // Draws the images with median filters and average filters
        graphics.drawString("Median filtering", 0, 285);
        graphics.drawString("Median filtering", 250, 285);
        graphics.drawString("Mean filtering", 500, 285);
        graphics.drawString("Mean filtering", 750, 285);
        graphics.drawImage(medianFilter(OGImageOne), 0, 290, 250, 250, this);
        graphics.drawImage(medianFilter(OGImageTwo), 250, 290, 250, 250, this);
        graphics.drawImage(averageFilter(OGImageThree), 500, 290, 250, 250, this);
        graphics.drawImage(averageFilter(OGImageOne), 750, 290, 250, 250, this);
        // Draws the images with Sobel and Roberts edge detection after median and average filtering
        graphics.drawString("Sobel edge detection", 0, 555);
        graphics.drawString("Roberts edge detection", 250, 555);
        graphics.drawString("Sobel edge detection", 500, 555);
        graphics.drawString("Roberts edge detection", 750, 555);
        graphics.drawImage(sobelEdgeDetection(medianFilter(OGImageOne)), 0, 560, 250, 250, this);
        graphics.drawImage(robertsEdgeDetection(medianFilter(OGImageTwo)), 250, 560, 250, 250, this);
        graphics.drawImage(sobelEdgeDetection(averageFilter(OGImageThree)), 500, 560, 250, 250, this);
        graphics.drawImage(robertsEdgeDetection(averageFilter(OGImageOne)), 750, 560, 250, 250, this);
    }

    // Main method which creates the frame, gives it a size, and gives it a title
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Image De-noise and Edge Detector");
        frame.getContentPane().add(new AIAssignmentThree());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020, 850);
        frame.setVisible(true);
    }
}
