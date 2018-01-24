package com.rodvar.kotlinbase.data.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Handles empty or malformed big decimal strings (treats them as null)
 */
public class EmptyBigDecimalTypeAdapter extends TypeAdapter<BigDecimal> {
    @Override
    public BigDecimal read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        try {
            return new BigDecimal(reader.nextString());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void write(JsonWriter writer, BigDecimal value) throws IOException {
        writer.value(value);
    }
}
