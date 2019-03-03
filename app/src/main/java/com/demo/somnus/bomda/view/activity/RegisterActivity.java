package com.demo.somnus.bomda.view.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.presenter.IPresenter.IRegisterPresenter;
import com.demo.somnus.bomda.presenter.RegisterPresenter;
import com.demo.somnus.bomda.util.ProgressDialogUtil;
import com.demo.somnus.bomda.view.IView.IRegisterActivity;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by Somnus on 2018/4/4.
 * 注册Activity
 */

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity
        ,View.OnClickListener{
    private IRegisterPresenter iRegisterPresenter;
    private ImageView register_close,register_back_one,register_back_two;
    private Button register_register,register_register_btn;
    private TextView register_agreement,register_privacy,code_phone,code_timer;
    private EditText register_phoneNumber;
    private PinEntryEditText input_code;
    private RelativeLayout register_first,register_second,register_third;
    private String phone;
    private ProgressDialogUtil progressDialog;
    private int show = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).transparentBar().init();
        iRegisterPresenter = new RegisterPresenter(this);
        setContentView(R.layout.activity_register);
        init();
        initListener();
    }

    /**
     * 控件初始化
     */
    private void init(){
        progressDialog = new ProgressDialogUtil(this);
        register_close = (ImageView) findViewById(R.id.register_close);
        register_back_one = (ImageView) findViewById(R.id.register_back_one);
        register_back_two = (ImageView) findViewById(R.id.register_back_two);

        register_register = (Button) findViewById(R.id.register_register);
        register_register_btn = (Button) findViewById(R.id.register_register_btn);

        register_agreement = (TextView) findViewById(R.id.register_agreement);
        register_agreement.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        register_privacy = (TextView) findViewById(R.id.register_privacy);
        register_privacy.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        code_phone = (TextView) findViewById(R.id.code_phone);
        code_timer = (TextView) findViewById(R.id.code_timer);
        register_phoneNumber = (EditText) findViewById(R.id.register_phoneNumber);
        register_phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                register_register_btn.setClickable(false);
                register_register_btn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                register_register_btn.setClickable(false);
                register_register_btn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (register_phoneNumber.getText().toString().length() == 11) {
                    register_register_btn.setClickable(true);
                    register_register_btn.setEnabled(true);
                    register_register_btn.setBackground(getResources().getDrawable(R.drawable.register_btn_bg));
                } else {
                    register_register_btn.setClickable(false);
                    register_register_btn.setEnabled(false);
                }
            }
        });
        input_code = (PinEntryEditText) findViewById(R.id.input_code);
        input_code.setOnPinEnteredListener(listener);

        register_first = (RelativeLayout) findViewById(R.id.register_first);
        register_second = (RelativeLayout) findViewById(R.id.register_second);
        register_third = (RelativeLayout) findViewById(R.id.register_third);
        register_first.setVisibility(View.VISIBLE);
        register_second.setVisibility(View.GONE);
        register_third.setVisibility(View.GONE);
    }

    /**
     * 监听时间注册
     */
    private void initListener(){
        register_close.setOnClickListener(this);
        register_back_one.setOnClickListener(this);
        register_back_two.setOnClickListener(this);
        register_register.setOnClickListener(this);
        register_register_btn.setOnClickListener(this);
        register_agreement.setOnClickListener(this);
        register_privacy.setOnClickListener(this);
        code_timer.setOnClickListener(this);
    }

    /**
     * 验证码监听事件
     */
    PinEntryEditText.OnPinEnteredListener listener = new PinEntryEditText.OnPinEnteredListener() {
        @Override
        public void onPinEntered(CharSequence str) {
            iRegisterPresenter.register(phone,"123456789",str.toString());
            progressDialog.setIngState("正在注册...");
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
            case R.id.register_register:
                register_first.setVisibility(View.GONE);
                register_second.setVisibility(View.VISIBLE);
                register_third.setVisibility(View.GONE);
                show = 2;
                break;
            case R.id.register_register_btn:
                phone = register_phoneNumber.getText().toString();
                iRegisterPresenter.sendSMS(this,phone);
                break;
            case R.id.register_close:
                finish();
                break;
            case R.id.register_back_one:
                register_first.setVisibility(View.VISIBLE);
                register_second.setVisibility(View.GONE);
                register_third.setVisibility(View.GONE);
                show = 1;
                break;
            case R.id.register_back_two:
                register_first.setVisibility(View.GONE);
                register_second.setVisibility(View.VISIBLE);
                register_third.setVisibility(View.GONE);
                show = 2;
                break;
            case R.id.code_timer:
                iRegisterPresenter.sendSMS(this,phone);
                break;
        }
    }

    /**
     * 注册成功回调
     */
    @Override
    public void registerSuccess() {
        progressDialog.setSuccessState("注册成功,你的默认密码为123456789");
        progressDialog.dismissDelay(new ProgressDialogUtil.OnDelayDismissCallback() {
            @Override
            public void onDismiss() {
                timer.cancel();
                finish();
            }
        });
    }

    /**
     * 注册失败
     * @param s 失败原因
     * @param i 错误码
     */
    @Override
    public void registerFail(String s, int i) {
        progressDialog.setFailureState("注册失败", i, s);
        progressDialog.setActionBtn("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.dismiss();
            }
        });
    }

    /**
     * 验证码发送成功
     */
    @Override
    public void sendSMSSuccess() {
        if (show != 3){
            register_first.setVisibility(View.GONE);
            register_second.setVisibility(View.GONE);
            register_third.setVisibility(View.VISIBLE);
            code_phone.setText(phone);
            timer.start();
            show = 3;
        } else {
            timer.start();
        }
    }

    @Override
    public void sendSMSSFail(String s, int i) {
        Toast.makeText(RegisterActivity.this,s+" code:"+i,Toast.LENGTH_SHORT).show();
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            code_timer.setText((millisUntilFinished / 1000) + "秒后可重发");
            code_timer.setEnabled(false);
        }

        @Override
        public void onFinish() {
            code_timer.setEnabled(true);
            code_timer.setText("获取验证码");
        }
    };
}
