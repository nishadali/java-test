import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.io.*;
import java.net.*;

public class OutdatedSSLExample {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        // Set up the SSL context with default, outdated protocols (SSLv3, TLSv1)
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new TrustAllCertificates()}, new java.security.SecureRandom());

        // Set the SSLSocketFactory for the connection
        SSLSocketFactory factory = sslContext.getSocketFactory();

        // Create a socket and connect to a secure server (note: outdated TLS/SSL protocols)
        SSLSocket socket = (SSLSocket) factory.createSocket("example.com", 443);

        // Enable older SSL/TLS protocols (may include SSLv3, which is insecure)
        String[] enabledProtocols = socket.getEnabledProtocols();
        System.out.println("Enabled protocols: ");
        for (String protocol : enabledProtocols) {
            System.out.println(protocol);
        }

        socket.startHandshake();
        socket.close();
    }

    // A TrustManager that does not perform any certificate validation (unsafe)
    static class TrustAllCertificates implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
            // Do nothing, trust all certificates
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
            // Do nothing, trust all certificates
        }
    }
}
