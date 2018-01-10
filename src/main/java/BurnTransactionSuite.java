import com.wavesplatform.wavesj.Node;
import com.wavesplatform.wavesj.PrivateKeyAccount;
import com.wavesplatform.wavesj.Transaction;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class BurnTransactionSuite {
    private final String nodeUrl = "https://testnode1.wavesnodes.com";
    private final Node node;
    private final static long FEE = 100_000;
    private final CloseableHttpClient client = HttpClients.createDefault();

    private final PrivateKeyAccount account = PrivateKeyAccount.fromSeed(
            "glimpse food loud wasp robust story soon mad width play grid still doctor glimpse humble", 0, 'T');
    private final String assetId = "7xf8zHJsNJ7ndyCjVWadHvWdoMc1VnfqD3FgQskTJLtT";

    public BurnTransactionSuite() throws IOException, URISyntaxException {
        node = new Node(nodeUrl);
    }

    @Test
    public void test() throws IOException {
        long balance0 = node.getBalance(account.getAddress(), assetId);

        Transaction burnTx = Transaction.makeBurnTx(account, assetId, 1, FEE);
        String burnTxId = node.send(burnTx);
        System.out.println("tx id: " + burnTxId);

        waitForTransaction(burnTxId);
        long balance1 = node.getBalance(account.getAddress(), assetId);
        assertThat(balance1, equalTo(balance0 - 1));
    }

    private void waitForTransaction(String txId) throws IOException {
        while (true) {
            HttpUriRequest req = new HttpGet(nodeUrl + "/transactions/info/" + txId);
            HttpResponse resp = client.execute(req);
            int status = resp.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore and wait further
            }
        }
    }
}