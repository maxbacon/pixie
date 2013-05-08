package maxbacon.pixie.hueshift;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HueShift {

   public static void perform(HueShiftArgs args) throws Exception {
      BufferedImage image = ImageIO.read(args.file);
      BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
      for (int y = 0; y < image.getHeight(); y++) {
         for (int x = 0; x < image.getWidth(); x++) {
            int rgb = image.getRGB(x, y);
            Color oldColor = new Color(rgb, true);
            float[] hsb = Color.RGBtoHSB(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(), null);
            hsb[0] += args.hueShift / 360.0;
            while (hsb[0] > 1.0f)
               hsb[0] -= 1.0f;
            Color newColorWithoutAlpha = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            Color newColor = new Color(newColorWithoutAlpha.getRed(), newColorWithoutAlpha.getGreen(), newColorWithoutAlpha.getBlue(), oldColor.getAlpha());
            image2.setRGB(x, y, newColor.getRGB());
         }
      }
      ImageIO.write(image2, args.imageFormat, args.output);
   }
}
