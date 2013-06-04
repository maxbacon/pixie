package maxbacon.pixie.shrinkborder;

import java.io.File;
import java.util.Map;

public class ShrinkBorderArgs {

   public final File    inputPath;
   public final File    outputPath;
   public final boolean hasErrors;

   public ShrinkBorderArgs(Map<String, String> argMap) {
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
      this.hasErrors = _hasErrors;
   }
}
