package com.bsxjzb.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PathUtil {
    public static String generatePath(String fileName) {
        String suffix = getSuffix(fileName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String date = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return date + uuid + suffix;
    }
    public static String getSuffix(String s) {
        int index = s.lastIndexOf(".");
        if (index == -1) return "";
        return s.substring(index);
    }
}
