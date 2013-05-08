package maxbacon.pixie.hueshift;

import java.io.File;
import java.util.Map;

public class HueShiftArgs {

   public final boolean hasErrors;
   public final File    file;
   public final File    output;
   public final String  imageFormat;
   public final double  hueShift;

   public HueShiftArgs(Map<String, String> argMap) {
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

      _tempFile = argMap.get("output");
      String _imageFormat = null;
      File output = null;
      if (_tempFile == null) {
         System.err.println(" missing argument --output <path>");
         _hasErrors = true;
      } else {
         output = new File(_tempFile);
         if (output.isDirectory()) {
            System.err.println("path specified by --output must not be a directory");
            _hasErrors = true;
         }
         _imageFormat = getFormat(_tempFile);
         if (_imageFormat == null) {
            System.err.println(" output file specified by --output must end with .png or .jpg");
            _hasErrors = true;
         }
      }

      String _hueShift = argMap.get("shift");
      double __hueShift = 0;
      if (_hueShift == null) {
         System.err.println(" missing argument --shift <degrees>");
         _hasErrors = true;
      } else {
         try {
            __hueShift = Double.parseDouble(_hueShift);
         } catch (NumberFormatException nfe) {
            System.err.println(" --shift argument not a double");
            _hasErrors = true;
         }
      }

      this.hasErrors = _hasErrors;
      this.file = file;
      this.output = output;
      this.imageFormat = _imageFormat;
      this.hueShift = __hueShift;
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
