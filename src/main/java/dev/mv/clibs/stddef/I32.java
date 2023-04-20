package dev.mv.clibs.stddef;

import dev.mv.clibs.stdlib.Pointer;
import dev.mv.clibs.stdlib.Ram;
import dev.mv.utils.ByteUtils;

public final class I32 implements Primitive {

    private long address;

    I32(long address, boolean ignore) {
        this.address = address;
    }

    public I32() {
        address = Ram.calloc(1, sizeof());
    }

    public I32(int value) {
        address = Ram.malloc(sizeof());
        set(value);
    }

    @Override
    public long addr() {
        return address;
    }

    public int get() {
        return ByteUtils.intFromBytes(Ram.get(address, sizeof()));
    }

    public void set(int value) {
        Ram.set(address, ByteUtils.toBytes(value));
    }

    public static int sizeof() {
        return 4;
    }

    public static I32 deref(long address) {
        return new I32(address, false);
    }

    public static I32 deref(Pointer<I32> ptr) {
        return new I32(ptr.deref(), false);
    }

}
