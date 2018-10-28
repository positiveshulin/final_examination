package cn.edu.swufe.reading_records;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText mEditText;
    private ImageView mImageView;
    private ListView mListView;
    private TextView mTextView;
    Context context;
    Cursor cursor;
    private List<HashMap<String, String>> listItems=new ArrayList<HashMap<String, String>>();
    private SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;
        initView();
    }

    private void initView() {
        mTextView =  findViewById(R.id.textview);
        mEditText =findViewById(R.id.edittext);
        //mImageView = findViewById(R.id.imageview);
        mListView = findViewById(R.id.listview);

        //设置删除图片的点击事件
//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //把EditText内容设置为空
//                mEditText.setText("");
//                //把ListView隐藏
//                mListView.setVisibility(View.GONE);
//            }
//        });

        //EditText添加监听
        mEditText.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}//文本改变之前执行

            @Override
            //文本改变的时候执行
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                //如果长度为0
                if (s.length() == 0) {
                    //隐藏“删除”图片
                   // mImageView.setVisibility(View.GONE);
                    mListView.setVisibility(View.GONE);
                } else {//长度不为0
                    //显示“删除图片”
                   // mImageView.setVisibility(View.VISIBLE);
                    //显示ListView
                    showListView();
                }
            }//文本改变之后执行
        });

        mTextView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //如果输入框内容为空，提示请输入搜索内容
                if(TextUtils.isEmpty(mEditText.getText().toString().trim())){
                    Toast.makeText(SearchActivity.this,"请输入您要搜索的内容",Toast.LENGTH_SHORT).show();
                }else {
                    //判断cursor是否为空
                    if (cursor != null) {
                        int columnCount = cursor.getCount();
                        if (columnCount == 0) {
                            Toast.makeText(SearchActivity.this,"对不起，没有您要搜索的内容",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }

    private void showListView() {
        listItems.clear();
        mListView.setVisibility(View.VISIBLE);
        //获得输入的内容
        String str = mEditText.getText().toString();
        //得到cursor
        DBManager dbManager=new DBManager(SearchActivity.this);
        cursor=dbManager.search(str);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            HashMap<String,String> map = new HashMap<String,String>();
            String date = cursor.getString(cursor.getColumnIndex("CURDATE"));
            String time = cursor.getString(cursor.getColumnIndex("CURTIME"));
            String content = cursor.getString(cursor.getColumnIndex("CURCONTENT"));
            map.put("date", date); // 标题文字
            map.put("time", time); // 详情描述
            map.put("content",content);
            listItems.add(map);
            listItemAdapter = new SimpleAdapter(this, listItems, // listItems
                    R.layout.search_item, // ListItem的XML布局实现
                    new String[] { "date", "time" ,"content"},
                    new int[] { R.id.date_search, R.id.time_search,R.id.content_search }
                    //控件和key数据对应
            );
            mListView.setAdapter(listItemAdapter);
            listItemAdapter.notifyDataSetChanged();
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //把cursor移动到指定行
                Log.i("run.......", "onItemClick:mlistview ");
                cursor.moveToPosition(position);
                //String name = cursor.getString(cursor.getColumnIndex("name"));
                Intent writing_activity=new Intent(SearchActivity.this,WritingActivity.class);
                String bookname=cursor.getString(cursor.getColumnIndex("CURBOOKNAME"));
                String time=cursor.getString(cursor.getColumnIndex("CURTIME"));
                String date=cursor.getString(cursor.getColumnIndex("CURDATE"));
                String content=cursor.getString(cursor.getColumnIndex("CURCONTENT"));
                writing_activity.putExtra("bookname_search",bookname);
                writing_activity.putExtra("time_search",time);
                writing_activity.putExtra("date_search",date);
                writing_activity.putExtra("content_search",content);
                writing_activity.putExtra("code_search",6);
                Log.i("run......", "onActivityResult: SA_position_reas="+position);
                //writing_activity.putExtra("position_search",position);cursor和原来列表的值不一样
                startActivityForResult(writing_activity,6);
                finish();
            }
        });
    }
}
