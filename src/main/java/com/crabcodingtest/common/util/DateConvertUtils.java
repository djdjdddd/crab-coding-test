package com.crabcodingtest.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author : yong
 * @fileName : DateConvertUtils
 * @date : 2024-02-19
 * @description : 나중에 백기선 자바 8 강의 보고 다시 복습할 클래스
 */
public class DateConvertUtils {

    public static Date toDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Long toEpochMilli(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

}
