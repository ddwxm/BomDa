package com.demo.somnus.bomda.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.GradeAdapter;
import com.demo.somnus.bomda.adapter.GradeMenuAdapter;
import com.demo.somnus.bomda.callback.CodeCallBack;
import com.demo.somnus.bomda.model.bean.Grade;
import com.demo.somnus.bomda.model.bean.GradeParent;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.GradePresenter;
import com.demo.somnus.bomda.presenter.IPresenter.IGradePresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.util.ProgressDialogUtil;
import com.demo.somnus.bomda.util.SharedPreferencesUtils;
import com.demo.somnus.bomda.util.ToastUtil;
import com.demo.somnus.bomda.view.IView.IGradeActivity;
import com.necer.ndialog.NDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import okhttp3.Call;


/**
 * Created by Somnus on 2018/4/5.
 * 成绩Activity
 */

public class GradeActivity extends AppCompatActivity implements IGradeActivity
        ,View.OnClickListener {
    private GradeMenuAdapter gradeMenuAdapter;
    private GradeAdapter gradeAdapter;
    private IGradePresenter iGradePresenter;
    private SwipeRefreshLayout grade_sw;
    private RecyclerView grade_re;
    private FloatingActionButton grade_choose_time;
    private String studentNum;
    private String password;
    private CoordinatorLayout grade_coordinatorLayout;
    private ProgressDialogUtil progressDialogUtil;
    private String url,mainHref;
    private Bitmap bitmap;
    private byte[] VerificationCode;
    private String year ="2017-2018",semester="第1学期";
    public List<Grade> grade_parent = new ArrayList<>();
    public List<String> grade_score = new ArrayList<>();
    public Map<String, List<String>> grade_map = new HashMap<>();
    private Spinner grade_year_sp,grade_semester_sp;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 1:
                    String code = (String)msg.obj;
                    Log.e("msg",code);
                    iGradePresenter.getGrade(ConstantsUtil.EDUCATION_SYSTEM_LOGIN_URL,studentNum,password,code,"历年成绩","","");
                    progressDialogUtil.setIngState("正在查询...");
                    progressDialogUtil.show();
                    break;
            }
        }
    };
    private List<String> yearList = new ArrayList<>();
    private List<String> semesterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iGradePresenter = new GradePresenter(this);
        setContentView(R.layout.activity_grade);
        initIntent();
        initData();
        init();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initIntent() {
        url = SharedPreferencesUtils.getString(ConstantsUtil.FILE_NAME,ConstantsUtil.FILE_NAME_STUDENT_GRADE,null);
        mainHref = SharedPreferencesUtils.getString(ConstantsUtil.FILE_NAME,ConstantsUtil.FILE_NAME_STUDENT_MAIN,null);
    }

    private void initData(){
        yearList.add("2014-2015");
        yearList.add("2015-2016");
        yearList.add("2016-2017");
        yearList.add("2017-2018");

        semesterList.add("第1学期");
        semesterList.add("第2学期");
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        grade_sw = (SwipeRefreshLayout) findViewById(R.id.grade_sw);
        grade_re = (RecyclerView) findViewById(R.id.grade_re);
        grade_re.setLayoutManager(new LinearLayoutManager(GradeActivity.this));
        grade_coordinatorLayout = (CoordinatorLayout) findViewById(R.id.grade_coordinatorLayout);
        //gradeMenuAdapter = new GradeMenuAdapter(GradeActivity.this,grade_parent,grade_map,R.layout.layout_score_zxcj_parent
        //        ,R.layout.layout_score_zxcj_children);
        //grade_re.setAdapter(gradeMenuAdapter);
       // grade_re.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
         //   @Override
         //   public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
       //         return false;
      //      }
        //});
        gradeAdapter = new GradeAdapter(GradeActivity.this,grade_re);
        grade_re.setAdapter(gradeAdapter);
        grade_year_sp = (Spinner) findViewById(R.id.grade_year_sp);
        ArrayAdapter arrayAdapter = new ArrayAdapter(GradeActivity.this,android.R.layout.simple_list_item_1,yearList);
        grade_year_sp.setAdapter(arrayAdapter);
        grade_year_sp.setSelection(3,true);
        grade_year_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = adapterView.getItemAtPosition(i).toString();
                Log.e("year",year);
                initd(year,semester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                year = "2017-2018";
            }
        });
        ArrayAdapter semesterAdapter = new ArrayAdapter(GradeActivity.this,android.R.layout.simple_list_item_1,semesterList);
        grade_semester_sp = (Spinner) findViewById(R.id.grade_semester_sp);
        grade_semester_sp.setAdapter(semesterAdapter);
        grade_semester_sp.setSelection(0,true);
        grade_semester_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = parent.getItemAtPosition(position).toString();
                Log.e("semester",semester);
                initd(year,semester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                semester = "第1学期";
            }
        });

        iGradePresenter.tryDatabase(BmobUser.getCurrentUser(User.class),"学年成绩","2017-2018","1",GradeActivity.this);
        progressDialogUtil.setIngState("正在查询...");
        progressDialogUtil.show();
    }

    /**
     * 检查是否保存，是则尝试直接查询，否先登录操作查询
     */
    private void check(){
        if (url != null){
            iGradePresenter.tryGet(url,mainHref,"历年成绩","","");
            progressDialogUtil.setIngState("正在查询...");
            progressDialogUtil.show();
        } else {
            codeDialog();
        }
    }

    private void getCode(){
        OkHttpUtils.get()
                .url("http://210.37.0.16/CheckCode.aspx")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                .addHeader("Cookie","ASP.NET_SessionId=5opk3c45cdnrcx55khwluh3j")
                .build().execute(new CodeCallBack() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Bitmap response) {
                bitmap = response;
            }
        });
    }

    private void initd(String year,String semester){
        iGradePresenter.tryDatabase(BmobUser.getCurrentUser(User.class),"学年成绩",year,semester.substring(1,2),GradeActivity.this);
        progressDialogUtil.setIngState("正在查询...");
        progressDialogUtil.show();
    }

    @Override
    public void noCJ() {
        progressDialogUtil.setSuccessState("查询成功");
        progressDialogUtil.dismissDelay(new ProgressDialogUtil.OnDelayDismissCallback() {
            @Override
            public void onDismiss() {
            }
        });
        ToastUtil.showShort(GradeActivity.this,"当前学期没有考试成绩");
    }

    /**
     * 弹出验证码
     */
    private void codeDialog(){

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_code,(ViewGroup) findViewById(R.id.dialog_code));
        final ImageView code = view.findViewById(R.id.dialog_code_image);
        final EditText input = view.findViewById(R.id.dialog_code_input);
        code.setImageBitmap(BitmapFactory.decodeByteArray(VerificationCode, 0,VerificationCode.length));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入验证码");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String code = input.getText().toString();
                Log.e("code",code);
                Message msg =Message.obtain();
                msg.obj = code;
                msg.what=1;   //标志消息的标志
                handler.sendMessage(msg);
                //iGradePresenter.getGrade(ConstantsUtil.EDUCATION_SYSTEM_LOGIN_URL,studentNum,password,code,"历年成绩","","");
                //dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }

    @Override
    public void getFail(String msg,int code) {
        progressDialogUtil.setFailureState("查询失败", code, msg);
        progressDialogUtil.setActionBtn("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialogUtil.dismiss();
            }
        });
    }

    @Override
    public void tryFail(String msg, int code) {
        codeDialog();
    }

    @Override
    public void loadGradeList(List<Grade> parent) {
        progressDialogUtil.setSuccessState("查询成功");
        progressDialogUtil.dismissDelay(new ProgressDialogUtil.OnDelayDismissCallback() {
            @Override
            public void onDismiss() {
            }
        });
        gradeAdapter.setGradeList(parent);
        Log.e("success","success");
    }

    @Override
    public void queryFail(String msg, int code) {
        check();
    }

}
