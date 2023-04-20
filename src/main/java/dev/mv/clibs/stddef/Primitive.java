package dev.mv.clibs.stddef;

public sealed interface Primitive extends CType permits I8, I16, I32, I64, F32, F64, Char, Bool { }
