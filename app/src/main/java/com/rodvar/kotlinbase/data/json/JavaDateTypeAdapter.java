package com.rodvar.kotlinbase.data.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handles non-standard dates (e.g. for Notifications)
 */
public class JavaDateTypeAdapter extends DefaultDateTypeAdapter {

    /**
     * TODO
     * Warning:(18, 50) To get local formatting use `getDateInstance()`, `getDateTimeInstance()`, or
     * `getTimeInstance()`, or use `new SimpleDateFormat(String template, Locale locale)`
     * with for example `Locale.US` for ASCII dates.
     */
    private static final DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");


    JavaDateTypeAdapter() {
        super();
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        synchronized (dateFormat) {
            try {
                return dateFormat.parse(json.getAsString());
            } catch (ParseException ignored) {
                return super.deserialize(json, typeOfT, context);
            }
        }
    }
}
