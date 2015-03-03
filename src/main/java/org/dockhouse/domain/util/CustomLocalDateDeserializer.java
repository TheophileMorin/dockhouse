package org.dockhouse.domain.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by BWI on 03/03/15.
 */
public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException {

        LocalDate date;
        try {
            date =  LocalDateDeserializer.INSTANCE.deserialize(jp,ctxt);
        } catch (Exception ex) {
            ObjectCodec oc = jp.getCodec();
            TextNode node = oc.readTree(jp);
            String dateString = node.textValue();

            Instant instant = Instant.parse(dateString);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            date = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
        }

        return date;


    }
}
