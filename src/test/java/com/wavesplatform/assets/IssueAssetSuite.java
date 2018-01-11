package com.wavesplatform.assets;

import com.wavesplatform.MethodEnum;
import com.wavesplatform.TestDefaults;
import com.wavesplatform.response.TransactionInfo;
import com.wavesplatform.wavesj.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IssueAssetSuite extends TestDefaults{

    @Test
    @Tag("sometimes")
    @DisplayName("Issue asset")
    public void test() throws IOException, TimeoutException, InterruptedException {

        Transaction issueAsset = Transaction.makeIssueTx(account, "abcd", "xyz", 9544324312L, 2, true, ISSUE_FEE);

        TransactionInfo issueTxInfo = steps.sendPost(issueAsset.getData(), TransactionInfo.class, MethodEnum.ASSETS_ISSUE);
        utils.waitForTransaction(issueTxInfo.getId());
        long assetBalance = accSteps.getBalance(account.getAddress(), issueTxInfo.getAssetId());

        assertThat(assetBalance, equalTo(9544324312L));
    }
}
