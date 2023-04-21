package dev.mv.clibs.stdio;

import dev.mv.clibs.stddef.*;
import dev.mv.clibs.stdlib.Pointer;
import dev.mv.clibs.stdlib.StdLib;
import dev.mv.utils.Utils;

public class Stdio {

    public static void printf(String format, Object... args) {
        char[] chars = format.toCharArray();
        int i = 0, j = 0;
        while (i < args.length) {
            while ((chars[j] != '%' || chars[j + 1] == '%') && j < chars.length) j++;
            j++;
            if (args[i] instanceof Pointer<?>) {
                if (chars[j] == 's') {
                    Pointer<Char> str = (Pointer<Char>) args[i];
                    args[i] = StdLib.toStr(str);
                }
                else {
                    Pointer<?> ptr = (Pointer<?>) args[i];
                    args[i] = ptr.deref();
                }
            }
            else {
                args[i] = Utils.<Class, Object>matchReturn(args[i].getClass())
                    .occasion(Char.class, ((Char) args[i]).get())
                    .occasion(Bool.class, ((Bool) args[i]).get())
                    .occasion(I8.class, ((I8) args[i]).get())
                    .occasion(I16.class, ((I16) args[i]).get())
                    .occasion(I32.class, ((I32) args[i]).get())
                    .occasion(I64.class, ((I64) args[i]).get())
                    .occasion(F32.class, ((F32) args[i]).get())
                    .occasion(F64.class, ((F64) args[i]).get())
                    .__(args[i])
                    .value();
            }
            i++;
        }
        System.out.printf(format, args);
    }

}
