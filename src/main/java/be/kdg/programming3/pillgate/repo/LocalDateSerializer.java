package be.kdg.programming3.pillgate.repo;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateSerializer implements JsonSerializer<Object> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public JsonElement serialize(Object obj, Type type, JsonSerializationContext context) {
        if (obj instanceof LocalDate localDate) {
            return new JsonPrimitive(formatter.format(localDate));
    }
        return null;
    }
}
