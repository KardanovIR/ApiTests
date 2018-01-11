package com.wavesplatform.steps;

import com.wavesplatform.response.TransactionInfo;
import io.qameta.allure.Step;

import static com.wavesplatform.MethodEnum.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.MatcherAssert.assertThat;

public class UtilsSteps {

    private String url;
    private BackendSteps steps;

    public UtilsSteps(String nodeUrl) {
        this.url = nodeUrl;
        this.steps = new BackendSteps();
    }

    @Step
    public void waitForTransaction(String txId) throws InterruptedException, UnsupportedEncodingException, TimeoutException {
        TransactionInfo txInfo = steps.sendGetAndWaitSuccess(TransactionInfo.class, TRANSACTION_INFO, txId);
    }

    @Step("Check that list is empty")
    public void checkThatListIsEmpty(List list) {
        assertThat("Список не пустой!", list.isEmpty());
    }
}
