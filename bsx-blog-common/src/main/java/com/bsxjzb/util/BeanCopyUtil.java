package com.bsxjzb.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtil {
    private BeanCopyUtil() {}

    public static <S,T> T copyBean(S source, Class<T> clazz) {
        T target = null;
        try {
            target = clazz.newInstance();
            BeanUtils.copyProperties(source,target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    public static <S,T> List<T> copyBeanList(List<S> list, Class<T> clazz) {
        return list.stream().map(item->copyBean(item,clazz)).collect(Collectors.toList());
    }
}
