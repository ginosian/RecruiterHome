package com.recruiting.converter;

import com.recruiting.utils.StringUtils;
import org.springframework.binding.convert.converters.StringToObject;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Martha on 5/20/2017.
 */
public class StringToDateConverter extends StringToObject implements GenericConverter {

    private SimpleDateFormat usDateFormatter;

    public StringToDateConverter() {
        super(Date.class);
        usDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    protected Object toObject(String string, Class targetClass) throws ParseException {
        try {
            Date date = usDateFormatter.parse(string);
            return date;
        } catch (Exception e) {
            return new Date(999999999999999999l);
        }
    }

    @Override
    protected String toString(Object object) {
        Date localDate = (Date) object;
        return usDateFormatter.format(localDate);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertiblePairs = new HashSet<>();
        convertiblePairs.add(new ConvertiblePair(String.class, Date.class));
        convertiblePairs.add(new ConvertiblePair(Date.class, String.class));
        return convertiblePairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        try {
            if (sourceType.getObjectType().equals(String.class) && !StringUtils.isNullOrEmpty((String) source)) {
                return usDateFormatter.parse((String) source);
            } else if (source != null && sourceType.getObjectType().equals(Date.class)) {
                usDateFormatter.format((Date) source);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
