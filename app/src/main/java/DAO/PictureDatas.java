package DAO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */
public class PictureDatas
{
    public static List<String> Path_sdcard=new ArrayList<String>();
    public static List<String> Path_cut1=new ArrayList<String>();
    public static List<String> Path_cut2=new ArrayList<String>();
    public static List<String> Path_share=new ArrayList<String>();
    public static List<String> Path_show=new ArrayList<String>();
    public static List<Integer> Width=new ArrayList<Integer>();
    public static  List<Integer> Height=new ArrayList<Integer>();;
    public static void init()
    {
        Path_sdcard.clear();
        Path_cut1.clear();
        Path_cut2.clear();
        Path_share.clear();
        Path_show.clear();
        Width.clear();
        Height.clear();
    }
    public static void save(String name,Bitmap bitmap)
    {
        Log.e("save",name);
        File destDir = new File("/sdcard/beautiful");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        destDir = new File("/sdcard/beautiful/"+name);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File file = new File("/sdcard/beautiful/"+name);
        if (file.exists())
        {
            file.delete();
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSaved = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
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
    public static void delectfile(String name)
    {
        File file = new File("/sdcard/beautiful/"+name);
        if (file.exists())
        {
            file.delete();
        }
    }
    public static void set()
    {
        for(int i=0;i<Path_sdcard.size();i++)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(Path_sdcard.get(i));
            Width.add(bitmap.getWidth());
            Height.add(bitmap.getHeight());
            bitmap.recycle();
        }
    }
    public  static void delete()
    {
        for(int i=0;i<9;i++)
        {
            File file = new File("/sdcard/beautiful/share"+String.valueOf(i)+".jpg");
            if (file.exists())
            {
                file.delete();
            }
            file = new File("/sdcard/beautiful/cut1"+String.valueOf(i)+".jpg");
            if (file.exists())
            {
                file.delete();
            }
            file = new File("/sdcard/beautiful/cut2"+String.valueOf(i)+".jpg");
            if (file.exists())
            {
                file.delete();
            }
            file = new File("/sdcard/beautiful/show"+String.valueOf(i)+".jpg");
            if (file.exists())
            {
                file.delete();
            }
        }
    }

}
