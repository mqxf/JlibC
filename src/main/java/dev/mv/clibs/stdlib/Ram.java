package dev.mv.clibs.stdlib;

import dev.mv.clibs.errno.SIGSEGV;

import java.util.Arrays;

public class Ram {

    private static final int SIZE = 1 << 24;

    private static byte[] data = new byte[SIZE];
    private static int[] size = new int[SIZE];

    static void init() {}

    public static byte get(long address) {
        checkVirtualAddress(address);
        int a = unmap(address);
        checkAddress(a);
        if (size[a] == 0) throw new SIGSEGV();
        return data[a];
    }

    public static byte[] get(long address, int n) {
        checkVirtualAddress(address);
        int a = unmap(address);
        checkAddress(a);
        for (int i = a; i < a + n; i++) {
            if (size[i] == 0) throw new SIGSEGV();
        }
        byte[] result = new byte[n];
        System.arraycopy(data, a, result, 0, n);
        return result;
    }

    public static void set(long address, byte value) {
        checkVirtualAddress(address);
        int a = unmap(address);
        checkAddress(a);
        if (size[a] == 0) throw new SIGSEGV();
        data[a] = value;
    }

    public static void set(long address, byte[] value) {
        checkVirtualAddress(address);
        int a = unmap(address);
        checkAddress(a);
        for (int i = a; i < a + value.length; i++) {
            if (size[i] == 0) throw new SIGSEGV();
        }
        System.arraycopy(value, 0, data, a, value.length);
    }

    public static long malloc(int sizeof) {
        int i = 0;
        loop:
        while (i < SIZE) {
            if (size[i] == 0) {
                for (int j = 1; j < sizeof; j++) {
                    if (size[i + j] != 0) {
                        i += size[i + j] < 0 ? 1 : size[i + j];
                        continue loop;
                    }
                }
                Arrays.fill(size, i, i + sizeof, -1);
                return map(i);
            }
            i += size[i] < 0 ? 1 : size[i];
        }
        throw new SIGSEGV();
    }

    public static long calloc(int n, int sizeof) {
        long address = malloc(sizeof * n);
        int a = unmap(address);
        Arrays.fill(data, a, a + sizeof * n, (byte) 0);
        return address;
    }

    public static long realloc(long address, int sizeof) {
        checkVirtualAddress(address);
        int a = unmap(address);
        checkAddress(a);
        long newAddress = malloc(sizeof);
        System.arraycopy(data, a, data, unmap(newAddress), Math.min(sizeof, size[a]));
        free(address);
        return newAddress;
    }

    public static void free(long address) {
        checkVirtualAddress(address);
        int a = unmap(address);
        checkAddress(a);
        while (size[a] < 0) {
            a--;
        }
        if (size[a] == 0) throw new SIGSEGV();
        Arrays.fill(size, a, a + size[a], 0);
    }

    public static long increment(long address, int n) {
        checkVirtualAddress(address);
        int a = unmap(address);
        checkAddress(a);
        a += n;
        checkAddress(a);
        if (size[a] == 0) throw new SIGSEGV();
        return map(a);
    }

    static void checkVirtualAddress(long address) {
        if (address % 3 != 0) throw new SIGSEGV();
    }

    static void checkAddress(long address) {
        if (address < 0 || address >= SIZE) throw new SIGSEGV();
    }

    static long map(int actual) {
        return ((long) (actual + 0x6f3) << 4) * 3;
    }

    static int unmap(long virtual) {
        return (int) ((virtual / 3) >>> 4) - 0x6f3;
    }
}
