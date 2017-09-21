package Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

import a123.beautifulpicture.MainActivity;

/**
 * Created by Administrator on 2017/9/3.
 */
public class cutPicture
{
    static  int CROP_PHOTO=10;
    public static void cropPic(Context context,String imagePath,int aspectX,int aspectY)
    {
        File file = new File(imagePath);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.leon.crop.fileprovider", file);
            intent.setDataAndType(contentUri, "image/*");
        }
        else
        {
            intent.setDataAndType(Uri.fromFile(file), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX",aspectX);
        intent.putExtra("aspectY",aspectY);
        intent.putExtra("outputX", aspectX);
        intent.putExtra("outputY", aspectY);
        intent.putExtra("return-data", true);
        ((Activity)context).startActivityForResult(intent, CROP_PHOTO);
    }
}
