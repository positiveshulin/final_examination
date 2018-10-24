package cn.edu.swufe.reading_records;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordsActivity extends AppCompatActivity {
    private List<HashMap<String, String>> listItems;
    private SimpleAdapter listItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        ListView listView=findViewById(R.id.lv_layout);
        initListView();
        listView.setAdapter(listItemAdapter);
        listView.setDividerHeight(0);
        //this.setListAdapter(listItemAdapter);
        //getListView().setDividerHeight(0);


    }
    private void initListView(){
      listItems=new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("date", "date：" + i); // 标题文字
            map.put("time", "time" + i); // 详情描述
            map.put("content","content"+i+"timetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetimetime");
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
            Intent list = new Intent(this, WritingActivity.class);
            startActivity(list);
        }
        return super.onOptionsItemSelected(item);
    }
}
