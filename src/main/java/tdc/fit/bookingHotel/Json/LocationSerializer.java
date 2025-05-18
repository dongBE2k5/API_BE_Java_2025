package tdc.fit.bookingHotel.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import tdc.fit.bookingHotel.entity.Location;

import java.io.IOException;

public class LocationSerializer extends JsonSerializer<Location> {

    @Override
    public void serialize(Location value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("locationId", value.getLocationId());
        gen.writeStringField("name", value.getName());
        gen.writeStringField("description", value.getDescription());
        gen.writeStringField("image", value.getImage());
        gen.writeEndObject();
    }
}
