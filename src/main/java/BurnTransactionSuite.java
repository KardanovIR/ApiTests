import com.wavesplatform.wavesj.Node;
import com.wavesplatform.wavesj.PrivateKeyAccount;
import com.wavesplatform.wavesj.Transaction;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class BurnTransactionSuite {
    private final Node node;
    private final static long FEE = 100_000;

    private final PrivateKeyAccount account = PrivateKeyAccount.fromSeed(
            "glimpse food loud wasp robust story soon mad width play grid still doctor glimpse humble", 0, 'T');
    private final String assetId = "7xf8zHJsNJ7ndyCjVWadHvWdoMc1VnfqD3FgQskTJLtT";

    public BurnTransactionSuite() throws IOException, URISyntaxException {
        node = new Node("https://testnode1.wavesnodes.com");
    }

    @Test
    public void test() throws IOException {
        Transaction burnTx = Transaction.makeBurnTx(account, assetId, 1, FEE);
        String burnTxId = node.send(burnTx);
        System.out.println("Burned with tx id " + burnTxId);
    }
}