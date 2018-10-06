package com.bekkostudio.meditasidanretret.Article;

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

public class TutorialActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_tutorial);

        List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < Global.videoTitle.length; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("listview_title", Global.videoTitle[i]);
            hm.put("listview_discription", Global.listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(Global.videoThumbnail[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_course_tutorial_list, from, to);
        ListView androidListView = findViewById(R.id.listTutorial);
        androidListView.setAdapter(simpleAdapter);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TutorialActivity.this, TutorialContentActivity.class);
                intent.putExtra("contentIndex", position);
                startActivity(intent);
            }
        });
    }
}
