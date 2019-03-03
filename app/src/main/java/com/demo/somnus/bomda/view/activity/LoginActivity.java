package com.demo.somnus.bomda.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.QQInfo;
import com.demo.somnus.bomda.presenter.IPresenter.ILoginPresenter;
import com.demo.somnus.bomda.presenter.LoginPresenter;
import com.demo.somnus.bomda.util.ProgressDialogUtil;
import com.demo.somnus.bomda.view.IView.ILoginActivity;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.b.V;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Somnus on 2018/4/4.
 * 登录Activity
 */

public class LoginActivity extends AppCompatActivity implements ILoginActivity
        ,View.OnClickListener {
    private ImageView login_close,login_back,login_qq_binding_back;
    private Button login_login,login_login_btn,login_for_code_btn,login_qq_binding_btn;
    private TextView login_register,login_password_forget_logo,login_type,login_for_code_two_code_phone;
    private EditText login_phoneNumber,login_password_input,login_for_code_phoneNumber,login_qq_binding_phoneNumber;
    private ILoginPresenter iLoginPresenter;
    private RelativeLayout login_first,login_second,login_for_code,login_for_password,login_for_code_two,login_for_code_one;
    public enum ResultCode { LOGIN_SUCCESS, NOT_LOGIN }
    private PinEntryEditText login_for_code_two_input_code,login_qq_binding_two_input_code;
    private TextView login_for_code_two_code_timer,login_qq_binding_two_code_phone,login_qq_binding_two_code_timer;
    private String type = "code";
    private int show = 1;
    private int showQQ = 1;
    private String phone_code,phone_qq;
    private RelativeLayout login_other_qq;
    private ProgressDialogUtil progressDialog;
    private RelativeLayout login_qq_binding,login_qq_binding_first,login_qq_binding_two;
    private QQInfo qqInfo;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 1:
                    qqInfo = (QQInfo) msg.obj;
                    iLoginPresenter.query(qqInfo);
                    break;
                case 2:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).transparentBar().init();
        iLoginPresenter = new LoginPresenter(this);
        setContentView(R.layout.activity_login);
        init();
        initListener();
    }

    /**
     * 初始化控件
     */
    private void init(){
        progressDialog = new ProgressDialogUtil(this);
        login_type = (TextView) findViewById(R.id.login_type);
        login_close = (ImageView) findViewById(R.id.login_close);
        login_login = (Button) findViewById(R.id.login_login);
        login_register = (TextView) findViewById(R.id.login_register);
        login_register.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        login_back = (ImageView) findViewById(R.id.login_back);
        login_password_forget_logo = (TextView) findViewById(R.id.login_password_forget_logo);
        login_phoneNumber = (EditText) findViewById(R.id.login_phoneNumber);
        login_phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                login_login_btn.setClickable(false);
                login_login_btn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (login_phoneNumber.getText().toString().equals("")|| login_password_input.getText().toString().equals("")){
                    login_login_btn.setClickable(false);
                    login_login_btn.setEnabled(false);
                } else {
                    login_login_btn.setClickable(true);
                    login_login_btn.setEnabled(true);
                    login_login_btn.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (login_phoneNumber.getText().toString().equals("")|| login_password_input.getText().toString().equals("")){
                    login_login_btn.setClickable(false);
                    login_login_btn.setEnabled(false);
                } else {
                    login_login_btn.setClickable(true);
                    login_login_btn.setEnabled(true);
                    login_login_btn.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }
        });
        login_password_input = (EditText) findViewById(R.id.login_password_input);
        login_password_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (login_phoneNumber.getText().toString().equals("")|| login_password_input.getText().toString().equals("")){
                    login_login_btn.setClickable(false);
                    login_login_btn.setEnabled(false);
                } else {
                    login_login_btn.setClickable(true);
                    login_login_btn.setEnabled(true);
                    login_login_btn.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (login_phoneNumber.getText().toString().equals("")|| login_password_input.getText().toString().equals("")){
                    login_login_btn.setClickable(false);
                    login_login_btn.setEnabled(false);
                } else {
                    login_login_btn.setClickable(true);
                    login_login_btn.setEnabled(true);
                    login_login_btn.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (login_phoneNumber.getText().toString().equals("")|| login_password_input.getText().toString().equals("")){
                    login_login_btn.setClickable(false);
                    login_login_btn.setEnabled(false);
                } else {
                    login_login_btn.setClickable(true);
                    login_login_btn.setEnabled(true);
                    login_login_btn.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }
        });
        login_login_btn = (Button) findViewById(R.id.login_login_btn);
        login_first = (RelativeLayout) findViewById(R.id.login_first);
        login_first.setVisibility(View.VISIBLE);
        login_second = (RelativeLayout) findViewById(R.id.login_second);
        login_second.setVisibility(View.GONE);

        login_other_qq = (RelativeLayout) findViewById(R.id.login_other_qq);

        login_for_code_two_code_timer = (TextView) findViewById(R.id.login_for_code_two_code_timer);
        login_for_code_two_code_phone = (TextView) findViewById(R.id.login_for_code_two_code_phone);
        login_for_code_two_input_code = (PinEntryEditText) findViewById(R.id.login_for_code_two_input_code);
        login_for_code = (RelativeLayout) findViewById(R.id.login_for_code);
        login_for_code_two = (RelativeLayout) findViewById(R.id.login_for_code_two);
        login_for_code_one = (RelativeLayout) findViewById(R.id.login_for_code_one);
        login_for_code_one.setVisibility(View.VISIBLE);
        login_for_code_two.setVisibility(View.GONE);
        login_for_password = (RelativeLayout) findViewById(R.id.login_for_password);
        login_for_password.setVisibility(View.VISIBLE);
        login_for_code.setVisibility(View.GONE);
        login_for_code_btn = (Button) findViewById(R.id.login_for_code_btn);
        login_for_code_phoneNumber = (EditText) findViewById(R.id.login_for_code_phoneNumber);
        login_for_code_phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (login_for_code_phoneNumber.getText().toString().length() == 11) {
                    login_for_code_btn.setClickable(true);
                    login_for_code_btn.setEnabled(true);
                    login_for_code_btn.setBackground(getResources().getDrawable(R.drawable.register_btn_bg));
                } else {
                    login_for_code_btn.setClickable(false);
                    login_for_code_btn.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (login_for_code_phoneNumber.getText().toString().length() == 11) {
                    login_for_code_btn.setClickable(true);
                    login_for_code_btn.setEnabled(true);
                    login_for_code_btn.setBackground(getResources().getDrawable(R.drawable.register_btn_bg));
                } else {
                    login_for_code_btn.setClickable(false);
                    login_for_code_btn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (login_for_code_phoneNumber.getText().toString().length() == 11) {
                    login_for_code_btn.setClickable(true);
                    login_for_code_btn.setEnabled(true);
                    login_for_code_btn.setBackground(getResources().getDrawable(R.drawable.register_btn_bg));
                } else {
                    login_for_code_btn.setClickable(false);
                    login_for_code_btn.setEnabled(false);
                }
            }
        });

        login_qq_binding_back = (ImageView) findViewById(R.id.login_qq_binding_back);
        login_qq_binding_btn = (Button) findViewById(R.id.login_qq_binding_btn);
        login_qq_binding_phoneNumber = (EditText) findViewById(R.id.login_qq_binding_phoneNumber);
        login_qq_binding_two_input_code = (PinEntryEditText) findViewById(R.id.login_qq_binding_two_input_code);
        login_qq_binding_two_code_phone = (TextView) findViewById(R.id.login_qq_binding_two_code_phone);
        login_qq_binding_two_code_timer = (TextView) findViewById(R.id.login_qq_binding_two_code_timer);
        login_qq_binding = (RelativeLayout) findViewById(R.id.login_qq_binding);
        login_qq_binding_first = (RelativeLayout) findViewById(R.id.login_qq_binding_first);
        login_qq_binding_two = (RelativeLayout) findViewById(R.id.login_qq_binding_two);
        login_qq_binding_phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (login_qq_binding_phoneNumber.getText().toString().length() == 11) {
                    login_qq_binding_btn.setClickable(true);
                    login_qq_binding_btn.setEnabled(true);
                    login_qq_binding_btn.setBackground(getResources().getDrawable(R.drawable.register_btn_bg));
                } else {
                    login_qq_binding_btn.setClickable(false);
                    login_qq_binding_btn.setEnabled(false);
                    login_qq_binding_btn.setTextColor(getResources().getColor(R.color.grey));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (login_qq_binding_phoneNumber.getText().toString().length() == 11) {
                    login_qq_binding_btn.setClickable(true);
                    login_qq_binding_btn.setEnabled(true);
                    login_qq_binding_btn.setBackground(getResources().getDrawable(R.drawable.register_btn_bg));
                } else {
                    login_qq_binding_btn.setClickable(false);
                    login_qq_binding_btn.setEnabled(false);
                    login_qq_binding_btn.setTextColor(getResources().getColor(R.color.grey));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (login_qq_binding_phoneNumber.getText().toString().length() == 11) {
                    login_qq_binding_btn.setClickable(true);
                    login_qq_binding_btn.setEnabled(true);
                    login_qq_binding_btn.setBackground(getResources().getDrawable(R.drawable.register_btn_bg));
                    phone_qq = login_qq_binding_phoneNumber.getText().toString();
                } else {
                    login_qq_binding_btn.setClickable(false);
                    login_qq_binding_btn.setEnabled(false);
                    login_qq_binding_btn.setTextColor(getResources().getColor(R.color.grey));
                }
            }
        });
    }

    /**
     * 初始化控件活动
     */
    private void initListener(){
        login_close.setOnClickListener(this);
        login_login.setOnClickListener(this);
        login_register.setOnClickListener(this);
        login_back.setOnClickListener(this);
        login_password_forget_logo.setOnClickListener(this);
        login_login_btn.setOnClickListener(this);
        login_type.setOnClickListener(this);
        login_for_code_btn.setOnClickListener(this);
        login_for_code_two_input_code.setOnPinEnteredListener(listener);
        login_other_qq.setOnClickListener(this);
        login_qq_binding_back.setOnClickListener(this);
        login_qq_binding_two_input_code.setOnPinEnteredListener(validation);
        login_qq_binding_btn.setOnClickListener(this);
        login_for_code_two_code_timer.setOnClickListener(this);
        login_qq_binding_two_code_timer.setOnClickListener(this);
    }

    /**
     * qq登录
     * @param qqInfo
     */
    private void QQLogin(QQInfo qqInfo){
        login_first.setVisibility(View.GONE);
        login_qq_binding.setVisibility(View.VISIBLE);
        login_qq_binding_first.setVisibility(View.VISIBLE);
        login_qq_binding_two.setVisibility(View.GONE);
    }


    /**
     * 验证码输入监听
     */
    PinEntryEditText.OnPinEnteredListener listener = new PinEntryEditText.OnPinEnteredListener() {
        @Override
        public void onPinEntered(CharSequence str) {
            iLoginPresenter.loginCode(LoginActivity.this,phone_code,str.toString());
            progressDialog.setIngState("正在登录...");
            progressDialog.show();
        }
    };

    /**
     * qq绑定验证码输入监听
     */
    PinEntryEditText.OnPinEnteredListener validation = new PinEntryEditText.OnPinEnteredListener() {
        @Override
        public void onPinEntered(CharSequence str) {
            iLoginPresenter.validation(LoginActivity.this,phone_qq,str.toString(),qqInfo);
            progressDialog.setIngState("正在登录...");
            progressDialog.show();
        }
    };

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_close:
                finish();
                break;
            case R.id.login_login:
                login_second.setVisibility(View.VISIBLE);
                login_first.setVisibility(View.GONE);
                break;
            case R.id.login_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_login_btn:
                String phone = login_phoneNumber.getText().toString();
                String password = login_password_input.getText().toString();
                if (phone.length() != 0 && password.length() != 0){
                    iLoginPresenter.loginPassword(LoginActivity.this,phone,password);
                    progressDialog.setIngState("正在登录...");
                    progressDialog.show();
                } else {
                }
                break;
            case R.id.login_back:
                login_second.setVisibility(View.GONE);
                login_first.setVisibility(View.VISIBLE);
                break;
            case R.id.login_type:
                switch (type){
                    case "code":
                        login_for_password.setVisibility(View.GONE);
                        login_for_code.setVisibility(View.VISIBLE);
                        login_type.setText(R.string.login_password);
                        type = "password";
                        break;
                    case "password":
                        login_for_password.setVisibility(View.VISIBLE);
                        login_for_code.setVisibility(View.GONE);
                        login_type.setText(R.string.login_code);
                        type = "code";
                        break;
                }
                break;
            case R.id.login_for_code_btn:
                phone_code = login_for_code_phoneNumber.getText().toString();
                iLoginPresenter.sendSMSCode(LoginActivity.this,phone_code,"login");
                break;
            case R.id.login_other_qq:
                final QQInfo qqInfo = new QQInfo();
                final Platform qq = ShareSDK.getPlatform(QQ.NAME);
                //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                qq.setPlatformActionListener(new PlatformActionListener() {

                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
                        arg2.printStackTrace();
                        qq.removeAccount(true);
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        arg0.getDb().exportData();
                        if (arg1 == Platform.ACTION_USER_INFOR) {
                            PlatformDb platDB = arg0.getDb();//获取数平台数据DB
                            //通过DB获取各种数据
                            platDB.getToken();
                            platDB.getUserGender();
                            platDB.getUserIcon();
                            platDB.getUserId();
                            platDB.getUserName();
                            Log.e("QQSex",platDB.getUserGender());
                            Log.e("QQIcon",platDB.getUserIcon());
                            Log.e("QQId",platDB.getUserId());
                            Log.e("QQName",platDB.getUserName());
                            Log.e("HashMap",arg2.toString());
                            Log.e("HashMap", (String) arg2.get("city"));
                            qqInfo.setName(platDB.getUserName());
                            qqInfo.setAvatar(platDB.getUserIcon());
                            qqInfo.setId(platDB.getUserId());
                            qqInfo.setCity(String.valueOf(arg2.get("city")));
                            qqInfo.setSex((String) arg2.get("gender"));
                            Message msg =Message.obtain();
                            msg.obj = qqInfo;
                            msg.what=1;   //标志消息的标志
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub

                    }
                });
                //authorize与showUser单独调用一个即可
                //qq.authorize();//单独授权,OnComplete返回的hashmap是空的
                qq.showUser(null);//授权并获取用户信息
                // 移除授权
                // weibo.removeAccount(true);
                break;
            case R.id.login_qq_binding_back:
                login_first.setVisibility(View.VISIBLE);
                login_qq_binding.setVisibility(View.GONE);
                final Platform platform = ShareSDK.getPlatform(QQ.NAME);
                platform.removeAccount(true);
                break;
            case R.id.login_qq_binding_btn:
                iLoginPresenter.sendSMSCode(LoginActivity.this,phone_qq,"validation");
                break;
            case R.id.login_for_code_two_code_timer:
                iLoginPresenter.sendSMSCode(LoginActivity.this,phone_code,"login");
                break;
            case R.id.login_qq_binding_two_code_timer:
                iLoginPresenter.sendSMSCode(LoginActivity.this,phone_qq,"validation");
                break;
        }
    }

    /**
     * 密码登录成功回调
     */
    @Override
    public void loginPasswordSuccess() {

        progressDialog.setSuccessState("登录成功");
        progressDialog.dismissDelay(new ProgressDialogUtil.OnDelayDismissCallback() {
            @Override
            public void onDismiss() {
                setResult(ResultCode.LOGIN_SUCCESS.ordinal());
                finish();
            }
        });
    }

    /**
     * 登录失败回调
     * @param s 失败原因
     * @param code 错误码
     */
    @Override
    public void loginFail(String s, int code) {
        progressDialog.setFailureState("登录失败", code, s);
        progressDialog.setActionBtn("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.dismiss();
            }
        });
    }

    /**
     * 时间触发器
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            login_for_code_two_code_timer.setText((millisUntilFinished / 1000) + "秒后可重发");
            login_for_code_two_code_timer.setEnabled(false);
        }

        @Override
        public void onFinish() {
            login_for_code_two_code_timer.setEnabled(true);
            login_for_code_two_code_timer.setText("获取验证码");
        }
    };

    /**
     * 时间触发器
     */
    private CountDownTimer timerQQ = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            login_qq_binding_two_code_timer.setText((millisUntilFinished / 1000) + "秒后可重发");
            login_qq_binding_two_code_timer.setEnabled(false);
        }

        @Override
        public void onFinish() {
            login_qq_binding_two_code_timer.setEnabled(true);
            login_qq_binding_two_code_timer.setText("获取验证码");
        }
    };

    /**
     * 短信验证码发送成功回调
     */
    @Override
    public void sendSMSSuccess(String type) {;
        if (type.equals("login")){
            if (show != 2){
                login_for_code_one.setVisibility(View.GONE);
                login_for_code_two.setVisibility(View.VISIBLE);
                login_for_code_two_code_phone.setText(phone_code);
                timer.start();
                show = 2;
            } else {
                timer.start();
            }
        } else {
            if (showQQ != 2){
                login_qq_binding_first.setVisibility(View.GONE);
                login_qq_binding_two.setVisibility(View.VISIBLE);
                login_qq_binding_two_code_phone.setText(phone_qq);
                timerQQ.start();
                showQQ = 2;
            } else {
                timerQQ.start();
            }
        }
    }

    /**
     * 验证码登录成功回调
     */
    @Override
    public void loginCodeSuccess() {
        progressDialog.setSuccessState("登录成功");
        progressDialog.dismissDelay(new ProgressDialogUtil.OnDelayDismissCallback() {
            @Override
            public void onDismiss() {
                timer.cancel();
                setResult(ResultCode.LOGIN_SUCCESS.ordinal());
                finish();
            }
        });
    }

    /**
     * 短信验证码发送失败回调
     * @param s 失败原因
     * @param i 错误码
     */
    @Override
    public void sendSMSSFail(String s, int i) {
        Toast.makeText(this,s+i,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notLogin() {
        QQLogin(qqInfo);
    }

    @Override
    public void isLogin() {
        iLoginPresenter.loginPassword(LoginActivity.this,qqInfo.getId(),qqInfo.getId());
        progressDialog.setIngState("正在登录...");
        progressDialog.show();
    }
}
