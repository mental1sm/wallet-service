package com.wallet.domain.dto.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.wallet.domain.entities.Log;

import java.io.IOException;

public class ActionsDeserializer extends JsonDeserializer<Log.Actions> {
    @Override
    public Log.Actions deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(deserializationContext.getParser());
        String actionAsString = node.asText();
        return Log.Actions.valueOf(actionAsString);
    }
}
