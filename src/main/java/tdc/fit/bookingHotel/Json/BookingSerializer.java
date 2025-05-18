package tdc.fit.bookingHotel.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import tdc.fit.bookingHotel.entity.Booking;

public class BookingSerializer extends JsonSerializer<Booking> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void serialize(Booking booking, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("bookingId", booking.getBookingId());

        // Customer nested
        if (booking.getCustomer() != null) {
            gen.writeObjectFieldStart("customer");
            gen.writeNumberField("customerId", booking.getCustomer().getCustomerId());
            gen.writeStringField("fullname", booking.getCustomer().getFullname() != null ? booking.getCustomer().getFullname() : "");
            gen.writeStringField("email", booking.getCustomer().getEmail());
            gen.writeStringField("phone", booking.getCustomer().getPhone() != null ? booking.getCustomer().getPhone() : "");
            gen.writeEndObject();
        } else {
            gen.writeNullField("customer");
        }

        // Room nested
        if (booking.getRoom() != null) {
            gen.writeObjectFieldStart("room");
            gen.writeNumberField("roomId", booking.getRoom().getRoomId());
            gen.writeStringField("roomNumber", booking.getRoom().getRoomNumber());
            gen.writeStringField("status", booking.getRoom().getStatus());
            gen.writeNumberField("price", booking.getRoom().getPrice().doubleValue());
            gen.writeEndObject();
        } else {
            gen.writeNullField("room");
        }

        // Dates
        gen.writeStringField("checkInDate", booking.getCheckInDate() != null ? booking.getCheckInDate().format(DATE_FORMATTER) : null);
        gen.writeStringField("checkOutDate", booking.getCheckOutDate() != null ? booking.getCheckOutDate().format(DATE_FORMATTER) : null);

        gen.writeStringField("status", booking.getStatus());

        gen.writeEndObject();
    }
}
