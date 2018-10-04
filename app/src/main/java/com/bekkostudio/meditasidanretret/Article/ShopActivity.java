package com.bekkostudio.meditasidanretret.Article;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.bekkostudio.compactWebview.DefaultSetting;
import com.bekkostudio.compactWebview.SmartWebViewCompact;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    SmartWebViewCompact smartWebViewCompact = new SmartWebViewCompact();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        smartWebViewCompact.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_shop);
//
//
//        List<HashMap<String, String>> aList = new ArrayList<>();
//
//        for (int i = 0; i < Global.shopTitle.length; i++) {
//            HashMap<String, String> hm = new HashMap<>();
//            hm.put("listview_title", Global.shopTitle[i]);
//            hm.put("listview_price", Global.listviewShopPrice[i]);
//            hm.put("listview_image", Integer.toString(Global.shopThumbnail[i]));
//            aList.add(hm);
//        }
//
//        String[] from = {"listview_image", "listview_title", "listview_price"};
//        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_price};
//
//        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_course_shop_list, from, to);
//        ListView androidListView = findViewById(R.id.listShop);
//        androidListView.setAdapter(simpleAdapter);
//
//        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(ShopActivity.this, ShopContentActivity.class);
//                intent.putExtra("contentIndex", position);
//                startActivity(intent);
//            }
//        });


        //Permission variables
        smartWebViewCompact.ASWP_JSCRIPT     = true;     //enable JavaScript for webview
        smartWebViewCompact.ASWP_FUPLOAD     = true;     //upload file from webview
        smartWebViewCompact.ASWP_CAMUPLOAD   = true;     //enable upload from camera for photos
        smartWebViewCompact.ASWP_ONLYCAM        = false;	//incase you want only camera files to upload
        smartWebViewCompact.ASWP_MULFILE     = false;    //upload multiple files in webview
        smartWebViewCompact.ASWP_LOCATION    = false;     //track GPS locations
        smartWebViewCompact.ASWP_RATINGS     = false;     //show ratings dialog; auto configured, edit method get_rating() for customizations
        smartWebViewCompact.ASWP_PBAR        = true;     //show progress bar in app
        smartWebViewCompact.ASWP_ZOOM        = false;    //zoom control for webpages view
        smartWebViewCompact.ASWP_SFORM       = true;    //save form cache and auto-fill information
        smartWebViewCompact.ASWP_OFFLINE     = false;    //whether the loading webpages are offline or online
        smartWebViewCompact.ASWP_EXTURL      = false;     //open external url with default browser instead of app webview
        smartWebViewCompact.ASWP_ROOT        = false;     //change it to false if you need to open webview in other intent activity

        //Configuration variables
        smartWebViewCompact.ASWV_URL          = "https://meditasi123.blogspot.com/search/label/olshop"; //complete URL of your website or webpage
        smartWebViewCompact.ASWV_F_TYPE       = "*/*";  //to upload any file type using "*/*"; check file type references for more

        //Rating system variables
        DefaultSetting.ASWR_DAYS            = 3;        //after how many days of usage would you like to show the dialoge
        DefaultSetting.ASWR_TIMES           = 10;       //overall request launch times being ignored
        DefaultSetting.ASWR_INTERVAL        = 2;        //reminding users to rate after days interval

        smartWebViewCompact.onCreate(this,(WebView) findViewById(R.id.msw_view),(ProgressBar) findViewById(R.id.msw_progress));
    }

    @Override
    public void onBackPressed() {
        smartWebViewCompact.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState ){
        super.onSaveInstanceState(outState);
        smartWebViewCompact.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        smartWebViewCompact.onRestoreInstanceState(savedInstanceState);
    }
}
