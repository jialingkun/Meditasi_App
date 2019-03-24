package com.bekkostudio.meditasidanretret.Course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Article.TutorialActivity;
import com.bekkostudio.meditasidanretret.Article.TutorialContentActivity;
import com.bekkostudio.meditasidanretret.CenteringHorizontalScrollView;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseFragmentList extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_course_tutorial, container, false);

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

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), aList, R.layout.activity_course_tutorial_list, from, to);
        ListView androidListView = view.findViewById(R.id.listTutorial);
        androidListView.setAdapter(simpleAdapter);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TutorialContentActivity.class);
                intent.putExtra("contentIndex", position);
                startActivity(intent);
            }
        });


        return view;
    }
}
