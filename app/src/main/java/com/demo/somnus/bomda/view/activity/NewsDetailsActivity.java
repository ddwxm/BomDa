package com.demo.somnus.bomda.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.NewsDetails;
import com.demo.somnus.bomda.presenter.IPresenter.INewsDetailsPresenter;
import com.demo.somnus.bomda.presenter.NewsDetailsPresenter;
import com.demo.somnus.bomda.view.IView.INewsDetailsActivity;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by Somnus on 2018/4/5.
 * 校园新闻详情Activity
 */

public class NewsDetailsActivity extends AppCompatActivity implements INewsDetailsActivity {
    private String href,title;
    private INewsDetailsPresenter iNewsDetailsPresenter;
    private int graphic[][] = new int[][]{
            {R.id.news_details_graphic11,R.id.news_details_im11,R.id.news_details_txt11},
            {R.id.news_details_graphic12,R.id.news_details_im12,R.id.news_details_txt12},
            {R.id.news_details_graphic13,R.id.news_details_im13,R.id.news_details_txt13},
            {R.id.news_details_graphic14,R.id.news_details_im14,R.id.news_details_txt14},
            {R.id.news_details_graphic15,R.id.news_details_im15,R.id.news_details_txt15}};
    private int lin[] = new int[]{R.id.news_details_graphic11,R.id.news_details_graphic12,R.id.news_details_graphic13
            ,R.id.news_details_graphic14,R.id.news_details_graphic15};
    private int im[] = new int[]{R.id.news_details_im11,R.id.news_details_im12,R.id.news_details_im13,R.id.news_details_im14
            ,R.id.news_details_im15};
    private int txt[] = new int[]{R.id.news_details_txt11,R.id.news_details_txt12,R.id.news_details_txt13,R.id.news_details_txt14
            ,R.id.news_details_txt15};
    private Toolbar news_details_toolbar;
    private TextView news_details_source,news_details_source_content,news_details_content_time,news_details_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iNewsDetailsPresenter = new NewsDetailsPresenter(this);
        ImmersionBar.with(this).transparentBar().statusBarColor(R.color.colorPrimary).init();
        setContentView(R.layout.activity_news_details);
        initIntent();
        init();
        iNewsDetailsPresenter.getDetails(href,NewsDetailsActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init(){
        news_details_toolbar = (Toolbar) findViewById(R.id.news_details_toolbar);
        news_details_toolbar.setTitle(title);
        this.setSupportActionBar(news_details_toolbar);

        news_details_source_content = (TextView) findViewById(R.id.news_details_source_content);
        news_details_content_time = (TextView) findViewById(R.id.news_details_content_time);
        news_details_content = (TextView) findViewById(R.id.news_details_content);
    }

    private void initIntent(){
        Intent intent = getIntent();
        href = intent.getStringExtra("href");
        title = intent.getStringExtra("title");

    }

    @Override
    public void NewsDetailsLoadSuccess(NewsDetails newsDetails) {
        String content = "";
        news_details_source_content.setText(newsDetails.getSource());
        news_details_content_time.setText(newsDetails.getTime());
        Log.e("time",newsDetails.getTime());
        Log.e("source",newsDetails.getSource());
        for (int i = 0;i<newsDetails.getContent().size();i++){
            Log.e("Content",newsDetails.getContent().get(i));
            content += newsDetails.getContent().get(i);
        }
        for (int i = 0;i<newsDetails.getUrl().length;i++){
            Log.e("url",newsDetails.getUrl()[i]);
        }
        for (int i = 0;i<newsDetails.getTit().length;i++){
            Log.e("title",newsDetails.getTit()[i]);
        }
        news_details_content.setText(content);
        initImage(newsDetails.getUrl(),newsDetails.getTit());
    }

    private void initImage(final String url[], String tit[]){
        Log.e("length",url.length+"");
        for (int i = 0;i<url.length;i++){
            Log.e("i",""+lin[i]);
            LinearLayout linearLayout = (LinearLayout) findViewById(lin[i]);
            ImageView imageView = (ImageView) findViewById(im[i]);
            TextView textView = (TextView) findViewById(txt[i]);
            linearLayout.setVisibility(View.VISIBLE);
            if (tit.length!=0){
                textView.setText(tit[i]);
            }
            Glide.with(NewsDetailsActivity.this).load(url[i]).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewsDetailsActivity.this, ViewImageActivity.class);
                    intent.putExtra("imagePath", url[finalI] == null ? "" : url[finalI]);
                    startActivity(intent);
                }
            });
        }
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
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
