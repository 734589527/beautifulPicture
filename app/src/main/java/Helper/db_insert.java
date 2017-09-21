package Helper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import DAO.Frame_db_DAO;

/**
 * Created by 159 on 2017-08-21.
 */
public class db_insert
{
    SQLiteDatabase db;
    String Path="/data/data/com.BeautifulFrame.db/databases/frame.db";
    public db_insert()
    {
        db=SQLiteDatabase.openOrCreateDatabase(Path,null);
        createTable();
    }
    private void createTable()
    {
        String frame_table="create table if not exists  Frame (id integer primary key autoincrement,name text,path text,color text,category text)";
        db.execSQL(frame_table);
    }
    public void insertTable(List<Frame_db_DAO> list)
    {
        for(int i=0;i<list.size();i++)
        {
            ContentValues cValue = new ContentValues();
            cValue.put("name",list.get(i).name);
            cValue.put("path",list.get(i).path);
            cValue.put("color",list.get(i).color);
            cValue.put("category",list.get(i).category);
            db.insert("Frame",null,cValue);
        }
    }
}
