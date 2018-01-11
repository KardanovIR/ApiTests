package com.wavesplatform.assets;

import com.wavesplatform.MethodEnum;
import com.wavesplatform.TestDefaults;
import com.wavesplatform.response.TransactionInfo;
import com.wavesplatform.wavesj.Transaction;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import static com.wavesplatform.TestVariables.getAssetId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BurnTransactionSuite extends TestDefaults {

    private String assetId;


    @BeforeEach
    public void setUp() throws IOException, URISyntaxException {
        assetId = getAssetId();

    }

    @Test
    @Tag("always")
    @DisplayName("Check burn transaction")
    public void test() throws IOException, TimeoutException, InterruptedException {
        long balance0 = accSteps.getBalance(account.getAddress(), assetId);

        Transaction burnTx = Transaction.makeBurnTx(account, assetId, 1, FEE);
        TransactionInfo burnTxInfo = steps.sendPost(burnTx.getData(), TransactionInfo.class, MethodEnum.ASSETS_BURN);
        utils.waitForTransaction(burnTxInfo.getId());
        long balance1 = accSteps.getBalance(account.getAddress(), assetId);
        assertThat(balance1, equalTo(balance0 - 1));
    }

}