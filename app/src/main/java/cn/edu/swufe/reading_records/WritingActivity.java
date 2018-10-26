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
        Intent intent=getIntent();
        int code1=intent.getIntExtra("code1",0);
        int code2=intent.getIntExtra("code2",0);
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

    }
}
