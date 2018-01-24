package com.rodvar.kotlinbase.data.json;

import com.google.api.client.util.DateTime;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

public class DateTimeDateTypeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    private static final Date NO_DATE = null;
    private static final DateTime NO_DATE_TIME = null;

    private final EmptyStringDateTypeAdapter actualAdapter;

    DateTimeDateTypeAdapter() {
        actualAdapter = new EmptyStringDateTypeAdapter();
    }

    @Override
    public DateTime deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        final Date theDate = actualAdapter.deserialize(json, Date.class, context);

        return (theDate != NO_DATE) ? new DateTime(theDate) : NO_DATE_TIME;
    }

    @Override
    public JsonElement serialize(final DateTime src, final Type typeOfSrc, final JsonSerializationContext context) {
        final Date theDate = new Date(src.getValue());

        return actualAdapter.serialize(theDate, Date.class, context);
    }
}
