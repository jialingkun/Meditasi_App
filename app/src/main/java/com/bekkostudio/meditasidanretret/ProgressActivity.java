package com.bekkostudio.meditasidanretret;

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

import com.bekkostudio.meditasidanretret.Chart.NoteActivity;

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
import lecho.lib.hellocharts.view.LineChartView;


public class ProgressActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvTitleMood, tvMood, tvMedicine, tvTextMood, tvKet1, tvKet2, tvKet3, tvKet4, tvTextMeditasi, tvTitleMeditasi;
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
    ArrayList<Date> newMoodDate = new ArrayList<>();
    ArrayList<Date> newMediasi = new ArrayList<>();
    ArrayList<Long> millMoodDate = new ArrayList<>();
    ArrayList<Integer> moodValue = new ArrayList<>();
    ArrayList<Integer> medicineValue = new ArrayList<>();
    ArrayList<Integer> durationValue = new ArrayList<>();
    List dateMeditasi, yDuration;

    int lastPosition = 0;
    //boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        dateFormatFilter = new SimpleDateFormat("dd-MM-yyyy");
 
        tvTitleMood = (TextView) findViewById(R.id.tvTitleMood);
        tvTextMood = (TextView) findViewById(R.id.tvTextMood);
        tvMood = (TextView) findViewById(R.id.tvMood);
        tvMedicine = (TextView) findViewById(R.id.tvMedicine);
        tvKet1 = (TextView) findViewById(R.id.ket1);
        tvKet2 = (TextView) findViewById(R.id.ket2);
        tvKet3 = (TextView) findViewById(R.id.ket3);
        tvKet4 = (TextView) findViewById(R.id.ket4);
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
                    //lastPosition = position;
                    LineChartMood();
                    LineChartMeditasi();

                } else if(parent.getItemAtPosition(position).toString().equals("Catatan")) {
                    spProgress.setSelection(lastPosition);
                    Intent intent = new Intent(ProgressActivity.this, NoteActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            Line line = new Line(yMoodAxisValue).setColor(Color.parseColor("#e60000"));
            Line line1 = new Line(yMedicineAxisValue).setColor(Color.parseColor("#ffff00"));

            //moodDate to hold data from Global mood date
            for (int i = 0; i < size; i++) {
                moodDate.add(Global.newFormatDate(Global.moods.get(i).date));
                dateAxisValue.add(i, new AxisValue(i).setLabel(moodDate.get(i)));
                try {
                    newMoodDate.add(Global.simpleDateFormatNew.parse(moodDate.get(i)));
                    millMoodDate.add(newMoodDate.get(i).getTime());
                    System.out.print(newMoodDate.get(i));
                    System.out.print(millMoodDate.get(i));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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

            tvMood.setText("Mood");
            tvMedicine.setText("Medicine");
            tvKet1.setText("1 : Sangat Tidak Tenang");
            tvKet2.setText("2 : Tidak Tenang");
            tvKet3.setText("3 : Tenang");
            tvKet4.setText("4 : Sangat Tenang");

        } else {
            tvTextMood.setText("Data kurang");
            progressChartMood.setLineChartData(null);
            tvMood.setText(null);
            tvMedicine.setText(null);
        }


    }

    private void LineChartMeditasi () {
        //MEDITASI
        //lastPosition = position;
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
                        durasi.add(totalDurasi);
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
            Line lineMeditasi = new Line(yDuration).setColor(Color.parseColor("#e60000"));

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
