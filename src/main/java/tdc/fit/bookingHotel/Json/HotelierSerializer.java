package tdc.fit.bookingHotel.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import tdc.fit.bookingHotel.entity.Hotelier;

import java.io.IOException;

public class HotelierSerializer extends JsonSerializer<Hotelier> {

    @Override
    public void serialize(Hotelier hotelier, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (hotelier == null) {
            gen.writeNull();
            return;
        }

        gen.writeStartObject();
        gen.writeNumberField("hotelierId", hotelier.getHotelierId());
        gen.writeStringField("fullName", hotelier.getFullName());
        gen.writeStringField("phone", hotelier.getPhone());
        gen.writeStringField("email", hotelier.getEmail());
        gen.writeEndObject();
    }
}

