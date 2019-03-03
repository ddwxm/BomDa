package com.demo.somnus.bomda.presenter;

import android.util.Log;

import com.demo.somnus.bomda.callback.InfoCallBack;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IBindingPresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.util.JsoupUtil;
import com.demo.somnus.bomda.util.StringUtil;
import com.demo.somnus.bomda.view.IView.IBindingActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;

/**
 * Created by Somnus on 2018/4/5.
 * 绑定Presenter
 */

public class BindingPresenter implements IBindingPresenter {
    private IBindingActivity iBindingActivity;

    public BindingPresenter(IBindingActivity iBindingActivity){
        this.iBindingActivity = iBindingActivity;
    }

    @Override
    public void Binding(final String studentId, final String password, String code) {
        OkHttpUtils.post()
                .url(ConstantsUtil.EDUCATION_SYSTEM_LOGIN_URL)
                .addHeader("Origin","http://210.37.0.16")
                .addHeader("Host","210.37.0.16")
                .addHeader("Referer","http://210.37.0.16/default2.aspx")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                .addHeader("Cookie","ASP.NET_SessionId=5opk3c45cdnrcx55khwluh3j")
                .addHeader("Connection","keep-alive")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addParams("__VIEWSTATE", "dDwtNTE2MjI4MTQ7Oz74C6j9EX7enP4US+gDPbHNzQuH7w==")
                .addParams("__VIEWSTATEGENERATOR","92719903")
                .addParams("txtUserName", studentId)
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
                        iBindingActivity.bindingFail(e.getMessage(),302);
                    }

                    @Override
                    public void onResponse(String response) {
                        JsoupUtil.saveInfo(response);
                        update(studentId,password, JsoupUtil.getName(response));
                    }
                });
    }

    private void update(String studentId, String password,String studentName){
        User user = new User();
        user.setStudentId(studentId);
        user.setStudentName(studentName);
        user.setStudentPassword(StringUtil.encryption(password));
        user.setBinding(true);
        user.update(BmobUser.getCurrentUser().getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    User.fetchUserJsonInfo(new FetchUserInfoListener<String>() { //更新本地用户信息
                                               @Override
                                               public void done(String s, BmobException e) {
                                                   Gson gson = new Gson();
                                                   User user = gson.fromJson(s, User.class);
                                                   User user1 = BmobUser.getCurrentUser(User.class);
                                                   user1.setBinding(user.getBinding());
                                                   user1.setStudentId(user.getStudentId());
                                                   user1.setStudentName(user.getStudentName());
                                                   user1.setStudentPassword(user.getStudentPassword());
                                                   iBindingActivity.bindingSuccess();
                                               }
                                           });
                } else {
                    iBindingActivity.bindingFail(e.getMessage(),e.getErrorCode());
                }
            }
        });
    }
}
