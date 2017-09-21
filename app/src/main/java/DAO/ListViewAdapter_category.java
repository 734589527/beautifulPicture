package DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import a123.beautifulpicture.R;

/**
 * Created by 159 on 2017-08-23.
 */
public class ListViewAdapter_category extends BaseAdapter
{
    View[] itemViews;
    Context context;
    public  ListViewAdapter_category(List<String> items,Context context)
    {
        itemViews=new View[items.size()];
        this.context=context;
        for(int i=0;i<items.size();i++)
        {
            String item1=(String)items.get(i);
            itemViews[i]=makeItemView(item1);
        }
    }
    public View makeItemView(String Name)
    {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.activity_item_type,null);
        TextView textView3=(TextView)itemView.findViewById(R.id.list_typess);
        textView3.setText(Name);
        textView3.setTextSize(13);
        return  itemView;

    }
    public void changeBackGround(int position,int color)
    {
        itemViews[position].setBackgroundColor(color);
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
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if(convertView==null)
            return  itemViews[position];
        return  convertView;
    }
}
