package com.demo.somnus.bomda.util;

import android.util.Log;


import com.demo.somnus.bomda.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Somnus on 2018/4/4.
 * 天气处理工具类
 */

public class WeatherUtil {

    public static final int UNKNOWN_ICON_ID = -1;

    /**
     * 取得格式化的服务器时间
     * @return 格式化的服务器时间
     */
    public static String serverTimeToString(long serverTime) {
        long server_time = serverTime * 1000;
        String updateTime = new Date(server_time).toLocaleString() + " ";
        Log.e("updateTime", updateTime);
        Date now = new Date();
        long timeDiff = now.getTime() - server_time;
        if (timeDiff < 5 * 60 * 1000) {
            updateTime = "刚刚";
        } else if (timeDiff < 60 * 60 * 1000) {
            int min = (int) (timeDiff / (60 * 1000));
            updateTime = min + " 分钟前";
        } else if (timeDiff < 3 * 60 * 60 * 1000) {
            int hour = (int) (timeDiff / (60 * 60 * 1000));
            int min = (int) ((timeDiff % (60 * 60 * 1000)) / (60 * 1000));
            if (min >= 40) hour += 1; // 比如，如果是 2 小时 40 分钟，就当做 3 小时返回
            updateTime = hour + " 小时前";
        } else {
            return "数据已过期";
        }
        Log.e("updateTime", updateTime);

        return updateTime + "更新";
    }

    /* 返回 temperature 的字符串形式 */
    public static String temperatureToString(double temperature) {
        return Math.round(temperature) + "°";
    }

    /* 返回 aqi 的严重程度说明 */
    public static String aqiToString(double aqi) {
        if (aqi >= 0 && aqi <= 50) {
            return "优";
        } else if (aqi >= 51 && aqi <= 100) {
            return "良";
        } else if (aqi >= 101 && aqi <= 150) {
            return "轻度污染";
        } else if (aqi >= 151 && aqi <= 200) {
            return "中度污染";
        } else if (aqi >= 201 && aqi <= 300) {
            return "重度污染";
        } else {
            return "严重污染";
        }
    }

    /* 返回 aqi 的严重级别 */
    public static int aqiToLevel(double aqi) {
        if (aqi >= 0 && aqi <= 50) {
            return 1;
        } else if (aqi >= 51 && aqi <= 100) {
            return 2;
        } else if (aqi >= 101 && aqi <= 150) {
            return 3;
        } else if (aqi >= 151 && aqi <= 200) {
            return 4;
        } else if (aqi >= 201 && aqi <= 300) {
            return 5;
        } else {
            return 6;
        }
    }

    /**
     * 将 skycon 字段转换成中文
     * @return skycon 字段的中文描述
     */
    public static String skyconToDescription(String skycon) {
        switch (skycon) {
            case "CLEAR_DAY":
            case "CLEAR_NIGHT":
                return "晴";
            case "PARTLY_CLOUDY_DAY":
            case "PARTLY_CLOUDY_NIGHT":
                return "多云";
            case "CLOUDY":
                return "阴";
            case "RAIN":
                return "雨";
            case "SNOW":
                return "雪";
            case "WIND":
                return "风";
            case "FOG":
                return "雾";
            default:
                return "未知天气";
        }
    }

    /**
     * 取得对应天气的 icon
     * @return 对应天气的 icon 的 id，UNKNOWN_ICON_ID 表示没有相匹配的 icon
     */
    public static int skyconToIconId(String skycon) {
        switch (skycon) {
            case "CLEAR_DAY":
                return R.drawable.ic_weather_clear_day;
            case "CLEAR_NIGHT":
                return R.drawable.ic_weather_clear_night;
            case "PARTLY_CLOUDY_DAY":
                return R.drawable.ic_weather_partly_cloudy_day;
            case "PARTLY_CLOUDY_NIGHT":
                return R.drawable.ic_weather_partly_cloudy_night;
            case "CLOUDY":
                return R.drawable.ic_weather_cloudy;
            case "RAIN":
                return R.drawable.ic_weather_rain;
            case "SNOW":
                return R.drawable.ic_weather_snow;
            case "WIND":
                return R.drawable.ic_weather_wind;
            case "FOG":
                return R.drawable.ic_weather_fog;
            default:
                return UNKNOWN_ICON_ID;
        }
    }

    /**
     * 将 cloudrate 字段转换成字符串
     */
    public static String cloudrateToString(double cloudrate) {
        int cloudRate = (int) (cloudrate * 100);
        return cloudRate + "%";
    }

    /**
     * 将 humidity 字段转换成字符串
     */
    public static String humidityToString(Double humidity) {
        int humidity_int = (int) (humidity * 100);
        return humidity_int + "%";
    }

    /* 返回 distance 的字符串表示 */
    public static String distanceToString(double distance) {
        return distance + " km";
    }

    /* 返回 direction 的语义化描述 */
    public static String directionToString(double direction) {
        if ((direction >= 337.5 && direction < 360) || (direction >= 0 && direction < 22.5)) {
            return "北风";
        } else if (direction >= 22.5 && direction < 67.5) {
            return "东北风";
        } else if (direction >= 67.5 && direction < 112.5) {
            return "东风";
        } else if (direction >= 112.5 && direction < 157.5) {
            return "东南风";
        } else if (direction >= 157.5 && direction < 202.5) {
            return "南风";
        } else if (direction >= 202.5 && direction < 247.5) {
            return "西南风";
        } else if (direction >= 247.5 && direction < 292.5) {
            return "西风";
        } else if (direction >= 292.5 && direction < 337.5) {
            return "西北风";
        } else {
            return "未知风向";
        }
    }

    /* 返回 speed 的字符串形式 */
    public static String speedToString(double speed) {
        return speed + " km/h";
    }

    /* 返回 speed 的语义化描述 */
    public static String speedToDescription(double speed) {
        if (speed >= 0 && speed < 1) {
            return "无风";
        } else if (speed >= 1 && speed < 6) {
            return "软风";
        } else if (speed >= 6 && speed < 12) {
            return "轻风";
        } else if (speed >= 12 && speed < 20) {
            return "微风";
        } else if (speed >= 20 && speed < 29) {
            return "和风";
        } else if (speed >= 29 && speed < 39) {
            return "清风";
        } else if (speed >= 39 && speed < 50) {
            return "强风";
        } else if (speed >= 50 && speed < 62) {
            return "疾风";
        } else if (speed >= 62 && speed < 75) {
            return "大风";
        } else if (speed >= 75 && speed < 89) {
            return "烈风";
        } else if (speed >= 89 && speed < 103) {
            return "狂风";
        } else if (speed >= 103 && speed < 117) {
            return "暴风";
        } else {
            return "飓风";
        }
    }

    /* 返回风力等级 */
    public static int speedToLevel(double speed) {
        if (speed >= 0 && speed < 1) {
            return 0;
        } else if (speed >= 1 && speed < 6) {
            return 1;
        } else if (speed >= 6 && speed < 12) {
            return 2;
        } else if (speed >= 12 && speed < 20) {
            return 3;
        } else if (speed >= 20 && speed < 29) {
            return 4;
        } else if (speed >= 29 && speed < 39) {
            return 5;
        } else if (speed >= 39 && speed < 50) {
            return 6;
        } else if (speed >= 50 && speed < 62) {
            return 7;
        } else if (speed >= 62 && speed < 75) {
            return 8;
        } else if (speed >= 75 && speed < 89) {
            return 9;
        } else if (speed >= 89 && speed < 103) {
            return 10;
        } else if (speed >= 103 && speed < 117) {
            return 11;
        } else if (speed >= 117 && speed < 134) {
            return 12;
        } else if (speed >= 134 && speed < 150) {
            return 13;
        } else if (speed >= 150 && speed < 167) {
            return 14;
        } else if (speed >= 167 && speed < 184) {
            return 15;
        } else if (speed >= 184 && speed < 202) {
            return 16;
        } else if (speed >= 202 && speed < 221) {
            return 17;
        } else {
            return 18;
        }
    }

    /* 把表示时间的字符串格式化，取出 hourly 和 minute */
    public static String dateTimeToHourly(String tempDateTime) {
        return tempDateTime.substring(11);
    }

    /* 表示日期的字符串中取出 月份和日 */
    public static String dateToDayOfMonth(String date) {
        return date.substring(5);
    }

    /* 把表示日期的字符串转换成一般描述 */
    public static String dateToDescription(String dateStr) {
        SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat sdf_2 = new SimpleDateFormat("EE", Locale.getDefault());

        Date today; // 今天的日期
        Date date; // 目标日期
        String date_description;
        try {
            today = sdf_1.parse(sdf_1.format(new Date())); // 只保留日期，不要时间
            date = sdf_1.parse(dateStr);
            date_description = sdf_2.format(date); // 格式化成周几
        } catch (ParseException e) {
            return "";
        }

        int hour24 = 24 * 60 * 60 * 1000; // 24 小时的毫秒数
        // Log.e("hour24", hour24+"");
        // Log.e("diff", date.getTime() - today.getTime() + "");
        long diff = date.getTime() - today.getTime();
        if (diff == 0) {
            date_description =  "今天";
        } else if (diff == hour24) {
            date_description =  "明天";
        }

        return date_description;
    }

    public static String probabilityToString(Double probabilityPercent) {
        return Math.round(probabilityPercent) + "%";
    }



    /* 返回 vis 的字符串表示 */
    public static String visToString(float vis) {
        return vis + " km";
    }

    /* 返回 pres 的字符串表示 */
    public static String presToString(float pres) {
        return Math.round(pres) + " hPa";
    }
}
