package com.recruiting.converter;

import com.recruiting.utils.StringUtils;
import org.springframework.binding.convert.converters.StringToObject;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Martha on 6/16/2017.
 */
public class StringToLocalDateConverter extends StringToObject implements GenericConverter {

    private DateTimeFormatter formatter;

    public StringToLocalDateConverter() {
        super(LocalDate.class);
        formatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd", Locale.US);
    }

    // region Subject of StringToObject abstraction
    @Override
    protected Object toObject(String string, Class targetClass) throws ParseException {
        try {
            LocalDate localDateTime = LocalDate.parse(string, formatter);
            return localDateTime;
        } catch (Exception e) {
            return LocalDate.of(9999, 12, 30);
        }
    }

    @Override
    protected String toString(Object object) {
        LocalDate localDateTime = (LocalDate) object;
        return formatter.format(localDateTime);
    }
    // endregion


    // region Subject of GenericConverter interface
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertiblePairs = new HashSet<>();
        convertiblePairs.add(new ConvertiblePair(String.class, LocalDate.class));
        convertiblePairs.add(new ConvertiblePair(LocalDate.class, String.class));
        return convertiblePairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (sourceType.getObjectType().equals(String.class) && !StringUtils.isNullOrEmpty((String) source)) {
            return LocalDate.parse((String) source, formatter);
        } else if (source != null && sourceType.getObjectType().equals(LocalDate.class)) {
            DateTimeFormatter.ofPattern("EEEE yyyy/MMMM/dd", Locale.US).format((LocalDate) source);
        }
        return null;
    }
    // endregion
}
