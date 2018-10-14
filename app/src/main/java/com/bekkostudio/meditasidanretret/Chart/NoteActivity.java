package com.bekkostudio.meditasidanretret.Chart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bekkostudio.meditasidanretret.Article.TutorialActivity;
import com.bekkostudio.meditasidanretret.Article.TutorialContentActivity;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_note);

        List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < Global.notes.size(); i++) {
            HashMap<String, String> hm = new HashMap<>();

            //date
            hm.put("date", Global.notes.get(i).date.split(",")[1]);

            //important
            if (Global.notes.get(i).important){
                hm.put("important", "PENTING!");
            }else{
                hm.put("important", "");
            }

            //text
            hm.put("text", Global.notes.get(i).noteValue);

            aList.add(hm);
        }

        String[] from = {"date", "important", "text"};
        int[] to = {R.id.date, R.id.important, R.id.text};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_progress_note_list, from, to);
        ListView androidListView = findViewById(R.id.listNote);
        androidListView.setAdapter(simpleAdapter);
    }
}
