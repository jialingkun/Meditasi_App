package com.bekkostudio.meditasidanretret;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;


public class ProgressActivity extends AppCompatActivity {
    TextView tvTitle, tvMood, tvMedicine;
    Spinner spProgress;
    ArrayAdapter adapterProgress;
    LineChartView progressChart;
    List yMoodAxisValue, dateAxisValue, yMedicineAxisValue;
    ArrayList<String> moodDate, meditasiDate;
    ArrayList<Integer> moodValue, medicineValue, durationValue;

    List dateMeditasi, yDuration;


    String[] point = {"0","1", "2", "3", "4", "5"};
    int[] point1 = {0,1,2,3,4,5};
    int[] zAxisData = {5, 4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
 
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMood = (TextView) findViewById(R.id.tvMood);
        tvMedicine = (TextView) findViewById(R.id.tvMedicine);

        spProgress = (Spinner) findViewById(R.id.spProgress);
        progressChart = (LineChartView) findViewById(R.id.progress_chart);

        adapterProgress = ArrayAdapter.createFromResource(this, R.array.metode_progressChart, android.R.layout.simple_spinner_item);
        adapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spProgress.setAdapter(adapterProgress);
        spProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Mood")) {
                    tvTitle.setText("Progress Mood");
                    int size = Global.moods.size();
                    yMoodAxisValue = new ArrayList();
                    dateAxisValue = new ArrayList();
                    yMedicineAxisValue = new ArrayList<>();
                    Line line = new Line(yMoodAxisValue).setColor(Color.parseColor("#e60000"));
                    Line line1 = new Line(yMedicineAxisValue).setColor(Color.parseColor("#ffff00"));

                    moodDate = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        moodDate.add(Global.moods.get(i).date);
                        dateAxisValue.add(i, new AxisValue(i).setLabel(moodDate.get(i)));
                    }
                    moodValue = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        moodValue.add(Global.moods.get(i).moodValue);
                        yMoodAxisValue.add(new PointValue(point1[i], moodValue.get(i)));
                    }

                    medicineValue = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        medicineValue.add(Global.moods.get(i).medicineValue);
                        yMedicineAxisValue.add(new PointValue(point1[i], medicineValue.get(i)));
                    }

                    List lines = new ArrayList();
                    lines.add(line);
                    lines.add(line1);

                    LineChartData data = new LineChartData();
                    data.setLines(lines);

                    Axis axis = new Axis();
                    axis.setValues(dateAxisValue);
                    axis.setTextSize(16);
                    axis.setTextColor(Color.parseColor("#03A9F4"));
                    data.setAxisXBottom(axis);

                    Axis yAxis = new Axis();
                    yAxis.setName("Mood");
                    yAxis.setTextColor(Color.parseColor("#03A9F4"));
                    yAxis.setTextSize(16);
                    data.setAxisYLeft(yAxis);

                    progressChart.setLineChartData(data);
                    tvMood.setText("Mood");
                    tvMedicine.setText("Medicine");

                } else {
                    tvTitle.setText("Progress Meditasi");
                    int size = Global.durations.size();
                   /* dateMeditasi = new ArrayList();
                    yDuration = new ArrayList();
                    Line line = new Line(yDuration).setColor(Color.parseColor("#e60000"));

                    meditasiDate = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        meditasiDate.add(Global.durations.get(i).date);
                        dateMeditasi.add(i, new AxisValue(i).setLabel(meditasiDate.get(i)));
                    }
                    durationValue = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        durationValue.add(Global.durations.get(i).duration);
                        yDuration.add(new PointValue(point1[i], durationValue.get(i)));
                    }

                    List lines = new ArrayList();
                    lines.add(line);

                    LineChartData data = new LineChartData();
                    data.setLines(lines);

                    Axis axis = new Axis();
                    axis.setValues(dateMeditasi);
                    axis.setTextSize(16);
                    axis.setTextColor(Color.parseColor("#03A9F4"));
                    data.setAxisXBottom(axis);

                    Axis yAxis = new Axis();
                    yAxis.setName("duration");
                    yAxis.setTextColor(Color.parseColor("#03A9F4"));
                    yAxis.setTextSize(16);
                    data.setAxisYLeft(yAxis);

                    progressChart.setLineChartData(data);
                    */tvMood.setText(null);
                    tvMedicine.setText(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
