package org.vitaliistf.blocktracker.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.vitaliistf.blocktracker.exception.CoinNotSupportedException;
import org.vitaliistf.blocktracker.service.KuCoinApiService;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Optional;

@Service
public class KuCoinApiServiceImpl implements KuCoinApiService {

    private static final String BASE_URI = "https://api.kucoin.com/api/v1/market";

    @Value("${kuCoin.secret}")
    private String secret;

    @Value("${kuCoin.key}")
    private String key;

    @Value("${kuCoin.passphrase}")
    private String passphrase;

    @Override
    public String hmacEncode(String key, String data) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            Key secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hmacBytes = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initializeConnection(HttpURLConnection connection, String uri) throws ProtocolException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("KC-API-KEY", key);
        connection.setRequestProperty("KC-API-SIGN", hmacEncode(secret, uri + timestamp + secret));
        connection.setRequestProperty("KC-API-TIMESTAMP", timestamp);
        connection.setRequestProperty("KC-API-PASSPHRASE", passphrase);
    }

    @Override
    public boolean isCoinSupported(String symbol) {
        return !getPrice(symbol).equals(BigDecimal.ZERO);
    }

    @Override
    public Optional<JsonObject> parseResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            )) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                JsonElement jsonResponse = JsonParser.parseString(response.toString());
                JsonObject jsonData = jsonResponse.getAsJsonObject().get("data").getAsJsonObject();

                if (jsonData != null && !jsonData.isJsonNull()) {
                    return Optional.of(jsonData);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public BigDecimal getPrice(String symbol) {
        try {
            String uri = BASE_URI + "/orderbook/level1?symbol=" + symbol + "-USDT";
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            initializeConnection(connection, uri);

            JsonObject jsonResponse = parseResponse(connection).orElseThrow(
                    () -> new CoinNotSupportedException("This coin is not supported.")
            );

            return jsonResponse.get("price").getAsBigDecimal();

        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public JsonObject getFullInfo(String symbol) {
        try {
            String uri = BASE_URI + "/stats?symbol=" + symbol + "-USDT";
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            initializeConnection(connection, uri);

            return parseResponse(connection).orElseThrow(
                    () -> new CoinNotSupportedException("This coin is not supported.")
            );
        } catch (Exception e) {
            throw new CoinNotSupportedException("This coin is not supported.");
        }
    }
}
