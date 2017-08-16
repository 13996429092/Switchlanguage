package com.xiaowenshuma.switchlanguage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences PREF;
    private SharedPreferences.Editor EDITOR;
    private View item_china;
    private View item_english;
    private TextView tv_swich;
    private static String  languge="locale.CHINESE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checklanguge();
        setContentView(R.layout.activity_main);
        ActionBar actionbar = getSupportActionBar();
        if (!actionbar.equals(null)) {
            actionbar.hide();
        }
        initview();
    }

    private void checklanguge() {
        PREF= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isexist=PREF.getBoolean("isexist",false);
        if(isexist){
            languge=PREF.getString("languge","");
            if("".equals(languge)){

            }
            if("locale.ENGLISH".equals(languge)){
                switchLanguage(Locale.ENGLISH);


            }
            if("locale.CHINESE".equals(languge)){
                switchLanguage(Locale.CHINESE);

            }

        }
    }

    private void initview() {
        tv_swich = (TextView) findViewById(R.id.swich_languge);
        tv_swich.setOnClickListener(this);
        TextView tv_content=(TextView)findViewById(R.id.tv_content);
        Button bt_tz= (Button) findViewById(R.id.bt_tz);
        bt_tz.setOnClickListener(this);
        item_china = findViewById(R.id.item_china);
        item_english = findViewById(R.id.item_english);
        registerForContextMenu(tv_swich);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.swich_languge:

                menu.add(Menu.NONE, 0, Menu.NONE, "中文");
                menu.add(Menu.NONE, 1, Menu.NONE, "english");
                break;

        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        EDITOR=PREF.edit();

        switch (item.getItemId()) {

            case 0:
                EDITOR.putString("languge","locale.CHINESE");
                EDITOR.putBoolean("isexist",true);
                EDITOR.apply();
                checklanguge();
                break;
            case 1:
                EDITOR.putString("languge","locale.ENGLISH");
                EDITOR.putBoolean("isexist",true);
                EDITOR.apply();
                checklanguge();
                break;
        }

        return super.onContextItemSelected(item);

    }


    @Override
    public void onClick(View view) {

            switch (view.getId()) {
                case R.id.swich_languge:
                    view.showContextMenu();
                    break;

            case R.id.bt_tz:
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                break;
        }
    }
    private void switchLanguage(Locale locale) {
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        DisplayMetrics dm = res.getDisplayMetrics();
        Locale currentLocal = config.locale;
        config.locale = locale;
        res.updateConfiguration(config, dm);

        // 如果切换了语言
        if (!currentLocal.equals(config.locale)) {
            // 这里需要重新刷新当前页面中使用到的资源
            MainActivity.this.finish();
            MainActivity.this.startActivity(getIntent());
        }
    }

}


