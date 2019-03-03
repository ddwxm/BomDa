package com.demo.somnus.bomda.presenter;

import android.util.Log;


import com.demo.somnus.bomda.callback.InfoCallBack;
import com.demo.somnus.bomda.model.bean.Course;
import com.demo.somnus.bomda.model.bean.Schedule;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.ISchedulePresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.util.JsoupUtil;
import com.demo.somnus.bomda.util.SharedPreferencesUtils;
import com.demo.somnus.bomda.view.IView.IScheduleActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;

/**
 * Created by Somnus on 2018/4/5.
 * 课表Presenter
 */

public class SchedulePresenter implements ISchedulePresenter {

    private IScheduleActivity iScheduleActivity;
    private String username,password;
    private List<Course> courses = new ArrayList<>();

    public SchedulePresenter(IScheduleActivity iScheduleActivity){
        this.iScheduleActivity = iScheduleActivity;
        this.username = username;
        this.password = password;
    }

    @Override
    public void getSchedule(User user,String xnd,String xqd) {
        BmobQuery<Schedule> query = new BmobQuery<>();
        query.addWhereEqualTo("year",xnd);
        query.addWhereEqualTo("semester",xqd);
        query.addWhereEqualTo("student",user.getObjectId());
        query.findObjects(new FindListener<Schedule>() {
            @Override
            public void done(List<Schedule> list, BmobException e) {
                if (e == null){
                    if (list.size() != 0){
                        for (Schedule schedule:list){
                            Course course = new Course();
                            course.setAddress(schedule.getAddress());
                            course.setName(schedule.getName());
                            course.setTeacher(schedule.getTeacher());
                            course.setDay(schedule.getTime().substring(0,2));
                            course.setWeek(schedule.getTime().substring(9,12));
                            course.setTime(schedule.getTime().substring(3,schedule.getTime().indexOf("节")));
                            courses.add(course);
                        }
                        iScheduleActivity.loadScheduleList(courses);
                        Log.e("个数",list.size()+"");
                    } else {
                        iScheduleActivity.noClass();
                    }
                } else {
                    Log.e("error",e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void querySchedule(final String url, final String mainHref, final String xnd, final String xqd) {
        if (xnd.equals("")){
            OkHttpUtils.get()
                    .url(url)
                    .addHeader("Origin", "http://210.37.0.16")
                    .addHeader("Host", "210.37.0.16")
                    .addHeader("Referer", mainHref)
                    //.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                    .addHeader("Cookie", ConstantsUtil.HEADER_VALUE_COOKIE)
                    .build().execute(new InfoCallBack() {
                @Override
                public void onError(Call call, Exception e) {
                    Log.e("Exception", e.getMessage());
                    iScheduleActivity.queryFail(e.getMessage(), 302);
                }

                @Override
                public void onResponse(String response) {

                }
            });
        } else {
            List<String> view = new ArrayList<>();
            String viewState = SharedPreferencesUtils.getString(ConstantsUtil.FILE_NAME, ConstantsUtil.FILE_NAME_STUDENT_SCHEDULE_VIEWSTATE, null);
            String eventTarget = SharedPreferencesUtils.getString(ConstantsUtil.FILE_NAME, ConstantsUtil.FILE_NAME_STUDENT_SCHEDULE_EVENTTARGET, null);
            String eventTargument = SharedPreferencesUtils.getString(ConstantsUtil.FILE_NAME, ConstantsUtil.FILE_NAME_STUDENT_SCHEDULE_EVENTARGUMENT, null);
            if (viewState != null) {
                view.add(viewState);
                view.add(eventTarget);
                view.add(eventTargument);
                postSchedule(url, mainHref, view, xnd, xqd);
            } else {
                OkHttpUtils.get()
                        .url(url)
                        .addHeader("Origin", "http://210.37.0.16")
                        .addHeader("Host", "210.37.0.16")
                        .addHeader("Referer", mainHref)
                        //.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                        .addHeader("Cookie", ConstantsUtil.HEADER_VALUE_COOKIE)
                        .build().execute(new InfoCallBack() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("Exception", e.getMessage());
                        iScheduleActivity.queryFail(e.getMessage(), 302);
                    }

                    @Override
                    public void onResponse(String response) {
                        postSchedule(url, mainHref, JsoupUtil.getViewStateForSchedule(response), xnd, xqd);
                    }
                });
            }
        }
    }

    private void postSchedule(String url, String mainHref, List<String> view, final String year, final String semester) {
        Log.e("url",url);
        Log.e("viewState",view.get(0));
        OkHttpUtils.post()
                .url(url)
                .addHeader("Origin", "http://210.37.0.16")
                .addHeader("Host", "210.37.0.16")
                .addHeader("Referer", mainHref)
                //.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                .addHeader("Cookie", "ASP.NET_SessionId=5opk3c45cdnrcx55khwluh3j")
                //.addHeader("Connection","keep-alive")
                //.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addParams("__VIEWSTATE", view.get(0))
                .addParams("__EVENTTARGET", "xnd")
                .addParams("xnd", year)
                .addParams("xqd", semester)
                .build().execute(new InfoCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("sb",e.getMessage());
                iScheduleActivity.queryFail(e.getMessage(),302);
            }

            @Override
            public void onResponse(String response) {
                Log.e("cg","cg");
                JsoupUtil.getSchedule(year,semester,response);
                Log.e("response",response);
            }
        });
    }

}
