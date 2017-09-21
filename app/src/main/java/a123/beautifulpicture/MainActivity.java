package a123.beautifulpicture;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import DAO.Color_DAO;
import DAO.ListViewAdapter_Frame;
import DAO.ListViewAdapter_category;
import DAO.PictureDatas;
import DAO.SelectPhotoData_DAO;
import DAO.SysApplication;
import Helper.Five_photo_frame;
import Helper.Five_photo_share;
import Helper.Five_photo_show;
import Helper.Four_photo_frame;
import Helper.Four_photo_share;
import Helper.Four_photo_show;
import Helper.One_photo_frame_show;
import Helper.Seven_photo_show;
import Helper.Six_photo_frame;
import Helper.Six_photo_show;
import Helper.Three_photo_frame;
import Helper.Three_photo_share;
import Helper.Three_photo_show;
import Helper.Two_phone_share;
import Helper.Two_phone_show;
import Helper.Two_photo_frame;
import Helper.eight_photo_frame;
import Helper.eight_photo_share;
import Helper.eight_photo_show;
import Helper.night_photo_frame;
import Helper.night_photo_share;
import Helper.night_photo_show;
import Helper.seven_photo_frame;
import Helper.seven_photo_share;
import Helper.six_photo_share;
import UI.ColorBar;
import UI.HorizontialListView;

public class MainActivity extends Activity
{
    HorizontialListView listView1,listView2;
    List<String> categorys=new ArrayList<String>();
    List<Bitmap> []Frames;
    List<String> []FramePaths;
    long Position=0;
    long Position2=-1;
    ListViewAdapter_Frame[]FrameAdapter;
    ListViewAdapter_category categoryAdapter;
    public final int CROP_PHOTO = 10;
    ImageView photoImageview;
    int state=0;
    int bitmapindex=0;
    int bitmapindex1=0;
    int adapterInedx=0;
    List<File> files = new ArrayList<File>();
    int selectState=0;
    Handler handler;
    Handler handler1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this);
        listView1 = (HorizontialListView) findViewById(R.id.item_layout);
        listView2 = (HorizontialListView) findViewById(R.id.item_type);
        photoImageview = (ImageView) findViewById(R.id.imageView);
        photoImageview.setImageResource(R.drawable.addphoto);
        PictureDatas.delete();
        PictureDatas.init();
        setCategorys();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            try {
                //检测是否有写的权限
                int permission = ActivityCompat.checkSelfPermission(this,
                        "android.permission.WRITE_EXTERNAL_STORAGE");
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // 没有写的权限，去申请写的权限，会弹出对话框
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        handler=new Handler()
        {
            public void handleMessage(Message msg)
            {
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inPreferredConfig=Bitmap.Config.ARGB_4444;
                options.inSampleSize=3;
                photoImageview.setImageBitmap(BitmapFactory.decodeFile(PictureDatas.Path_show.get(0),options));
            }
        };
        handler1=new Handler()
        {
            public void handleMessage(Message msg)
            {
                try {
                    Color_DAO.OldColor = Color_DAO.NewColor;
                    int W1=SelectPhotoData_DAO.frame1.getWidth();
                    int H1=SelectPhotoData_DAO.frame1.getHeight();
                    int W2=SelectPhotoData_DAO.frame2.getWidth();
                    int H2=SelectPhotoData_DAO.frame2.getHeight();
                    int W3=SelectPhotoData_DAO.frame3.getWidth();
                    int H3=SelectPhotoData_DAO.frame3.getHeight();
                    int W4=SelectPhotoData_DAO.frame4.getWidth();
                    int H4=SelectPhotoData_DAO.frame4.getHeight();
                    int []arryy1=new int[W1*H1];
                    int []arryy2=new int[W2*H2];
                    int []arryy3=new int[W3*H3];
                    int []arryy4=new int[W4*H4];
                    int count=0;
                    for (int x = 0; x < SelectPhotoData_DAO.frame1.getHeight(); x++)
                    {
                        for (int y = 0; y < SelectPhotoData_DAO.frame1.getWidth(); y++)
                        {
                           arryy1[count]=SelectPhotoData_DAO.frame1.getPixel(y,x);
                            count++;
                        }
                    }
                    count=0;
                    for (int x = 0; x < H2; x++) {
                        for (int y = 0; y < W2; y++) {
                            arryy2[count]=SelectPhotoData_DAO.frame2.getPixel(y,x);
                            count++;
                        }
                    }
                    count=0;
                    for (int x = 0; x < H3; x++) {
                        for (int y = 0; y < W3; y++) {
                            arryy3[count]=SelectPhotoData_DAO.frame3.getPixel(y,x);
                            count++;
                        }
                    }
                    count=0;
                    for (int x = 0; x < H4; x++)
                    {
                        for (int y = 0; y <W4; y++)
                        {
                            arryy4[count]=SelectPhotoData_DAO.frame4.getPixel(y,x);
                            count++;
                        }
                    }
                    for(int i=0;i<arryy1.length;i++)
                    {
                        if(arryy1[i]<-10)
                            arryy1[i]=Color_DAO.NewColor;
                    }
                    for(int i=0;i<arryy2.length;i++)
                    {
                        if(arryy2[i]<-10)
                            arryy2[i]=Color_DAO.NewColor;
                    }
                    for(int i=0;i<arryy3.length;i++)
                    {
                        if(arryy3[i]<-10)
                            arryy3[i]=Color_DAO.NewColor;
                    }
                    for(int i=0;i<arryy4.length;i++)
                    {
                        if(arryy4[i]<-10)
                            arryy4[i]=Color_DAO.NewColor;
                    }
                    SelectPhotoData_DAO.frame1=Bitmap.createBitmap(arryy1,W1,H1, Bitmap.Config.ARGB_8888);
                    SelectPhotoData_DAO.frame2=Bitmap.createBitmap(arryy2,W2,H2, Bitmap.Config.ARGB_8888);
                    SelectPhotoData_DAO.frame3=Bitmap.createBitmap(arryy3,W3,H3, Bitmap.Config.ARGB_8888);
                    SelectPhotoData_DAO.frame4=Bitmap.createBitmap(arryy4,W4,H4, Bitmap.Config.ARGB_8888);
                         switch (PictureDatas.Path_sdcard.size()) {
                              case 1:
                                  PictureDatas.Path_share.clear();
                                  Log.e("tag2", "1");
                                  One_photo_frame_show one = new One_photo_frame_show();
                                  photoImageview.setImageBitmap(one.showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                  PictureDatas.save("share0.jpg", one.showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                  PictureDatas.Path_share.add("/sdcard/beautiful/share0.jpg");
                                  break;
                              case 2:
                                  photoImageview.setImageBitmap(new Two_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                  break;
                              case 3:
                                  photoImageview.setImageBitmap(new Three_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                  break;
                              case 4:
                                  photoImageview.setImageBitmap(new Four_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                  break;
                              case 5:
                                  photoImageview.setImageBitmap(new Five_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                  break;
                              case 6:
                                  photoImageview.setImageBitmap(new Six_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                  break;
                              case 7:
                                  photoImageview.setImageBitmap(new seven_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                  break;
                              case 8:
                                  photoImageview.setImageBitmap(new eight_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                  break;
                              case 9:
                                  photoImageview.setImageBitmap(new night_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                  break;
                          }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        ColorBar colorBar=(ColorBar)findViewById(R.id.colorbar);
        printColor();
       /* colorBar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.e("color", String.valueOf(Color_DAO.NewColor));
            }
        });*/
        Button bt1=(Button)findViewById(R.id.button);
        bt1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PictureDatas.delete();
                try {
                    state = 0;
                    PictureDatas.init();
                    bitmapindex = 0;
                    bitmapindex1=0;
                    photoImageview.setImageResource(R.drawable.addphoto);
                    selectState = 0;
                }
                catch (Exception e)
                {
                    Log.e("tags",e.getMessage());
                }
            }
        });
        Button bt2=(Button)findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                bitmapindex = 0;
                bitmapindex1 = 0;
                PictureDatas.set();
                if (PictureDatas.Path_sdcard.size() != 0 && !SelectPhotoData_DAO.FramePath.equals("") && SelectPhotoData_DAO.FramePath != null)
                {
                    Log.d("onclick",String.valueOf(PictureDatas.Path_sdcard.size()));
                    Log.d("onclick",String.valueOf(bitmapindex));
                    switch (PictureDatas.Path_sdcard.size())
                    {
                        case 1:
                            share();
                            break;
                        case 2: {
                            cutPicture(PictureDatas.Path_sdcard.get(0), (int) (PictureDatas.Width.get(0)), (int) ((float) PictureDatas.Width.get(0) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(0)))));
                            cutPicture(PictureDatas.Path_sdcard.get(1), (int) (PictureDatas.Width.get(1)), (int) ((float) PictureDatas.Width.get(1) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(1)))));
                            break;
                        }
                        case 3: {
                            cutPicture(PictureDatas.Path_sdcard.get(0), (int) (PictureDatas.Width.get(0)), (int) ((float) PictureDatas.Width.get(0) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(0)))));
                            cutPicture(PictureDatas.Path_sdcard.get(1), (int) (PictureDatas.Width.get(1)), (int) ((float) PictureDatas.Width.get(1) - 2 * ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(1)))));
                            cutPicture(PictureDatas.Path_sdcard.get(2), (int) (PictureDatas.Width.get(2)), (int) ((float) PictureDatas.Width.get(2) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(2)))));
                            break;
                        }
                        case 4:
                        {
                            cutPicture(PictureDatas.Path_sdcard.get(0), (int) (PictureDatas.Width.get(0)), (int) ((float) PictureDatas.Width.get(0)));
                            cutPicture(PictureDatas.Path_sdcard.get(1), (int) (PictureDatas.Width.get(1)), (int) ((float) PictureDatas.Width.get(1)));
                            cutPicture(PictureDatas.Path_sdcard.get(2), (int) (PictureDatas.Width.get(2)), (int) ((float) PictureDatas.Width.get(2)));
                            cutPicture(PictureDatas.Path_sdcard.get(3), (int) (PictureDatas.Width.get(3)), (int) ((float) PictureDatas.Width.get(3)));
                                break;
                        }
                        case 5:
                        {
                            cutPicture(PictureDatas.Path_sdcard.get(0),(int) (PictureDatas.Width.get(0)), (int) ((float) PictureDatas.Width.get(0)));
                            cutPicture(PictureDatas.Path_sdcard.get(1), (int) (PictureDatas.Width.get(1)), (int) ((float) PictureDatas.Width.get(1) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(1)))));
                            cutPicture(PictureDatas.Path_sdcard.get(2), (int) (PictureDatas.Width.get(2)), (int) ((float) PictureDatas.Width.get(2) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(2)))));
                            cutPicture(PictureDatas.Path_sdcard.get(3),(int) (PictureDatas.Width.get(3)), (int) ((float) PictureDatas.Width.get(3)));
                            cutPicture(PictureDatas.Path_sdcard.get(4),(int) (PictureDatas.Width.get(4)), (int) ((float) PictureDatas.Width.get(4)));
                            break;
                        }
                        case 6:
                        {
                            cutPicture(PictureDatas.Path_sdcard.get(0),(int) (PictureDatas.Width.get(0)), (int) ((float) PictureDatas.Width.get(0)));
                            cutPicture(PictureDatas.Path_sdcard.get(1), (int) (PictureDatas.Width.get(1)), (int) ((float) PictureDatas.Width.get(1) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(1)))));
                            cutPicture(PictureDatas.Path_sdcard.get(2), (int) (PictureDatas.Width.get(2)), (int) ((float) PictureDatas.Width.get(2) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(2)))));
                            cutPicture(PictureDatas.Path_sdcard.get(3),(int) (PictureDatas.Width.get(3)), (int) ((float) PictureDatas.Width.get(3)));
                            cutPicture(PictureDatas.Path_sdcard.get(4), (int) (PictureDatas.Width.get(4)), (int) ((float) PictureDatas.Width.get(4) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(4)))));
                            cutPicture(PictureDatas.Path_sdcard.get(5),(int) (PictureDatas.Width.get(5)), (int) ((float) PictureDatas.Width.get(5)));
                            break;
                        }
                        case 7:
                        {
                            cutPicture(PictureDatas.Path_sdcard.get(0),(int) (PictureDatas.Width.get(0)), (int) ((float) PictureDatas.Width.get(0)));
                            cutPicture(PictureDatas.Path_sdcard.get(1), (int) (PictureDatas.Width.get(1)), (int) ((float) PictureDatas.Width.get(1) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(1)))));
                            cutPicture(PictureDatas.Path_sdcard.get(2), (int) (PictureDatas.Width.get(2)), (int) ((float) PictureDatas.Width.get(2) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(2)))));
                            cutPicture(PictureDatas.Path_sdcard.get(3), (int) (PictureDatas.Width.get(3)), (int) ((float) PictureDatas.Width.get(3) + ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(3)))));
                            cutPicture(PictureDatas.Path_sdcard.get(4), (int) (PictureDatas.Width.get(4)), (int) ((float) PictureDatas.Width.get(4) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(4)))));
                            cutPicture(PictureDatas.Path_sdcard.get(5),(int) (PictureDatas.Width.get(5)), (int) ((float) PictureDatas.Width.get(5)));
                            cutPicture(PictureDatas.Path_sdcard.get(6), (int) (PictureDatas.Width.get(6)), (int) ((float) PictureDatas.Width.get(6) + ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(6)))));
                            break;
                        }
                        case 8:
                        {
                            cutPicture(PictureDatas.Path_sdcard.get(0),(int) (PictureDatas.Width.get(0)), (int) ((float) PictureDatas.Width.get(0)));
                            cutPicture(PictureDatas.Path_sdcard.get(1), (int) (PictureDatas.Width.get(1)), (int) ((float) PictureDatas.Width.get(1) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(1)))));
                            cutPicture(PictureDatas.Path_sdcard.get(2), (int) (PictureDatas.Width.get(2)), (int) ((float) PictureDatas.Width.get(2) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(2)))));
                            cutPicture(PictureDatas.Path_sdcard.get(3), (int) (PictureDatas.Width.get(3)), (int) ((float) PictureDatas.Width.get(3) + ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(3)))));
                            cutPicture(PictureDatas.Path_sdcard.get(4),(int) (PictureDatas.Width.get(4)), (int) ((float) PictureDatas.Width.get(4)));
                            cutPicture(PictureDatas.Path_sdcard.get(5),(int) (PictureDatas.Width.get(5)), (int) ((float) PictureDatas.Width.get(5)));
                            cutPicture(PictureDatas.Path_sdcard.get(6),(int) (PictureDatas.Width.get(6)), (int) ((float) PictureDatas.Width.get(6)));
                            cutPicture(PictureDatas.Path_sdcard.get(7),(int) (PictureDatas.Width.get(7)), (int) ((float) PictureDatas.Width.get(7)));
                            break;
                        }
                        case 9:
                        {
                            cutPicture(PictureDatas.Path_sdcard.get(0),(int) (PictureDatas.Width.get(0)), (int) ((float) PictureDatas.Width.get(0)));
                            cutPicture(PictureDatas.Path_sdcard.get(1), (int) (PictureDatas.Width.get(1)), (int) ((float) PictureDatas.Width.get(1) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(1)))));
                            cutPicture(PictureDatas.Path_sdcard.get(2), (int) (PictureDatas.Width.get(2)), (int) ((float) PictureDatas.Width.get(2) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(2)))));
                            cutPicture(PictureDatas.Path_sdcard.get(3), (int) (PictureDatas.Width.get(3)), (int) ((float) PictureDatas.Width.get(3) + ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(3)))));
                            cutPicture(PictureDatas.Path_sdcard.get(4),(int) (PictureDatas.Width.get(4)), (int) ((float) PictureDatas.Width.get(4)));
                            cutPicture(PictureDatas.Path_sdcard.get(5), (int) (PictureDatas.Width.get(5)), (int) ((float) PictureDatas.Width.get(5) + ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(5)))));
                            cutPicture(PictureDatas.Path_sdcard.get(6),(int) (PictureDatas.Width.get(6)), (int) ((float) PictureDatas.Width.get(6)));
                            cutPicture(PictureDatas.Path_sdcard.get(7), (int) (PictureDatas.Width.get(7)), (int) ((float) PictureDatas.Width.get(7) - ((float) SelectPhotoData_DAO.frame1.getHeight() / ((float) SelectPhotoData_DAO.frame1.getWidth() / (float) PictureDatas.Width.get(7)))));
                            cutPicture(PictureDatas.Path_sdcard.get(8),(int) (PictureDatas.Width.get(8)), (int) ((float) PictureDatas.Width.get(8)));
                            break;
                        }

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"请选择图片和花边",Toast.LENGTH_LONG).show();
                }
            }
        });
        FrameAdapter=new ListViewAdapter_Frame[categorys.size()];
        for(int i=0;i<categorys.size();i++)
        {
            FrameAdapter[i]=new ListViewAdapter_Frame(Frames[i],FramePaths[i],this.getBaseContext());
        }
        categoryAdapter=new ListViewAdapter_category(categorys,this.getApplicationContext());
        listView2.setAdapter(categoryAdapter);
        listView1.setAdapter(FrameAdapter[0]);
        categoryAdapter.changeBackGround((int) Position, Color.parseColor("#678fed"));
        /****************选择分类**********************************************/
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (Position == -1)
                {
                    categoryAdapter.changeBackGround((int) position, Color.parseColor("#678fed"));
                    Position = position;
                }
                else
                {
                    categoryAdapter.changeBackGround((int) Position, Color.parseColor("#f7e8e8"));
                    categoryAdapter.changeBackGround((int) position, Color.parseColor("#678fed"));
                    String text = ((TextView) ((View) categoryAdapter.getItem(position)).findViewById(R.id.list_typess)).getText().toString();
                    for (int i = 0; i < categorys.size(); i++) {
                        if (categorys.get(i).equals(text))
                        {
                            listView1.setAdapter(FrameAdapter[i]);
                            adapterInedx=i;
                        }
                    }
                    Position = position;
                }

            }
        });
        /**************************************************************/
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (PictureDatas.Path_sdcard.size() != 0) {
                    try {

                        if (Position2 == -1)
                        {
                            FrameAdapter[adapterInedx].changeBackGround((int) position, Color.parseColor("#678fed"));
                            String text = ((ImageView) ((View) FrameAdapter[adapterInedx].getItem(position)).findViewById(R.id.list_items)).getTag().toString();
                            SelectPhotoData_DAO.FramePath = text;
                            Log.e("tag2", String.valueOf(PictureDatas.Path_sdcard.size()));
                            SelectPhotoData_DAO.frame1 = BitmapFactory.decodeFile(text.replace("_S", "_T"));
                            SelectPhotoData_DAO.frame2 = BitmapFactory.decodeFile(text.replace("_S", "_B"));
                            SelectPhotoData_DAO.frame3 = BitmapFactory.decodeFile(text.replace("_S", "_L"));
                            SelectPhotoData_DAO.frame4 = BitmapFactory.decodeFile(text.replace("_S", "_R"));
                            switch (PictureDatas.Path_sdcard.size()) {
                                case 1:
                                    PictureDatas.Path_share.clear();
                                    Log.e("tag2", "1");
                                    One_photo_frame_show one = new One_photo_frame_show();
                                    photoImageview.setImageBitmap(one.showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    PictureDatas.save("share0.jpg", one.showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    PictureDatas.Path_share.add("/sdcard/beautiful/share0.jpg");
                                    break;
                                case 2:
                                    photoImageview.setImageBitmap(new Two_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 3:
                                    photoImageview.setImageBitmap(new Three_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 4:
                                    photoImageview.setImageBitmap(new Four_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 5:
                                    photoImageview.setImageBitmap(new Five_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 6:
                                    photoImageview.setImageBitmap(new Six_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 7:
                                    photoImageview.setImageBitmap(new seven_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 8:
                                    photoImageview.setImageBitmap(new eight_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 9:
                                    photoImageview.setImageBitmap(new night_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                            }
                            Position2 = position;
                        } else {
                            FrameAdapter[adapterInedx].changeBackGround((int) Position2, Color.parseColor("#f7e8e8"));
                            FrameAdapter[adapterInedx].changeBackGround((int) position, Color.parseColor("#678fed"));
                            String text = ((ImageView) ((View) FrameAdapter[adapterInedx].getItem(position)).findViewById(R.id.list_items)).getTag().toString();
                            SelectPhotoData_DAO.FramePath = text;
                            Log.e("tag2", String.valueOf(PictureDatas.Path_sdcard.size()));
                            SelectPhotoData_DAO.frame1 = BitmapFactory.decodeFile(text.replace("_S", "_T"));
                            SelectPhotoData_DAO.frame2 = BitmapFactory.decodeFile(text.replace("_S", "_B"));
                            SelectPhotoData_DAO.frame3 = BitmapFactory.decodeFile(text.replace("_S", "_L"));
                            SelectPhotoData_DAO.frame4 = BitmapFactory.decodeFile(text.replace("_S", "_R"));
                            switch (PictureDatas.Path_sdcard.size()) {
                                case 1:
                                    Log.e("tag2", "1");
                                    One_photo_frame_show one = new One_photo_frame_show();
                                    photoImageview.setImageBitmap(one.showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    PictureDatas.save("share0.jpg", one.showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    PictureDatas.Path_share.set(0, "/sdcard/beautiful/share0.jpg");
                                    break;
                                case 2:
                                    photoImageview.setImageBitmap(new Two_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 3:
                                    photoImageview.setImageBitmap(new Three_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 4:
                                    photoImageview.setImageBitmap(new Four_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 5:
                                    photoImageview.setImageBitmap(new Five_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 6:
                                    photoImageview.setImageBitmap(new Six_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 7:
                                    photoImageview.setImageBitmap(new seven_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 8:
                                    photoImageview.setImageBitmap(new eight_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                                case 9:
                                    photoImageview.setImageBitmap(new night_photo_frame().showBitmap(photoImageview.getWidth(), photoImageview.getHeight(), SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4));
                                    break;
                            }
                            Position2 = position;
                          //  readColor();
                        }
                    } catch (Exception e) {
                        Log.e("errors", e.getMessage());
                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this,"请选择图片",Toast.LENGTH_LONG).show();
                }
            }
        });
        ImageView view=(ImageView)findViewById(R.id.imageView);
        view.setImageResource(R.drawable.addphoto);
        view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        if(state==0||PictureDatas.Path_sdcard.size()==0)
                                        {
                                            selectPhotos();
                                           // PictureConfig.getInstance().init(options).openPhoto(mContext, resultCallback);
                                        }
                                    }
                                }
        );
    }
    /***************************************************************************/
    /*************************************
     * *
     * **
     * **
     * **
     * **
     * **
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                // 选择照片
                case RESULT_CANCELED:
                    refreshAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                case CROP_PHOTO:
                    if (data != null)
                    {
                        if(selectState==0)
                        {
                            try
                            {
                                   bitmapindex++;
                                    switch (PictureDatas.Path_sdcard.size()) {
                                        case 2:
                                            photoImageview.setImageBitmap(new Two_phone_show().showBitmap(photoImageview.getWidth(), photoImageview.getHeight()));
                                            break;
                                        case 3:
                                            photoImageview.setImageBitmap(new Three_photo_show().showBitmap(photoImageview.getWidth(), photoImageview.getHeight()));
                                            break;
                                        case 4:
                                            photoImageview.setImageBitmap(new Four_photo_show().showBitmap(photoImageview.getWidth(), photoImageview.getHeight()));
                                            break;
                                        case 5:
                                            photoImageview.setImageBitmap(new Five_photo_show().showBitmap(photoImageview.getWidth(), photoImageview.getHeight()));
                                            break;
                                        case 6:
                                            photoImageview.setImageBitmap(new Six_photo_show().showBitmap(photoImageview.getWidth(), photoImageview.getHeight()));
                                            break;
                                        case 7:
                                            photoImageview.setImageBitmap(new Seven_photo_show().showBitmap(photoImageview.getWidth(), photoImageview.getHeight()));
                                            break;
                                        case 8:
                                            photoImageview.setImageBitmap(new eight_photo_show().showBitmap(photoImageview.getWidth(), photoImageview.getHeight()));
                                            break;
                                        case 9:
                                            if (bitmapindex == PictureDatas.Path_sdcard.size()) {
                                                photoImageview.setImageBitmap(new night_photo_show().showBitmap(photoImageview.getWidth(), photoImageview.getHeight()));
                                            }
                                            break;

                                }
                               if(bitmapindex==PictureDatas.Path_sdcard.size())
                                    selectState=1;
                            }
                            catch (Exception e)
                            {
                            }
                        }
                        else
                        {
                            try
                            {
                                bitmapindex++;
                                switch(PictureDatas.Path_sdcard.size())
                                {
                                    case 2:
                                        new Two_phone_share().showBitmap(0, 0, SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4, bitmapindex-1);
                                        if(bitmapindex==PictureDatas.Path_sdcard.size())
                                            share();
                                        break;
                                    case 3:
                                        new Three_photo_share().showBitmap(0, 0, SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4, bitmapindex-1);
                                        Log.d("onclick2",String.valueOf(PictureDatas.Path_sdcard.size()));
                                        Log.d("onclick2", String.valueOf(bitmapindex));
                                        if(bitmapindex==PictureDatas.Path_sdcard.size())
                                            share();
                                        break;
                                    case 4:
                                        new Four_photo_share().showBitmap(0, 0, SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4, bitmapindex - 1);
                                        Log.d("onclick3",String.valueOf(PictureDatas.Path_sdcard.size()));
                                        Log.d("onclick3", String.valueOf(bitmapindex));
                                        if(bitmapindex==PictureDatas.Path_sdcard.size())
                                            share();
                                        break;
                                    case 5:
                                        new Five_photo_share().showBitmap(0, 0, SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4, bitmapindex - 1);
                                        if(bitmapindex==PictureDatas.Path_sdcard.size())
                                            share();
                                        break;
                                    case 6:
                                        new six_photo_share().showBitmap(0, 0, SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4, bitmapindex - 1);
                                        if(bitmapindex==PictureDatas.Path_sdcard.size())
                                            share();
                                        break;
                                    case 7:
                                        new seven_photo_share().showBitmap(0, 0, SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4, bitmapindex - 1);
                                        if(bitmapindex==PictureDatas.Path_sdcard.size())
                                            share();
                                        break;
                                    case 8:
                                        new eight_photo_share().showBitmap(0, 0, SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4, bitmapindex - 1);
                                        if(bitmapindex==PictureDatas.Path_sdcard.size())
                                            share();
                                        break;
                                    case 9:
                                        new night_photo_share().showBitmap(0, 0, SelectPhotoData_DAO.frame1, SelectPhotoData_DAO.frame2, SelectPhotoData_DAO.frame3, SelectPhotoData_DAO.frame4, bitmapindex - 1);
                                        if(bitmapindex==PictureDatas.Path_sdcard.size())
                                            share();
                                        break;
                                }

                            }
                            catch (Exception e)
                            {
                                Log.e("tags",e.getMessage());
                            }
                        }
                    }
                    //裁剪后删除拍照的照片
                    break;
            }
        }
    }
    private void share()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                files = new ArrayList<File>();
                for (int i = 0; i < PictureDatas.Path_share.size(); i++)
                {
                    File file = new File("/sdcard/beautiful/share" + String.valueOf(i) + ".jpg");//将要保存图片的路径
                    files.add(file);
                }
                Intent intent = new Intent();
                ComponentName comp = new ComponentName("com.tencent.mm",
                        "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                intent.setComponent(comp);
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");
                intent.putExtra("Kdescription", "实验");
                ArrayList<Uri> imageUris = new ArrayList<Uri>();
                    for (File f : files) {
                   //     intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = Uri.fromFile(f);
                        imageUris.add(contentUri);
                    }
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                startActivity(intent);
            }
        }).start();
    }
    private void refreshAdpater(final ArrayList<String> paths)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    PictureDatas.Path_sdcard=paths;
                    PictureDatas.set();
                    PictureDatas.set();
                if (paths.size() != 0)
                    state = 1;
                switch (paths.size())
                {

                    case 1:
                        try {
                            PictureDatas.Path_show = paths;
                            Message msg = new Message();
                            handler.sendMessage(msg);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                         break;
                    default:
                    for (int i = 0; i < PictureDatas.Path_sdcard.size(); i++)
                    {
                        if (PictureDatas.Height.get(i) != PictureDatas.Width.get(i))
                        {
                            PictureDatas.Path_cut1.add("/sdcard/beautiful/cut1" + i + ".jpg");
                            cutPicture(paths.get(i), 200, 200);
                        }
                    }
                    break;
                }
                }
                catch (Exception e)
                {
                    Log.e("Tagerr",e.getMessage());
                    e.printStackTrace();
                 } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).start();
    }
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    /**********************设置分类*******************************************/
    public void setCategorys()
    {
        int category_Sum=0;
        File destDir = new File("/sdcard/beautiful");
        File files[] = destDir.listFiles();
        if(files != null)
        {
            for (File f : files)
            {
                if (f.isDirectory())
                {
                    categorys.add(f.getName());
                    category_Sum++;
                    Log.e("category",f.getName());
                }
            }
        }
        Frames=new List[category_Sum];
        FramePaths=new List[category_Sum];
        setFrames();
    }
    private void setFrames()
    {
        for(int i=0;i<Frames.length;i++)
        {
            Frames[i]=new ArrayList<Bitmap>();
            FramePaths[i]=new ArrayList<String>();
        }
        for(int i=0;i<categorys.size();i++)
        {
            File destDir = new File("/sdcard/beautiful/"+categorys.get(i));
            File files[] = destDir.listFiles();
            if (files != null)
            {
                for (File f : files)
                {
                    if (!f.isDirectory())
                    {
                        if(f.getName().contains("_S"))
                        {
                            Bitmap bitmap= BitmapFactory.decodeFile(destDir.getPath()+"/"+f.getName());
                            Frames[i].add(bitmap);
                            FramePaths[i].add(destDir.getPath()+"/"+f.getName());
                            Log.e("bitmapPath",destDir.getPath()+"/"+f.getName());
                        }
                    }
                }
            }
        }

    }
    /*************************************************/
    /****************t退出系统***********************/
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private long mExitTime;
    public void exit()
    {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出系统", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        }
        else
        {
            PictureDatas.delete();
            SysApplication.getInstance().exit();
        }
    }
    public void selectPhotos()
    {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(false); // 是否显示拍照， 默认false
        intent.setMaxTotal(9); // 最多选择照片数量，默认为9;
        startActivityForResult(intent, RESULT_CANCELED);
    }
    /*****************************************************************************/
    public void cutPicture(String imagePath,int aspectX,int aspectY)
    {

        try {
            String uristring="";
            if(selectState==0)
            {
                uristring="file:///sdcard/beautiful/cut1"+String.valueOf(bitmapindex1)+".jpg";
                bitmapindex1++;
            }
            else
            {
                PictureDatas.Path_cut2.add("/sdcard/beautiful/cut2" + String.valueOf(bitmapindex1) + ".jpg");
                uristring="file:///sdcard/beautiful/cut2"+String.valueOf(bitmapindex1)+".jpg";
                bitmapindex1++;
            }
            Uri uri=Uri.parse(uristring);
            File file = new File(imagePath);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
            intent.putExtra("outputX", aspectX);
            intent.putExtra("outputY", aspectY);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
           // intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, CROP_PHOTO);
        }
        catch (Exception e)
        {
            Log.e("tagsss",e.getMessage());
            e.printStackTrace();
        }
    }
    public void printColor()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                  if(Color_DAO.NewColor!=Color_DAO.OldColor)
                  {
                        Message msg=new Message();
                      handler1.sendMessage(msg);
                  }
                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}