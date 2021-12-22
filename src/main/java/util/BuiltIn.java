package util;

import type.Mapping;
import type.Sequence;
import type.Type;

import java.util.ArrayList;

public enum BuiltIn {
    READ("read", new Mapping(new Sequence(new ArrayList<>()), Type.VOID)),
    READLN("readLn", new Mapping(new Sequence(new ArrayList<>()), Type.VOID)),
    WRITE("write", new Mapping(new Sequence(new ArrayList<>()), Type.VOID)),
    WRITELN("writeLn", new Mapping(new Sequence(new ArrayList<>()), Type.VOID));

    private String id;
    private Type type;

    BuiltIn(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }
}
