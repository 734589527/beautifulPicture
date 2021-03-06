package Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.List;

import DAO.PictureDatas;

/**
 * Created by Administrator on 2017/9/3.
 */
public class Two_phone_share
{
    public int showBitmap(float width,float heigh,Bitmap frame1,Bitmap frame2,Bitmap frame3,Bitmap frame4,int index)
    {

        try {
            Log.e("index",String.valueOf(2));
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPreferredConfig=Bitmap.Config.ARGB_4444;
            options.inSampleSize=3;
            Bitmap bitmap2=BitmapFactory.decodeFile(PictureDatas.Path_cut2.get(index),options);
            int W= bitmap2.getWidth();
            int H=(int) ((float)W / frame1.getWidth() * frame1.getHeight());
            Bitmap bitmap = Bitmap.createBitmap((int) (W+H), (int) (W+H), Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bitmap);
            Paint paint=new Paint();
            paint.setColor(Color.WHITE);
            canvas.drawRect(0, 0, W + H, W + H, paint);
            if(index==0)
            {
                Rect rect=new Rect(H,H,W+H,W);
                canvas.drawBitmap(bitmap2,null,rect,null);
                Rect rect1=new Rect(H,0,W+H,H);
                canvas.drawBitmap(frame1,null,rect1,null);
                Rect rect2=new Rect(H,H+bitmap2.getHeight(),W+H,H*2+bitmap2.getHeight());
                canvas.drawBitmap(frame2,null,rect2,null);
                Rect rect3 = new Rect(0, H, H, H + bitmap2.getHeight());
                canvas.drawBitmap(frame3, null, rect3, null);
            }
            else
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
            PictureDatas.save("share" + String.valueOf(index) + ".jpg", bitmap);
            PictureDatas.Path_share.add("/sdcard/beautiful/share" + String.valueOf(index) + ".jpg");
            bitmap.recycle();;
            bitmap2.recycle();
            return  1;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return  0;
        }

    }

}
