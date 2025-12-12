package com.ronan.common.json.convert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;


/**
 * 字段类型是LocalDateTime时，可以按照以下6种格式反序列化：
 * 1. yyyy-MM-dd
 * 2. yyyy年MM月dd日
 * 3. yyyy/MM/dd
 * 4. yyyy-MM-dd HH:mm:ss
 * 5. yyyy年MM月dd日HH时mm分ss秒
 * 6. yyyy/MM/dd HH:mm:ss
 * @author L.J.Ran
 */
public class LocalDateTimeDeserializer extends JSR310DateTimeDeserializerBase<LocalDateTime> {
    public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
    public static final String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";
    public static final String SLASH_DATE_FORMAT = "yyyy/MM/dd";

    public static final String NORM_TIME_PATTERN = "HH:mm:ss";
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String CHINESE_DATE_TIME_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String SLASH_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT_MATCHES = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
    public static final String DEFAULT_DATE_FORMAT_EN_MATCHES = "^\\d{4}年\\d{1,2}月\\d{1,2}日$";
    public static final String SLASH_DATE_FORMAT_MATCHES = "^\\d{4}/\\d{1,2}/\\d{1,2}$";
    public static final String DEFAULT_DATE_TIME_FORMAT_MATCHES = "^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$";
    public static final String DEFAULT_DATE_TIME_FORMAT_EN_MATCHES = "^\\d{4}年\\d{1,2}月\\d{1,2}日\\d{1,2}时\\d{1,2}分\\d{1,2}秒$";
    public static final String SLASH_DATE_TIME_FORMAT_MATCHES = "^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$";


    /**
     * 以下是支持的6种参数格式
     */
    private static final DateTimeFormatter DEFAULT_DATE_FORMAT_DTF = DateTimeFormatter.ofPattern(NORM_DATE_PATTERN);
    private static final DateTimeFormatter DEFAULT_DATE_FORMAT_EN_DTF = DateTimeFormatter.ofPattern(CHINESE_DATE_PATTERN);
    private static final DateTimeFormatter SLASH_DATE_FORMAT_DTF = DateTimeFormatter.ofPattern(SLASH_DATE_FORMAT);
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMAT_DTF = DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN);
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMAT_EN_DTF = DateTimeFormatter.ofPattern(CHINESE_DATE_TIME_PATTERN);
    private static final DateTimeFormatter SLASH_DATE_TIME_FORMAT_DTF = DateTimeFormatter.ofPattern(SLASH_DATE_TIME_FORMAT);

    private LocalDateTimeDeserializer() {
        this(DEFAULT_FORMATTER);
    }

    public LocalDateTimeDeserializer(DateTimeFormatter formatter) {
        super(LocalDateTime.class, formatter);
    }

    protected LocalDateTimeDeserializer(LocalDateTimeDeserializer base, Boolean leniency) {
        super(base, leniency);
    }

    @Override
    protected JSR310DateTimeDeserializerBase<LocalDateTime> withShape(JsonFormat.Shape shape) {
        return this;
    }

    @Override
    protected LocalDateTimeDeserializer withLeniency(Boolean leniency) {
        return new LocalDateTimeDeserializer(this, leniency);
    }

    @Override
    protected JSR310DateTimeDeserializerBase<LocalDateTime> withDateFormat(DateTimeFormatter formatter) {
        return new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer(formatter);
    }

    private LocalDateTime convert(String source) {
        if (source.matches(DEFAULT_DATE_FORMAT_MATCHES)) {
            return LocalDateTime.of(LocalDate.parse(source, DEFAULT_DATE_FORMAT_DTF), LocalTime.MIN);
        }
        if (source.matches(DEFAULT_DATE_FORMAT_EN_MATCHES)) {
            return LocalDateTime.of(LocalDate.parse(source, DEFAULT_DATE_FORMAT_EN_DTF), LocalTime.MIN);
        }
        if (source.matches(SLASH_DATE_FORMAT_MATCHES)) {
            return LocalDateTime.of(LocalDate.parse(source, SLASH_DATE_FORMAT_DTF), LocalTime.MIN);
        }
        if (source.matches(DEFAULT_DATE_TIME_FORMAT_MATCHES)) {
            return LocalDateTime.parse(source, DEFAULT_DATE_TIME_FORMAT_DTF);
        }
        if (source.matches(DEFAULT_DATE_TIME_FORMAT_EN_MATCHES)) {
            return LocalDateTime.parse(source, DEFAULT_DATE_TIME_FORMAT_EN_DTF);
        }
        if (source.matches(SLASH_DATE_TIME_FORMAT_MATCHES)) {
            return LocalDateTime.parse(source, SLASH_DATE_TIME_FORMAT_DTF);
        }
        return null;
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        // 字符串
        if (parser.hasTokenId(JsonTokenId.ID_STRING)) {
            String input = parser.getText().trim();
            if (input.isEmpty()) {
                return null;
            }
            return parseString(input, context);
        }
        // 数组
        if (parser.isExpectedStartArrayToken()) {
            JsonToken token = parser.nextToken();
            if (token == JsonToken.END_ARRAY) {
                return null;
            }
            return parseArray(parser, token, context);
        }

        // 处理数字时间戳
        if (parser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            return Instant.ofEpochMilli(parser.getLongValue()).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        }

        // 处理嵌入对象
        if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            Object embedded = parser.getEmbeddedObject();
            if (embedded instanceof LocalDateTime) {
                return (LocalDateTime) embedded;
            }
        }


        return _handleUnexpectedToken(context, parser, "当前参数需要数组、字符串、时间戳。");
    }

    private LocalDateTime parseString(String input, DeserializationContext context) throws IOException {
        try {
            if (_formatter == null || _formatter == DEFAULT_FORMATTER) {
                // ISO 格式 "yyyy-MM-ddTHH:mm:ss" 或 "yyyy-MM-ddTHH:mm:ssZ"
                if (input.length() > 10 && input.charAt(10) == 'T') {
                    if (input.endsWith("Z")) {
                        return LocalDateTime.ofInstant(Instant.parse(input), ZoneOffset.UTC);
                    }
                    return LocalDateTime.parse(input, DEFAULT_FORMATTER);
                }
                return convert(input);
            }
            return LocalDateTime.parse(input, _formatter);
        } catch (DateTimeException e) {
            return _handleDateTimeException(context, e, input);
        }
    }

    // 解析数组
    private LocalDateTime parseArray(JsonParser parser, JsonToken token, DeserializationContext context) throws IOException {

        // 单值数组，尝试解包
        if ((token == JsonToken.VALUE_STRING || token == JsonToken.VALUE_EMBEDDED_OBJECT)
                && context.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            LocalDateTime singleValue = deserialize(parser, context);
            if (parser.nextToken() != JsonToken.END_ARRAY) {
                handleMissingEndArrayForSingle(parser, context);
            }
            return singleValue;
        }

        // 按字段解析数组 [year, month, day, hour, minute, second, nano]
        if (token == JsonToken.VALUE_NUMBER_INT) {
            int year = parser.getIntValue();
            int month = parser.nextIntValue(-1);
            int day = parser.nextIntValue(-1);
            int hour = parser.nextIntValue(-1);
            int minute = parser.nextIntValue(-1);

            int second = parser.nextToken() == JsonToken.VALUE_NUMBER_INT ? parser.getIntValue() : 0;
            int nano = parser.nextToken() == JsonToken.VALUE_NUMBER_INT ? parser.getIntValue() : 0;

            if (nano < 1_000 && !context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
                // 毫秒转纳秒
                nano *= 1_000_000;
            }

            if (parser.nextToken() != JsonToken.END_ARRAY) {
                throw context.wrongTokenException(parser, handledType(), JsonToken.END_ARRAY, "Expected array to end");
            }
            return LocalDateTime.of(year, month, day, hour, minute, second, nano);
        }

        context.reportInputMismatch(handledType(), "Unexpected token (%s) within Array, expected VALUE_NUMBER_INT", token);
        return null;
    }

}
