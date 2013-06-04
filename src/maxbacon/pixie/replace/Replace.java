package maxbacon.pixie.replace;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import maxbacon.pixie.Common;

public class Replace {
   
   private static final Color CLEAR = new Color(255, 255, 255, 0); 
   public interface Action {
      public void evaluate(BufferedImage image, Map<Integer, Integer> replacement);
   }

   public static List<Action> parse(String algo) {
      ArrayList<Action> actions = new ArrayList<Replace.Action>();
      String[] script = algo.trim().split(";");
      for (int k = 0; k < script.length; k++) {
         script[k] = script[k].toLowerCase().trim().replaceAll(" ", "");
         String action = script[k];
         if (action.matches("[0-9]*,[0-9]*=[\\-0-9#]*(clear)?")) {
            String[] temp = action.split("=");
            String[] point = temp[0].split(",");
            final int x = Integer.parseInt(point[0]);
            final int y = Integer.parseInt(point[1]);
            final int r;
            if ("clear".equals(temp[1])) {
               r = CLEAR.getRGB();
            } else {
               r = Integer.parseInt(temp[1]);
            }
            actions.add(new Action() {
               public void evaluate(BufferedImage image, Map<Integer, Integer> replacement) {
                  int old  = image.getRGB(x, y);
                  System.out.println("R:" + x+ "," + y + "=" + old + "-->" + r);
                  replacement.put(old, r);
               }
            });
         }
      }
      return actions;
   }

   public static void perform(ReplaceArgs args) throws Exception {
      List<Action> actions = parse(args.algo);
      for (File input : args.source.listFiles()) {
         BufferedImage image = ImageIO.read(input);
         Map<Integer, Integer> replacement = new HashMap<>();
         for (Action action : actions) {
            action.evaluate(image, replacement);
         }
         BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
         for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
               int rgb = image.getRGB(x, y);
               if (replacement.containsKey(rgb)) {
                  rgb = replacement.get(rgb);
               }
               image2.setRGB(x, y, rgb);
            }
         }
         ImageIO.write(image2, Common.getFormat(input.getName()), new File(args.destination, input.getName()));
      }
   }
}
