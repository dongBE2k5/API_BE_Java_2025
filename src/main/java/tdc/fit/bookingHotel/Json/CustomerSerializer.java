package tdc.fit.bookingHotel.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import tdc.fit.bookingHotel.entity.Customer;

public class CustomerSerializer extends JsonSerializer<Customer> {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void serialize(Customer customer, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("customerId", customer.getCustomerId());
        gen.writeStringField("fullname", customer.getFullname() != null ? customer.getFullname() : "");
        gen.writeStringField("email", customer.getEmail() != null ? customer.getEmail() : "");
        gen.writeStringField("phone", customer.getPhone() != null ? customer.getPhone() : "");
        gen.writeStringField("cccd", customer.getCccd() != null ? customer.getCccd() : "");
        gen.writeStringField("gender", customer.getGender() != null ? customer.getGender() : "");
        gen.writeStringField("address", customer.getAddress() != null ? customer.getAddress() : "");

        if (customer.getDob() != null) {
            gen.writeStringField("dob", customer.getDob().format(dateFormatter));
        } else {
            gen.writeStringField("dob", "");
        }

        // User nested object
        if (customer.getUserId() != null) {
            gen.writeObjectFieldStart("user");
            gen.writeNumberField("userId", customer.getUserId().getUserId());
            gen.writeStringField("username", customer.getUserId().getUsername());
            gen.writeStringField("role", customer.getUserId().getRoles());
            gen.writeEndObject();
        } else {
            gen.writeNullField("user");
        }

        gen.writeEndObject();
    }
}
