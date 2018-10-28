package cn.edu.swufe.reading_records;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WritingActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        button=findViewById(R.id.btn);
        textView=findViewById(R.id.add_time);
        editText=findViewById(R.id.content);
        final Intent intent=getIntent();
        int code1=intent.getIntExtra("code1",0);
        int code2=intent.getIntExtra("code2",0);
        int code_search=intent.getIntExtra("code_search",0);
        Log.i("code_search", "on "+code_search);
        if(code1==2){
            Log.i("look", "onActivityResult: requestcode2");
            Date today= Calendar.getInstance().getTime();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            final String todayStr=sdf.format(today);
            //TextView textView=findViewById(R.id.add_time);
            textView.setText(todayStr);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //返回和保存功能
                    Intent intent=new Intent(WritingActivity.this,RecordsActivity.class);
                    //Bundle bundle=new Bundle();
                    intent.putExtra("content",editText.getText().toString());
                    //startActivity(intent);
                    setResult(2,intent);
                    finish();
                }
            });
        }
        if(code2==4){
            if (intent != null) {
                textView.setText(intent.getStringExtra("date")+" "+intent.getStringExtra("time"));
                editText.setText(intent.getStringExtra("content"));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //返回和保存功能
                        Intent intent2=new Intent(WritingActivity.this,RecordsActivity.class);
                        //Bundle bundle=new Bundle();
                        intent2.putExtra("content2",editText.getText().toString());
                        //startActivity(intent);
                        setResult(4,intent2);
                        finish();
                    }
                });
            }
        }
        if(code_search==6){
            Log.i("code_search", "code=666: run... ");
            final String bookname_search=intent.getStringExtra("bookname_search");
             final String date_search=intent.getStringExtra("date_search");
             final String time_search=intent.getStringExtra("time_search");
            // final int position_research=intent.getIntExtra("position_research",0);
            textView.setText(date_search+" "+time_search);
            editText.setText(intent.getStringExtra("content_search"));
            Log.i("", "onClick: run button 3");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //返回和保存功能
                    Log.i("", "onClick: run button 4");
                    Intent intent3=new Intent(WritingActivity.this,RecordsActivity.class);
                    //Bundle bundle=new Bundle();
                    intent3.putExtra("bookname2",bookname_search);
                    intent3.putExtra("content2",editText.getText().toString());
                    intent3.putExtra("date2",date_search);
                    intent3.putExtra("time2",time_search);
                    intent3.putExtra("resultCode3",6);
                    Log.i("", "onClick: run button 5");
                    startActivityForResult(intent3,6);
                    Log.i("", "onClick: run button 6");
                    //setResult(6,intent3);
                    finish();
                }
            });
        }
    }


}
