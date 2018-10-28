package cn.edu.swufe.reading_records;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BookshelfActivity extends AppCompatActivity{
    private List<HashMap<String, String>> listItems=new ArrayList<HashMap<String, String>>();
    List<RecordsItem> items=new ArrayList<RecordsItem>();
    private SimpleAdapter listItemAdapter;
    HashMap<String, String> map;
    GridView gridView;
    TextView title;
    ArrayAdapter adapter;
    List<String> data=new ArrayList<String>();
    private String TAG="mylist";
    String input;
    RecordsItem recordsItem;
    DBManager dbManager = new DBManager(BookshelfActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);
        gridView=findViewById(R.id.bookshelf);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "wozhixingl");
                HashMap<String, String> object = (HashMap<String, String>) gridView.getItemAtPosition(position);
                Intent intent = new Intent(BookshelfActivity.this, RecordsActivity.class);
                intent.putExtra("bookname",object.get("bookName"));
                Log.i(TAG, "onItemClick: input="+object.get("bookName"));
                startActivityForResult(intent,7);
                //startActivity(intent);//有
                Log.i(TAG, "start finish");
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookshelfActivity.this);
                builder.setTitle("温馨提示")
                        .setMessage("确定要删除选中读书笔记吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HashMap<String, String> map3 = (HashMap<String, String>) gridView.getItemAtPosition(position);
                                        String bookname= map3.get("bookName");
                                        Log.i(TAG, "onClick: bookname"+bookname);
                                        listItems.remove(position);//删除对应position的数据
                                        listItemAdapter.notifyDataSetChanged();
                                        dbManager.deleteBook(bookname);
                                    }
                                }
                        ).setNegativeButton("否", null);
                builder.create().show();
                return true;
            }
        });

        for (RecordsItem item : dbManager.listAllBook()) {
            Log.i(TAG, "onCreate: wozaizheli");
            map = new HashMap<String, String>();
            map.put("bookName", item.getCurBookName()); // 标题文字
            listItems.add(map);
            listItemAdapter = new SimpleAdapter(this, listItems, // listItems
                    R.layout.book_item, // ListItem的XML布局实现
                    new String[]{ "bookName"},
                    new int[]{ R.id.book_name}
                    //控件和key数据对应
            );
            Collections.reverse(listItems);
            gridView.setAdapter(listItemAdapter);
            listItemAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if (item.getItemId() == R.id.add_book) {
           // Log.i(TAG, "onItemLongClick: long is running and position="+position);
            //构造对话框进行删除数据的确认操作
            final EditText editText=new EditText(this);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("请输入书籍名称")
                    .setView(editText)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    input=editText.getText().toString();
                                    if(input.equals("")){
                                        Toast.makeText(getApplicationContext(), "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        add_book(input);
                                    }
                                }
                            }
                    ).setNegativeButton("取消",null);
            builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void add_book(String bookname){
            map = new HashMap<String, String>();
           // map.put("bookCover", content); // 标题文字
            map.put("bookName", bookname); // 详情描述
            listItems.add(map);
            listItemAdapter = new SimpleAdapter(this, listItems, // listItems
                    R.layout.book_item, // ListItem的XML布局实现
                    new String[]{ "bookName"},
                    new int[]{R.id.book_name}
                    //控件和key数据对应
            );
            gridView.setAdapter(listItemAdapter);
            listItemAdapter.notifyDataSetChanged();
            Collections.reverse(listItems);
            //要用另外一张表存储
            dbManager.book_add(bookname);
    }
}
