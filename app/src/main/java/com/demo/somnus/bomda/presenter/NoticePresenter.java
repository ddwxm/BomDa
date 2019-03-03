package com.demo.somnus.bomda.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.demo.somnus.bomda.model.bean.Announcement;
import com.demo.somnus.bomda.presenter.IPresenter.ISchoolNoticePresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.view.IView.ISchoolNoticeActivity;
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
 * Created by Somnus on 2018/4/5.
 * 校园公告Presenter
 */

public class NoticePresenter implements ISchoolNoticePresenter {

    private ISchoolNoticeActivity iSchoolNoticeActivity;

    public NoticePresenter(ISchoolNoticeActivity iSchoolNoticeActivity){
        this.iSchoolNoticeActivity = iSchoolNoticeActivity;
    }

    @Override
    public void getNotice(final Context context) {
        final GetBuilder post = OkHttpUtils.get();
        post.url("http://www.hainnu.edu.cn/html/xxgg/")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Connection","keep-alive")
                //.addHeader(Constants.HOME_HEADER_NAME_COOKIE,"Hm_lvt_409ad8f5fb35c5df7264e7bdb1448686=1516418579,1516515820,1516587473,1516626412; Hm_lpvt_409ad8f5fb35c5df7264e7bdb1448686=1516632923; jiathis_rdc=%7B%22http%3A//www.hainnu.edu.cn/html/2018/xiaoneixinwen_0118/17128.html%22%3A504668501%2C%22http%3A//www.hainnu.edu.cn/html/2018/xiaoneixinwen_0116/17118.html%22%3A506900602%2C%22http%3A//www.hainnu.edu.cn/html/2018/xiaoneixinwen_0111/17103.html%22%3A507029632%2C%22http%3A//www.hainnu.edu.cn/html/2018/xiaoneixinwen_0122/17143.html%22%3A507412600%2C%22http%3A//www.hainnu.edu.cn/html/2018/xxgg_0122/17144.html%22%3A%220%7C1516632923264%22%7D")
                .addHeader(ConstantsUtil.HOME_HEADER_NAME_HOST, "www.hainnu.edu.cn")
                .addHeader(ConstantsUtil.HOME_HEADER_NAME_AGENT, ConstantsUtil.HOME_HEADER_VALUE_AGENT)
                .build().execute(new StringCallback() {

            @Override
            public void onError(okhttp3.Call call, Exception e) {
                Toast.makeText(context, "获取失败", Toast.LENGTH_SHORT).show();
                Log.e("error",e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                parseHtml(response);
            }
        });
    }

    private void parseHtml(String html) {
        //将html转为Document对象
        Document document = Jsoup.parse(html);
        //获得li的元素集合
        Elements elements = document.select("div.newslistcontainer ul li");
        List<Announcement> lists = new ArrayList<>();
        for (Element element : elements) {
            //获得链接
            Announcement announcement = new Announcement();
            String href = element.select("a").last().attr("href");
            Log.e("href",href);
            //获得标题
            String content = element.select("a").attr("title");
            String nes = element.getElementsByIndexEquals(2).attr("href");
            String type = element.select("a").text().substring(0,4);
            String title = content.substring(content.indexOf("题")+2,content.indexOf("发")-1);
            String time = content.substring(content.indexOf("间")+2);
            Log.e("title",title);
            Log.e("content",content);
            Log.e("time",time);
            Log.e("nes",nes);
            Log.e("type",type);
            announcement.setHref(href);
            announcement.setTime(time);
            announcement.setTitle(title);
            announcement.setType(type);
            lists.add(announcement);
        }
        iSchoolNoticeActivity.onLoadNoticeListSuccess(lists);
        iSchoolNoticeActivity.RefreshSuccess();
    }
}
