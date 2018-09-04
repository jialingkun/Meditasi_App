package com.bekkostudio.meditasidanretret.Course.Retret;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Course.TutorialActivity;
import com.bekkostudio.meditasidanretret.Course.TutorialContentActivity;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimelineActivity extends AppCompatActivity {
    RetretDays[] retretDays;
    public int activeIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_retret_timeline);

        //Title
        TextView title = findViewById(R.id.title);
        title.setText(Global.courseRetret.get(Global.activeRetretId).title);

        //Title
        TextView description = findViewById(R.id.description);
        description.setText(Global.courseRetret.get(Global.activeRetretId).description);




        List<HashMap<String, String>> aList = new ArrayList<>();
        retretDays = Global.courseRetret.get(Global.activeRetretId).retretDays;

        //find active index
        int datediff = Global.checkEndDateDifference();
        if (datediff>=0){
            activeIndex = retretDays.length - datediff - 1;
//            Log.d("active index", "onCreate: "+activeIndex);
//            Log.d("date diff", "onCreate: "+datediff);
        }

        for (int i = 0; i < retretDays.length; i++) {
            HashMap<String, String> hm = new HashMap<>();

            String[] dayAndDate = Global.calculateRetretDaysDate(i).split(",");
            hm.put("listview_dayofweek", dayAndDate[0]);
            hm.put("listview_date", dayAndDate[1]);

            int duration = retretDays[i].morningDuration + retretDays[i].nightDuration;
            hm.put("listview_duration", Global.formatMinutesToHoursMinutes(duration));

            aList.add(hm);
        }

        String[] from = {"listview_dayofweek", "listview_date", "listview_duration"};
        int[] to = {R.id.DayOfWeek, R.id.Date, R.id.Duration};

        CustomAdapter simpleAdapter = new CustomAdapter(getBaseContext(), aList, R.layout.activity_course_retret_timeline_list, from, to);
        ListView androidListView = findViewById(R.id.listDays);
        androidListView.setAdapter(simpleAdapter);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == activeIndex){
                    Intent intent = new Intent(TimelineActivity.this, TimelineContentActivity.class);
                    intent.putExtra("contentIndex", position);
                    startActivity(intent);
                }
            }
        });
    }


    public class CustomAdapter extends SimpleAdapter{
        public CustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if (position == activeIndex){
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }else{
                view.setBackgroundColor(Color.parseColor("#D2D7D3"));
            }
            return view;
        }
    }
}
