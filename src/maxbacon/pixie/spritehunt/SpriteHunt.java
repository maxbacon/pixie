package maxbacon.pixie.spritehunt;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SpriteHunt {

   private static void extractSprite(SpriteHuntArgs args, BufferedImage image, int x, int y, int index) throws Exception {
      int right = x;
      while (image.getRGB(right, y) == args.hunt) {
         right++;
      }
      int bottom = y;
      while (image.getRGB(x, bottom) == args.hunt) {
         bottom++;
      }
      BufferedImage extracted = new BufferedImage(right - 1 - (x + 1), bottom - 1 - (y + 1), BufferedImage.TYPE_INT_ARGB);
      for (int v = y + 1; v < bottom - 1; v++) {
         for (int u = x + 1; u < right - 1; u++) {
            extracted.setRGB(u - (x + 1), v - (y + 1), image.getRGB(u, v));
         }
      }
      String newFile = args.prefix + "-" + index + "." + args.format;
      System.out.println("# Made " + newFile);
      ImageIO.write(extracted, args.format, new File(args.outputPath, newFile));
   }

   public static void perform(SpriteHuntArgs args) throws Exception {
      BufferedImage image = ImageIO.read(args.inputFile);
      int index = 0;
      for (int y = 0; y < image.getHeight(); y++) {
         for (int x = 0; x < image.getWidth(); x++) {
            int rgb = image.getRGB(x, y);
            boolean hasHuntAbove = y > 0 ? image.getRGB(x, y - 1) == args.hunt : false;
            boolean hasHuntLeft = x > 0 ? image.getRGB(x - 1, y) == args.hunt : false;
            if (!(hasHuntAbove || hasHuntLeft) && rgb == args.hunt) {
               extractSprite(args, image, x, y, index);
               index++;
            }
         }
      }
   }
}
