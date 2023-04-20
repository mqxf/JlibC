package dev.mv.clibs.stddef;

import dev.mv.clibs.stdlib.Pointer;
import dev.mv.clibs.stdlib.Ram;
import dev.mv.utils.ByteUtils;

public final class Char implements Primitive {

    private long address;

    Char(long address, boolean ignore) {
        this.address = address;
    }

    public Char() {
        address = Ram.calloc(1, sizeof());
    }

    public Char(char value) {
        address = Ram.malloc(sizeof());
        set(value);
    }

    @Override
    public long addr() {
        return address;
    }

    public char get() {
        return (char) ByteUtils.shortFromBytes(Ram.get(address, sizeof()));
    }

    public void set(char value) {
        Ram.set(address, ByteUtils.toBytes((short) value));
    }

    public static int sizeof() {
        return 2;
    }

    public static Char deref(long address) {
        return new Char(address, false);
    }

    public static Char deref(Pointer<Char> ptr) {
        return new Char(ptr.deref(), false);
    }

}
