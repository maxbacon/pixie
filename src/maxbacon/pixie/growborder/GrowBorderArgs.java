package maxbacon.pixie.growborder;

import java.io.File;
import java.util.Map;

public class GrowBorderArgs {

   public final File    inputPath;
   public final File    outputPath;
   public final boolean hasErrors;
   public final int     width;
   public final int     height;

   public GrowBorderArgs(Map<String, String> argMap) {
      boolean _hasErrors = false;
      String input = argMap.get("input");
      if (input == null) {
         System.err.println(" --input path must be specified");
         this.inputPath = null;
         _hasErrors = true;
      } else {
         this.inputPath = new File(input);
         if (!inputPath.exists()) {
            System.err.println(" --input path must exist");
            _hasErrors = true;
         }
         if (!inputPath.isDirectory()) {
            System.err.println(" --input path must be a directory");
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
      this.width = getPositiveInt(argMap, "width");
      this.height = getPositiveInt(argMap, "height");
      if (this.width <= 0 || this.height <= 0)
         _hasErrors = true;
      this.hasErrors = _hasErrors;
   }

   private int getPositiveInt(Map<String, String> argMap, String name) {
      String _val = argMap.get(name);
      if (_val == null) {
         System.err.println(" --" + name + " must be specified");
         return -1;
      } else {
         try {
            int v = Integer.parseInt(_val);
            if (v <= 0) {
               System.err.println(" --width must be positive (>0)");
               return -1;
            }
            return v;
         } catch (NumberFormatException nfe) {
            System.err.println(" --width must be an integer");
            return -1;
         }
      }
   }
}
