package com.wavesplatform;

/**
 * Created by DrBlast on 28.11.2016.
 */
public enum MethodEnum {

    GET_BALANCE("addresses/balance/%s"),
    GET_ASSET_BALANCE("assets/balance/%s/%s"),
    TRANSACTION_INFO("transactions/info/%s"),

    ASSETS_ISSUE("assets/broadcast/issue"),
    ASSETS_BURN("assets/broadcast/burn");

    private String name;

    MethodEnum(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return this.name;
    }

    public static MethodEnum fromString(String text) {
        if (text != null) {
            for (MethodEnum b : MethodEnum.values()) {
                if (text.equalsIgnoreCase(b.name)) {
                    return b;
                }
            }
        }
        return null;
    }
}
