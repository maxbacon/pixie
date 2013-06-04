package maxbacon.pixie.spritehunt;

import java.awt.Color;
import java.io.File;
import java.util.Map;

import maxbacon.pixie.Common;

public class SpriteHuntArgs {

   public final int     hunt;
   public final File    inputFile;
   public final File    outputPath;
   public final String  format;
   public final String  prefix;
   public final boolean hasErrors;

   private Integer parseHuntColor(String localHuntColor) {
      String[] partsHuntColor = localHuntColor.split(",");
      int[] huntColor = new int[partsHuntColor.length];
      for (int k = 0; k < huntColor.length; k++) {
         try {
            huntColor[k] = Integer.parseInt(partsHuntColor[k]);
         } catch (NumberFormatException nfe) {
            return null;
         }
      }
      if (huntColor.length == 3) {
         return new Color(huntColor[0], huntColor[1], huntColor[2]).getRGB();
      } else if (huntColor.length == 4) {
         return new Color(huntColor[0], huntColor[1], huntColor[2], huntColor[3]).getRGB();
      } else {
         return null;
      }
   }

   public SpriteHuntArgs(Map<String, String> argMap) {
      boolean _hasErrors = false;
      String huntColor = "255,0,255";
      if (argMap.containsKey("hunt")) {
         huntColor = argMap.get("hunt");
      }

      Integer parsedHuntColor = parseHuntColor(huntColor);
      if (parsedHuntColor == null) {
         _hasErrors = true;
         System.err.println(" hunt specified by --hunt makes no sense '" + huntColor + "'");
         this.hunt = 0;
      } else {
         this.hunt = parsedHuntColor.intValue();
      }

      String input = argMap.get("input");
      if (input == null) {
         System.err.println(" --input file must be specified");
         this.inputFile = null;
         this.format = null;
         _hasErrors = true;
      } else {
         this.inputFile = new File(input);
         this.format = Common.getFormat(input);
         if (!inputFile.exists()) {
            System.err.println(" --input file must exist");
            _hasErrors = true;
         }
         if (!inputFile.isFile()) {
            System.err.println(" --input file must be a file");
            _hasErrors = true;
         }
      }

      String output = argMap.get("output");
      if (output == null) {
         System.err.println(" --output directory must be specified");
         this.outputPath = null;
         _hasErrors = true;
      } else {
         this.outputPath = new File(output);
         if (!outputPath.exists()) {
            System.err.println(" --output file must exist");
            _hasErrors = true;
         }
         if (!outputPath.isDirectory()) {
            System.err.println(" --output file must be a directory");
            _hasErrors = true;
         }
      }

      String prefix = "extracted";
      if (argMap.containsKey("prefix")) {
         prefix = argMap.get("prefix");
      }
      this.prefix = prefix;

      this.hasErrors = _hasErrors;
   }
}
