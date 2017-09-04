package com.recruiting.converter;

import com.recruiting.domain.State;
import com.recruiting.service.entity.StateService;
import com.recruiting.utils.StringUtils;
import org.springframework.binding.convert.converters.InvalidFormatException;
import org.springframework.binding.convert.converters.StringToObject;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Martha on 5/20/2017.
 */
public class StringToStateConverter extends StringToObject implements Converter {

    private final StateService service;

    public StringToStateConverter(StateService service) {
        super(State.class);
        this.service = service;
    }

    @Override
    protected Object toObject(String string, Class targetClass) throws Exception {
        if (StringUtils.isNullOrEmpty(string) || new Long(string) < 1) {
            return null;
        }
        try {
            return service.findById(new Long(string));
        } catch (Exception e) {
            throw new InvalidFormatException(string, "State", e);
        }
    }

    @Override
    protected String toString(Object object) throws Exception {
        State state = (State) object;
        if (state == null || state.getId() == null) {
            return "";
        }
        return state.getId().toString();
    }

    @Override
    public Object convert(Object source) {
        return service.findById(new Long((String) source));
    }
}
