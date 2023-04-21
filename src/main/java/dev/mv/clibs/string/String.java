package dev.mv.clibs.string;

import dev.mv.clibs.stddef.Char;
import dev.mv.clibs.stddef.I32;
import dev.mv.clibs.stdlib.Pointer;

public class String {

    public static I32 strlen(Pointer<Char> ptr) {
        int len = 0;
        while (Char.deref(ptr.deref(len)).get() != '\0') {
            len++;
        }
        return new I32(len);
    }

    public static I32 strcmp(Pointer<Char> ptr1, Pointer<Char> ptr2) {
        int len1 = strlen(ptr1).get();
        int len2 = strlen(ptr2).get();
        for (int i = 0; i < Math.min(len1, len2); i++) {
            Char c1 = Char.deref(ptr1.deref(i));
            Char c2 = Char.deref(ptr2.deref(i));
            if (c1.get() != c2.get()) {
                return new I32(c1.get() - c2.get());
            }
        }
        if (len1 < len2) {
            return new I32(Char.deref(ptr2.deref(len1)).get());
        }
        if (len1 > len2) {
            return new I32(Char.deref(ptr1.deref(len2)).get());
        }
        return new I32(0);
    }

    public static void strcpy(Pointer<Char> dst, Pointer<Char> src) {
        int len = strlen(src).get();
        for (int i = 0; i < len; i++) {
            dst.set(i, new Char(Char.deref(src.deref(i)).get()));
        }
    }

    public static void strcpy(Pointer<Char> dst, java.lang.String src) {
        for (int i = 0; i < src.length(); i++) {
            dst.set(i, new Char(src.charAt(i)));
        }
    }

}
