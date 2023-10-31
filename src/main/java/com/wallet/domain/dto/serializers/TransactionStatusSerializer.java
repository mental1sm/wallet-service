package com.wallet.domain.dto.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wallet.domain.entities.Transaction;

import java.io.IOException;

public class TransactionStatusSerializer extends JsonSerializer {
    @Override
    public void serialize(Object status, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(status.toString());
    }
}
