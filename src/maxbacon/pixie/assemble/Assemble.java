package maxbacon.pixie.assemble;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Hex;

public class Assemble {

   private static class BlitState {
      private int x;
      private int y;
      private int w;
      private int h;
      private int lineHeight;
      
      private final MessageDigest signature;
      
      public BlitState() throws Exception {
         signature = MessageDigest.getInstance("MD5");
      }
      
      private void update() throws Exception {
         signature.update((x + "," + y + "," + w + "," + h).getBytes("UTF-8"));
      }

      private void resetPosition() {
         this.x = 0;
         this.y = 0;
         lineHeight = 0;
      }
   }

   private static interface AssembleCommand {
      public void blit(BlitState blitState, BufferedImage targetImage);

      public void adv(BlitState blitState);
   }

   private static class BlitTile implements AssembleCommand {

      private final String        filename;
      private final BufferedImage image;

      private BlitTile(String filename, BufferedImage image) {
         this.filename = filename;
         this.image = image;
      }

      @Override
      public void adv(BlitState blitState) {
         blitState.x += image.getWidth();
         blitState.lineHeight = Math.max(image.getHeight(), blitState.lineHeight);
         blitState.w = Math.max(blitState.w, blitState.x);
         blitState.h = Math.max(blitState.h, blitState.y + image.getHeight());
      }

      @Override
      public void blit(BlitState blitState, BufferedImage targetImage) {
         for (int v = 0; v < image.getHeight(); v++) {
            for (int u = 0; u < image.getWidth(); u++) {
               targetImage.setRGB(blitState.x + u, blitState.y + v, image.getRGB(u, v));
            }
         }
         System.out.println("#" + blitState.x + "," + blitState.y + "," + image.getWidth() + "," + image.getHeight() + "#" + filename);
         adv(blitState);
      }
   }

   private static class NewLine implements AssembleCommand {
      @Override
      public void adv(BlitState blitState) {
         blitState.x = 0;
         blitState.y += blitState.lineHeight;
         blitState.lineHeight = 0;
      }

      @Override
      public void blit(BlitState blitState, BufferedImage targetImage) {
         adv(blitState);
      }
   }

   private static List<AssembleCommand> compile(File file) throws Exception {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      try {
         ArrayList<AssembleCommand> commands = new ArrayList<Assemble.AssembleCommand>();
         String ln;
         boolean lastCommandWasNewLine = true;
         while ((ln = reader.readLine()) != null) {
            ln = ln.trim();
            if (ln.length() == 0) {
               if (!lastCommandWasNewLine)
                  commands.add(new NewLine());
               lastCommandWasNewLine = true;
               continue;
            }
            commands.add(new BlitTile(ln, ImageIO.read(new File(ln))));
            lastCommandWasNewLine = false;
         }
         if (!lastCommandWasNewLine) {
            commands.add(new NewLine());
         }
         return commands;
      } finally {
         reader.close();
      }
   }

   public static void perform(AssembleArgs args) throws Exception {
      List<AssembleCommand> script = compile(args.file);

      // compute how big the image will be
      BlitState state = new BlitState();
      for (AssembleCommand cmd : script) {
         cmd.adv(state);
         state.update();
      }
      state.resetPosition();
      System.out.println("##signature:" + Hex.encodeHexString(state.signature.digest()));

      // create the final image
      BufferedImage targetImage = new BufferedImage(state.w, state.h, BufferedImage.TYPE_INT_ARGB);
      for (AssembleCommand cmd : script) {
         cmd.blit(state, targetImage);
      }

      ImageIO.write(targetImage, args.imageFormat, args.output);
   }
}
