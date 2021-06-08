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
import java.util.HashMap;
import java.util.List;

public class MyRate implements Runnable{
    Handler handler;
    String TAG;

    public void setHandler(Handler h) {
        handler = new Handler();
        this.handler = h;
    }
    @Override
    public void run() {
        Document document = null;
        List<HashMap<String,String>> retList = new ArrayList<HashMap<String, String>>();
        try {
            Thread.sleep(5000);
            document = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/ ").get();
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
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("ItemTitle",str1);
                map.put("ItemDetail",val);
                retList.add(map);
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(9);
        msg.obj = retList;
        handler.sendMessage(msg);

    }
    
}
