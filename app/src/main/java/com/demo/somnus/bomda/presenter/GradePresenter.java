package com.demo.somnus.bomda.presenter;


import android.content.Context;
import android.util.Log;

import com.demo.somnus.bomda.callback.InfoCallBack;
import com.demo.somnus.bomda.model.bean.Grade;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IGradePresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.util.JsoupUtil;
import com.demo.somnus.bomda.util.SharedPreferencesUtils;
import com.demo.somnus.bomda.util.ToastUtil;
import com.demo.somnus.bomda.view.IView.IGradeActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;

/**
 * Created by Somnus on 2018/4/5.
 * 成绩Presenter
 */

public class GradePresenter implements IGradePresenter {

    private IGradeActivity iGradeActivity;

    public GradePresenter(IGradeActivity iGradeActivity){
        this.iGradeActivity = iGradeActivity;
    }



    @Override
    public void tryGet(final String url, final String mainHref, final String type, final String year, final String semester) {
        List<String> view = new ArrayList<>();
        String viewState = SharedPreferencesUtils.getString(ConstantsUtil.FILE_NAME,ConstantsUtil.FILE_NAME_STUDENT_GRADE_VIEWSTATE,null);
        String eventTarget = SharedPreferencesUtils.getString(ConstantsUtil.FILE_NAME,ConstantsUtil.FILE_NAME_STUDENT_GRADE_EVENTTARGET,null);
        String eventTargument = SharedPreferencesUtils.getString(ConstantsUtil.FILE_NAME,ConstantsUtil.FILE_NAME_STUDENT_GRADE_EVENTARGUMENT,null);
        if (viewState != null){
            view.add(viewState);
            view.add(eventTarget);
            view.add(eventTargument);
            postGrade(url,mainHref,view,type,year,semester);
        } else {
            OkHttpUtils.get()
                    .url(url)
                    .addHeader("Origin","http://210.37.0.16")
                    .addHeader("Host","210.37.0.16")
                    .addHeader("Referer",mainHref)
                    //.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                    .addHeader("Cookie",ConstantsUtil.HEADER_VALUE_COOKIE)
                    .build().execute(new InfoCallBack() {
                @Override
                public void onError(Call call, Exception e) {
                    Log.e("Exception",e.getMessage());
                    iGradeActivity.tryFail(e.getMessage(),302);
                }

                @Override
                public void onResponse(String response) {
                    postGrade(url,mainHref,JsoupUtil.getViewStateForGrade(response),type,year,semester);
                }
            });
        }
    }

    private void postGrade(String url,String mainHref,List<String> view,String type, String year, String semester){
        OkHttpUtils.post()
                .url(url)
                .addHeader("Origin","http://210.37.0.16")
                .addHeader("Host","210.37.0.16")
                .addHeader("Referer",mainHref)
                //.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                .addHeader("Cookie","ASP.NET_SessionId=5opk3c45cdnrcx55khwluh3j")
                .addParams("__VIEWSTATE",view.get(0))
                .addParams("__EVENTTARGET",view.get(1))
                .addParams("__EVENTARGUMENT",view.get(2))
                .addParams("ddlXN","")
                .addParams("ddlXQ","")
                .addParams("ddl_kcxz","")
                .addParams("hidLanguage","")
                .addParams("btn_zcj",type)
                .build().execute(new InfoCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("Exception",e.getMessage());
                iGradeActivity.tryFail(e.getMessage(),302);
            }

            @Override
            public void onResponse(String response) {
                List<Grade> grades = new ArrayList<>(JsoupUtil.grade(response));
                iGradeActivity.loadGradeList(grades);
            }
        });
    }

    @Override
    public void getGrade(String url,String studentNum,String password, String code, String type, String year, String semester) {
        OkHttpUtils.post()
                .url(url)
                .addHeader("Origin","http://210.37.0.16")
                .addHeader("Host","210.37.0.16")
                .addHeader("Referer","http://210.37.0.16/default2.aspx")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                .addHeader("Cookie","ASP.NET_SessionId=5opk3c45cdnrcx55khwluh3j")
                .addHeader("Connection","keep-alive")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addParams("__VIEWSTATE", "dDwtNTE2MjI4MTQ7Oz74C6j9EX7enP4US+gDPbHNzQuH7w==")
                .addParams("__VIEWSTATEGENERATOR","92719903")
                .addParams("txtUserName", studentNum)
                .addParams("Textbox1", password)
                .addParams("TextBox2", password)
                .addParams("txtSecretCode", code)
                .addParams("RadioButtonList1","%D1%A7%C9%FA")
                .addParams("Button1", "")
                .addParams("lbLanguage","")
                .addParams("hidPdrs", "")
                .addParams("hidsc", "")
                .build()
                .execute(new InfoCallBack() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("Exception",e.getMessage());
                        iGradeActivity.getFail(e.getMessage(),302);
                    }

                    @Override
                    public void onResponse(String response) {
                        JsoupUtil.saveInfo(response);
                        tryGet(JsoupUtil.getGradeHref(response),JsoupUtil.getMainHref(response),"历年成绩","","");
                    }
                });
    }

    @Override
    public void tryDatabase(User user, String type, String year, String semester, final Context context) {
        BmobQuery<Grade> gradeBmobQuery = new BmobQuery<>();
        switch (type){
            case "历年成绩":
                gradeBmobQuery.addWhereEqualTo("user",user.getObjectId());
                gradeBmobQuery.findObjects(new FindListener<Grade>() {
                    @Override
                    public void done(List<Grade> list, BmobException e) {
                        if (e == null){
                            if (list.size() ==0){
                                iGradeActivity.noCJ();
                            } else {
                                iGradeActivity.loadGradeList(list);
                            }
                            Log.e("size",list.size()+"");
                        } else {
                            iGradeActivity.queryFail(e.getMessage(),e.getErrorCode());
                        }
                    }
                });
                break;
            case "学年成绩":
                gradeBmobQuery.addWhereEqualTo("user",user.getObjectId());
                gradeBmobQuery.addWhereEqualTo("year",year);
                gradeBmobQuery.addWhereEqualTo("semester",semester);
                gradeBmobQuery.findObjects(new FindListener<Grade>() {
                    @Override
                    public void done(List<Grade> list, BmobException e) {
                        if (e == null){
                            if (list.size() == 0){
                                iGradeActivity.noCJ();
                            } else {
                                iGradeActivity.loadGradeList(list);
                            }
                            Log.e("size",list.size()+"");
                        } else {
                            iGradeActivity.queryFail(e.getMessage(),e.getErrorCode());
                        }
                    }
                });
                break;
        }
    }
}
