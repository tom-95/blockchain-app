package tom.wehner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import org.hyperledger.fabric.client.*;
import org.hyperledger.fabric.client.identity.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class BlockchainController {

    private static final String MSP_ID = System.getenv().getOrDefault("MSP_ID", "Org1MSP");
    private static final String CHANNEL_NAME = System.getenv().getOrDefault("CHANNEL_NAME", "mychannel");
    private static final String CHAINCODE_NAME = System.getenv().getOrDefault("CHAINCODE_NAME", "basic");

    // Path to crypto materials.
    private static final ClassLoader classLoader = BlockchainController.class.getClassLoader();
    private static final URL resourceUrl = classLoader.getResource("org1.example.com");
    private static final Path CRYPTO_PATH;

    static {
        try {
            assert resourceUrl != null;
            CRYPTO_PATH = Paths.get(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    //private static final Path CRYPTO_PATH = Paths.get("org1.example.com");
    // Path to user certificate.
    private static final Path CERT_PATH = CRYPTO_PATH.resolve(Paths.get("users/User1@org1.example.com/msp/signcerts/cert.pem"));
    // Path to user private key directory.
    private static final Path KEY_DIR_PATH = CRYPTO_PATH.resolve(Paths.get("users/User1@org1.example.com/msp/keystore"));
    // Path to peer tls certificate.
    private static final Path TLS_CERT_PATH = CRYPTO_PATH.resolve(Paths.get("peers/peer0.org1.example.com/tls/ca.crt"));

    // Gateway peer end point.
    private static final String PEER_ENDPOINT = "localhost:7051";
    private static final String OVERRIDE_AUTH = "peer0.org1.example.com";

    private Contract contract;
    private ManagedChannel channel;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public BlockchainController() throws URISyntaxException {
    }

    @PreDestroy
    void onShutdown() throws InterruptedException {
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    void connect(@Observes StartupEvent event) throws IOException, CertificateException, InvalidKeyException, InterruptedException {

        channel = newGrpcConnection();

        var builder = Gateway.newInstance().identity(newIdentity()).signer(newSigner()).connection(channel)
                // Default timeouts for different gRPC calls
                .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                .submitOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES));

        Gateway gateway = builder.connect();
        Network network = gateway.getNetwork(CHANNEL_NAME);
        contract = network.getContract(CHAINCODE_NAME);
    }

    /**
     * Submit a transaction synchronously, blocking until it has been committed to
     * the ledger.
     */
    public void transfer(Payment payment) throws CertificateException, IOException, InvalidKeyException, InterruptedException, EndorseException, CommitException, SubmitException, CommitStatusException {
        //connect();
        String assetId = "transfer" + Instant.now().toEpochMilli();

        contract.submitTransaction("Transfer", assetId, "account_" + payment.getSender().toLowerCase(), "account_" + payment.getReceiver().toLowerCase(), payment.getPurpose(), String.valueOf(payment.getAmount()));

        //channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Evaluate a transaction to query ledger state.
     */
    public String getAllTransactions() throws GatewayException, CertificateException, IOException, InvalidKeyException, InterruptedException {
        //connect();

        byte[] result = contract.evaluateTransaction("QueryTransfers");

        //channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);

        return prettyJson(result);
    }

    public String getAssetHistory(String user) throws CertificateException, IOException, InvalidKeyException, InterruptedException, GatewayException, CommitException {
        //connect();

        byte[] result = contract.evaluateTransaction("GetAssetHistory", "account_" + user.toLowerCase());

        //channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);

        return prettyJson(result);
    }

    private static ManagedChannel newGrpcConnection() throws IOException {
        var credentials = TlsChannelCredentials.newBuilder()
                .trustManager(TLS_CERT_PATH.toFile())
                .build();
        return Grpc.newChannelBuilder(PEER_ENDPOINT, credentials)
                .overrideAuthority(OVERRIDE_AUTH)
                .build();
    }

    private static Identity newIdentity() throws IOException, CertificateException {
        var certReader = Files.newBufferedReader(CERT_PATH);
        var certificate = Identities.readX509Certificate(certReader);

        return new X509Identity(MSP_ID, certificate);
    }

    private static Signer newSigner() throws IOException, InvalidKeyException {
        var keyReader = Files.newBufferedReader(getPrivateKeyPath());
        var privateKey = Identities.readPrivateKey(keyReader);

        return Signers.newPrivateKeySigner(privateKey);
    }

    private static Path getPrivateKeyPath() throws IOException {
        try (var keyFiles = Files.list(KEY_DIR_PATH)) {
            return keyFiles.findFirst().orElseThrow();
        }
    }

    private String prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private String prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return gson.toJson(parsedJson);
    }

}
