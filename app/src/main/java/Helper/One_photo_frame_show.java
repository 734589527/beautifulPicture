package Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.List;

import DAO.PictureDatas;

/**
 * Created by 159 on 2017-08-28.
 */
public class One_photo_frame_show
{

    public Bitmap showBitmap(float width,float heigh,Bitmap frame1,Bitmap frame2,Bitmap frame3,Bitmap frame4)
    {
        try
        {
            Bitmap bitmaps= BitmapFactory.decodeFile(PictureDatas.Path_sdcard.get(0));
            int W=(int)(heigh/3*0.9);
            int H=(int) ((float)W / frame1.getWidth() * frame1.getHeight());
            for(int i=0;i<3;i++) {
                if (bitmaps.getWidth() > width) {
                    bitmaps=small(bitmaps, width / bitmaps.getWidth());
                }
                if (bitmaps.getHeight() > heigh) {
                    bitmaps= small(bitmaps,heigh / bitmaps.getHeight());
                }
            }
            Bitmap bitmap = Bitmap.createBitmap((int)bitmaps.getWidth()+2*H,(int)bitmaps.getHeight()+2*H, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(bitmaps,H,H,null);
            int TBNum = bitmaps.getWidth() / W;
            if ((float)TBNum * W < bitmaps.getWidth())
            {
                TBNum += 1;
            }
            for(int i=0;i<TBNum;i++)
            {
                int WN=(i+1)*W+H;
                Rect rect=new Rect(i*W+H,0,WN,H);
                canvas.drawBitmap(frame1,null,rect,null);
            }
            for(int i=0;i<TBNum;i++)
            {
                int WN=(i+1)*W+H;
                Rect rect=new Rect(i*W+H,bitmaps.getHeight()+H,WN,bitmaps.getHeight()+2*H);
                canvas.drawBitmap(frame2,null,rect,null);
            }
            int RLNum = bitmaps.getHeight() / W;
            if ((float)RLNum * W < bitmaps.getHeight())
            {
                RLNum += 1;
            }
            for (int i = 0; i < RLNum; i++)
            {
                int HN=(i+1)*W+H;
                Rect rect = new Rect( 0,i*W+H,H,HN);
                canvas.drawBitmap(frame3, null, rect, null);
            }
            for (int i = 0; i < RLNum; i++)
            {
                int HN=(i+1)*W+H;
                Rect rect = new Rect( bitmaps.getWidth()+H,i*W+H,bitmaps.getWidth()+2*H,HN);
                canvas.drawBitmap(frame3, null, rect, null);
            }
            Paint paint=new Paint();
            paint.setColor(Color.WHITE);
            canvas.drawRect(0, 0, H, H, paint);
            canvas.drawRect(0,H+bitmaps.getHeight(),H,H*2+bitmaps.getHeight(),paint);
            canvas.drawRect(H+bitmaps.getWidth(),0,H*2+bitmaps.getWidth(),H,paint);
            canvas.drawRect(H+bitmaps.getWidth(),H+bitmaps.getHeight(),H*2+bitmaps.getWidth(),H*2+bitmap.getHeight(),paint);
            bitmaps.recycle();
            return bitmap;

        }
        catch (Exception e)
        {
            return  null;
        }
    }
    private static Bitmap small(Bitmap bitmap,float post)
    {
        Matrix matrix = new Matrix();
        matrix.postScale(post,post); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }
    public static interface OnLowMemoryListener {
        void onLowMemoryReceived();
    }
}
