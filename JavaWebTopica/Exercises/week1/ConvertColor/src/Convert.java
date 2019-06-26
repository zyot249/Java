import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Convert {
    public void readBitmapImage(String fileName) throws Exception {
        // get file input
        Path inputImagePath = Paths.get(fileName);
        String inputPath = inputImagePath.toAbsolutePath().toString();
        File inputFile = new File(inputPath);
        BufferedImage image = ImageIO.read(inputFile);
        for (int xPixel = 0; xPixel < image.getWidth(); xPixel++) {
            for (int yPixel = 0; yPixel < image.getHeight(); yPixel++) {
                int color = image.getRGB(xPixel, yPixel);
                int newColor = red2blue(color);
                if (newColor != 0)
                    image.setRGB(xPixel,yPixel,newColor);
            }
        }
        // create file output
        Path outputImagePath = Paths.get("outputimage.bmp");
        String outputPath = outputImagePath.toAbsolutePath().toString();
        File outputFile = new File(outputPath);
        ImageIO.write(image, "bmp", outputFile);
    }

    public int red2blue(int redRGB) {
        Color redColor = new Color(redRGB);
        int red = redColor.getRed();
        int green = redColor.getGreen();
        int blue = redColor.getBlue();
        float hsb[] = new float[3];
        Color.RGBtoHSB(red, green, blue, hsb);
        float blueHue = hsb[0] * 360;
        if ( blueHue <= 11 || blueHue >= 311) {
            blueHue = (hsb[0] + 240) % 360; // move from tone red --> blue
        } else {
            blueHue = 0;
        }
        if (blueHue != 0)
            return Color.HSBtoRGB(blueHue/360, hsb[1], hsb[2]);
        else return 0;
    }
}
