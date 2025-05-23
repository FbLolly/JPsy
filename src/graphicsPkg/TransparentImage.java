package graphicsPkg;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class TransparentImage {
    public static Image getTransparentImage(Image image, int transparency) { //0 is invisible, 255 is opaque
        if (image == null) return image;

        BufferedImage img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        int colorMask = 0x00FFFFFF; //AARRGGBB
        int alphaShift = 24;
        for (int y = 0; y < img.getHeight(); y++)
            for (int x = 0; x < img.getWidth(); x++)
                img.setRGB(x, y, (img.getRGB(x, y) & colorMask) | (transparency << alphaShift));

        return img;
    }
}
