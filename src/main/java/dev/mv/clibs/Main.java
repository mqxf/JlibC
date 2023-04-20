package dev.mv.clibs;

import dev.mv.clibs.stddef.Char;
import dev.mv.clibs.stddef.I32;
import dev.mv.clibs.stdlib.Pointer;

import static dev.mv.clibs.stdlib.StdLib.*;

public class Main {

    public static int main(I32 argc, Pointer<Pointer<Char>> argv) {
        for (int i = 0; i < argc.get(); i++) {
            System.out.println(toStr(Pointer.of(argv.deref(i))));
        }
        return 0;
    }

}
