package ru.ndg.myapp.util;

import lombok.Getter;

@Getter
public enum Operation {
    CREATE(0),
    UPDATE(1),
    DELETE(2);

    private final int code;

    Operation(int code) {
        this.code = code;
    }
}
