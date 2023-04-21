package dev.mv.clibs.stdlib;

import dev.mv.clibs.stddef.CType;
import dev.mv.clibs.stddef.Char;
import dev.mv.clibs.stddef.I64;

public class StdLib {

    public static int sizeof(CType type) {
        try {
            return (Integer) type.getClass().getDeclaredMethod("sizeof").invoke(type);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int sizeof(Class<? extends CType> clazz) {
        try {
            return (Integer) clazz.getDeclaredMethod("sizeof").invoke(StdLib.class);
        } catch (Exception e) {
            return 0;
        }
    }

    public static <T extends CType> Pointer<T> malloc(int size) {
        long addr = Ram.malloc(Pointer.sizeof());
        I64 ptr = I64.deref(addr);
        I64 element = new I64(0);
        ptr.set(element.addr());
        element.set(Ram.malloc(size));
        return new Pointer<>(addr, ptr);
    }

    public static <T extends CType> Pointer<T> calloc(int n, int size) {
        long addr = Ram.malloc(Pointer.sizeof());
        I64 ptr = I64.deref(addr);
        long arr = Ram.calloc(n, I64.sizeof());
        ptr.set(arr);
        for (int i = 0; i < size; i++) {
            I64 element = I64.deref(Ram.increment(arr, i * I64.sizeof()));
            element.set(Ram.calloc(1, size));
        }
        return new Pointer<>(addr, ptr);
    }

    public static <T extends CType> Pointer<T> realloc(Pointer<T> ptr, int size) {
        I64 element = I64.deref(ptr.deref());
        element.set(Ram.realloc(element.get(), size));
        return ptr;
    }

    public static <T extends CType> Pointer<T> realloc(Pointer<T> ptr, int n, int size) {
        long arr = ptr.deref();
        for (int i = 0; i < size; i++) {
            I64 element = I64.deref(Ram.increment(arr, i * I64.sizeof()));
            element.set(Ram.realloc(element.get(), size));
        }
        I64 addr = I64.deref(ptr.addr());
        arr = Ram.realloc(addr.get(), n * I64.sizeof());
        ptr.set(arr);
        return ptr;
    }

    public static <T extends CType> Pointer<T> mallocPointer() {
        long addr = Ram.malloc(Pointer.sizeof());
        I64 ptr = I64.deref(addr);
        I64 val = new I64(0);
        ptr.set(val.addr());
        return new Pointer<>(addr, ptr);
    }

    public static <T extends CType> Pointer<T> callocPointer(int n) {
        long addr = Ram.malloc(Pointer.sizeof());
        I64 ptr = I64.deref(addr);
        long arr = Ram.calloc(n, I64.sizeof());
        ptr.set(arr);
        return new Pointer<>(addr, ptr);
    }

    public static <T extends CType> Pointer<T> reallocPointer(Pointer<T> ptr, int n) {
        I64 addr = I64.deref(ptr.addr());
        long arr = Ram.realloc(addr.get(), n * I64.sizeof());
        ptr.set(arr);
        return ptr;
    }

    public static <T extends CType> void free(Pointer<T> ptr) {
        long addr = ptr.deref();
        Ram.free(addr);
        Ram.free(ptr.addr());
    }

    public static void free(CType type) {
        Ram.free(type.addr());
    }

    public static <T extends CType> void memcpy(Pointer<T> src, Pointer<T> dst, int n) {
        for (int i = 0; i < n; i++) {
            dst.set(i, src.deref(i));
        }
    }

    public static Pointer<Char> toPointer(String s) {
        Pointer<Char> ptr = calloc(s.length() + 1, Char.sizeof());
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            ptr.set(i, new Char(chars[i]));
        }
        ptr.set(chars.length, new Char('\0'));
        return ptr;
    }

    public static String toStr(Pointer<Char> ptr) {
        int len = 0;
        while (Char.deref(ptr.deref(len)).get() != '\0') {
            len++;
        }
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = Char.deref(ptr.deref(i)).get();
        }
        return new String(chars);
    }

}
