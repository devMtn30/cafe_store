package com.payhere.kimsan.common.constant;

import lombok.Getter;

@Getter
public enum DomainConstant {
    PAGE_DEFAULT_SIZE(10);


    private final int value;

    DomainConstant(int value) {
        this.value = value;
    }
}
