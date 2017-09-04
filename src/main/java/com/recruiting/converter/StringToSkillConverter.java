package com.recruiting.converter;

import com.recruiting.domain.Skill;
import com.recruiting.service.entity.SkillService;
import com.recruiting.utils.StringUtils;
import org.springframework.binding.convert.converters.InvalidFormatException;
import org.springframework.binding.convert.converters.StringToObject;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Martha on 5/20/2017.
 */
public class StringToSkillConverter extends StringToObject implements Converter {

    private final SkillService service;

    public StringToSkillConverter(SkillService service) {
        super(Skill.class);
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
            throw new InvalidFormatException(string, "Skill", e);
        }
    }

    @Override
    protected String toString(Object object) throws Exception {
        Skill skill = (Skill) object;
        if (skill == null || skill.getId() == null) {
            return "";
        }
        return skill.getId().toString();
    }

    @Override
    public Object convert(Object source) {
        return service.findById(new Long((String) source));
    }
}
