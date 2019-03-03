package com.demo.somnus.bomda.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.Course;
import com.demo.somnus.bomda.model.bean.Schedule;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.ISchedulePresenter;
import com.demo.somnus.bomda.presenter.SchedulePresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.util.ProgressDialogUtil;
import com.demo.somnus.bomda.util.RandomUtil;
import com.demo.somnus.bomda.util.ScheduleUtil;
import com.demo.somnus.bomda.util.SharedPreferencesUtils;
import com.demo.somnus.bomda.util.ToastUtil;
import com.demo.somnus.bomda.view.IView.IScheduleActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.necer.ndialog.NDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by Somnus on 2018/4/5.
 * 课表Activity
 */

public class ScheduleActivity extends AppCompatActivity implements IScheduleActivity,View.OnClickListener{
    // 课程页面的button引用，6行7列
    private int[][] lessons = {
            {R.id.lesson11, R.id.lesson12, R.id.lesson13, R.id.lesson14, R.id.lesson15, R.id.lesson16, R.id.lesson17},
            {R.id.lesson21, R.id.lesson22, R.id.lesson23, R.id.lesson24, R.id.lesson25, R.id.lesson26, R.id.lesson27},
            {R.id.lesson31, R.id.lesson32, R.id.lesson33, R.id.lesson34, R.id.lesson35, R.id.lesson36, R.id.lesson37},
            {R.id.lesson41, R.id.lesson42, R.id.lesson43, R.id.lesson44, R.id.lesson45, R.id.lesson46, R.id.lesson47},
            {R.id.lesson51, R.id.lesson52, R.id.lesson53, R.id.lesson54, R.id.lesson55, R.id.lesson56, R.id.lesson57}};

    // 某节课的背景图,用于随机获取
    private int[] bg = {R.drawable.kb1, R.drawable.kb2, R.drawable.kb3, R.drawable.kb4, R.drawable.kb5, R.drawable.kb6,
            R.drawable.kb7, R.drawable.kb8, R.drawable.kb9, R.drawable.kb10, R.drawable.kb11, R.drawable.kb12,
            R.drawable.kb13, R.drawable.kb14, R.drawable.kb15, R.drawable.kb16};
    private ISchedulePresenter iSchedulePresenter;

    private Context context;
    private ProgressDialogUtil mProgressDialog;
    private PopupWindow mPopWindows;// 课程详细
    private List<Course> courseList = new ArrayList<>();
    private int position = -1;
    private final int success = 1;// 成功
    private final int fish = 2;// 失败
    private final int refresh = 3; //刷新成功
    public static ScheduleActivity instance = null;
    public ImageView iv_bg;
    private TextView activity_course_add,activity_course_check,activity_course_refresh;
    private Toolbar toolbar;
    private String url,mainHref;
    private Spinner activity_course_week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).transparentBar().statusBarColor(R.color.colorPrimary).init();
        iSchedulePresenter = new SchedulePresenter(this);
        setContentView(R.layout.activity_schedule);
        url = SharedPreferencesUtils.getString(ConstantsUtil.FILE_NAME,ConstantsUtil.FILE_NAME_STUDENT_SCHEDULE,null);
        mainHref = SharedPreferencesUtils.getString(ConstantsUtil.FILE_NAME,ConstantsUtil.FILE_NAME_STUDENT_MAIN,null);
        init();
        initListener();
        //iSchedulePresenter.getSchedule(BmobUser.getCurrentUser(User.class));

        Log.e("url",url);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case success:
                    Toast.makeText(context, "刷新课表成功", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    break;
                case fish:
                    Toast.makeText(context, "获取课表失败了", Toast.LENGTH_SHORT).show();
                    break;
                case refresh:
                    Toast.makeText(context, "刷新课表成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        };
    };

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.activity_course_toolbar);
        toolbar.setTitle("课表");
        this.setSupportActionBar(toolbar);
        mProgressDialog = new ProgressDialogUtil(ScheduleActivity.this);
        iv_bg = (ImageView) findViewById(R.id.iv_course_bg);
        Glide.with(ScheduleActivity.this)
                .load(R.mipmap.bg_course1)
                .placeholder(R.mipmap.bg_course1)
                .centerCrop()
                .into(iv_bg);

        activity_course_week = (Spinner) findViewById(R.id.activity_course_week);

        activity_course_add = (TextView) findViewById(R.id.activity_course_add);
        activity_course_check = (TextView) findViewById(R.id.activity_course_check);
        activity_course_refresh = (TextView) findViewById(R.id.activity_course_refresh);

        //iSchedulePresenter.querySchedule(url,mainHref,"2016-2017","1");
        iSchedulePresenter.getSchedule(BmobUser.getCurrentUser(User.class),"2017-2018","1");
    }

    private void initListener() {
        activity_course_add.setOnClickListener(this);
        activity_course_check.setOnClickListener(this);
        activity_course_refresh.setOnClickListener(this);
    }

    @Override
    public void loadScheduleList(List<Course> courseList) {
        this.courseList = courseList;
        initSchedule(courseList);
    }

    @Override
    public void noClass() {

    }

    @Override
    public void queryFail(String msg, int code) {
        ToastUtil.showShort(ScheduleActivity.this,"服务器繁忙,请稍后再试");
    }

    private void initSchedule(List<Course> courseList) {
        Course course = null;
        int  x, y;
        // 循环遍历
        for (int i = 0; i < courseList.size(); i++) {
            Log.d("有多少节课：", courseList.size() + "");
            course = courseList.get(i);// 拿到当前课程
            x = ScheduleUtil.changeDay(course.getDay());
            y = ScheduleUtil.changeTime(course.getTime());
            Log.e("xy",x+"xx"+y);
            Button lesson = (Button) findViewById(lessons[y-1][x-1]);// 获得该节课的button
            if (y == 5){
                lesson.setHeight(100);
            }
            // Log.d("背景图：", course.getBg() + "");
            int bgRes = bg[RandomUtil.RandomInt()];// 随机获取背景色
            lesson.setBackgroundResource(bgRes);// 设置背景
            lesson.setText(course.getName() + "@" + course.getAddress().substring(0,course.getAddress().indexOf("（")));// 设置文本为课程名+“@”+教室
            final Course finalSchedule = course;
            lesson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Course(finalSchedule);
                }
            });
        }
    }

    /**
     * 课程详细
     *
     * @param course
     */
    public void Course(Course course) {
        String[][] info = new String[][]{{"课程名:",course.getName()},{"时间:",course.getDay()+" "+course.getTime()+"节"},{"教室:",course.getAddress()}
                ,{"任课老师:",course.getTeacher()},{"周数:",course.getWeek()}};
        String item[] = new String[]{info[0][0]+" "+info[0][1],info[1][0]+"     "+info[1][1],info[2][0]+"     "+info[2][1],info[3][0]+" "+info[3][1]
                ,info[4][0]+"     "+info[4][1],};
        new NDialog(this)
                .setMessage(item[0]+"\n"+item[1]+"\n"+item[2]+"\n"+item[3]+"\n"+item[4])
                .setMessageColor(R.color.colorLightBlue)
                .setTitle(getResources().getString(R.string.dialog_course_title))
                .setTitleCenter(true)
                .setTitleSize(16)
                .setPositiveTextColor(getResources().getColor(R.color.colorBlue))
                .setButtonCenter(false)
                .setButtonSize(14)
                .setCancleable(true)
                .setCancleable(false)
                .setOnConfirmListener(new NDialog.OnConfirmListener() {
                    @Override
                    public void onClick(int which) {
                        //which,0代表NegativeButton，1代表PositiveButton

                        //Toast.makeText(MainActivity.this, "点击了：：" + which, Toast.LENGTH_SHORT).show();

                    }
                }).create(NDialog.CONFIRM).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_schedule_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.activity_schedule_menu_look:
                Intent intent = new Intent(ScheduleActivity.this,CalendarActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
