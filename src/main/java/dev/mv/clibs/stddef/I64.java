package dev.mv.clibs.stddef;

import dev.mv.clibs.stdlib.Pointer;
import dev.mv.clibs.stdlib.Ram;
import dev.mv.utils.ByteUtils;

public final class I64 implements Primitive {

    private long address;

    I64(long address, boolean ignore) {
        this.address = address;
    }

    public I64() {
        address = Ram.calloc(1, sizeof());
    }

    public I64(long value) {
        address = Ram.malloc(sizeof());
        set(value);
    }

    @Override
    public long addr() {
        return address;
    }

    public long get() {
        return ByteUtils.longFromBytes(Ram.get(address, sizeof()));
    }

    public void set(long value) {
        Ram.set(address, ByteUtils.toBytes(value));
    }

    public static int sizeof() {
        return 8;
    }

    public static I64 deref(long address) {
        return new I64(address, false);
    }

    public static I64 deref(Pointer<I64> ptr) {
        return new I64(ptr.deref(), false);
    }

}
