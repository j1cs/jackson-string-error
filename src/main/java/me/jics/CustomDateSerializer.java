package me.jics;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import io.micronaut.core.annotation.Introspected;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Introspected
public class CustomDateSerializer extends JsonSerializer<LocalDateTime> implements ContextualSerializer {
    private final String format;

    private CustomDateSerializer(final String format) {
        this.format = format;
    }

    public CustomDateSerializer() {
        this.format = null;
    }

    @Override
    public void serialize(final LocalDateTime value, final JsonGenerator jsonGenerator, final SerializerProvider provider) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        log.info("the formatter: {}", format);
        jsonGenerator.writeString(value.format(formatter));
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider, final BeanProperty beanProperty) throws JsonMappingException {
        DateJsonFormat dateJsonFormat = Objects.isNull(beanProperty.getAnnotation(DateJsonFormat.class)) ? beanProperty.getContextAnnotation(DateJsonFormat.class) :
                beanProperty.getAnnotation(DateJsonFormat.class);
        return new CustomDateSerializer(dateJsonFormat.value());
    }
}
