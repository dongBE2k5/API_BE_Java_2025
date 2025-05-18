package tdc.fit.bookingHotel.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import tdc.fit.bookingHotel.entity.Hotel;

import java.io.IOException;

public class HotelSerializer extends JsonSerializer<Hotel> {

    @Override
    public void serialize(Hotel hotel, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (hotel == null) {
            gen.writeNull();
            return;
        }

        gen.writeStartObject();
        gen.writeNumberField("hotelId", hotel.getHotelId());
        gen.writeStringField("name", hotel.getName());

        // Serialize location
        gen.writeFieldName("location");
        serializers.defaultSerializeValue(hotel.getLocation(), gen);

        gen.writeStringField("address", hotel.getAddress());
        gen.writeStringField("status", hotel.getStatus());

        // Serialize hotelier
        gen.writeFieldName("hotelier");
        serializers.defaultSerializeValue(hotel.getHotelier(), gen);

        gen.writeStringField("image", hotel.getImage());
        gen.writeEndObject();
    }
}
