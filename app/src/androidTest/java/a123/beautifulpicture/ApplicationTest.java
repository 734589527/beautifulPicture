package a123.beautifulpicture;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import Helper.One_photo_frame_show;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application>
{
    private ArrayList<WeakReference<One_photo_frame_show.OnLowMemoryListener>> mLowMemoryListeners;
    public ApplicationTest() {

        super(Application.class);
        mLowMemoryListeners = new ArrayList<WeakReference<One_photo_frame_show.OnLowMemoryListener>>();
    }


}