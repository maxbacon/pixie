package maxbacon.pixie.resize;

import java.io.File;
import java.util.Map;

public class ResizeArgs {

   public final boolean hasErrors;
   public final File    source;
   public final File    destination;
   public final int     width;
   public final int     height;
   public final int hints;

   public ResizeArgs(Map<String, String> argMap) {
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

      int _width = 0;
      int _height = 0;
      int _hints = 5;
      String width = argMap.get("width");
      if (width == null) {
         System.err.println(" missing argument --width <32>");
         _hasErrors = true;
      } else {
         try {
            _width = Integer.parseInt(width);
         } catch (Exception err) {
            System.err.println(" width specified by --width is not an integer");
            _hasErrors = true;
         }
      }

      String height = argMap.get("height");
      if (height == null) {
         System.err.println(" missing argument --height <32>");
         _hasErrors = true;
      } else {
         try {
            _height = Integer.parseInt(height);
         } catch (Exception err) {
            System.err.println(" height specified by --height is not an integer");
            _hasErrors = true;
         }
      }

      String hints = argMap.get("hints");
      if (height == null) {
         System.err.println(" missing argument --hints <5>");
         _hasErrors = true;
      } else {
         try {
            _hints = Integer.parseInt(hints);
         } catch (Exception err) {
            System.err.println(" hint specified by --hints is not an integer");
            _hasErrors = true;
         }
      }
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
      this.width = _width;
      this.height = _height;
      this.hints = _hints;
      this.destination = dest;
   }

}
