package com.example.huilv;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyTask implements Runnable{
    Handler handler;
    String TAG;

    public void setHandler(Handler h) {
        handler = new Handler();
        this.handler = h;
    }
    public  void run(){
        List<String> retList = new ArrayList<String>();
        Document document = null;
        try {
            document = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG,"run:title=" + document.title());

            Elements table = document.getElementsByTag("table");
            Element table2 = table.get(1);
            Elements tds = table2.getElementsByTag("td");
            for(int i = 0;i<tds.size();i+=8){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG,"run:" + str1 + "==>" + val);
                retList.add(str1 + "==>" + val);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);

    }
}
