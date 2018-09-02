package com.bekkostudio.meditasidanretret.Course;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_shop);


        List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < Global.shopTitle.length; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("listview_title", Global.shopTitle[i]);
            hm.put("listview_price", Global.listviewShopPrice[i]);
            hm.put("listview_image", Integer.toString(Global.shopThumbnail[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_price"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_price};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_course_shop_list, from, to);
        ListView androidListView = findViewById(R.id.listShop);
        androidListView.setAdapter(simpleAdapter);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShopActivity.this, ShopContentActivity.class);
                intent.putExtra("contentIndex", position);
                startActivity(intent);
            }
        });
    }
}
