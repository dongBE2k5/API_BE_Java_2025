package tdc.fit.bookingHotel.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

import tdc.fit.bookingHotel.entity.Room;

public class RoomSerializer extends JsonSerializer<Room> {

    @Override
    public void serialize(Room room, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("roomId", room.getRoomId());
        gen.writeStringField("roomNumber", room.getRoomNumber());
        gen.writeStringField("status", room.getStatus());
        gen.writeNumberField("price", room.getPrice() != null ? room.getPrice().doubleValue() : 0.0);
        gen.writeStringField("image", room.getImage() != null ? room.getImage() : "");
        gen.writeNumberField("capacity", room.getCapacity());
        gen.writeStringField("description", room.getDescription() != null ? room.getDescription() : "");

        // Hotel nested object
        if (room.getHotel() != null) {
            gen.writeObjectFieldStart("hotel");
            gen.writeNumberField("hotelId", room.getHotel().getHotelId());
            gen.writeStringField("name", room.getHotel().getName());
            gen.writeStringField("address", room.getHotel().getAddress());
            gen.writeStringField("status", room.getHotel().getStatus());
            gen.writeStringField("image", room.getHotel().getImage() != null ? room.getHotel().getImage() : "");
            gen.writeEndObject();
        } else {
            gen.writeNullField("hotel");
        }

        // RoomType nested object
        if (room.getRoomType() != null) {
            gen.writeObjectFieldStart("roomType");
            gen.writeNumberField("roomTypeId", room.getRoomType().getRoomTypeId());
            gen.writeStringField("name", room.getRoomType().getName());
            gen.writeEndObject();
        } else {
            gen.writeNullField("roomType");
        }

        gen.writeEndObject();
    }
}
