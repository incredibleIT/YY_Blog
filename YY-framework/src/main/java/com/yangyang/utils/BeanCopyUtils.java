package com.yangyang.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    public static <V> V copyBean(Object object, Class<V> clazz) {


        V result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(object, result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;

    }


    public static <O, V> List<V> copyBeanList(List<O> sourceList, Class<V> clazz) {

        return sourceList.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }




}
