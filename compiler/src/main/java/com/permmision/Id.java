package com.permmision;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

/**
 * Author: hzlishang
 * Data: 16/10/29 下午1:16
 * Des:
 * version:
 */
final class Id {
    private static final ClassName ANDROID_R = ClassName.get("android", "R");

    final int value;
    final CodeBlock code;
    final boolean qualifed;

    Id(int value) {
        this.value = value;
        this.code = CodeBlock.of("$L", value);
        this.qualifed = false;
    }

    Id(int value, ClassName className, String resourceName) {
        this.value = value;
        this.code = className.topLevelClassName().equals(ANDROID_R)
                ? CodeBlock.of("$L.$N", className, resourceName)
                : CodeBlock.of("$T.$N", className, resourceName);
        this.qualifed = true;
    }

    @Override public boolean equals(Object o) {
        return o instanceof Id && value == ((Id) o).value;
    }

    @Override public int hashCode() {
        return value;
    }

    @Override public String toString() {
        throw new UnsupportedOperationException("Please use value or code explicitly");
    }
}
