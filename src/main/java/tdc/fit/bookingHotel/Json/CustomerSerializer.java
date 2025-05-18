package tdc.fit.bookingHotel.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import tdc.fit.bookingHotel.entity.Customer;

public class CustomerSerializer extends JsonSerializer<Customer> {

    @Override
    public void serialize(Customer customer, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("customerId", customer.getCustomerId());
        gen.writeStringField("fullname", customer.getFullname() != null ? customer.getFullname() : "");
        gen.writeStringField("email", customer.getEmail());
        gen.writeStringField("phone", customer.getPhone() != null ? customer.getPhone() : "");
        gen.writeStringField("cccd", customer.getCccd() != null ? customer.getCccd() : "");

        // User nested object (nếu cần)
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
