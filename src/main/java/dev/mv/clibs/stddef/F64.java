package dev.mv.clibs.stddef;

import dev.mv.clibs.stdlib.Pointer;
import dev.mv.clibs.stdlib.Ram;
import dev.mv.utils.ByteUtils;

public final class F64 implements Primitive {

    private long address;

    F64(long address, boolean ignore) {
        this.address = address;
    }

    public F64() {
        address = Ram.calloc(1, sizeof());
    }

    public F64(double value) {
        address = Ram.malloc(sizeof());
        set(value);
    }

    @Override
    public long addr() {
        return address;
    }

    public double get() {
        return ByteUtils.doubleFromBytes(Ram.get(address, sizeof()));
    }

    public void set(double value) {
        Ram.set(address, ByteUtils.toBytes(value));
    }

    public static int sizeof() {
        return 8;
    }

    public static F64 deref(long address) {
        return new F64(address, false);
    }

    public static F64 deref(Pointer<F64> ptr) {
        return new F64(ptr.deref(), false);
    }

}
