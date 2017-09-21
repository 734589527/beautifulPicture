package Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import DAO.PictureDatas;

/**
 * Created by Administrator on 2017/9/12.
 */
public class Three_photo_frame {
    public Bitmap showBitmap(float width, float heigh, Bitmap frame1, Bitmap frame2, Bitmap frame3, Bitmap frame4) {
        try {
            int W = (int) (heigh / 3);
            int H = (int) ((float) W / frame1.getWidth() * frame1.getHeight());
            Bitmap bitmap = Bitmap.createBitmap((int) (width / 3 * 3.2 + H * 2), (int) (width / 3 + H * 2), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Rect rect = new Rect(H, H, (int) width / 3 + H, (int) heigh / 3 + H);
            Rect rect2 = new Rect((int) (width / 3 * 1.1 + H), H, (int) (width / 3 * 2.1 + H), (int) heigh / 3 + H);
            Rect rect6 = new Rect((int) (width / 3 * 2.2 + H), H, (int) (width / 3 *3.2 + H), (int) heigh / 3 + H);
            Bitmap bitmap1 = BitmapFactory.decodeFile(PictureDatas.Path_cut1.get(0));
            canvas.drawBitmap(bitmap1, null, rect, null);
            bitmap1 = BitmapFactory.decodeFile(PictureDatas.Path_cut1.get(1));
            canvas.drawBitmap(bitmap1, null, rect2, null);
            bitmap1 = BitmapFactory.decodeFile(PictureDatas.Path_cut1.get(2));
            canvas.drawBitmap(bitmap1, null, rect6, null);
            bitmap1.recycle();
            for (int i = 0; i < 3; i++) {
                Rect rect1 = new Rect((int) (H + i * (1.1 * W)), 0, (int) (H + i * (1.1 * W) + W), H);
                canvas.drawBitmap(frame1, null, rect1, null);
            }
            for (int i = 0; i < 3; i++) {
                Rect rect1 = new Rect((int) (H + i * (1.1 * W)), H + W, (int) (H + i * (1.1 * W) + W), H * 2 + W);
                canvas.drawBitmap(frame2, null, rect1, null);
            }
            Rect rect3 = new Rect(0, H, H, H + W);
            canvas.drawBitmap(frame3, null, rect3, null);
            Rect rect4 = new Rect((int) (W * 3.2 + H), H, (int) (W * 3.2 + 2 * H), H + W);
            canvas.drawBitmap(frame4, null, rect4, null);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
