package Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import DAO.PictureDatas;

/**
 * Created by Administrator on 2017/9/16.
 */
public class night_photo_share
{
    public void showBitmap(float width,float heigh,Bitmap frame1,Bitmap frame2,Bitmap frame3,Bitmap frame4,int index)
    {
        try
        {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPreferredConfig=Bitmap.Config.ARGB_4444;
            options.inSampleSize=3;
            Bitmap bitmap2= BitmapFactory.decodeFile(PictureDatas.Path_cut2.get(index),options);
            int W=bitmap2.getWidth();
            int H=(int)((float)W/(float)frame1.getWidth()*(float)frame1.getHeight());
            Paint paint=new Paint();
            paint.setColor(Color.WHITE);
            Bitmap bitmap = null;
            if(index==0)
            {
                bitmap=Bitmap.createBitmap(W+H,W+H, Bitmap.Config.ARGB_4444);
                Canvas canvas=new Canvas(bitmap);
                canvas.drawRect(0,0,W+H,W+H,paint);
                Rect rect=new Rect(H,H,W+H,W+H);
                canvas.drawBitmap(bitmap2,null,rect,null);
                rect=new Rect(H,0,W+H,H);
                canvas.drawBitmap(frame1,null,rect,null);
                rect=new Rect(0,H,H,W+H);
                canvas.drawBitmap(frame3,null,rect,null);
            }
            if(index==1)
            {
                bitmap=Bitmap.createBitmap(W,W, Bitmap.Config.ARGB_4444);
                Canvas canvas=new Canvas(bitmap);
                canvas.drawRect(0,0,W,W,paint);
                Rect rect=new Rect(0,H,W,W);
                canvas.drawBitmap(bitmap2,null,rect,null);
                rect=new Rect(0,0,W,H);
                canvas.drawBitmap(frame1,null,rect,null);
            }
            if(index==2)
            {
                bitmap=Bitmap.createBitmap(W+H,W+H, Bitmap.Config.ARGB_4444);
                Canvas canvas=new Canvas(bitmap);
                canvas.drawRect(0,0,W+H,W+H,paint);
                Rect rect=new Rect(0,H,W,W+H);
                canvas.drawBitmap(bitmap2,null,rect,null);
                rect=new Rect(0,0,W,H);
                canvas.drawBitmap(frame1,null,rect,null);
                rect=new Rect(W,H,W+H,W+H);
                canvas.drawBitmap(frame4,null,rect,null);
            }
            if(index==3)
            {
                bitmap=Bitmap.createBitmap(W+H,W+H, Bitmap.Config.ARGB_4444);
                Canvas canvas=new Canvas(bitmap);
                canvas.drawRect(0,0,W+H,W+H,paint);
                Rect rect=new Rect(H,0,W+H,W+H);
                canvas.drawBitmap(bitmap2,null,rect,null);
                rect=new Rect(0,0,H,W+H);
                canvas.drawBitmap(frame3,null,rect,null);
            }
            if(index==4)
            {
                bitmap=Bitmap.createBitmap(W,W, Bitmap.Config.ARGB_4444);
                Canvas canvas=new Canvas(bitmap);
                canvas.drawRect(0,0,W,W,paint);
                Rect rect=new Rect(0,0,W,W);
                canvas.drawBitmap(bitmap2,null,rect,null);
            }
            if(index==5)
            {
                bitmap=Bitmap.createBitmap(W+H,W+H, Bitmap.Config.ARGB_4444);
                Canvas canvas=new Canvas(bitmap);
                canvas.drawRect(0,0,W+H,W+H,paint);
                Rect rect=new Rect(0,0,W,W+H);
                canvas.drawBitmap(bitmap2,null,rect,null);
                rect=new Rect(W,0,W+H,W+H);
                canvas.drawBitmap(frame4,null,rect,null);
            }
            if(index==6)
            {
                bitmap=Bitmap.createBitmap(W+H,W+H, Bitmap.Config.ARGB_4444);
                Canvas canvas=new Canvas(bitmap);
                canvas.drawRect(0,0,W+H,W+H,paint);
                Rect rect=new Rect(H,0,W+H,W);
                canvas.drawBitmap(bitmap2,null,rect,null);
                rect=new Rect(H,W,W+H,W+H);
                canvas.drawBitmap(frame2,null,rect,null);
                rect=new Rect(0,0,H,W);
                canvas.drawBitmap(frame3,null,rect,null);
            }
            if(index==7)
            {
                bitmap=Bitmap.createBitmap(W,W, Bitmap.Config.ARGB_4444);
                Canvas canvas=new Canvas(bitmap);
                canvas.drawRect(0,0,W,W,paint);
                Rect rect=new Rect(0,0,W,W-H);
                canvas.drawBitmap(bitmap2,null,rect,null);
                rect=new Rect(0,W-H,W,W);
                canvas.drawBitmap(frame2,null,rect,null);
            }
            if(index==8)
            {
                bitmap=Bitmap.createBitmap(W+H,W+H, Bitmap.Config.ARGB_4444);
                Canvas canvas=new Canvas(bitmap);
                canvas.drawRect(0,0,W+H,W+H,paint);
                Rect rect=new Rect(0,0,W,W);
                canvas.drawBitmap(bitmap2,null,rect,null);
                rect=new Rect(0,W,W,W+H);
                canvas.drawBitmap(frame2,null,rect,null);
                rect=new Rect(W,0,W+H,W);
                canvas.drawBitmap(frame4,null,rect,null);
            }
            PictureDatas.save("share"+String.valueOf(index)+".jpg",bitmap);
            PictureDatas.Path_share.add("/sdcard/beautiful/share"+String.valueOf(index)+".jpg");
            bitmap.recycle();;
            bitmap2.recycle();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
