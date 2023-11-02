package com.wallet.domain.dto.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wallet.domain.entities.Log;

import java.io.IOException;

public class ActionsSerializer extends JsonSerializer<Log.Actions> {
    @Override
    public void serialize(Log.Actions action, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(action.toString());
    }
}
