package com.example.administrator.miniweather;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.miniweather.app.MyApplication;
import com.example.administrator.miniweather.bean.City;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrew on 2016/10/18.
 */

public class SelectCity extends Activity implements View.OnClickListener {
    private ImageView mBackBtn;
    private TextView CurrentTitle;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String selectedID;
    private String selectedCityname;

    private  MyApplication mapplication1;

    private ListView mlistView;

    private EditText mEditText;

    private final List <String> data= new ArrayList<>(); //用于存放城市名称
    private final List <String> cityID = new ArrayList<>();      //用于存放城市ID
    private final List <String> data1 = new ArrayList<>();//用于存放城市名称备份
    private final List <String> cityID1 = new ArrayList<>();//用于存放城市ID备份
    private final List <String> cityallpy = new ArrayList<>();
    private final List <String> cityfirstpy= new ArrayList<>();

    private ArrayAdapter <String> adapter2;

    private boolean edited = false;


    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            temp = charSequence;

            Log.d("myapp", "beforeTextChanged:" + temp);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }
        @Override
        public void afterTextChanged(Editable editable) {
            if(editable != null) {
                search(editable.toString());
            }
            else
            {
                data.clear();
                cityID.clear();
                data.addAll(data1);
                cityID.addAll(cityID1);
                adapter2.notifyDataSetChanged();

            }


            Log.d("myapp","afterTextChanged:") ;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
        init();
        mEditText.addTextChangedListener(mTextWatcher);
        mBackBtn.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Iterator <City> it = mapplication1.getmCityList().iterator();
        while(it.hasNext())
        {
            City tmp = it.next();
            String cityname1 = tmp.getCity();
            String cityid1 = tmp.getNumber();
            String cityallpy1 = tmp.getAllPY();
            String cityfirstpy1 = tmp.getAllFristPY();

            Log.d("pinyin",cityfirstpy1);
            data.add(cityname1);
            data1.add(cityname1);
            cityID.add(cityid1);
            cityID1.add(cityid1);
            cityallpy.add(cityallpy1);
            cityfirstpy.add(cityfirstpy1);
        }
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1
        ,data);
        mlistView.setAdapter(adapter2);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(SelectCity.this, "你单击了:"+i, Toast.LENGTH_SHORT).show();

                    selectedID = cityID.get(i);
                    selectedCityname = data.get(i);
                    CurrentTitle = (TextView) findViewById(R.id.title_name);
                    CurrentTitle.setText("当前城市：" + selectedCityname);

            }
        });
    }
    void init()
    {
        mapplication1 = (MyApplication) getApplication();
        mEditText = (EditText)findViewById(R.id.search_edit);
        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mlistView = (ListView) findViewById(R.id.list_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                Intent i = new Intent();
                i.putExtra("cityCode", selectedID);
                setResult(RESULT_OK, i);
                finish();
            default:
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SelectCity Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    public void search(String name){
        data.clear();
        cityID.clear();
        if (name.length()==0)
        {
            data.addAll(data1);
            cityID.addAll(cityID1);
        }
        else if(Character.isLetter(name.charAt(0))) {
                name=name.toUpperCase();
                Pattern pattern = Pattern.compile(name);
                for (int i = 0; i < cityallpy.size(); i++) {
                    Matcher matcher = pattern.matcher((String) cityallpy.get(i));
                    if (matcher.find()) {
                        data.add(data1.get(i));
                        cityID.add(cityID1.get(i));
                        Log.d("selectcity", data1.get(i));

                    }

                }
                for (int i = 0; i < cityfirstpy.size(); i++) {
                    Matcher matcher = pattern.matcher((String) cityfirstpy.get(i));
                    if (matcher.find()) {
                        data.add(data1.get(i));
                        cityID.add(cityID1.get(i));
                    }
                }
            }

                Pattern pattern = Pattern.compile(name);

                for (int i = 0; i < data1.size(); i++) {
                    Matcher matcher = pattern.matcher((String) data1.get(i));
                    if (matcher.find()) {
                        data.add(data1.get(i));
                        cityID.add(cityID1.get(i));
                    }
                }



        adapter2.notifyDataSetChanged();
    }
}
