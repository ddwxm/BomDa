package com.demo.somnus.bomda.util;

import android.util.Log;

import com.demo.somnus.bomda.model.bean.Grade;
import com.demo.somnus.bomda.model.bean.GradeParent;
import com.demo.somnus.bomda.model.bean.Schedule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Somnus on 2018/4/5.
 * 网页解析工具类
 */

public class JsoupUtil {

    public JsoupUtil(){
    }

    /**
     * 获取主页链接
     * @param html
     */
    public static String getMainHref(String html) {
        Document document = Jsoup.parse(html);
        Element element = document.select("a.top_link").first();
        String mainHref = element.attr("href");
        return mainHref;
    }

    /**
     * 获取成绩网页ViewState信息
     * @param html
     * @return
     */
    public static List<String> getViewStateForGrade(String html){
        List<String> view = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Element viewStateElement =
                document.select("input[name=\"__VIEWSTATE\"]").first();
        Element viewStateGeneratorElement =
                document.select("input[name=\"__VIEWSTATEGENERATOR\"]").first();
        Element eventTargetElement =
                document.select("input[name=\"__EVENTTARGET\"]").first();
        Element eventTargumentElement =
                document.select("input[name=\"__EVENTARGUMENT\"]").first();
        String viewState = viewStateElement.attr("value");
        String eventTarget = eventTargetElement.attr("value");
        String eventTargument = eventTargumentElement.attr("value");
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_GRADE_VIEWSTATE, viewState);
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_GRADE_EVENTTARGET, eventTarget);
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_GRADE_EVENTARGUMENT, eventTargument);
        view.add(viewState);
        view.add(eventTarget);
        view.add(eventTargument);
        return view;
    }

    /**
     * 获取课表网页ViewState信息
     * @param html
     * @return
     */
    public static List<String> getViewStateForSchedule(String html){
        List<String> view = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Element viewStateElement =
                document.select("input[name=\"__VIEWSTATE\"]").first();
        Element viewStateGeneratorElement =
                document.select("input[name=\"__VIEWSTATEGENERATOR\"]").first();
        Element eventTargetElement =
                document.select("input[name=\"__EVENTTARGET\"]").first();
        Element eventTargumentElement =
                document.select("input[name=\"__EVENTARGUMENT\"]").first();
        String viewState = viewStateElement.attr("value");
        String eventTarget = eventTargetElement.attr("value");
        String eventTargument = eventTargumentElement.attr("value");
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_SCHEDULE_VIEWSTATE, viewState);
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_SCHEDULE_EVENTTARGET, eventTarget);
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_SCHEDULE_EVENTARGUMENT, eventTargument);
        view.add(viewState);
        view.add(eventTarget);
        view.add(eventTargument);
        return view;
    }

    /**
     * 获取课表链接
     * @param html
     */
    public static String getScheduleHref(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div#headDiv ul li ul");
        Elements elements1 = elements.get(4).select("ul.sub li");
        String scheduleHref = elements1.get(2).select("a").attr("href");
        return scheduleHref;
    }

    /**
     * 获取考试链接
     * @param html
     */
    public static String getTestHref(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div#headDiv ul li ul");
        Elements elements1 = elements.get(4).select("ul.sub li");
        String testHref = elements1.get(3).select("a").attr("href");
        return testHref;
    }

    /**
     * 获取成绩链接
     * @param html
     */
    public static String getGradeHref(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div#headDiv ul li ul");
        Elements elements1 = elements.get(4).select("ul.sub li");
        String gradeHref = elements1.get(4).select("a").attr("href");
        Log.e("gradeHref",gradeHref);
        return gradeHref;
    }

    /**
     * 获取姓名
     * @param html
     */
    public static String getName(String html){
        Document document = Jsoup.parse(html);
        Element nameElement = document.getElementById("xhxm");
        String xhxm = nameElement.text();
        String name = xhxm.substring(0,xhxm.indexOf("同"));
        return name;
    }

    /**
     * 信息保存
     * @param html
     */
    public static void saveInfo(String html){
        Document document = Jsoup.parse(html);
        Element elementMain = document.select("a.top_link").first();
        String mainHref = elementMain.attr("href");
        Log.e("mainHref",mainHref);
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_MAIN, "http://210.37.0.16/"+mainHref);

        Elements elementsSchedule = document.select("div#headDiv ul li ul");
        Elements elementsSchedule1 = elementsSchedule.get(4).select("ul.sub li");
        String scheduleHref = elementsSchedule1.get(2).select("a").attr("href");
        Log.e("scheduleHref",scheduleHref);
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_SCHEDULE, "http://210.37.0.16/"+scheduleHref);

        Elements elementsTest = document.select("div#headDiv ul li ul");
        Elements elementsTest1 = elementsTest.get(4).select("ul.sub li");
        String testHref = elementsTest1.get(3).select("a").attr("href");
        Log.e("testHref",testHref);
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_TEST, "http://210.37.0.16/"+testHref);

        Elements elementsGrade = document.select("div#headDiv ul li ul");
        Elements elementsGrade1 = elementsGrade.get(4).select("ul.sub li");
        String gradeHref = elementsGrade1.get(4).select("a").attr("href");
        Log.e("gradeHref",gradeHref);
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_GRADE, "http://210.37.0.16/"+gradeHref);

        Element nameElement = document.getElementById("xhxm");
        String xhxm = nameElement.text();
        String name = xhxm.substring(0,xhxm.indexOf("同"));
        Log.e("name",name);
        SharedPreferencesUtils.putString(ConstantsUtil.FILE_NAME,
                ConstantsUtil.FILE_NAME_STUDENT_NAME, name);
    }

    /**
     * 从网页获取成绩信息
     * @param html
     * @return
     */
    public static List<Grade> grade(String html){
        List<Grade> grades = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table#Datagrid1 tbody tr");
        for (int i = 1;i<elements.size();i++){
            Grade grade = new Grade();
            Elements elements1 = elements.get(i).select("td");
            grade.setYear(elements1.get(0).text());
            grade.setSemester(elements1.get(1).text());
            grade.setCode(elements1.get(2).text());
            grade.setName(elements1.get(3).text());
            grade.setType(elements1.get(4).text());
            grade.setCredits(elements1.get(6).text());
            grade.setGpa(elements1.get(7).text());
            grade.setGrade(elements1.get(8).text());
            grade.setMinor(elements1.get(9).text());
            grade.setExamination(elements1.get(10).text());
            grade.setResetGrade(elements1.get(11).text());
            grade.setCollege(elements1.get(12).text());
            grade.setReset(elements1.get(13).text());
            grades.add(grade);
        }
        return grades;
    }

    public static List<GradeParent> parent(List<Grade> grades){
        List<GradeParent> parent = new ArrayList<>();
        for (int i = 0; i < grades.size(); i++) {
            GradeParent gradeParent = new GradeParent();
            gradeParent.setName(grades.get(i).getName());
            gradeParent.setGrade(grades.get(i).getGrade());
            parent.add(gradeParent);
            Log.e("name",grades.get(i).getName());
        }
        Log.e("parent",parent.size()+"");
        return parent;
    }

    public static List<String> score(List<Grade> grades){
        List<String> score = new ArrayList<>();
        for (int i = 0; i < grades.size(); i++) {
            score.add(grades.get(i).getGrade());
            Log.e("score",grades.get(i).getGrade());
        }
        Log.e("score",score.size()+"");
        return score;
    }

    public static Map<String, List<String>> child(List<Grade> grades){
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> list = null;
        for (int i = 0; i < grades.size(); i++) {
            list = new ArrayList<String>();

            list.add("学年学期：" + grades.get(i).getYear() + "第" + grades.get(i).getSemester() + "学期");
            list.add("课程性质：" + grades.get(i).getType());
            list.add("开课学院：" + grades.get(i).getCollege());
            list.add("学分：" + grades.get(i).getCredits());
            list.add("绩点：" + grades.get(i).getGpa());
            list.add("     总评成绩：" + grades.get(i).getGrade());

            if ("重修".equals(grades.get(i).getReset())) {
                list.add("\t\t\t\t\t\t备注： 重修");
            }

            if (grades.get(i).getExamination()!=null){
                list.add("     补考成绩：" + grades.get(i).getExamination());
            }

            map.put(grades.get(i).getName(), list);
            list = null;
        }
        return map;
    }

    public static void getSchedule(String year, String semester, String html){
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table#Table1 tbody tr");
        for(Element element:elements){
            Elements elements1 = element.select("td");
            for (Element element1:elements1){
                if (element1.attr("align").equals("Center")&&(element1.attr("rowspan").equals("2") || element1.attr("rowspan").equals("3"))){
                    Log.e("info",element1.text());
                }
            }
        }
    }

    private static String parsePersonalCourse(String text){
        //正则表达式获取课名，和教室
        Pattern courseNamePattern = Pattern.compile("^.+?(\\s{1})");
        Matcher courseNameMatcher = courseNamePattern.matcher(text);
        courseNameMatcher.find();
        String str = courseNameMatcher.group(0);

        //Pattern courseLocPattern = Pattern.compile("\\s{1}(\\d+)");
        //Matcher courseLocMatcher = courseLocPattern.matcher(text);
        //courseLocMatcher.find();
        //String data = courseLocMatcher.group(0);

        return str+"@";
    }
}
