package dev.mv.clibs.stddef;

import dev.mv.clibs.stdlib.Pointer;
import dev.mv.clibs.stdlib.Ram;

public final class I8 implements Primitive {

    private long address;

    I8(long address, boolean ignore) {
        this.address = address;
    }

    public I8() {
        address = Ram.calloc(1, sizeof());
    }

    public I8(byte value) {
        address = Ram.malloc(sizeof());
        set(value);
    }

    @Override
    public long addr() {
        return address;
    }

    public byte get() {
        return Ram.get(address);
    }

    public void set(byte value) {
        Ram.set(address, value);
    }

    public static int sizeof() {
        return 1;
    }

    public static I8 deref(long address) {
        return new I8(address, false);
    }

    public static I8 deref(Pointer<I8> ptr) {
        return new I8(ptr.deref(), false);
    }

}
