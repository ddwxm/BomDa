package com.demo.somnus.bomda.presenter;

import android.content.Context;
import android.util.Log;

import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IRegisterPresenter;
import com.demo.somnus.bomda.util.RandomUtil;
import com.demo.somnus.bomda.view.IView.IRegisterActivity;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Somnus on 2018/4/4.
 * 注册Presenter
 */

public class RegisterPresenter implements IRegisterPresenter {
    private static final String template = "register";

    private IRegisterActivity iRegisterActivity;

    public RegisterPresenter(IRegisterActivity iRegisterActivity){
        this.iRegisterActivity = iRegisterActivity;
    }

    @Override
    public void sendSMS(Context context, String phone) {
        BmobSMS.requestSMSCode(phone,template, new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){//验证码发送成功
                    Log.i("smile", "短信id："+smsId);//用于后续的查询本次短信发送状态
                    iRegisterActivity.sendSMSSuccess();
                } else {
                    iRegisterActivity.sendSMSSFail(ex.getMessage(),ex.getErrorCode());
                }
            }
        });
    }


    @Override
    public void register(String username, String password,String code) {
        User user = new User();
        user.setUsername(username);                  //设置用户名，如果没有传用户名，则默认为手机号码
        user.setPassword(password);                  //设置用户密码
        user.setMobilePhoneNumber(username);          //设置额外信息：此处为手机号码
        user.setMobilePhoneNumberVerified(true);
        user.setNick(RandomUtil.Random());
        user.setAddress("未填写");
        user.setIntroduction("未填写");
        user.setSex("未填写");
        user.setSignature("未填写");
        user.setBinding(false);
        user.setQqBinding(false);
        user.setQqavatar("未填写");
        user.setQqId("未填写");
        user.setBirth("未填写");
        user.setStudentId("未填写");
        user.setStudentName("未填写");
        user.setStudentPassword("未填写");
        user.signOrLogin(code, new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    iRegisterActivity.registerSuccess();
                    Log.i("smile", ""+user.getUsername()+"-"+user.getObjectId());
                }else{
                    iRegisterActivity.registerFail(e.getMessage(),e.getErrorCode());
                    Log.e("error",e.getMessage()+e.getErrorCode());
                }
            }
        });
    }
}
