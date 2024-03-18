package com.example.common.enums;

public enum ApplyEnum {
    PROCESS_HEADER_APPLYING("社长审核中"),
    PROCESS_HEADER_PASS("审核通过"),

    STATUS_APPLYING("待审核"),
    STATUS_PASS("审核通过"),
    STATUS_REFUSE("审核不通过")
    ;
    public String status;

    ApplyEnum(String status) {
        this.status = status;
    }

}
