package com.wavesplatform.response.addresses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balance {


    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("confirmations")
    @Expose
    private Integer confirmations;
    @SerializedName("balance")
    @Expose
    private Long balance;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

}

