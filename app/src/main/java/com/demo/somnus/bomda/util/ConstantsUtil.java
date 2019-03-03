package com.demo.somnus.bomda.util;

/**
 * Created by Somnus on 2018/4/4.
 * 常量字符管理工具类
 */

public class ConstantsUtil {
    // 用于SharedPreferences保存程序状态文件时
    public static final String APP_DATA = "app_data";
    public static final String FIRST_INSTALL = "first_install";
    public static final String TIME_OF_ENROLLMENT = "time_of_enrollment";

    /**
     * xml文件名
     */
    public static final String SHARD_NAME = "AppTools";

    public static final String SHARD_NAME_STRING = "string";
    public static final String SHARD_NAME_INT = "int";
    public static final String SHARD_NAME_FLOAT = "float";
    public static final String SHARD_NAME_LONG = "long";
    public static final String SHARD_NAME_BOOLEAN = "boolean";


    public static final String FILE_NAME = "UserInfo";
    public static final String FILE_NAME_STUDENT_NAME = "studentName";
    public static final String FILE_NAME_STUDENT_ID = "studentId";
    public static final String FILE_NAME_STUDENT_MAIN = "main";
    public static final String FILE_NAME_STUDENT_GRADE = "grade";
    public static final String FILE_NAME_STUDENT_SCHEDULE = "schedule";
    public static final String FILE_NAME_STUDENT_TEST = "test";
    public static final String FILE_NAME_STUDENT_GRADE_VIEWSTATE = "__VIEWSTATE_GRADE";
    public static final String FILE_NAME_STUDENT_GRADE_EVENTTARGET = "__EVENTTARGET_GRADE";
    public static final String FILE_NAME_STUDENT_GRADE_EVENTARGUMENT = "__EVENTARGUMENT_GRADE";
    public static final String FILE_NAME_STUDENT_SCHEDULE_VIEWSTATE = "__VIEWSTATE_SCHEDULE";
    public static final String FILE_NAME_STUDENT_SCHEDULE_EVENTTARGET = "__EVENTTARGET_SCHEDULE";
    public static final String FILE_NAME_STUDENT_SCHEDULE_EVENTARGUMENT = "__EVENTARGUMENT_SCHEDULE";


    /**
     * 设置选项
     */
    public static final String TOOLS_TRAFFIC = "tools_provincial_traffic"; // 省流量
    public static final String TOOLS_VIDEO = "tools_video_playback"; // 视频自动播放
    public static final String TOOLS_NIGHT = "tools_night_pattern"; // 日夜自动开启
    public static final String TOOLS_MESSAGE = "tools_notice_new_message"; // 新消息提醒
    public static final String TOOLS_PROMPT = "tools_notice_prompt"; // 提示音
    public static final String TOOLS_VIBRATION = "tools_notice_vibration"; // 震动
    public static final String TOOLS_SKIN = "tools_skin";



    // 要查询的信息的类型
    public static final int SEARCH_SCHEDULE = 1;
    public static final int SEARCH_SCORE = 2;

    public static final int GRADE = 0;
    public static final int SCHEDULE = 1;
    public static final int TIMES = 2;
    public static final int LIBRARY = 3;
    public static final int SERVICE = 4;

    // Jsoup解析后传递的Map所用的key值
    public static final String STUDENTNAME = "studentName";
    public static final String FAILEDINFO = "failedInfo";
    public static final String STUDENTNUM = "201424070133";

    /**
     * 验证码网址
     */
    public static final String VERIFICATION_CODE_URL = "http://210.37.0.16/CheckCode.aspx";
    /**
     * 教务系统登录界面网址
     */
    public static final String EDUCATION_SYSTEM_LOGIN_URL = "http://210.37.0.16/default2.aspx";


    // 请求头
    public static final String HEADER_NAME_HOST = "Host";
    public static final String HEADER_VALUE_HOST = "210.37.0.16";
    public static final String HEADER_NAME_REFERER ="Referer";
    public static final String HEADER_VALUE_REFERER ="http://210.37.0.16/";
    public static final String HEADER_NAME_COOKIE ="Cookie";
    public static final String HEADER_VALUE_COOKIE ="ASP.NET_SessionId=5opk3c45cdnrcx55khwluh3j";
    public static final String HEADER_NAME_AGENT ="User-Agent";
    public static final String HEADER_VALUE_AGENT ="User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E; GWX:DOWNLOADED; GWX:RESERVED; InfoPath.3)";
    // 登录时的请求参数
    public static final String LOGIN_BODY_NAME_VIEWSTATE = "__VIEWSTATE";
    public static final String LOGIN_BODY_VALUE_VIEWSTATE = "dDwtNTE2MjI4MTQ7Oz4d5Bvx/yx5FV84/+3jV8n9VuU9xw==";
    public static final String LOGIN_BODY_NAME_TXTUSERNAME = "txtUserName";
    public static final String LOGIN_BODY_VALUE_TXTUSERNAME = "";
    public static final String LOGIN_BODY_NAME_TEXTBOX1 = "Textbox1";
    public static final String LOGIN_BODY_VALUE_TEXTBOX1 = "";
    public static final String LOGIN_BODY_NAME_TEXTBOX2 = "TextBox2";
    public static final String LOGIN_BODY_VALUE_TEXTBOX2 = "";
    public static final String LOGIN_BODY_NAME_SECRETCODE = "txtSecretCode";
    public static final String LOGIN_BODY_VALUE_SECRETCODE = "";
    public static final String LOGIN_BODY_NAME_TYPE = "RadioButtonList1";
    public static final String LOGIN_BODY_VALUE_TYPE = "学生";
    public static final String LOGIN_BODY_NAME_BUTTON1 = "Button1";
    public static final String LOGIN_BODY_VALUE_BUTTON1 = "";
    public static final String LOGIN_BODY_NAME_LANGUAGE = "lbLanguage";
    public static final String LOGIN_BODY_VALUE_LANGUAGE = "";
    public static final String LOGIN_BODY_NAME_HIDPDRS = "hidPdrs";
    public static final String LOGIN_BODY_VALUE_HIDPDRS = "";
    public static final String LOGIN_BODY_NAME_HIDSC = "hidsc";
    public static final String LOGIN_BODY_VALUE_HIDSC = "";

    /**
     * 学生学号
     */
    public static  String XH ;
    /**
     * 教务系统首页界面网址
     */
    public static final String EDUCATION_SYSTEM_HOME_URL = "http://210.37.0.22/xs_main.aspx?xh=";

    // 请求头

    public static final String HOME_HEADER_NAME_HOST = "Host";
    public static final String HOME_HEADER_VALUE_HOST = "210.37.0.27";
    public static final String HOME_HEADER_NAME_REFERER ="Referer";
    public static final String HOME_HEADER_VALUE_REFERER ="http://210.37.0.27/xs_main.aspx?xh="+ ConstantsUtil.STUDENTNAME;
    public static final String HOME_HEADER_NAME_AGENT ="User-Agent";
    public static final String HOME_HEADER_VALUE_AGENT ="User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";

    // 查询时的请求参数
    public static final String HOME__BODY_NAME_VIEWSTATE = "__VIEWSTATE";
    public static final String HOME__BODY_VALUE_VIEWSTATE = "dDwxNDM1NzMwODY2O3Q8O2w8aTwxPjs+O2w8dDw7bDxpPDE+O2k8Mz47aTw1PjtpPDk+Oz47bDx0PHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+O2w8aTw3Pjs+O2w8dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjt0PEAwPHA8cDxsPERhdGFLZXlzO18hSXRlbUNvdW50Oz47bDxsPD47aTwxPjs+Pjs+Ozs7Ozs7Ozs+O2w8aTwwPjs+O2w8dDw7bDxpPDA+O2k8MT47PjtsPHQ8QDzpgJrnn6U7Pjs7Pjt0PEAwPHA8cDxsPFBhZ2VDb3VudDtfIUl0ZW1Db3VudDtfIURhdGFTb3VyY2VJdGVtQ291bnQ7RGF0YUtleXM7PjtsPGk8MT47aTw0PjtpPDQ+O2w8Pjs+Pjs+Ozs7Ozs7Ozs7Oz47bDxpPDA+Oz47bDx0PDtsPGk8MT47aTwyPjtpPDM+O2k8ND47PjtsPHQ8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8XDxhIGhyZWY9JyMnIG9uY2xpY2s9IndpbmRvdy5vcGVuKCdnZ3NtLmFzcHg/ZmJzaj0yMDE2LTA1LTEwIDEwOjM0OjEyJnl4cXg9MjAxOS0wNS0zMCZ4aD0mbmJzcFw7JywnZ3hsYicsJ21lbnViYXI9MCxzY3JvbGxiYXJzPTEscmVzaXphYmxlPTEsd2lkdGg9OTAwLGhlaWdodD03MDAsbGVmdD0xMDAsdG9wPTAnKSJcPuWFs+S6juaIkOe7qeaPkOS6pOWQju+8jOS9v+eUqDM2MOa1j+iniOWZqOS4jeiDveato+W4uOaJk+WNsOi+k+WHuueahOino+WGs+aWueazlVw8L2FcPjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85pWZ5Yqh5aSEOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwyMDE2LTA1LTEwIDEwOjM0OjEyOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwyMDE5LTA1LTMwOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47Pj47dDw7bDxpPDA+O2k8MT47aTwyPjtpPDM+O2k8ND47aTw1Pjs+O2w8dDxwPHA8bDxUZXh0Oz47bDxcPGEgaHJlZj0nIycgb25jbGljaz0id2luZG93Lm9wZW4oJ2dnc20uYXNweD9mYnNqPTIwMTYtMDEtMTAgMTc6MjY6MTUmeXhxeD0yMDE5LTAxLTEwJnhoPSZuYnNwXDsnLCdneGxiJywnbWVudWJhcj0wLHNjcm9sbGJhcnM9MSxyZXNpemFibGU9MSx3aWR0aD05MDAsaGVpZ2h0PTcwMCxsZWZ0PTEwMCx0b3A9MCcpIlw+5YWz5LqO4oCc5riF56m65oC76K+E4oCd44CB4oCc5L+d5a2Y4oCd44CB4oCc5omT5Y2w4oCd44CB4oCc6L6T5Ye65oiQ57up5qC45a+55Y2V4oCd562J5pON5L2c5oyJ6ZKu5ZON5bqU5pe26Ze055qE6YCa55+lXDwvYVw+Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzmlZnliqHlpIQ7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMTYtMDEtMTAgMTc6MjY6MTU7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMTktMDEtMTA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjs+Pjt0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPFw8YSBocmVmPScjJyBvbmNsaWNrPSJ3aW5kb3cub3BlbignZ2dzbS5hc3B4P2Zic2o9MjAxNS0wMy0yNSAxNDoyNjo0MCZ5eHF4PTIwMTgtMDMtMjUmeGg9Jm5ic3BcOycsJ2d4bGInLCdtZW51YmFyPTAsc2Nyb2xsYmFycz0xLHJlc2l6YWJsZT0xLHdpZHRoPTkwMCxoZWlnaHQ9NzAwLGxlZnQ9MTAwLHRvcD0wJykiXD7vvIjkv67mlLnvvInmlZnluIjkuLTml7bosIPlgZzor77nlLPor7fmk43kvZzor7TmmI5cPC9hXD47Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOaVmeWKoeWkhDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MjAxNS0wMy0yNSAxNDoyNjo0MDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MjAxOC0wMy0yNTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+Oz4+O3Q8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8XDxhIGhyZWY9JyMnIG9uY2xpY2s9IndpbmRvdy5vcGVuKCdnZ3NtLmFzcHg/ZmJzaj0yMDE0LTA5LTI2IDE0OjU1OjU1Jnl4cXg9MjAyMC0wOS0zMCZ4aD0mbmJzcFw7JywnZ3hsYicsJ21lbnViYXI9MCxzY3JvbGxiYXJzPTEscmVzaXphYmxlPTEsd2lkdGg9OTAwLGhlaWdodD03MDAsbGVmdD0xMDAsdG9wPTAnKSJcPuWFs+S6juato+aWueaVmeWKoeeuoeeQhuezu+e7n+WuouaIt+err+S4i+i9veWcsOWdgOeahOivtOaYju+8iOaVmeWKoeWcqOe6vy3lhaznlKjkv6Hmga8t5LiK5Lyg5LiL6L295paH5Lu277yJXDwvYVw+Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzmlZnliqHlpIQ7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMTQtMDktMjYgMTQ6NTU6NTU7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMjAtMDktMzA7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjs+Pjs+Pjs+Pjs+Pjs+Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PDtsPGk8MT47PjtsPHQ8QDA8Ozs7Ozs7Ozs7Oz47Oz47Pj47Pj47Pj47PuLZQEukBJ/iIUKspm8bgLGRVpY0";
    public static final String HOME__BODY_NAME_VIEWSTATEGENERATOR = "__VIEWSTATEGENERATOR";
    public static final String HOME__BODY_VALUE_VIEWSTATEGENERATOR = "C209AA73";

    /**
     * 教务系统课表界面网址
     */
    public static final String SCHEDULE_SEARCH_URL = "http://210.37.0.27/xskbcx.aspx?xh="+ ConstantsUtil.STUDENTNUM+"&xm="+ ConstantsUtil.STUDENTNAME+"&gnmkdm=N121603";

    // 请求头
    public static final String SCHEDULE_HEADER_NAME_HOST = "Host";
    public static final String SCHEDULE_HEADER_VALUE_HOST = "210.37.0.27";
    public static final String SCHEDULE_HEADER_NAME_REFERER ="Referer";
    public static final String SCHEDULE_HEADER_VALUE_REFERER ="http://210.37.0.27/xs_main.aspx?xh="+ ConstantsUtil.STUDENTNUM;
    public static final String SCHEDULE_HEADER_NAME_AGENT ="User-Agent";
    public static final String SCHEDULE_HEADER_VALUE_AGENT ="User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";

    // 提交参数
    public static final String SCHEDULE_BODY_NAME_EVENTTARGET = "__EVENTTARGET";
    public static final String SCHEDULE_BODY_VALUE_EVENTTARGET = "xqd";
    public static final String SCHEDULE_BODY_NAME_EVENTARGUMENT = "__EVENTARGUMENT";
    public static final String SCHEDULE_BODY_VALUE_EVENTARGUMENT = "";
    public static final String SCHEDULE_BODY_NAME_VIEWSTATEGENERATOR = "__VIEWSTATEGENERATOR";
    public static final String SCHEDULE_BODY_VALUE_VIEWSTATEGENERATOR = "55530A43";
    public static final String SCHEDULE_BODY_NAME_XND = "xnd";
    public static final String SCHEDULE_BODY_NAME_XQD = "xqd";


    /**
     * 字体拼接
     */
    public static final int ALL = 0;
    public static final int TXT = 1;
    public static final int SIZE = 2;
    public static final int COLOR = 3;

    /**
     * Tag
     */
    public static final String TAG_SIZE = "size";
    public static final String TAG_ERROR = "error";
    public static final String TAG_CODE = "code";
}
