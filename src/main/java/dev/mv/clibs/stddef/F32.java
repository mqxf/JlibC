package dev.mv.clibs.stddef;

import dev.mv.clibs.stdlib.Pointer;
import dev.mv.clibs.stdlib.Ram;
import dev.mv.utils.ByteUtils;

public final class F32 implements Primitive {

    private long address;

    F32(long address, boolean ignore) {
        this.address = address;
    }

    public F32() {
        address = Ram.calloc(1, sizeof());
    }

    public F32(float value) {
        address = Ram.malloc(sizeof());
        set(value);
    }

    @Override
    public long addr() {
        return address;
    }

    public float get() {
        return ByteUtils.floatFromBytes(Ram.get(address, sizeof()));
    }

    public void set(float value) {
        Ram.set(address, ByteUtils.toBytes(value));
    }

    public static int sizeof() {
        return 4;
    }

    public static F32 deref(long address) {
        return new F32(address, false);
    }

    public static F32 deref(Pointer<F32> ptr) {
        return new F32(ptr.deref(), false);
    }

}
