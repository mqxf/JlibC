package dev.mv.clibs.stdlib;

import dev.mv.clibs.Main;
import dev.mv.clibs.errno.SIGSEGV;
import dev.mv.clibs.stddef.Char;
import dev.mv.clibs.stddef.I32;

import static dev.mv.clibs.stdlib.StdLib.*;

public class Init {

    public static void main(String[] args) {
        try {
            Ram.init();
            I32 argc = new I32(args.length);
            Pointer<Pointer<Char>> argv = callocPointer(args.length);
            for (int i = 0; i < args.length; i++) {
                argv.set(i, toPointer(args[i]));
            }
            int res = Main.main(argc, argv);
            System.exit(res);
        } catch (SIGSEGV s) {
            System.err.println("Segmentation fault (core dumped)");
            System.exit(11);
        } catch (Throwable ignored) {

        }
    }

}
