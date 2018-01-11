package com.wavesplatform.steps;

import com.wavesplatform.MethodEnum;
import com.wavesplatform.response.addresses.Balance;
import com.wavesplatform.wavesj.Asset;
import io.qameta.allure.Step;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.wavesplatform.TestVariables.getHost;
import static com.wavesplatform.TestVariables.getProtocol;

public class AddressesSteps {


    private BackendSteps steps;
    private UtilsSteps utils;
    private String URL = getProtocol().concat(getHost());

    public AddressesSteps(){
        this.steps = new BackendSteps();
        this.utils = new UtilsSteps(URL);
    }


    @Step
    public Long getBalance(String address) throws UnsupportedEncodingException {
        Balance balance = steps.sendGet(Balance.class, MethodEnum.GET_BALANCE, address);
        return balance.getBalance();
    }

    @Step
    public long getBalance(String address, String assetId) throws IOException {
        return Asset.WAVES.equals(assetId)
                ? getBalance(address)
                : steps.sendGet(Balance.class, MethodEnum.GET_ASSET_BALANCE, address, assetId).getBalance();
    }
}
