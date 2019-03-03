package com.demo.somnus.bomda.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.demo.somnus.bomda.model.bean.AnnouncementDetails;
import com.demo.somnus.bomda.presenter.IPresenter.INoticeDetailsPresenter;
import com.demo.somnus.bomda.view.IView.INoticeDetailsActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus on 2018/1/24.
 * 校内公告详情Presenter
 */

public class NoticeDetailsPresenter implements INoticeDetailsPresenter {
    private INoticeDetailsActivity iNoticeDetailsActivity;

    public NoticeDetailsPresenter(INoticeDetailsActivity iNoticeDetailsActivity){
        this.iNoticeDetailsActivity = iNoticeDetailsActivity;
    }

    @Override
    public void details(final Context context, String href) {
        final GetBuilder get = OkHttpUtils.get();
        get.url(href)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e) {
                Toast.makeText(context, "获取失败", Toast.LENGTH_SHORT).show();
                Log.e("error",e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                Log.e("response",response);
                Html(response);
            }
        });
    }

    private void Html(String html) {
        AnnouncementDetails announcementDetails = new AnnouncementDetails();
        List<String> con = new ArrayList<>();
        //将html转为Document对象
        Document document = Jsoup.parse(html);
        //获得li的元素集合
        Elements elements = document.select("div.indexContents");
        Elements elements1 = elements.select("div.info_sinfol span");
        Log.e("size",elements1.size()+"");
        String content = elements1.get(0).text();
        String time = content.substring(0,content.indexOf("来"));
        String source = content.substring(content.indexOf("源")+2,content.indexOf("站")+1);
        announcementDetails.setTime(time);
        announcementDetails.setSource(source);
        Log.e("content",content);
        Log.e("time",time);
        Log.e("source",source);
        Elements elements2 = elements.select("div.info_contents p");
        for (Element element:elements2){
            String cc = element.text()+"\n".replaceAll("","");
            //String content = element.first().text();
            //Log.e("clicks",clicks);
            String cs = cc.replaceAll("","");
            con.add(cs);
            Log.e("cc",cs);
        }
        announcementDetails.setContent(con);
        iNoticeDetailsActivity.NoticeDetailsLoadSuccess(announcementDetails);
    }
}
