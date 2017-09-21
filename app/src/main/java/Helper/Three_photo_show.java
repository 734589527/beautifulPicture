package Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.List;

import DAO.PictureDatas;

/**
 * Created by Administrator on 2017/9/10.
 */
public class Three_photo_show
{
    public Bitmap showBitmap(float width,float heigh)
    {
        Bitmap bitmap = Bitmap.createBitmap((int)(width/3*3.2),(int)heigh/3,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Rect rect=new Rect(0,0,(int)width/3,(int)heigh/3);
        Rect rect2=new Rect((int)(width/3*1.1),0,(int)(width/3*2.1),(int)heigh/3);
        Rect rect3=new Rect((int)(width/3*2.2),0,(int)(width/3*3.2),(int)heigh/3);
        Bitmap bitmap1= BitmapFactory.decodeFile(PictureDatas.Path_cut1.get(0));
        canvas.drawBitmap(bitmap1,null,rect,null);
        bitmap1= BitmapFactory.decodeFile(PictureDatas.Path_cut1.get(1));
        canvas.drawBitmap(bitmap1,null,rect2,null);
        bitmap1= BitmapFactory.decodeFile(PictureDatas.Path_cut1.get(2));
        canvas.drawBitmap(bitmap1,null,rect3,null);
        bitmap1.recycle();
        return bitmap;
    }
}
