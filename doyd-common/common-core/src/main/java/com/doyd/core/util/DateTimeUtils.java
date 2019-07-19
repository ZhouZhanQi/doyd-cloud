package com.doyd.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 *
 * @author ZhouZQ
 * @create 2019/3/20
 */
@Slf4j
public class DateTimeUtils {

    public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public final static String DEFAULT_DAY_FORMAT = "yyyyMMdd";

    /**
     * 将date类型日期转换为localdatetime
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将localdatetime类型日期转换为date
     * @param time
     * @return
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 获取时间戳
     * @param localDateTime 当前时间
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 获取当前日期
     * @return
     */
    public static LocalDateTime getCurrentDateTime(){
        return LocalDateTime.now();
    }

    /**
     * 字符串转换为指定格式日期(带时分秒)
     * @param dateStr
     * @param format
     * @return
     */
    public static LocalDateTime convertStrToLocalDateTime(String dateStr, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * 字符串转换为指定格式日期
     * @param dateStr
     * @param format
     * @return
     */
    public static LocalDate convertStrToLocalDate(String dateStr, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * 将日期转换为 yyyy-MM-dd 默认日期格式
     * @param localDate
     * @return
     */
    public static String convertLocalDateDefaultStr(LocalDate localDate){
        return convertLocalDateToStr(localDate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 将日期转换为指定格式
     * @param localDate
     * @param format
     * @return
     */
    public static String convertLocalDateToStr(LocalDate localDate, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }


    /**
     * 字符串转换为标准日期 yyyy-MM-dd HH:mm:ss
     * @param dateStr
     * @return
     */
    public static LocalDateTime covertStrToDefaultDateTime(String dateStr){
        return convertStrToLocalDateTime(dateStr, DEFAULT_DATE_TIME_FORMAT);
    }


    /**
     * 将日期转换为默认格式 yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return
     */
    public static String convertLocalDateTimeToDefaultStr(LocalDateTime dateTime){
        return convertLocalDateTimeToStr(dateTime, DEFAULT_DATE_TIME_FORMAT);
    }


    /**
     * 将日期转换为指定格式
     * @param dateTime
     * @param formatterStr
     * @return
     */
    public static String convertLocalDateTimeToStr(LocalDateTime dateTime, String formatterStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterStr);
        return dateTime.format(formatter);
    }


    /**
     * 获取本月第一天时间
     * @return
     */
    public static LocalDateTime getFirstDayOfCurrentMonth(){
        return getCurrentDateTime().with(TemporalAdjusters.firstDayOfMonth());
    }


    /**
     * 获取本月最后一天时间
     * @return
     */
    public static LocalDateTime getLastDayOfCurrentMonth(){
        return getCurrentDateTime().with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取本周第一天
     * @return
     */
    public static LocalDateTime getFirstDayOfWeek(){
        return getCurrentDateTime().with(temporal -> temporal.with(ChronoField.DAY_OF_WEEK, temporal.range(ChronoField.DAY_OF_WEEK).getMinimum()));
    }


    /**
     * 获取本周最后一天
     * @return
     */
    public static LocalDateTime getLastDayOfWeek(){
        return getCurrentDateTime().with(temporal -> temporal.with(ChronoField.DAY_OF_WEEK, temporal.range(ChronoField.DAY_OF_WEEK).getMaximum()));
    }

    /**
     * 获取当前日期前一天
     * @return
     */
    public static LocalDateTime getPreDateTime(){
        return getCurrentDateTime().plus(-1, ChronoUnit.DAYS);
    }

    /**
     * 获取当前日期后一天
     * @return
     */
    public static LocalDateTime getNextDateTime(){
        return getCurrentDateTime().plus(1, ChronoUnit.DAYS);
    }

    /**
     * 指定时间加天数获取新的日期
     * @param dateTime
     * @param days
     * @return
     */
    public static LocalDateTime dateTimeAddByDays(LocalDateTime dateTime, Long days){
        return dateTime.plus(days, ChronoUnit.DAYS);
    }

    /**
     * 指定时间加月数获取新的日期
     * @param dateTime
     * @param months
     * @return
     */
    public static LocalDateTime dateTimeAddByMonth(LocalDateTime dateTime, Long months){
        return dateTime.plus(months, ChronoUnit.MONTHS);
    }


    /**
     * 获取两个时间相差多少天
     * @param startDateTime
     * @param endDatetime
     * @return
     */
    public static long getDaysBetweenDateTime(LocalDateTime startDateTime, LocalDateTime endDatetime){
        return startDateTime.until(endDatetime, ChronoUnit.DAYS);
    }

    /**
     * 获取两个时间相差多少个月
     * @param startDateTime
     * @param endDatetime
     * @return
     */
    public static long getMonthsBetweenDateTime(LocalDateTime startDateTime, LocalDateTime endDatetime){
        return startDateTime.until(endDatetime, ChronoUnit.MONTHS);
    }

    /**
     * @param date
     * @param format
     * @return
     * 将DATA转换成指定格式
     */
    public static String convertToString(Date date, String format) {
        return getSimpleDateFormat(format).format(date);
    }

    /**
     * 将Date类型转换为普通格式的String类型时间
     *
     * @param timestamp
     * @return
     */
    public static String timestampConvertToString(long timestamp) {
        return convertToString(timestamp, DEFAULT_DATE_FORMAT);
    }
    /**
     * 将Date类型转换为自定义类型的String类型时间
     *
     * @param format
     * @param timestamp
     * @return
     */
    public static String convertToString(long timestamp, String format) {
        return getSimpleDateFormat(format).format(timestamp);
    }

    /**
     * 获得明天凌晨的时间time
     *
     * @return
     */
    public static long getNextDayZeroTime() {
        //用新日期类型更简单
        return regulationByDate(1, LocalTime.MIN);
    }
    /**
     * 根据当前时间获取plusDay的凌晨时间或者最晚时间
     * @param plusDay
     * @return
     */
    public static Long regulationByDate(Integer plusDay, LocalTime localTime) {
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.of(LocalDate.now().plusDays(plusDay), localTime)
                .atZone(zone).toInstant().toEpochMilli();
    }


    /**
     * 根据当前时间获取plusYear的凌晨时间或者最晚时间
     * @param plusYear
     * @param localTime
     * @return
     */
    public static Long regulationByYear(Integer plusYear, LocalTime localTime) {
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.of(LocalDate.now().plusDays(plusYear), localTime)
                .atZone(zone).toInstant().toEpochMilli();
    }

    /**
     * 获取SimpleDateFormat实例
     *
     * @param format
     * @return
     */
    private static SimpleDateFormat getSimpleDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * @Description: 时间搓转成格式化日期
     * @param
     * @return
     * @author 李光华
     * @date 2019/6/14  17:58
     */
    private static String convertTimeToString(Long time,String format){
        Assert.notNull(time, "time is null");
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(format);
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time),ZoneId.systemDefault()));
    }



    /**
     * Description:获取自定义时间字符串 //x分钟前/x小时前
     * @return:java.lang.String
     * Author:周涛
     * Date Created in 2019/6/10 11:35
     */
    public static String getDefaultTimeStr(long timestamp){
        long diffTimestamp=getTimestampOfDateTime(LocalDateTime.now())-timestamp;
        if(diffTimestamp<60*1000){
            return 1+Math.round(Math.ceil(diffTimestamp/(1000)))+"秒前";
        }else if(60*1000<=diffTimestamp&&diffTimestamp<=60*60*1000){
           return Math.round(Math.ceil(diffTimestamp/(60*1000)))+"分钟前";
        }else if(60*60*1000<diffTimestamp&&diffTimestamp<=24*60*60*1000){
            return  Math.round(Math.ceil(diffTimestamp/(60*60*1000)))+"小时前";
        }else {
            return  Math.round(Math.ceil(diffTimestamp/(24*60*60*1000)))+"天前";
        }
    }
    /**
     * Description:获取今天是星期几
     * @return:java.lang.Integer
     * Author:周涛
     * Date Created in 2019/6/10 15:44
     */
    public static Integer getWeekDay(){
        LocalDateTime ldt = LocalDateTime.now();
        return ldt.get(WeekFields.of(DayOfWeek.of(1), 1).dayOfWeek());
    }
}
