package cn.edu.swufe.reading_records;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RecordsActivity extends AppCompatActivity {
    private List<HashMap<String, String>> listItems=new ArrayList<HashMap<String, String>>();;
    List<RecordsItem> items=new ArrayList<RecordsItem>();
    private SimpleAdapter listItemAdapter;
    HashMap<String, String> map;
    ListView listView;
    int p1;
    RecordsItem recordsItem;
    DBManager dbManager = new DBManager(RecordsActivity.this);
    private final String TAG = "RecordsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        listView=findViewById(R.id.lv_layout);
               //当按下返回键的时候再添加item
         //add_items();
        //initListView();

        listView.setDividerHeight(0);
        //this.setListAdapter(listItemAdapter);
        //getListView().setDividerHeight(0);
        Log.i(TAG, "onCreate: duibuqi"+dbManager.listAll());
        for(RecordsItem item : dbManager.listAll()){
            Log.i(TAG, "onCreate: wozaizheli");
            map= new HashMap<String, String>();
            map.put("date", item.getCurDate()); // 标题文字
            map.put("time", item.getCurTime()); // 详情描述
            map.put("content",item.getCurContent());
            listItems.add(map);
            listItemAdapter = new SimpleAdapter(this, listItems, // listItems
                    R.layout.specific_item, // ListItem的XML布局实现
                    new String[] { "date", "time" ,"content"},
                    new int[] { R.id.tv_date, R.id.tv_time,R.id.tv_content }
                    //控件和key数据对应
            );
            Collections.reverse(listItems);
            listView.setAdapter(listItemAdapter);
            listItemAdapter.notifyDataSetChanged();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent writing_activity=new Intent(RecordsActivity.this,WritingActivity.class);
                p1=position;
                HashMap<String, String> object=(HashMap<String, String>)listView.getItemAtPosition(position);
                writing_activity.putExtra("original_item",object);
                writing_activity.putExtra("date",object.get("date"));
                writing_activity.putExtra("time",object.get("time"));
                writing_activity.putExtra("content",object.get("content"));
                writing_activity.putExtra("code2",4);
                startActivityForResult(writing_activity,4);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(RecordsActivity.this);
                builder.setTitle("温馨提示")
                        .setMessage("确定要删除选中读书笔记吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listItems.remove(position);//删除对应position的数据
                                        listItemAdapter.notifyDataSetChanged();
                                    }
                                }
                        ).setNegativeButton("否",null);
                builder.create().show();
                return true;
            }
        });

    }
    private void add_items(String content){
        //Intent intent=getIntent();
        //String  content= intent.getStringExtra("content");
        Date today= Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm:ss");
        final String todayStr=sdf.format(today);
        final String todayStr2=sdf2.format(today);
         map= new HashMap<String, String>();
        map.put("date", todayStr); // 标题文字
        map.put("time", todayStr2); // 详情描述
        map.put("content",content);
        listItems.add(map);
        dbManager.add(new RecordsItem(todayStr,todayStr2,content));
        Log.i(TAG, "add_items: listall"+dbManager.listAll());
        Collections.reverse(listItems);
        listItemAdapter = new SimpleAdapter(this, listItems, // listItems
                R.layout.specific_item, // ListItem的XML布局实现
                new String[] { "date", "time" ,"content"},
                new int[] { R.id.tv_date, R.id.tv_time,R.id.tv_content }
                //控件和key数据对应
        );
        listView.setAdapter(listItemAdapter);
        listItemAdapter.notifyDataSetChanged();
    }
    private void initListView(){
      listItems=new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("date", "date：" + i); // 标题文字
            map.put("time", "time" + i); // 详情描述
            map.put("content","content"+i+"timetimetimetimetimetimetimetimetimetimetimetimetimetimetimetime");
            //key不能重复
            listItems.add(map);
            //往list列表里面放入map数据
        }
// 生成适配器的Item和动态数组对应的元素
            listItemAdapter = new SimpleAdapter(this, listItems, // listItems
                R.layout.specific_item, // ListItem的XML布局实现
                new String[] { "date", "time" ,"content"},
                new int[] { R.id.tv_date, R.id.tv_time,R.id.tv_content }
                //控件和key数据对应
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_records, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.it_add){
            Intent writing_activity2 = new Intent(this, WritingActivity.class);
            writing_activity2.putExtra("code1",2);
            startActivityForResult(writing_activity2,2);

            //add_items();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==2){
            String  content= data.getStringExtra("content");
            add_items(content);
        }
        if(resultCode==4){
            //更新
            HashMap<String, String> map2=(HashMap<String, String>)listView.getItemAtPosition(p1);
            map2.put("content",data.getStringExtra("content2"));
            //listItems.add(map2);
            listView.setAdapter(listItemAdapter);
            listItemAdapter.notifyDataSetChanged();
            dbManager.update(new RecordsItem("null","null",data.getStringExtra("content2")));
        }
    }
}
