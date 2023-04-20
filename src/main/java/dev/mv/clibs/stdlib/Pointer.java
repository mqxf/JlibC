package dev.mv.clibs.stdlib;

import dev.mv.clibs.stddef.CType;
import dev.mv.clibs.stddef.I64;

public class Pointer<T extends CType> implements CType {

    private long address;
    private I64 ptr;

    Pointer(long address) {
        this.address = address;
        this.ptr = I64.deref(address);
    }

    Pointer(long address, I64 ptr) {
        this.address = address;
        this.ptr = ptr;
    }

    void set(long address) {
        ptr.set(address);
    }

    @Override
    public long addr() {
        return address;
    }

    public void set(T value) {
        I64 val = I64.deref(ptr.get());
        val.set(value.addr());
    }

    public void set(int index, T value) {
        increment(index);
        set(value);
        decrement(index);
    }

    public void set(T[] values) {
        for (T value : values) {
            set(value);
            increment();
        }
        decrement(values.length);
    }

    public long deref() {
        I64 val = I64.deref(ptr.get());
        return val.get();
    }

    public long deref(int index) {
        increment(index);
        long addr = deref();
        decrement(index);
        return addr;
    }

    public void increment() {
        ptr.set(Ram.increment(ptr.get(), I64.sizeof()));
    }

    public void increment(int count) {
        ptr.set(Ram.increment(ptr.get(), count * I64.sizeof()));
    }

    public void decrement() {
        ptr.set(Ram.increment(ptr.get(), -I64.sizeof()));
    }

    public void decrement(int count) {
        ptr.set(Ram.increment(ptr.get(), -count * I64.sizeof()));
    }

    public static <T extends CType> Pointer<T> wrap(T value) {
        long addr = Ram.malloc(sizeof());
        I64 ptr = I64.deref(addr);
        I64 val = new I64(value.addr());
        ptr.set(val.addr());
        return new Pointer<>(addr, ptr);
    }

    public static <T extends CType> Pointer<T> of(long addr) {
        return new Pointer<>(addr);
    }

    public static int sizeof() {
        return I64.sizeof();
    }

}
