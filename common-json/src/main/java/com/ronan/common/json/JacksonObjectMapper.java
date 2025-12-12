package com.ronan.common.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ronan.common.json.convert.LocalDateTimeDeserializer;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author L.J.Ran
 */
public class JacksonObjectMapper extends ObjectMapper {

    public JacksonObjectMapper() {
        super();
        super.setLocale(Locale.CHINA)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))
                .setDateFormat(new SimpleDateFormat(LocalDateTimeDeserializer.NORM_DATETIME_PATTERN, Locale.CHINA))
                .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true)
                .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true)
                .findAndRegisterModules()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        super.registerModule(new JacksonModule());
        super.findAndRegisterModules();
    }


}
