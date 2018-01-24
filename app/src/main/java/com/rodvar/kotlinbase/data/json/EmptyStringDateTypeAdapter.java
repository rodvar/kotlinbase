package com.rodvar.kotlinbase.data.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

public class EmptyStringDateTypeAdapter extends JavaDateTypeAdapter {
    private static final String EMPTY = "";
    private static final Date NO_DATE = null;

    @Override
    public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        return EMPTY.equals(json.getAsString()) ? NO_DATE : super.deserialize(json, typeOfT, context);
    }
}
