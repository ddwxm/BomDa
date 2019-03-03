package com.demo.somnus.bomda.view.activity;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.somnus.bomda.BomDaApplication;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.callback.CodeCallBack;
import com.demo.somnus.bomda.presenter.BindingPresenter;
import com.demo.somnus.bomda.presenter.IPresenter.IBindingPresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.util.ProgressDialogUtil;
import com.demo.somnus.bomda.view.IView.IBindingActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by Somnus on 2018/4/5.
 * 学号绑定
 */

public class BindingActivity extends AppCompatActivity implements IBindingActivity
        ,View.OnClickListener{
    private IBindingPresenter iBindingPresenter;
    private EditText binding_id,binding_password,binding_code;
    private ImageView binding_verificationCode;
    private Button binding_binding;
    private TextView binding_service;
    private CheckBox binding_check;
    private ProgressDialogUtil progressDialogUtil;
    public enum ResultCode { BINDING_SUCCESS, NOT_BINDING }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iBindingPresenter = new BindingPresenter(this);
        setContentView(R.layout.activity_binding);

        init();
        initClick();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 控件初始化
     */
    private void init(){
        progressDialogUtil = new ProgressDialogUtil(BindingActivity.this);
        binding_id = (EditText) findViewById(R.id.binding_id);
        binding_password = (EditText) findViewById(R.id.binding_password);
        binding_code = (EditText) findViewById(R.id.binding_code);

        binding_verificationCode = (ImageView) findViewById(R.id.binding_verificationCode);

        binding_binding = (Button) findViewById(R.id.binding_binding);

        binding_service = (TextView) findViewById(R.id.binding_service);
        binding_service.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线

        binding_check = (CheckBox) findViewById(R.id.binding_check);
        binding_check.setChecked(true);

        getCode(ConstantsUtil.VERIFICATION_CODE_URL);

        binding_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding_id.getText().toString().length() == 0|| binding_password.getText().toString().length() == 0
                        || binding_code.getText().toString().length() == 0){
                    binding_binding.setClickable(false);
                } else {
                    binding_binding.setClickable(true);
                    binding_binding.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding_id.getText().toString().length() == 0|| binding_password.getText().toString().length() == 0
                        || binding_code.getText().toString().length() == 0){
                    binding_binding.setClickable(false);
                } else {
                    binding_binding.setClickable(true);
                    binding_binding.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }
        });
        binding_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding_id.getText().toString().length() == 0|| binding_password.getText().toString().length() == 0
                        || binding_code.getText().toString().length() == 0){
                    binding_binding.setClickable(false);
                } else {
                    binding_binding.setClickable(true);
                    binding_binding.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding_id.getText().toString().length() == 0|| binding_password.getText().toString().length() == 0
                        || binding_code.getText().toString().length() == 0){
                    binding_binding.setClickable(false);
                } else {
                    binding_binding.setClickable(true);
                    binding_binding.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }
        });
        binding_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding_id.getText().toString().length() == 0|| binding_password.getText().toString().length() == 0
                        || binding_code.getText().toString().length() == 0){
                    binding_binding.setClickable(false);
                } else {
                    binding_binding.setClickable(true);
                    binding_binding.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding_id.getText().toString().length() == 0|| binding_password.getText().toString().length() == 0
                        || binding_code.getText().toString().length() == 0){
                    binding_binding.setClickable(false);
                } else {
                    binding_binding.setClickable(true);
                    binding_binding.setBackground(getResources().getDrawable(R.drawable.btn_login_bg));
                }
            }
        });
    }

    /**
     * 点击事件初始化
     */
    private void initClick(){
        binding_binding.setOnClickListener(this);
        binding_service.setOnClickListener(this);
        binding_verificationCode.setOnClickListener(this);
    }

    /**
     * 获取验证码
     * @param url
     */
    private void getCode(String url){
        OkHttpUtils.get()
                .url(url)
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                .addHeader("Cookie","ASP.NET_SessionId=5opk3c45cdnrcx55khwluh3j")
                .build().execute(new CodeCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("error",e.getMessage());
                BomDaApplication.toastShow(BindingActivity.this,"验证码加载失败,请重试!",Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(Bitmap response) {
                binding_verificationCode.setImageBitmap(response);
            }
        });
    }

    /**
     * 刷新验证码
     */
    private void refreshCode(){
        String url = ConstantsUtil.VERIFICATION_CODE_URL+"?";
        getCode(url);
    }

    /**
     * 绑定成功回调
     */
    @Override
    public void bindingSuccess() {
        progressDialogUtil.setSuccessState("绑定成功");
        progressDialogUtil.dismissDelay(new ProgressDialogUtil.OnDelayDismissCallback() {
            @Override
            public void onDismiss() {
                setResult(ResultCode.BINDING_SUCCESS.ordinal());
                finish();
            }
        });
    }

    /**
     * 绑定失败回调
     * @param s 失败原因
     * @param code 错误码
     */
    @Override
    public void bindingFail(String s, int code) {
        progressDialogUtil.setFailureState("绑定失败", code, s);
        progressDialogUtil.setActionBtn("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialogUtil.dismiss();
            }
        });
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.binding_binding:
                if (binding_check.isChecked()){
                    iBindingPresenter.Binding(binding_id.getText().toString()
                            ,binding_password.getText().toString(),binding_code.getText().toString());
                    progressDialogUtil.setIngState("正在绑定...");
                    progressDialogUtil.show();
                } else {
                    BomDaApplication.toastShow(BindingActivity.this,"请同意BomDa隐私条款", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.binding_verificationCode:
                refreshCode();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(ResultCode.NOT_BINDING.ordinal());
                finish();
                break;
        }
        return false;
    }
}
