package maxbacon.pixie.cut;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class Cut {

   public static void perform(CutArgs args) throws Exception {
      BufferedImage img = ImageIO.read(args.file);
      for (int y = 0; y < img.getHeight(); y += args.height) {
         for (int x = 0; x < img.getWidth(); x += args.width) {
            BufferedImage tile = new BufferedImage(args.width, args.height, BufferedImage.TYPE_INT_ARGB);
            for (int v = 0; v < args.height; v++) {
               for (int u = 0; u < args.width; u++) {
                  tile.setRGB(u, v, img.getRGB(x + u, y + v));
               }
            }
            String _output = args.format;
            _output = _output.replaceAll(Pattern.quote("%x"), Integer.toString(x / args.width));
            _output = _output.replaceAll(Pattern.quote("%y"), Integer.toString(y / args.height));
            ImageIO.write(tile, args.imageFormat, new File(args.output, _output));
         }
      }
   }
}
