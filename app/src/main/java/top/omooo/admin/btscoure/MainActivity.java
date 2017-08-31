package top.omooo.admin.btscoure;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button mButton1;
    private EditText mEditText1;
    private ListView mListView1;
    String[] itemSrc=new String[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mButton1 = (Button) findViewById(R.id.button1);
        mEditText1 = (EditText) findViewById(R.id.editText1);
        mListView1 = (ListView) findViewById(R.id.listView1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "反馈邮→869759698@qq.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        List<ItemBean> been = new ArrayList<>();
//        int[] colors = {Color.parseColor("#CC9999"),
//                Color.parseColor("#FFFF99"),
//                Color.parseColor("#666699"),
//                Color.parseColor("#FF9900"),
//                Color.parseColor("#99CCFF"),
//                Color.parseColor("#0099CC"),
//                Color.parseColor("#99CC00"),
//                Color.parseColor("#FFCC33"),
//                Color.parseColor("#999999"),
//                Color.parseColor("#CC3399")};
//        for (int i = 0; i < 30; i++) {
//            Random random = new Random();
//            int index = random.nextInt(10);
//            been.add(new ItemBean(
//                    "辣鸡",
//                    "收录时间：2017-08-29" + i,
//                    "资源大小：" + i * 100 + "M",
//                    colors[index]
//            ));
////            if (i % 2 == 0) {
////                been.get(i).item_Color = Color.parseColor("#0099CC");
////            }
//        }
//        mListView1.setAdapter(new MyAdapter(this, been));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //动态调节
    public void getAndroiodScreenProperty(){
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度（像素）
        int height= dm.heightPixels; // 屏幕高度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width/density);//屏幕宽度(dp)
        int screenHeight = (int)(height/density);//屏幕高度(dp)
        Log.e("123", screenWidth + "======" + screenHeight);
    }

    //搜索
    public void search_buttonClick(View view) {
        String text1 = mEditText1.getText().toString().trim();
        //URL编码
            try {
                String text = URLEncoder.encode(text1, "UTF-8");
                Log.i("2333---text", text);
                String url = "http://www.btcerise.com/search?keyword=" + text;
                //请求网络
                OkHttpClient mOkHttpClient = new OkHttpClient();
                Request mRequest = new Request.Builder().url(url).build();
                Call call = mOkHttpClient.newCall(mRequest);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("提示")
                                .setMessage("返回错误")
                                .setIcon(R.drawable.alertpic)
                                .create();
                        dialog.show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.i("2333---result", result);
                        //Jsoup解析
                        Document document = Jsoup.parse(result);
                        //标题
                        Elements title1 = document.select("h5.h");
                        String title2 = document.select("h5.h").text().toString();
                        String first = title1.get(1).text().toString();
                        String last = title1.last().text().toString();
                        int re_size = title1.size();
                        Log.i("2333---size", "size ---"+re_size);
                        Log.i("2333---title", title2);
                        Log.i("2333---first", first);
                        Log.i("2333---last", last);
                        //收录时间和大小
                        Elements dateSize = document.select("span.prop_val");
                        Log.i("2333---dateSiza", document.select("span.prop_val").text().toString());
                        //拆分数组
                        String[] date=new String[20];
                        String[] size=new String[20];
                        for (int i = 1; i < 60; i++) {
                            if (i % 3 == 1) {
                                date[i/3] = dateSize.get(i-1).text().toString();
                            }else if (i%3==2)
                                size[i / 3] = dateSize.get(i-1).text().toString();
                        }
                        //链接地址
                        Elements elements_src = document.select("[href^=magnet]");
                        String url = elements_src.get(1).attr("href").toString().substring(0,60);
                        Log.i("2333---url", url);
                        //加载解析好的数据
                        final List<ItemBean> been = new ArrayList<>();
                        int[] colors = {Color.parseColor("#CC9999"),
                                Color.parseColor("#FFFF99"),
                                Color.parseColor("#666699"),
                                Color.parseColor("#FF9900"),
                                Color.parseColor("#99CCFF"),
                                Color.parseColor("#0099CC"),
                                Color.parseColor("#99CC00"),
                                Color.parseColor("#FFCC33"),
                                Color.parseColor("#999999"),
                                Color.parseColor("#CC3399")};
                        for (int i = 0; i < 20; i++) {
                            Random random = new Random();
                            int index = random.nextInt(10);
                            been.add(new ItemBean(
                                    title1.get(i).text().toString(),
                                    "收录时间：" +date[i]+"  " ,
                                    "资源大小：" + size[i],
                                    colors[index]
                            ));
                            Log.i("2333---src", elements_src.get(i).attr("href").toString().substring(0,60));
                            itemSrc[i] = elements_src.get(i).attr("href").toString().substring(0,60);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mListView1.setAdapter(new MyAdapter(MainActivity.this, been));

                                mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Log.i("2333---i", "i="+i);
                                        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData data = ClipData.newPlainText("message", itemSrc[i]);
                                        manager.setPrimaryClip(data);
                                        Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("嘀嘀嘀")
                                                .setMessage("复制磁链接发车？？？")
                                                .setIcon(R.drawable.emoji)
                                                .setNegativeButton("没有，快滚", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Toast.makeText(MainActivity.this, "大佬，打扰了", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .setPositiveButton("是的，爸爸", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                                        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                                                        ClipData data = ClipData.newPlainText("message", itemSrc[i]);
//                                                        manager.setPrimaryClip(data);
                                                        Toast.makeText(MainActivity.this, "已复制到剪切板准备发车", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                            .create();
                                        dialog.show();
                                }
                            });
                        }
                    });
                }
            });
//            //直接使用Jsoup
//            try {
//                Document document = Jsoup.connect(url).get();
//                String title = document.title();
//                Log.i("2333---title", title);
//                String htmlStr = document.body().toString();
//                Log.i("2333---htmlStr", htmlStr);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
