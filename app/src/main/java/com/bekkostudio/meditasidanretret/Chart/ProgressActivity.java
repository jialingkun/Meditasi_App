package com.bekkostudio.meditasidanretret.Chart;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


public class ProgressActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvTitleMood, tvMood, tvMedicine, tvTextMood, tvTextMeditasi, tvTitleMeditasi;
    EditText edtTglAwal, edtTglAkhir;
    Button btnFilter;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatFilter;
    Spinner spProgress;
    ArrayAdapter adapterProgress;
    LineChartView progressChartMood, progressChartMeditasi;
    List yMoodAxisValue, dateAxisValue, yMedicineAxisValue;
    ArrayList<String> moodDate = new ArrayList<>();
    ArrayList<String> meditasiDate = new ArrayList<>();
    ArrayList<Integer> moodValue = new ArrayList<>();
    ArrayList<Integer> medicineValue = new ArrayList<>();
    ArrayList<Integer> durationValue = new ArrayList<>();
    List dateMeditasi, yDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        dateFormatFilter = new SimpleDateFormat("dd-MM-yyyy");
 
        tvTitleMood = (TextView) findViewById(R.id.tvTitleMood);
        tvTextMood = (TextView) findViewById(R.id.tvTextMood);
        tvMood = (TextView) findViewById(R.id.tvMood);
        tvMedicine = (TextView) findViewById(R.id.tvMedicine);
        tvTextMeditasi = (TextView) findViewById(R.id.tvTextMeditasi);
        tvTitleMeditasi = (TextView) findViewById(R.id.tvTitleMeditasi);

        edtTglAwal = (EditText) findViewById(R.id.edtTglAwal);
        edtTglAkhir = (EditText) findViewById(R.id.edtTglAkhir);
        edtTglAwal.setOnClickListener(this);
        edtTglAkhir.setOnClickListener(this);

        btnFilter = (Button) findViewById(R.id.btnFilter) ;
        btnFilter.setOnClickListener(this);

        spProgress = (Spinner) findViewById(R.id.spProgress);
        progressChartMood = (LineChartView) findViewById(R.id.progress_chartMood);
        progressChartMeditasi = (LineChartView) findViewById(R.id.progress_chartMeditasi);

        adapterProgress = ArrayAdapter.createFromResource(this, R.array.metode_progressChart, android.R.layout.simple_spinner_item);
        adapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spProgress.setAdapter(adapterProgress);

        //set spinner Mood or Meditasi
        spProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Progress")) {


                } else if(parent.getItemAtPosition(position).toString().equals("Catatan")) {
                    Intent intent = new Intent(ProgressActivity.this, NoteActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //show chart
        LineChartMood();
        LineChartMeditasi();
    }

    private void showDateDialogAwal() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
        tvTitleMood.setText("Progress Mood");
        int size = Global.moods.size();
        if (size > 1) {
            //Hold data mood, date, medicine
            yMoodAxisValue = new ArrayList();
            dateAxisValue = new ArrayList();
            yMedicineAxisValue = new ArrayList<>();

            //line will hold data value mood, medicine
            Line line = new Line(yMoodAxisValue).setColor(Color.parseColor("#27ae60"));
            Line line1 = new Line(yMedicineAxisValue).setColor(Color.parseColor("#c0392b"));

            //moodDate to hold data from Global mood date
            for (int i = 0; i < size; i++) {
                moodDate.add(Global.newFormatDate(Global.moods.get(i).date));
                dateAxisValue.add(i, new AxisValue(i).setLabel(moodDate.get(i)));
            }

            //hold data value from moodValue in Global
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

            tvMood.setText("Mood");
            tvMedicine.setText("Medicine");

        } else {
            tvTextMood.setText("Data kurang");
            progressChartMood.setLineChartData(null);
            tvMood.setText(null);
            tvMedicine.setText(null);
        }


    }

    private void LineChartMeditasi () {
        //MEDITASI
        tvTitleMeditasi.setText("Progress Meditasi");
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnFilter :
                try {
                    Date ptAwal = dateFormatFilter.parse(edtTglAwal.getText().toString());
                    Date ptAkhir = dateFormatFilter.parse(edtTglAkhir.getText().toString());
                    int dayDiff = (int) Global.getDateDiff(ptAwal, ptAkhir, TimeUnit.DAYS);
                    Log.d("FILTER", "daydiff: "+dayDiff);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < moodDate.size(); i++) {
                    //Terakhir Coding yang codinganku udah tak hapus ko.
                }
                break;
            case R.id.edtTglAwal :
                showDateDialogAwal();
                break;
            case R.id.edtTglAkhir :
                showDateDialogAkhir();
                break;
        }

    }
}
