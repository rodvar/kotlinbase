package com.rodvar.kotlinbase.data.json;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.ObjectParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;

import com.rodvar.kotlinbase.base.utils.android.Logger;

public class GsonObjectParser implements ObjectParser {
    public static final String TAG = "HttpTransport";
    private final Gson parser;

    public GsonObjectParser() {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new EmptyStringDateTypeAdapter());
        builder.registerTypeAdapter(DateTime.class, new DateTimeDateTypeAdapter());
        builder.registerTypeAdapter(BigDecimal.class, new EmptyBigDecimalTypeAdapter());

        parser = builder.setPrettyPrinting().disableHtmlEscaping().create();
    }

    @Override
    public <T> T parseAndClose(final InputStream in, final Charset charset, final Class<T> dataClass) throws IOException {
        T response = parseAndClose(new InputStreamReader(in, charset), dataClass);

        return response;
    }

    @Override
    public Object parseAndClose(final InputStream in, final Charset charset, final Type dataType) throws IOException {
        Object response = parseAndClose(new InputStreamReader(in, charset), dataType);

        return response;
    }

    @Override
    public <T> T parseAndClose(final Reader reader, final Class<T> dataClass) throws IOException {
        T response = parser.fromJson(reader, dataClass);

        if (Logger.isDebugEnabled()) {
            Logger.debug(TAG, parser.toJson(response));
        }

        return response;
    }

    @Override
    public Object parseAndClose(final Reader reader, final Type dataType) throws IOException {
        Object response = parser.fromJson(reader, dataType);

        if (Logger.isDebugEnabled()) {
            Logger.debug(TAG, parser.toJson(response));
        }

        return response;
    }

    public Gson get() {
        return parser;
    }
}