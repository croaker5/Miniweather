package com.example.administrator.miniweather;
import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Andrew on 2016/10/18.
 */

public class SelectCity extends Activity implements View.OnClickListener {
    private ImageView mBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_back:
                finish();
            default:
                break;
        }
    }
}