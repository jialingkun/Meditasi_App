package com.bekkostudio.meditasidanretret.Chart;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class ProgressFragment extends Fragment {
    TextView tvTitleMood, tvMood, tvMedicine, tvTextMood, tvTextMeditasi, tvTitleMeditasi;
    EditText edtTglAwal, edtTglAkhir;
    Button btnFilter;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatFilter;
    Spinner spProgress;
    ArrayAdapter adapterProgress;
    LineChartView progressChartMood, progressChartMeditasi;
    List yMoodAxisValue, dateAxisValue, yMedicineAxisValue;
    ArrayList<String> moodDate;
    ArrayList<String> meditasiDate = new ArrayList<>();
    ArrayList<Integer> moodValue;
    ArrayList<Integer> medicineValue;
    ArrayList<Integer> durationValue = new ArrayList<>();
    List dateMeditasi, yDuration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_progress_fragment, container, false);

        dateFormatFilter = new SimpleDateFormat("dd-MM-yyyy");

        tvTitleMood = (TextView) view.findViewById(R.id.tvTitleMood);
        tvTextMood = (TextView) view.findViewById(R.id.tvTextMood);
        tvMood = (TextView) view.findViewById(R.id.tvMood);
        tvMedicine = (TextView) view.findViewById(R.id.tvMedicine);
        tvTextMeditasi = (TextView) view.findViewById(R.id.tvTextMeditasi);
        tvTitleMeditasi = (TextView) view.findViewById(R.id.tvTitleMeditasi);

        edtTglAwal = (EditText) view.findViewById(R.id.edtTglAwal);
        edtTglAkhir = (EditText) view.findViewById(R.id.edtTglAkhir);
        edtTglAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogAwal();
            }
        });
        edtTglAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogAkhir();
            }
        });

        btnFilter = (Button) view.findViewById(R.id.btnFilter) ;
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        spProgress = (Spinner) view.findViewById(R.id.spProgress);
        progressChartMood = (LineChartView) view.findViewById(R.id.progress_chartMood);
        progressChartMeditasi = (LineChartView) view.findViewById(R.id.progress_chartMeditasi);

        adapterProgress = ArrayAdapter.createFromResource(getActivity(), R.array.metode_progressChart, android.R.layout.simple_spinner_item);
        adapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spProgress.setAdapter(adapterProgress);

        //set spinner Mood or Meditasi
        spProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Progress")) {


                } else if(parent.getItemAtPosition(position).toString().equals("Catatan")) {
                    Intent intent = new Intent(getActivity(), NoteActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //set default date
        Date todayDate = new Date();
        edtTglAkhir.setText(dateFormatFilter.format(todayDate));
        edtTglAwal.setText(dateFormatFilter.format(Global.substractDateByDays(todayDate,10)));


        //show chart
        LineChartMood();
        LineChartMeditasi();



        return view;
    }

    private void showDateDialogAwal() {
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(dateFormatFilter.parse(edtTglAwal.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                String tglAwal = dateFormatFilter.format(newDate.getTime());
                edtTglAwal.setText(tglAwal);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showDateDialogAkhir() {
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(dateFormatFilter.parse(edtTglAkhir.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                String tglAkhir = dateFormatFilter.format(newDate.getTime());
                edtTglAkhir.setText(tglAkhir);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void LineChartMood() {

        //Hold data mood, date, medicine
        yMoodAxisValue = new ArrayList();
        dateAxisValue = new ArrayList();
        yMedicineAxisValue = new ArrayList<>();

        moodDate = new ArrayList<>();
        moodValue = new ArrayList<>();
        medicineValue = new ArrayList<>();

        //line will hold data value mood, medicine
        Line line = new Line(yMoodAxisValue).setColor(Color.parseColor("#27ae60"));
        Line line1 = new Line(yMedicineAxisValue).setColor(Color.parseColor("#c0392b"));

        try {
            Date ptAwal = dateFormatFilter.parse(edtTglAwal.getText().toString());
            Date ptAkhir = dateFormatFilter.parse(edtTglAkhir.getText().toString());
            boolean keTglAkhir = false;
            int dayDiff;
            for (int i = 0; i < Global.moods.size(); i++){
                if (!keTglAkhir){
                    dayDiff = (int) Global.getDateDiff(ptAwal, Global.parseDate(Global.moods.get(i).date), TimeUnit.DAYS);
                    if (dayDiff>=0){
                        addMoodData(i);
                        //to data by date akhir
                        keTglAkhir = true;
                    }
                }else{
                    dayDiff = (int) Global.getDateDiff(Global.parseDate(Global.moods.get(i).date), ptAkhir, TimeUnit.DAYS);
                    if (dayDiff>=0){
                        addMoodData(i);
                        //to data by date akhir
                        keTglAkhir = true;
                    }else{
                        break;
                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }




        int size = moodDate.size();
        if (size > 1) {
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
            progressChartMood.setLineChartData(data);

            //set chart data to initialize viewport, otherwise it will be[0,0;0,0]
            //get initialized viewport and change if ranges according to your needs.
            final Viewport v = new Viewport(progressChartMood.getMaximumViewport());
            v.top =10; //example max value
            v.bottom = 0;  //example min value
            progressChartMood.setMaximumViewport(v);
            progressChartMood.setCurrentViewport(v);
            //Optional step: disable viewport recalculations, thanks to this animations will not change viewport automatically.
            progressChartMood.setViewportCalculationEnabled(false);

        } else {
            tvTextMood.setText("Data kurang");
            progressChartMood.setLineChartData(null);
        }
    }


    private void addMoodData(int i){
        //add date
        moodDate.add(Global.newFormatDate(Global.moods.get(i).date));
        int dataIndex = moodDate.size()-1;
        dateAxisValue.add(new AxisValue(dataIndex).setLabel(moodDate.get(dataIndex)));
        //add value
        moodValue.add(Global.moods.get(i).moodValue);
        yMoodAxisValue.add(new PointValue(dataIndex, Global.moods.get(i).moodValue));
        //add medicine value
        medicineValue.add(Global.moods.get(i).medicineValue);
        yMedicineAxisValue.add(new PointValue(dataIndex, Global.moods.get(i).medicineValue));
    }

    private void LineChartMeditasi () {
        //MEDITASI
        int sizeMeditasi = Global.durations.size();
        ArrayList<String> dateArray = new ArrayList();
        ArrayList<Integer> durasi = new ArrayList<>();
        int totalDurasi = 0;

        //Calculate total duration & insert data total and date in the dateArray and durasi
        if (sizeMeditasi > 0) {
            for (int i = 0; i < sizeMeditasi; i++) {
                if (i != sizeMeditasi-1) {
                    if (Global.durations.get(i).date.equals(Global.durations.get(i + 1).date)) {
                        totalDurasi = totalDurasi + Global.durations.get(i).duration;
                    }
                    else {
                        totalDurasi = totalDurasi + Global.durations.get(i).duration;
                        dateArray.add(Global.newFormatDate(Global.durations.get(i).date));
                        durasi.add(totalDurasi/60); //convert ke menit
                        totalDurasi = 0;
                    }
                }
                else {
                    totalDurasi = totalDurasi + Global.durations.get(i).duration;
                    dateArray.add(Global.newFormatDate(Global.durations.get(i).date));
                    durasi.add(totalDurasi);
                }
            }
        }

        if (sizeMeditasi > 1) {
            dateMeditasi = new ArrayList();
            yDuration = new ArrayList();
            Line lineMeditasi = new Line(yDuration).setColor(Color.parseColor("#27ae60"));

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

            List linesMeditasi = new ArrayList();
            linesMeditasi.add(lineMeditasi);

            LineChartData data = new LineChartData();
            data.setLines(linesMeditasi);

            Axis axis = new Axis();
            axis.setValues(dateMeditasi);
            axis.setTextSize(16);
            axis.setTextColor(Color.parseColor("#03A9F4"));
            data.setAxisXBottom(axis);

            Axis yAxis = new Axis();
            yAxis.setName("duration(menit)");
            yAxis.setTextColor(Color.parseColor("#03A9F4"));
            yAxis.setTextSize(16);
            data.setAxisYLeft(yAxis);

            progressChartMeditasi.setLineChartData(data);

        } else {
            tvTextMeditasi.setText("Data kurang");
            progressChartMeditasi.setLineChartData(null);
        }
    }

    public void filterDate(){
        try {
            Date ptAwal = dateFormatFilter.parse(edtTglAwal.getText().toString());
            Date ptAkhir = dateFormatFilter.parse(edtTglAkhir.getText().toString());
            int dayDiff = (int) Global.getDateDiff(ptAwal, ptAkhir, TimeUnit.DAYS);
            Log.d("FILTER", "daydiff: "+dayDiff);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
