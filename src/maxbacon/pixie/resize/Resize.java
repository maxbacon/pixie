package maxbacon.pixie.resize;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Resize {
   
   private static String getFormat(String format) {
      String ext = format.toLowerCase();
      if (ext.endsWith(".png"))
         return "png";
      if (ext.endsWith(".jpg") || ext.endsWith(".jpeg"))
         return "jpg";
      return null;
   }
   
   public static void perform(ResizeArgs args) throws Exception {
      for(File input : args.source.listFiles()) {
         BufferedImage image = ImageIO.read(input);
         Image resized = image.getScaledInstance(args.width, args.height, args.hints);
         BufferedImage resized2 = new BufferedImage(args.width, args.height, BufferedImage.TYPE_INT_ARGB);
         resized2.createGraphics().drawImage(resized, 0, 0, args.width, args.height, null);
         ImageIO.write(resized2, getFormat(input.getName()), new File(args.destination, input.getName()));
      }
   }
}
