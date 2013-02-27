package maxbacon.pixie;

import java.util.HashMap;
import java.util.Map;

import maxbacon.pixie.assemble.Assemble;
import maxbacon.pixie.assemble.AssembleArgs;
import maxbacon.pixie.cut.Cut;
import maxbacon.pixie.cut.CutArgs;

public class Pixie {

   public static void main(String[] args) throws Exception {
      Map<String, String> argMap = assembleArgs(args);
      if (test(args, "cut", "--file <filename.png> --width <32> --height <32> --output <path> --format")) {
         CutArgs cutArgs = new CutArgs(argMap);
         if (!cutArgs.hasErrors) {
            Cut.perform(cutArgs);
         }
      }

      if (test(args, "assemble", "--file <input.script> --output <filename.png> --format")) {
         AssembleArgs assembleArgs = new AssembleArgs(argMap);
         if (!assembleArgs.hasErrors) {
            Assemble.perform(assembleArgs);
         }
      }
   }

   private static Map<String, String> assembleArgs(String[] args) {
      HashMap<String, String> map = new HashMap<>();
      for (int k = 0; k + 1 < args.length; k++) {
         if (args[k].startsWith("--")) {
            map.put(args[k].trim().toLowerCase().substring(2), args[k + 1]);
         }
      }
      return map;
   }

   private static boolean test(String[] args, String command, String help) {
      if (args.length == 0) {
         System.err.println("Usage: pixie " + command + " " + help);
         return false;
      }
      return args[0].equalsIgnoreCase(command);
   }
}
