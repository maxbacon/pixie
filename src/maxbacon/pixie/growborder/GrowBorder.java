package maxbacon.pixie.growborder;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import maxbacon.pixie.Common;

public class GrowBorder {
   private static void grow(File file, File dest, int desiredW, int desiredH) throws Exception {
      BufferedImage image = ImageIO.read(file);
      int w = Math.max(image.getWidth(), desiredW);
      int h = Math.max(image.getHeight(), desiredH);
      BufferedImage grown = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      int border = image.getRGB(0, 0);
      int offsetx = (w - image.getWidth()) / 2;
      int offsety = (h - image.getHeight()) / 2;
      for (int y = 0; y < h; y++) {
         for (int x = 0; x < w; x++) {
            grown.setRGB(x, y, border);
         }
      }
      for (int y = 0; y < image.getHeight(); y++) {
         for (int x = 0; x < image.getWidth(); x++) {
            grown.setRGB(x + offsetx, y + offsety, image.getRGB(x, y));
         }
      }

      ImageIO.write(grown, Common.getFormat(dest.getName()), dest);
   }

   public static void perform(GrowBorderArgs args) throws Exception {
      File[] files = args.inputPath.listFiles();
      for (File file : files) {
         File dest = new File(args.outputPath, "grown-" + file.getName());
         System.out.println("# Growing " + file.getPath() + " to " + dest.getPath());
         grow(file, dest, args.width, args.height);
      }
   }
}
