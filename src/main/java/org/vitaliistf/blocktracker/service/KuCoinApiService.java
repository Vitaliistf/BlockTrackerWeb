package org.vitaliistf.blocktracker.service;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.Optional;

public interface KuCoinApiService {
    String hmacEncode(String key, String data);

    void initializeConnection(HttpURLConnection connection, String uri) throws ProtocolException;

    boolean isCoinSupported(String symbol);

    Optional<JsonObject> parseResponse(HttpURLConnection connection) throws IOException;

    BigDecimal getPrice(String symbol);

    JsonObject getFullInfo(String symbol);
}
