package be.kdg.programming3.pillgate.repo;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The {@code LocalDateSerializer} class is a custom Gson serializer for {@link LocalDate}.
 * It provides the serialization logic to convert a {@code LocalDate} object to a JSON primitive element
 * with a formatted date string.
 *
 * <p>This class is part of the PillGate application developed by Team PillGate.</p>
 *
 * @author Team PillGate
 */
@Component
public class LocalDateSerializer implements JsonSerializer<Object> {

    /**
     * The DateTimeFormatter used for formatting {@link LocalDate} objects.
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Serializes a {@link LocalDate} object to a JSON primitive element with a formatted date string.
     *
     * @param obj     The object to be serialized.
     * @param type    The type of the object to be serialized.
     * @param context The context for serialization.
     * @return A {@link JsonElement} representing the serialized form of the input object.
     */
    @Override
    public JsonElement serialize(Object obj, Type type, JsonSerializationContext context) {
        if (obj instanceof LocalDate localDate) {
            return new JsonPrimitive(formatter.format(localDate));
        }
        return null;
    }
}
