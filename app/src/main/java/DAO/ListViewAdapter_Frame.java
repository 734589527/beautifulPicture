package DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import a123.beautifulpicture.R;

/**
 * Created by 159 on 2017-08-23.
 */
public class ListViewAdapter_Frame extends BaseAdapter
{
    View[] itemViews;
    Context context;
    public  ListViewAdapter_Frame(List<Bitmap> items,List<String> Paths,Context context)
    {
        itemViews=new View[items.size()];
        this.context=context;
        for(int i=0;i<items.size();i++)
        {
            Bitmap item1=(Bitmap)items.get(i);
            itemViews[i]=makeItemView(item1,Paths.get(i));
        }
    }
    private int selectedPosition = 0;// 选中的位置
    public void setSelectedPosition(int position)
    {
        selectedPosition = position;
    }
    public void changeBackGround(int position,int color)
    {
        itemViews[position].setBackgroundColor(color);
    }
    public View makeItemView(Bitmap bit,String path)
    {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.activity_frame_item,null);
        ImageView imageView3=(ImageView)itemView.findViewById(R.id.list_items);
        imageView3.setImageBitmap(bit);
        imageView3.setTag(path);
        return  itemView;
    }


    @Override
    public int getCount() {
        return itemViews.length;
    }

    @Override
    public Object getItem(int position) {
        return itemViews[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            return  itemViews[position];
        return  convertView;
    }

}
