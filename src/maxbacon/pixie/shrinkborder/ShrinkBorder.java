package maxbacon.pixie.shrinkborder;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import maxbacon.pixie.Common;

public class ShrinkBorder {

   private static boolean areVerticalLinesEqual(BufferedImage image, int a, int b) {
      for (int y = 0; y < image.getHeight(); y++)
         if (image.getRGB(a, y) != image.getRGB(b, y))
            return false;
      return true;
   }

   private static boolean areHorizontalLinesEqual(BufferedImage image, int a, int b) {
      for (int x = 0; x < image.getWidth(); x++)
         if (image.getRGB(x, a) != image.getRGB(x, b))
            return false;
      return true;
   }

   private static void shrink(File file, File dest) throws Exception {
      BufferedImage image = ImageIO.read(file);
      int newLeft = 0;
      int newRight = image.getWidth() - 1;
      int newTop = 0;
      int newBottom = image.getHeight() - 1;

      while (newLeft + 1 < image.getWidth() && areVerticalLinesEqual(image, newLeft, newLeft + 1)) {
         newLeft++;
      }
      while (newRight - 1 > newLeft && areVerticalLinesEqual(image, newRight, newRight - 1)) {
         newRight--;
      }
      while (newTop + 1 < image.getHeight() && areHorizontalLinesEqual(image, newTop, newTop + 1)) {
         newTop++;
      }
      while (newBottom - 1 > newTop && areHorizontalLinesEqual(image, newBottom, newBottom - 1)) {
         newBottom--;
      }
      BufferedImage shrunk = new BufferedImage(newRight - newLeft + 1, newBottom - newTop + 1, BufferedImage.TYPE_INT_ARGB);
      for (int v = newTop; v <= newBottom; v++) {
         for (int u = newLeft; u <= newRight; u++) {
            shrunk.setRGB(u - newLeft, v - newTop, image.getRGB(u, v));
         }
      }
      ImageIO.write(shrunk, Common.getFormat(dest.getName()), dest);
   }

   public static void perform(ShrinkBorderArgs args) throws Exception {
      File[] files = args.inputPath.listFiles();
      for (File file : files) {
         File dest = new File(args.outputPath, "shrunk-" + file.getName());
         System.out.println("# Shrinking " + file.getPath() + " to " + dest.getPath());
         shrink(file, dest);
      }
   }
}
