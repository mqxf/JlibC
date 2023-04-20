package dev.mv.clibs.stddef;

import dev.mv.clibs.stdlib.Pointer;

public interface CType {

    long addr();

    default Pointer<CType> pointer() {
        return Pointer.wrap(this);
    }

}
