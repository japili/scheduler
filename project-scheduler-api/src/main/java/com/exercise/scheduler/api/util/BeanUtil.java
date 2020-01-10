/*
 * Copyright 2020
 */

package com.exercise.scheduler.api.util;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

/**
 * Created by japili on 08/01/2020.
 */
public class BeanUtil {

    private static ModelMapper modelMapper = new ModelMapper();

    private BeanUtil() {
    }

    public static <I, O> O convert(I input, Class<O> o) {
        return modelMapper.map(input, o);
    }

    static {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }
}
