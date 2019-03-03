package com.demo.somnus.bomda.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.demo.somnus.bomda.model.bean.Graphic;
import com.demo.somnus.bomda.model.bean.NewsDetails;
import com.demo.somnus.bomda.presenter.IPresenter.INewsDetailsPresenter;
import com.demo.somnus.bomda.view.IView.INewsDetailsActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus on 2018/4/5.
 * 校内新闻详情Presenter
 */

public class NewsDetailsPresenter implements INewsDetailsPresenter {

    private INewsDetailsActivity iNewsDetailsActivity;

    public NewsDetailsPresenter(INewsDetailsActivity iNewsDetailsActivity){
        this.iNewsDetailsActivity = iNewsDetailsActivity;
    }

    @Override
    public void getDetails(String url, final Context context) {
        final GetBuilder get = OkHttpUtils.get();
        get.url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e) {
                Toast.makeText(context, "获取失败", Toast.LENGTH_SHORT).show();
                //Log.e("error",e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                Html(response);
            }
        });
    }

    private void Html(String html){
        NewsDetails newsDetails = new NewsDetails();
        Graphic graphiC = new Graphic();
        String graphic[][] = new String[][]{};
        List<String> con = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        //将html转为Document对象
        Document document = Jsoup.parse(html);
        //获得li的元素集合
        Elements elements = document.select("div.indexContents");
        Elements elements1 = elements.select("div.info_sinfol span");
        Log.e("size",elements1.size()+"");
        String content = elements1.get(0).text();
        String time = content.substring(0,content.indexOf("来"));
        String source = "校"+content.substring(content.indexOf("源")+2,content.indexOf("点"));
        newsDetails.setTime(time);
        newsDetails.setSource(source);
        Log.e("content",content);
        Log.e("time",time);
        Log.e("source",source);
        Elements elements2 = elements.select("div.info_contents p");
        Elements elements3 = elements2.select("img");
        Elements elements4 = elements2.select("font");
        Log.e("elements2",elements2.size()+"");
        Log.e("elements3",elements3.size()+"");
        Log.e("elements4",elements4.size()+"");
        String tit[] = new String[elements4.size()];
        String url[] = new String[elements3.size()];
        String titl[] = new String[elements2.size()];
        String im[] = new String[elements2.size()];
        for (int i = 0;i<elements2.size();i++){
            String image = elements2.get(i).select("img").attr("src");
            String cc = elements2.get(i).select("span").text()+"\n";
            String title = elements2.get(i).select("font").text()+"\n".replaceAll("","");
            String cs = cc.replaceAll("","");
            Log.e("cc",cs);
            Log.e("image",image);
            Log.e("title",title);
            con.add(cs);
            im[i] = image;
        }

        for (int i =0;i<elements4.size();i++){
            Log.e("font",elements4.get(i).text());
            tit[i] = elements4.get(i).text();
        }

        for (int i =0;i<elements3.size();i++){
            Log.e("url",elements3.get(i).attr("src"));
            url[i] = elements3.get(i).attr("src");
        }
        newsDetails.setContent(con);
        newsDetails.setTit(tit);
        newsDetails.setUrl(url);
        Log.e("con",con.size()+"");
        Log.e("url",url.length+"");
        Log.e("tit",tit.length+"");
        Log.e("数组",titl.length+"");
        iNewsDetailsActivity.NewsDetailsLoadSuccess(newsDetails);
    }
}
