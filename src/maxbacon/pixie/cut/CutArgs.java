package maxbacon.pixie.cut;

import java.io.File;
import java.util.Map;

/**
 * Validates that the arguments are valid
 * 
 * @author bacon
 * 
 */
public class CutArgs {

   public final boolean hasErrors;
   public final File    file;
   public final int     width;
   public final int     height;
   public final File    output;
   public final String  format;
   public final String  imageFormat;

   public CutArgs(Map<String, String> argMap) {
      boolean _hasErrors = false;
      String _tempFile = argMap.get("file");
      File file = null;
      if (_tempFile == null) {
         System.err.println(" missing argument --file <filename.png>");
         _hasErrors = true;
      } else {
         file = new File(_tempFile);
         if (!file.exists()) {
            System.err.println("file specified by --file must exist");
            _hasErrors = true;
         }
         if (file.isDirectory()) {
            System.err.println("file specified by --file must not be a directory");
            _hasErrors = true;
         }
      }

      int _width = 0;
      int _height = 0;
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

      _tempFile = argMap.get("output");
      File output = null;
      if (_tempFile == null) {
         System.err.println(" missing argument --output <path>");
         _hasErrors = true;
      } else {
         output = new File(_tempFile);
         if (!output.exists()) {
            System.err.println("path specified by --output must exist");
            _hasErrors = true;
         }
         if (!output.isDirectory()) {
            System.err.println("path specified by --output must be a directory");
            _hasErrors = true;
         }
      }

      String format = argMap.get("format");
      String _imageFormat = null;
      if (format == null) {
         System.err.println(" missing argument --format <path>");
         _hasErrors = true;
      } else {
         if (format.indexOf("%x") < 0) {
            System.err.println(" format specified by --format is missing %x");
            _hasErrors = true;
         }
         if (format.indexOf("%y") < 0) {
            System.err.println(" format specified by --format is missing %y");
            _hasErrors = true;
         }
         _imageFormat = getFormat(format);
         if (_imageFormat == null) {
            System.err.println(" format specified by --format must end with .png or .jpg");
            _hasErrors = true;
         }
      }
      this.hasErrors = _hasErrors;
      this.file = file;
      this.width = _width;
      this.height = _height;
      this.output = output;
      this.format = format;
      this.imageFormat = _imageFormat;
   }

   private static String getFormat(String format) {
      String ext = format.toLowerCase();
      if (ext.endsWith(".png"))
         return "png";
      if (ext.endsWith(".jpg") || ext.endsWith(".jpeg"))
         return "jpg";
      return null;
   }
}
