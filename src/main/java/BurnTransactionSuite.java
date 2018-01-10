import com.wavesplatform.wavesj.PrivateKeyAccount;
import com.wavesplatform.wavesj.Transaction;

import java.io.IOException;

public class BurnTransactionSuite {
    private final static long FEE = 100_000;

    private final PrivateKeyAccount account = PrivateKeyAccount.fromSeed(
            "glimpse food loud wasp robust story soon mad width play grid still doctor glimpse humble", 0, 'T');
    private final String assetId = "7xf8zHJsNJ7ndyCjVWadHvWdoMc1VnfqD3FgQskTJLtT";

    private Transaction sendBurnTx() throws IOException {
        return Transaction.makeBurnTx(account, assetId, 1, FEE);
    }
}