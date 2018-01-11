package com.wavesplatform;

import com.wavesplatform.steps.AddressesSteps;
import com.wavesplatform.steps.BackendSteps;
import com.wavesplatform.steps.UtilsSteps;
import com.wavesplatform.wavesj.Node;
import com.wavesplatform.wavesj.PrivateKeyAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.net.URISyntaxException;

import static com.wavesplatform.TestVariables.*;

public abstract class TestDefaults {

    protected String nodeUrl;
    protected Node node;
    protected PrivateKeyAccount account;
    protected final static long FEE = 100_000;
    protected final static long ISSUE_FEE= 100000000;


    protected BackendSteps steps;
    protected UtilsSteps utils;
    protected AddressesSteps  accSteps;

    @BeforeEach
    public void setUP() throws URISyntaxException {
        System.setProperty("env", "devnet");
        nodeUrl = getProtocol() + getHost();
        node = new Node(nodeUrl);
        account = PrivateKeyAccount.fromSeed(getSeed(), 0, 'D');
        steps = new BackendSteps();
        utils = new UtilsSteps(nodeUrl);
        accSteps = new AddressesSteps();

    }

    @AfterEach
    public void tearDown() {

    }
}
