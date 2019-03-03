package com.demo.somnus.bomda.presenter;

import android.content.Context;
import android.util.Log;


import com.demo.somnus.bomda.model.bean.QQInfo;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.ILoginPresenter;
import com.demo.somnus.bomda.view.IView.ILoginActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Somnus on 2018/4/4.
 * 登录Presenter
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginActivity iLoginActivity;
    private static final String template = "register";

    public LoginPresenter(ILoginActivity iLoginActivity){
        this.iLoginActivity = iLoginActivity;
    }

    @Override
    public void loginPassword(Context context, String phone, String password) {
        BmobUser user = new BmobUser();
        user.setUsername(phone);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e == null){
                    iLoginActivity.loginPasswordSuccess();
                } else {
                    iLoginActivity.loginFail(e.getMessage(),e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void loginCode(Context context, String phone, String code) {
        BmobUser.signOrLoginByMobilePhone(phone, code, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if(user!=null){
                    Log.i("smile","用户登陆成功");
                    iLoginActivity.loginCodeSuccess();
                } else {
                    iLoginActivity.loginFail(e.getMessage(),e.getErrorCode());
                }
            }
        });

    }

    @Override
    public void sendSMSCode(Context context, String phone, final String type) {
        BmobSMS.requestSMSCode(phone,template, new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){//验证码发送成功
                    Log.i("smile", "短信id："+smsId);//用于查询本次短信发送详情
                    iLoginActivity.sendSMSSuccess(type);
                } else {
                    iLoginActivity.sendSMSSFail(ex.getMessage(),ex.getErrorCode());
                }
            }
        });
    }

    @Override
    public void validation(final Context context, final String phone, String code, final QQInfo qqInfo) {
        BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    register(context,phone,qqInfo);
                } else {
                    iLoginActivity.loginFail(e.getMessage(),e.getErrorCode());
                }
            }
        });
    }

    private void register(final Context context, String phone, final QQInfo qqInfo){
        User user = new User();
        user.setUsername(qqInfo.getId());
        user.setPassword(qqInfo.getId());
        user.setMobilePhoneNumber(phone);
        user.setMobilePhoneNumberVerified(true);
        user.setQqLogin(true);
        user.setQqBinding(false);
        user.setQqavatar(qqInfo.getAvatar());
        user.setQqId(qqInfo.getId());
        user.setNick(qqInfo.getName());
        user.setAddress("未填写");
        user.setIntroduction("未填写");
        user.setSex(qqInfo.getSex());
        user.setSignature("未填写");
        user.setBinding(false);
        user.setBirth("未填写");
        user.setStudentId("未填写");
        user.setStudentName("未填写");
        user.setStudentPassword("未填写");
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user1, BmobException e) {
                if(e==null){
                    loginPassword(context,user1.getUsername(),qqInfo.getId());
                }else{
                    iLoginActivity.loginFail(e.getMessage(),e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void query(QQInfo qqInfo) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("username",qqInfo.getId());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null){
                    if (list.size() == 0){
                        iLoginActivity.notLogin();
                    } else {
                        iLoginActivity.isLogin();
                    }
                } else {

                }
            }
        });
    }
}
