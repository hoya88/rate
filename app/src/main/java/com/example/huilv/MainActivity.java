package com.example.huilv;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText input;
    TextView result;
    float dollarRate = 0.15f;
    float euroRate = 0.12f;
    float wonRate = 172.0f;
    Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.input);
        result = (TextView) findViewById(R.id.result);

        SharedPreferences sharedPreferences =
                getSharedPreferences("rate", Activity.MODE_PRIVATE);

        PreferenceManager.getDefaultSharedPreferences(this);

        dollarRate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate", 0.0f);
        wonRate = sharedPreferences.getFloat("won_rate", 0.0f);

        Thread t = new Thread(String.valueOf(this));
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    String str = (String) msg.obj;
                    Log.i(TAG, "handlerMessage:get str=" + str);
                    result.setText(str);
                }
                super.handleMessage(msg);
            }
        };
    }

    public void onClick(View btn) {
        String str = input.getText().toString();
        float r = 0;
        if (str.length() > 0) {
            r = Float.parseFloat(str);
        }
    }

    public void click(View btn) {
        Log.d(TAG, "click: ");

        float r = 0.0f;
        switch (btn.getId()) {
            case R.id.dollar:
                r = dollarRate;
            case R.id.euro:
                r = euroRate;
            case R.id.won:
                r = wonRate;
        }
        //获取用户输入
        String str = input.getText().toString();
        Log.i(TAG, "click: str=" + str);
        if (str == null || str.length() == 0) {
            //提示
            Toast.makeText(this, "Please input RMB", Toast.LENGTH_SHORT).show();
        } else {
            //进行计算
            //......
            result.setText("1234.4444");
        }
    }

    public void openSecond(View btn){
        openConfig();
    }

    private void openConfig() {
        Intent config = new Intent(this, ConfigActivity.class);
        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("dollar_rate_key", euroRate);
        config.putExtra("dollar_rate_key", wonRate);

        Log.i(TAG, "openConfig:dollarRate=" + dollarRate);
        Log.i(TAG, "openConfig:euroRate=" + euroRate);
        Log.i(TAG, "openConfig:wonRate=" + wonRate);

        //startActivity(config);
        startActivityForResult(config, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_set) {
            openConfig();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(requestCode == 1 && resultCode==2){
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar",0.1f);
            euroRate = bundle.getFloat("key_euro",0.1f);
            wonRate = bundle.getFloat("key_won",0.1f);

            SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);
            editor.commit();
            Log.i(TAG,"onActivityR");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void run() {
        Log.i(TAG, "run:run()");
        URL url = null;
        try {
            org.jsoup.nodes.Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG,"run:title=" + doc.title());

            Element publicTime = doc.getElementsByClass("time").first();
            Log.i(TAG,"run:time=" + publicTime.html());

            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
            for(Element tr : trs){
                Elements tds = tr.getElementsByTag("td");
                if(tds.size()>0){
                    Log.i(TAG,"run:td="+ tds.first().text());
                    Log.i(TAG,"run:rate=" + tds.get(3).text());
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(7,"from message");
        //msg.obj = "from message";
        handler.sendMessage(msg);
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}