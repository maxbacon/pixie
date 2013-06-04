package maxbacon.pixie;

import java.util.HashMap;
import java.util.Map;

import maxbacon.pixie.assemble.Assemble;
import maxbacon.pixie.assemble.AssembleArgs;
import maxbacon.pixie.cut.Cut;
import maxbacon.pixie.cut.CutArgs;
import maxbacon.pixie.growborder.GrowBorder;
import maxbacon.pixie.growborder.GrowBorderArgs;
import maxbacon.pixie.hueshift.HueShift;
import maxbacon.pixie.hueshift.HueShiftArgs;
import maxbacon.pixie.replace.Replace;
import maxbacon.pixie.replace.ReplaceArgs;
import maxbacon.pixie.resize.Resize;
import maxbacon.pixie.resize.ResizeArgs;
import maxbacon.pixie.shrinkborder.ShrinkBorder;
import maxbacon.pixie.shrinkborder.ShrinkBorderArgs;
import maxbacon.pixie.spritehunt.SpriteHunt;
import maxbacon.pixie.spritehunt.SpriteHuntArgs;

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

      if (test(args, "resize", "--source <directoryIn> --dest <pathOut> --width <64> --height <64>")) {
         ResizeArgs resizeArgs = new ResizeArgs(argMap);
         if (!resizeArgs.hasErrors) {
            Resize.perform(resizeArgs);
         }
      }

      if (test(args, "replace", "--source <directoryIn> --dest <pathOut> --algo")) {
         ReplaceArgs replaceArgs = new ReplaceArgs(argMap);
         if (!replaceArgs.hasErrors) {
            Replace.perform(replaceArgs);
         }
      }

      if (test(args, "hueshift", "--file <input.png> --output <output.png> --shift <d>")) {
         HueShiftArgs hueShiftArgs = new HueShiftArgs(argMap);
         if (!hueShiftArgs.hasErrors) {
            HueShift.perform(hueShiftArgs);
         }
      }

      if (test(args, "spritehunt", "--input <input.png> [--hunt 255,0,255] --output <path> [--prefix <newfileprefix>]")) {
         SpriteHuntArgs spriteHuntArgs = new SpriteHuntArgs(argMap);
         if (!spriteHuntArgs.hasErrors) {
            SpriteHunt.perform(spriteHuntArgs);
         }
      }

      if (test(args, "shrinkborder", "--input <path> --output <path>")) {
         ShrinkBorderArgs shrinkBorderArgs = new ShrinkBorderArgs(argMap);
         if (!shrinkBorderArgs.hasErrors) {
            ShrinkBorder.perform(shrinkBorderArgs);
         }
      }
      if (test(args, "growborder", "--input <path> --output <path> --width <w> --height <h>")) {
         GrowBorderArgs growBorderArgs = new GrowBorderArgs(argMap);
         if (!growBorderArgs.hasErrors) {
            GrowBorder.perform(growBorderArgs);
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
