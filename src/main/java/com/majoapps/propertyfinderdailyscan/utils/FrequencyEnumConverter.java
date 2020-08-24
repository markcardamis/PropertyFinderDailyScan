package com.majoapps.propertyfinderdailyscan.utils;

import com.majoapps.propertyfinderdailyscan.data.enums.Frequency;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter()
public class FrequencyEnumConverter implements AttributeConverter<Frequency, String> {

    @Override
    public String convertToDatabaseColumn(Frequency frequency) {
        if (frequency == null) {
            return null;
        }
        return frequency.name();
    }

    @Override
    public Frequency convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        try {
            return Frequency.valueOf(code);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}