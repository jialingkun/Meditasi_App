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
    TextView tvTitle, tvMood, tvMedicine, tvText;
    Spinner spProgress;
    ArrayAdapter adapterProgress;
    LineChartView progressChart;
    List yMoodAxisValue, dateAxisValue, yMedicineAxisValue;
    ArrayList<String> moodDate, meditasiDate;
    ArrayList<Integer> moodValue, medicineValue, durationValue;
    List dateMeditasi, yDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
 
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvText = (TextView) findViewById(R.id.tvText);
        tvMood = (TextView) findViewById(R.id.tvMood);
        tvMedicine = (TextView) findViewById(R.id.tvMedicine);

        spProgress = (Spinner) findViewById(R.id.spProgress);
        progressChart = (LineChartView) findViewById(R.id.progress_chart);

        adapterProgress = ArrayAdapter.createFromResource(this, R.array.metode_progressChart, android.R.layout.simple_spinner_item);
        adapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spProgress.setAdapter(adapterProgress);

        //set spinner Mood or Meditasi
        spProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Mood")) {
                    tvTitle.setText("Progress Mood");
                    int size = Global.moods.size();
                    if (size > 1) {
                        //Hold data mood, date, medicine
                        yMoodAxisValue = new ArrayList();
                        dateAxisValue = new ArrayList();
                        yMedicineAxisValue = new ArrayList<>();

                        //line will hold data value mood, medicine
                        Line line = new Line(yMoodAxisValue).setColor(Color.parseColor("#e60000"));
                        Line line1 = new Line(yMedicineAxisValue).setColor(Color.parseColor("#ffff00"));

                        //moodDate to hold data from Global mood date
                        moodDate = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            moodDate.add(Global.moods.get(i).date);
                            dateAxisValue.add(i, new AxisValue(i).setLabel(moodDate.get(i)));
                        }

                        //hold data value from moodValue in Global
                        moodValue = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            moodValue.add(Global.moods.get(i).moodValue);
                            yMoodAxisValue.add(new PointValue(i, moodValue.get(i)));
                        }

                        //hold data value from medicineValue in Global
                        medicineValue = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            medicineValue.add(Global.moods.get(i).medicineValue);
                            yMedicineAxisValue.add(new PointValue(i, medicineValue.get(i)));
                        }

                        //hold the line of the graph chart
                        List lines = new ArrayList();
                        lines.add(line);
                        lines.add(line1);

                        //add the graph line to the overall data chart
                        LineChartData data = new LineChartData();
                        data.setLines(lines);

                        //show value date in line graph chart
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

                        //set data in the Chart
                        progressChart.setLineChartData(data);

                        tvMood.setText("Mood");
                        tvMedicine.setText("Medicine");
                    } else {
                        tvText.setText("Data kurang");
                        progressChart.setLineChartData(null);
                        tvMood.setText(null);
                        tvMedicine.setText(null);
                    }


                } else {
                    tvTitle.setText("Progress Meditasi");
                    int size = Global.durations.size();
                    ArrayList<String> dateArray = new ArrayList();
                    ArrayList<Integer> durasi = new ArrayList<>();
                    int totalDurasi = 0;

                    //Calculate total duration & insert data total and date in the dateArray and durasi
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            if (i != size-1) {
                                if (Global.durations.get(i).date.equals(Global.durations.get(i + 1).date)) {
                                    totalDurasi = totalDurasi + Global.durations.get(i).duration;
                                }
                                else {
                                    totalDurasi = totalDurasi + Global.durations.get(i).duration;
                                    dateArray.add(Global.durations.get(i).date);
                                    durasi.add(totalDurasi);
                                    totalDurasi = 0;
                                }
                            }
                            else {
                                totalDurasi = totalDurasi + Global.durations.get(i).duration;
                                dateArray.add(Global.durations.get(i).date);
                                durasi.add(totalDurasi);
                            }
                        }
                    }

                    if (size > 1) {
                        dateMeditasi = new ArrayList();
                        yDuration = new ArrayList();
                        Line line = new Line(yDuration).setColor(Color.parseColor("#e60000"));

                        meditasiDate = new ArrayList<>();
                        for (int i = 0; i < dateArray.size(); i++) {
                            meditasiDate.add(dateArray.get(i));
                            dateMeditasi.add(i, new AxisValue(i).setLabel(meditasiDate.get(i)));
                        }
                        durationValue = new ArrayList<>();
                        for (int i = 0; i < durasi.size(); i++) {
                            durationValue.add(durasi.get(i));
                            yDuration.add(new PointValue(i, durationValue.get(i)));
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

                        tvMood.setText(null);
                        tvMedicine.setText(null);
                    } else {
                        tvText.setText("Data kurang");
                        progressChart.setLineChartData(null);
                        tvMood.setText(null);
                        tvMedicine.setText(null);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
