package com.demo.somnus.bomda.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.demo.somnus.bomda.callback.InfoCallBack;
import com.demo.somnus.bomda.model.bean.Library;
import com.demo.somnus.bomda.model.bean.News;
import com.demo.somnus.bomda.presenter.IPresenter.ILibraryPresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.view.IView.ILibraryActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Somnus on 2018/4/15.
 * 图书馆Presenter
 */

public class LibraryPresenter implements ILibraryPresenter {

    private ILibraryActivity iLibraryActivity;

    public LibraryPresenter(ILibraryActivity iLibraryActivity){
        this.iLibraryActivity = iLibraryActivity;
    }

    @Override
    public void getNews(final Context context) {
        final GetBuilder getBuilder = OkHttpUtils.get();
        getBuilder.url("http://210.37.2.184/hs/gonggao/lib-news/news-222/show.asp")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Connection","keep-alive")
                //.addHeader(Constants.HOME_HEADER_NAME_COOKIE,"Hm_lvt_409ad8f5fb35c5df7264e7bdb1448686=1516418579,1516515820,1516587473,1516626412; Hm_lpvt_409ad8f5fb35c5df7264e7bdb1448686=1516632923; jiathis_rdc=%7B%22http%3A//www.hainnu.edu.cn/html/2018/xiaoneixinwen_0118/17128.html%22%3A504668501%2C%22http%3A//www.hainnu.edu.cn/html/2018/xiaoneixinwen_0116/17118.html%22%3A506900602%2C%22http%3A//www.hainnu.edu.cn/html/2018/xiaoneixinwen_0111/17103.html%22%3A507029632%2C%22http%3A//www.hainnu.edu.cn/html/2018/xiaoneixinwen_0122/17143.html%22%3A507412600%2C%22http%3A//www.hainnu.edu.cn/html/2018/xxgg_0122/17144.html%22%3A%220%7C1516632923264%22%7D")
                .addHeader("Host", "www.hainnu.edu.cn")
                .addHeader("User-Agent", ConstantsUtil.HOME_HEADER_VALUE_AGENT)
                .build().execute(new InfoCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                parseHtml(response);
            }
        });
    }

    private void parseHtml(String html){
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table#table25 tbody tr");
        elements.remove(0);
        List<Library> libraryList = new ArrayList<>();
        for (Element element : elements) {
            //获得链接
            Library library = new Library();
            String href = element.select("a").last().attr("href");
            Log.e("href",href);
            //获得标题
            String title = element.select("a").text();
            Log.e("title",title);
            library.setLink(href);
            library.setTitle(title);
            libraryList.add(library);
        }
        iLibraryActivity.loadLibraryList(libraryList);
        iLibraryActivity.RefreshSuccess();
    }
}
