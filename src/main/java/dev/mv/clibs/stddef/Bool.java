package dev.mv.clibs.stddef;

import dev.mv.clibs.stdlib.Pointer;
import dev.mv.clibs.stdlib.Ram;

public final class Bool implements Primitive {

    private long address;

    Bool(long address, boolean ignore) {
        this.address = address;
    }

    public Bool() {
        address = Ram.calloc(1, sizeof());
    }

    public Bool(boolean value) {
        address = Ram.malloc(sizeof());
        set(value);
    }

    @Override
    public long addr() {
        return address;
    }

    public boolean get() {
        return Ram.get(address) != 0;
    }

    public void set(boolean value) {
        Ram.set(address, (byte) (value ? 1 : 0));
    }

    public static int sizeof() {
        return 1;
    }

    public static Bool deref(long address) {
        return new Bool(address, false);
    }

    public static Bool deref(Pointer<Bool> ptr) {
        return new Bool(ptr.deref(), false);
    }

}
