package a123.beautifulpicture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONObject;

    import java.io.BufferedReader;
    import java.io.ByteArrayOutputStream;
    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.text.DecimalFormat;
    import java.util.ArrayList;
    import java.util.List;

    import DAO.Frame_db_DAO;
import DAO.Load_Data;
import DAO.SysApplication;

    public class LoadFrame extends Activity
    {
        double NowSize = 0;
        double SumSize = 0;
        double Percentage = 0;
    List<String> Paths = new ArrayList<String>();
    Handler handler_loadSize;
    Handler handler_startLoad;
    Handler handler;
    private static final int LOADING = 1;
    private static final int END = 2;
        AsyncHttpClient client = new AsyncHttpClient();
    int ID = 0;
        String Frame_ID="frame_ID";
        String UpDate_Time="Update_Time";
    List<Frame_db_DAO> list = new ArrayList<Frame_db_DAO>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_frame);
        SysApplication.getInstance().addActivity(this);
       //setFrameID("frame_ID","0");
        final TextView textView = (TextView) findViewById(R.id.LoadText);
        final ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
        textView.setText("正在加载更新数据");
        InitData();
        if (getFrameID(Frame_ID) == null || getFrameID(Frame_ID) == "")
        {
            Load_Data.ID="0";
        }
        else
        {
            Load_Data.ID=getFrameID(Frame_ID);
        }
        if(getFrameID(UpDate_Time)==null||getFrameID(UpDate_Time)=="")
        {
            Load_Data.upDateTime="0000-00-00";
        }
        else
        {
            Load_Data.upDateTime=getFrameID(UpDate_Time);
        }
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                NowSize += msg.getData().getInt("loadingSize");
                Log.e("tag", String.valueOf(NowSize));
                progress.setProgress((int) (NowSize * 100  / SumSize));
                DecimalFormat df = new DecimalFormat("0.00");
                Percentage = (NowSize * 100 / SumSize);
                textView.setText("正在更新，数据大小为" + String.valueOf(df.format(SumSize / 1024/1024)) + "M            " + String.valueOf(df.format(Percentage)) + "%");
                if(Percentage==100)
                {
                    toMain();
                }
            }
        };
        handler_loadSize = new Handler()
        {
            public void handleMessage(Message msg)
            {
                if (msg.what == 1)
                {
                    if(SumSize==0)
                    {
                       toMain();
                    }
                    else
                    {
                        DecimalFormat df = new DecimalFormat("0.00");
                        textView.setText("正在更新，数据大小为" + String.valueOf(df.format(SumSize / 1024 / 1024)) + "M");
                        startLoad();
                    }
                }
                else if (msg.what == 0)
                {
                    Toast.makeText(LoadFrame.this, "服务器连接失败", Toast.LENGTH_LONG).show();
                    toMain();
                }
            }
        };

        handler_startLoad = new Handler()
        {
            public void handleMessage(Message msg)
            {
                if (msg.what == 1)
                {
                    DecimalFormat df = new DecimalFormat("0.00");
                    textView.setText("正在更新，数据大小为" + String.valueOf(df.format(SumSize / 1024/1024)) + "M            " + String.valueOf(df.format(Percentage)) + "%");
                }
                else
                {
                    Toast.makeText(LoadFrame.this, "服务器连接失败", Toast.LENGTH_LONG).show();
                    toMain();
                }
            }
        };

    }
        private void InitData()
        {

            client.setTimeout(10000);
            client.post("http://50772d0.nat123.cc:43175/UpDate.php", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    try {
                        Log.e("respone", "Load");
                        JSONObject json = new JSONObject(response);
                        JSONArray products = null;
                        int success = json.getInt("success");
                        if (success == 1) {

                            products = json.getJSONArray("products");
                            JSONObject c = products.getJSONObject(1);
                            if (c.getString("Sum").equals(Load_Data.upDateTime))
                            {
                            } else
                            {
                                setFrameID(Frame_ID, "0");
                                Load_Data.upDateTime=c.getString("Sum");
                            }

                        } else {
                            Log.e("respone", "error1");
                        }
                        Load();
                    } catch (Exception e)
                    {
                        Log.e("respone", "error2");
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      byte[] responseBody, Throwable throwable) {
//                    Log.e("respone", throwable.getMessage());
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub
                    super.onFinish();
                }
            });
        }
    private void toMain()
    {
        Intent intent = new Intent();
        intent.setClass(LoadFrame.this, MainActivity.class);
        LoadFrame.this.startActivity(intent);
    }
    public void setFrameID(String key, String value) {
        SharedPreferences sp = this.getApplicationContext().getSharedPreferences(key,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.putString(key, value);
        editor.commit();
    }

    public String getFrameID(String key) {
        SharedPreferences sp = this.getApplicationContext().getSharedPreferences(key,
                Context.MODE_PRIVATE);
            return sp.getString(key, "");
    }
        public void Load()
        {
            new Thread(){
            public void run () {
                HttpClient client = new DefaultHttpClient();
                client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
                // 读取超时
                client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
                StringBuilder builder = new StringBuilder();
                ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                HttpPost myget = new HttpPost("http://50772d0.nat123.cc:43175/Frame_ID.php");
                List params = new ArrayList();
                nameValuePair.add(new BasicNameValuePair("frame_ID", getFrameID("frame_ID")));
                try {
                    myget.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
                    HttpResponse response = client.execute(myget);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            response.getEntity().getContent()));
                    for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                        builder.append(s);
                    }
                    JSONObject json = new JSONObject(builder.toString());
                    JSONArray products = null;
                    int success = json.getInt("success");
                    if (success == 1) {
                        products = json.getJSONArray("products");
                        // products found
                        JSONObject c = products.getJSONObject(0);
                        Log.e("error1", c.getString("Sum"));
                        SumSize = c.getDouble("Sum");
                        Message m = new Message();
                        m.what = 1;
                        // 发送消息到Handler
                        handler_loadSize.sendMessage(m);
                    } else {
                        Log.e("error2", "");
                        Message m = new Message();
                        m.what = 0;
                        // 发送消息到Handler
                        handler_loadSize.sendMessage(m);
                    }
                } catch (Exception e) {
                    Message m = new Message();
                    m.what = 0;
                    // 发送消息到Handler
                    handler_loadSize.sendMessage(m);
                    //   Log.e("error3", e.getMessage());
                } finally {

                }
            }
            }.start();
        }

    void startLoad()
    {
        new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
                // 读取超时
                client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
                StringBuilder builder = new StringBuilder();
                ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                HttpPost myget = new HttpPost("http://50772d0.nat123.cc:43175/Frame_load.php");

                List params = new ArrayList();
                nameValuePair.add(new BasicNameValuePair("frame_ID", getFrameID("frame_ID")));
                try {
                    myget.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
                    HttpResponse response = client.execute(myget);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            response.getEntity().getContent()));
                    for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                        builder.append(s);
                    }
                    JSONObject json = new JSONObject(builder.toString());
                    JSONArray products = null;
                    int success = json.getInt("success");
                    if (success == 1) {
                        products = json.getJSONArray("products");

                        // products found
                        for (int i = 0; i < products.length(); i++)
                        {
                            JSONObject c = products.getJSONObject(i);
                            Frame_db_DAO frame_db = new Frame_db_DAO();
                            frame_db.name = c.getString("Name");
                            frame_db.path = c.getString("Path");
                            frame_db.category = c.getString("category");
                            frame_db.size = c.getDouble("Size");
                            if (c.getInt("ID") > ID)
                                ID = c.getInt("ID");
                            Paths.clear();
                            Paths.add("http://50772d0.nat123.cc:43175/" + c.getString("Path") + c.getString("Name") + "_" + c.getString("Color") + "_L.png");
                            Paths.add("http://50772d0.nat123.cc:43175/" + c.getString("Path") + c.getString("Name") + "_" + c.getString("Color") + "_R.png");
                            Paths.add("http://50772d0.nat123.cc:43175/" + c.getString("Path") + c.getString("Name") + "_" + c.getString("Color") + "_S.png");
                            Paths.add("http://50772d0.nat123.cc:43175/" + c.getString("Path") + c.getString("Name") + "_" + c.getString("Color") + "_T.png");
                            Paths.add("http://50772d0.nat123.cc:43175/" + c.getString("Path") + c.getString("Name") + "_" + c.getString("Color") + "_B.png");
                            for(int i1=0;i1<Paths.size();i1++)
                            {
                                download(Paths.get(i1),"/sdcard/beautiful/"+c.getString("category")+"/"+Paths.get(i1).replace("http://50772d0.nat123.cc:43175/"+ c.getString("Path") ,""),c.getString("category"));

                            }
                            Message m = new Message();
                            m.what = 1;
                            // 发送消息到Handler
                            handler_startLoad.sendMessage(m);
                        }
                        // setFrameID("frame_ID",String.valueOf(ID));
                    } else {
                        Log.e("error2", "");
                        Message m = new Message();
                        m.what = 0;
                        // 发送消息到Handler
                        handler_startLoad.sendMessage(m);
                    }
                }
                catch (Exception e)
                {
                    Message m = new Message();
                    m.what = 0;
                    // 发送消息到Handler
                    handler_startLoad.sendMessage(m);
                    Log.e("error3", e.getMessage());
                }
                finally
                {

                }
            }

        }.start();
    }

    void download(final String imageurl, final String savePath,final String category)
    {
        Log.e("errr",savePath);
        new Thread() {
            public void run()
            {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                URL url;
                Bitmap bitmap = null;
                int Size = 0;
                try {
                    url = new URL(imageurl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(10000);
                    con.setDoInput(true);
                    con.setUseCaches(false);
                    con.connect();
                    InputStream is = con.getInputStream();//获取文件的大小
                    Size = con.getContentLength();
                    Log.e("tag1",String.valueOf(Size));
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        bos.write(buffer, 0, len);
                        bos.flush();
                        Message msg = new Message();
                        msg.what = LOADING;
                        Bundle bundle = new Bundle();
                        bundle.putInt("loadingSize", len);
                        msg.setData(bundle);
                        Thread.sleep(100);
                        handler.sendMessage(msg);
                    }
                    is.close();
                    con.disconnect();
                    byte[] imgBytes = bos.toByteArray();
                    bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                    saveImg(savePath, bitmap,category);
                    setFrameID("frame_ID", String.valueOf(ID));
                    setFrameID(UpDate_Time,Load_Data.upDateTime);
                    Message msg = new Message();
                    msg.what = END;
                    handler.sendMessage(msg);

                }
                catch (MalformedURLException e)
                {
                    Log.e("Download_Progressbar", "MalformedURLException");
                    e.printStackTrace();
                    toMain();
                }
                catch (IOException e)
                {
                    Log.e("Download_Progressbar", "IOException");
                    e.printStackTrace();
                    toMain();
                }
                catch (InterruptedException e)
                {
                    Log.e("Download_Progressbar", "InterruptedException");
                    e.printStackTrace();
                    toMain();
                }
            }
        }.start();
    }

    public void saveImg(String fullPath, Bitmap bitmap,String category)
    {
        File destDir = new File("/sdcard/beautiful");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        destDir = new File("/sdcard/beautiful/"+category);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File file = new File(fullPath);
        if (file.exists())
        {
            file.delete();
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSaved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            if (isSaved)
            {
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
