package dev.mv.clibs.stddef;

import dev.mv.clibs.stdlib.Pointer;
import dev.mv.clibs.stdlib.Ram;
import dev.mv.utils.ByteUtils;

public final class I16 implements Primitive {

    private long address;

    I16(long address, boolean ignore) {
        this.address = address;
    }

    public I16() {
        address = Ram.calloc(1, sizeof());
    }

    public I16(short value) {
        address = Ram.malloc(sizeof());
        set(value);
    }

    @Override
    public long addr() {
        return address;
    }

    public short get() {
        return ByteUtils.shortFromBytes(Ram.get(address, sizeof()));
    }

    public void set(short value) {
        Ram.set(address, ByteUtils.toBytes(value));
    }

    public static int sizeof() {
        return 2;
    }

    public static I16 deref(long address) {
        return new I16(address, false);
    }

    public static I16 deref(Pointer<I16> ptr) {
        return new I16(ptr.deref(), false);
    }

}
