package Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import DAO.PictureDatas;

/**
 * Created by Administrator on 2017/9/12.
 */
public class Three_photo_share
{
    public void showBitmap(float width,float heigh,Bitmap frame1,Bitmap frame2,Bitmap frame3,Bitmap frame4,int index)
    {
        try {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPreferredConfig=Bitmap.Config.ARGB_4444;
            options.inSampleSize=3;
            Bitmap bitmap2= BitmapFactory.decodeFile(PictureDatas.Path_cut2.get(index),options);
            int W= bitmap2.getWidth();
            int H=(int) ((float)W / (float)frame1.getWidth() * (float)frame1.getHeight());
            Bitmap bitmap = Bitmap.createBitmap((int) (W+H), (int) (W+H), Bitmap.Config.ARGB_4444);
            Log.e("width",String.valueOf(index));
            Log.e("index",String.valueOf(3));
            Log.e("width",String.valueOf(H));
            Canvas canvas = new Canvas(bitmap);
            Paint paint=new Paint();
            paint.setColor(Color.WHITE);
            canvas.drawRect(0, 0, W + H, W + H, paint);
            Log.e("index","3");
            if(index==0)
            {
                Log.e("width",String.valueOf(bitmap.getWidth()));
                Log.e("width",String.valueOf(bitmap.getHeight()));
                Rect rect=new Rect(H,H,W+H,W);
                canvas.drawBitmap(bitmap2,null,rect,null);
                Rect rect1=new Rect(H,0,W+H,H);
                canvas.drawBitmap(frame1,null,rect1,null);
                Rect rect2=new Rect(H,W,W+H,W+H);
                canvas.drawBitmap(frame2,null,rect2,null);
                Rect rect3 = new Rect(0,H,H,W);
                canvas.drawBitmap(frame3, null, rect3, null);
            }
            else if(index==2)
            {
                Rect rect=new Rect(0,H,W,bitmap2.getHeight()+H);
                canvas.drawBitmap(bitmap2,null,rect,null);
                Rect rect1=new Rect(0,0,W,H);
                canvas.drawBitmap(frame1,null,rect1,null);
                Rect rect2=new Rect(0,H+bitmap2.getHeight(),W,H*2+bitmap2.getHeight());
                canvas.drawBitmap(frame2,null,rect2,null);
                Rect rect3=new Rect(W,H,H+W,H+bitmap2.getHeight());
                canvas.drawBitmap(frame4,null,rect3,null);
            }
            else
            {
                bitmap = Bitmap.createBitmap((int) (W), (int) (W), Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                paint=new Paint();
                paint.setColor(Color.WHITE);
                canvas.drawRect(0, 0, W, W, paint);
                Rect rect=new Rect(0,H,W,W-H);
                canvas.drawBitmap(bitmap2,null,rect,null);
                Rect rect1=new Rect(0,0,W,H);
                canvas.drawBitmap(frame1,null,rect1,null);
                Rect rect2=new Rect(0,W-H,W,W);
                canvas.drawBitmap(frame2,null,rect2,null);
            }
            PictureDatas.save("share" + String.valueOf(index) + ".jpg", bitmap);
            PictureDatas.Path_share.add("/sdcard/beautiful/share" + String.valueOf(index) + ".jpg");
            bitmap.recycle();;
            bitmap2.recycle();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
