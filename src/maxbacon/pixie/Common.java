package maxbacon.pixie;

public class Common {
   public static String getFormat(String filename) {
      String ext = filename.toLowerCase();
      if (ext.endsWith(".png"))
         return "png";
      if (ext.endsWith(".jpg") || ext.endsWith(".jpeg"))
         return "jpg";
      return null;
   }
}
