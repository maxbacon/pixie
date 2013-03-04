package maxbacon.pixie.replace;

import java.io.File;
import java.util.Map;

public class ReplaceArgs {

   public final boolean hasErrors;
   public final File    source;
   public final File    destination;
   public final String  algo;

   public ReplaceArgs(Map<String, String> argMap) {
      boolean _hasErrors = false;
      String _tempFile = argMap.get("source");
      File src = null;
      if (_tempFile == null) {
         System.err.println(" missing argument --source <path>");
         _hasErrors = true;
      } else {
         src = new File(_tempFile);
         if (!src.exists()) {
            System.err.println("file specified by --file must exist");
            _hasErrors = true;
         }
         if (!src.isDirectory()) {
            System.err.println("file specified by --file must be a directory");
            _hasErrors = true;
         }
      }

      String algo = argMap.get("algo");
      if (algo == null) {
         System.err.println(" missing argument --algo <#0,0=>-1,...,>");
         _hasErrors = true;
      }
      this.algo = algo;

      _tempFile = argMap.get("dest");
      File dest = null;
      if (_tempFile == null) {
         System.err.println(" missing argument --dest <path>");
         _hasErrors = true;
      } else {
         dest = new File(_tempFile);
         if (!dest.exists()) {
            System.err.println("path specified by --output must exist");
            _hasErrors = true;
         }
         if (!dest.isDirectory()) {
            System.err.println("path specified by --output must be a directory");
            _hasErrors = true;
         }
      }

      this.hasErrors = _hasErrors;
      this.source = src;
      this.destination = dest;
   }

}
