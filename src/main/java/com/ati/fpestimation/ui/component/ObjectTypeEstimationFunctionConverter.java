package com.ati.fpestimation.ui.component;

import com.ati.fpestimation.domain.kpi.EstimationFunction;
import com.vaadin.data.util.converter.Converter;

import java.util.Locale;

public class ObjectTypeEstimationFunctionConverter implements Converter<Object, Object> {
    @Override
    public Object convertToModel(Object value, Class<?> targetType, Locale locale) throws
            Converter.ConversionException {
        //TODO ATI use function from repository
        if (value != null)
            return new EstimationFunction(value.toString(), null);
        else
            return null;
    }

    @Override
    public Object convertToPresentation(Object value, Class<?> targetType, Locale locale) throws
            Converter.ConversionException {
        if (value != null)
            return value.toString();
        else
            return "";
    }

    @Override
    public Class<Object> getModelType() {
        return Object.class;
    }

    @Override
    public Class<Object> getPresentationType() {
        return Object.class;
    }


}