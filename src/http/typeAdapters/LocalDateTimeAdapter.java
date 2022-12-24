package http.typeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * "Класс сериализации и десериализации LocalDateTime"
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private String timeDatePattern = "dd.MM.yyyy HH:mm";

    @Override
    public void write(final JsonWriter jsonWriter, final LocalDateTime localDateTime) throws IOException {
        LocalDateTime value;
        if (localDateTime == null) {
            value = LocalDateTime.now();
        } else {
            value = localDateTime;
        }
        jsonWriter.value(value.format(DateTimeFormatter.ofPattern(timeDatePattern)));
    }

    @Override
    public LocalDateTime read(final JsonReader jsonReader) throws IOException {
        return LocalDateTime.parse(jsonReader.nextString(), DateTimeFormatter.ofPattern(timeDatePattern));
    }

    public String getTimeDatePattern() {
        return timeDatePattern;
    }
}

