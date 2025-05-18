package tdc.fit.bookingHotel.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import tdc.fit.bookingHotel.entity.RoomType;

import java.io.IOException;

public class RoomTypeSerializer extends JsonSerializer<RoomType> {

    @Override
    public void serialize(RoomType roomType, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("roomTypeId", roomType.getRoomTypeId());
        gen.writeStringField("name", roomType.getName());
        gen.writeEndObject();
    }
}
