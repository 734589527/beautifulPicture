package Helper;

import android.app.Activity;
import android.content.Context;

import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

/**
 * Created by Administrator on 2017/9/4.
 */
public class selectPhoto
{
    private int RESULT_CANCELED=1;
    public selectPhoto(Context context)
    {
        PhotoPickerIntent intent = new PhotoPickerIntent(context);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(false); // 是否显示拍照， 默认false
        intent.setMaxTotal(9); // 最多选择照片数量，默认为9;
        ((Activity)context).startActivityForResult(intent, RESULT_CANCELED);
    }
}
