package com.bekkostudio.meditasidanretret.Chart;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    LineChartView progressChartMood, progressChartMeditasi;


    List<PointValue> yMoodAxisValue, yMedicineAxisValue;
    List<AxisValue> dateAxisValue;
    ArrayList<String> moodDate;
    ArrayList<Integer> moodValue;
    ArrayList<Integer> medicineValue;

    List<PointValue> yDurationAxisValue;
    List<AxisValue> dateMeditasiAxisValue;
    ArrayList<String> meditasiDate;
    ArrayList<Integer> durationValue;
    Date lastMeditasiDate; ////help adding duration on same date

    //note
    ListView noteListView;
    List<HashMap<String, String>> noteList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_progress_fragment, container, false);

        dateFormatFilter = new SimpleDateFormat("dd MMM yyyy");

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
                //show chart
                LineChartMood();
                LineChartMeditasi();
                //show note
                showNote();
            }
        });
        progressChartMood = (LineChartView) view.findViewById(R.id.progress_chartMood);
        progressChartMeditasi = (LineChartView) view.findViewById(R.id.progress_chartMeditasi);

        //set default date
        Date todayDate = new Date();
        edtTglAkhir.setText(dateFormatFilter.format(todayDate));
        edtTglAwal.setText(dateFormatFilter.format(Global.substractDateByDays(todayDate,30))); //minus 30 hari

        //note
        noteListView = view.findViewById(R.id.listNote);

        //show chart
        LineChartMood();
        LineChartMeditasi();

        //show note
        showNote();


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
        yMoodAxisValue = new ArrayList<>();
        dateAxisValue = new ArrayList<>();
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
            List<Line> lines = new ArrayList<>();
            lines.add(line);
            lines.add(line1);

            //add the graph line to the overall data chart
            LineChartData data = new LineChartData();
            data.setLines(lines);

            //show value date in line graph chart
            Axis axis = new Axis();
            axis.setValues(dateAxisValue);
            axis.setTextSize(14);
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
            v.top =10.5f; //example max value
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
        moodValue.add(Global.moods.get(i).moodBeforeValue);
        yMoodAxisValue.add(new PointValue(dataIndex, Global.moods.get(i).moodBeforeValue));
        //add medicine value
        medicineValue.add(Global.moods.get(i).moodAfterValue);
        yMedicineAxisValue.add(new PointValue(dataIndex, Global.moods.get(i).moodAfterValue));
    }

    private void LineChartMeditasi () {
        //Hold data
        yDurationAxisValue = new ArrayList<>();
        dateMeditasiAxisValue = new ArrayList<>();

        meditasiDate = new ArrayList<>();
        durationValue = new ArrayList<>();

        //line will hold data value
        Line line = new Line(yDurationAxisValue).setColor(Color.parseColor("#27ae60"));


        lastMeditasiDate = null; //help adding duration on same date
        try {
            Date ptAwal = dateFormatFilter.parse(edtTglAwal.getText().toString());
            Date ptAkhir = dateFormatFilter.parse(edtTglAkhir.getText().toString());
            boolean keTglAkhir = false;
            int dayDiff;
            for (int i = 0; i < Global.durations.size(); i++){
                if (!keTglAkhir){
                    dayDiff = (int) Global.getDateDiff(ptAwal, Global.parseDate(Global.durations.get(i).date), TimeUnit.DAYS);
                    if (dayDiff>=0){
                        addDurationData(i);
                        //to data by date akhir
                        keTglAkhir = true;
                    }
                }else{
                    dayDiff = (int) Global.getDateDiff(Global.parseDate(Global.durations.get(i).date), ptAkhir, TimeUnit.DAYS);
                    if (dayDiff>=0){
                        addDurationData(i);
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

        int size = meditasiDate.size();
        if (size > 1) {
            //hold the line of the graph chart
            List<Line> lines = new ArrayList<>();
            lines.add(line);

            //add the graph line to the overall data chart
            LineChartData data = new LineChartData();
            data.setLines(lines);

            //show value date in line graph chart
            Axis axis = new Axis();
            axis.setValues(dateMeditasiAxisValue);
            axis.setTextSize(14);
            axis.setTextColor(Color.parseColor("#03A9F4"));
            data.setAxisXBottom(axis);

            Axis yAxis = new Axis();
            yAxis.setName("Durasi (Menit)");
            yAxis.setTextColor(Color.parseColor("#03A9F4"));
            yAxis.setTextSize(16);
            data.setAxisYLeft(yAxis);

            //set data in the Chart
            progressChartMeditasi.setLineChartData(data);

        } else {
            tvTextMeditasi.setText("Data kurang");
            progressChartMeditasi.setLineChartData(null);
        }
    }


    private void addDurationData(int i){
        if (lastMeditasiDate!=null){
            int dayDiff = (int) Global.getDateDiff(lastMeditasiDate, Global.parseDate(Global.durations.get(i).date), TimeUnit.DAYS);
            if (dayDiff==0){
                //accumulate duration
                int dataIndex = meditasiDate.size()-1;
                yDurationAxisValue.get(dataIndex).set(dataIndex,yDurationAxisValue.get(dataIndex).getY()+Global.durations.get(i).duration/60f); //convert to minutes

            }else{
                addNewDurationData(i);
            }
        }else{
            addNewDurationData(i);
        }

        lastMeditasiDate = Global.parseDate(Global.durations.get(i).date);
    }


    private void addNewDurationData(int i){
        //add date
        meditasiDate.add(Global.newFormatDate(Global.durations.get(i).date));
        int dataIndex = meditasiDate.size()-1;
        dateMeditasiAxisValue.add(new AxisValue(dataIndex).setLabel(meditasiDate.get(dataIndex)));
        //add value
        durationValue.add(Global.durations.get(i).duration);
        yDurationAxisValue.add(new PointValue(dataIndex, Global.durations.get(i).duration/60f)); //convert to minutes

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 100; //+100 height extension to avoid cropping. Default 0
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void showNote(){
        noteList = new ArrayList<>();
        try {
            Date ptAwal = dateFormatFilter.parse(edtTglAwal.getText().toString());
            Date ptAkhir = dateFormatFilter.parse(edtTglAkhir.getText().toString());
            boolean keTglAkhir = false;
            int dayDiff;
            for (int i = 0; i < Global.notes.size(); i++){
                if (!keTglAkhir){
                    dayDiff = (int) Global.getDateDiff(ptAwal, Global.parseDate(Global.notes.get(i).date), TimeUnit.DAYS);
                    if (dayDiff>=0){
                        addNoteData(i);
                        //to data by date akhir
                        keTglAkhir = true;
                    }
                }else{
                    dayDiff = (int) Global.getDateDiff(Global.parseDate(Global.notes.get(i).date), ptAkhir, TimeUnit.DAYS);
                    if (dayDiff>=0){
                        addNoteData(i);
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

        String[] from = {"date", "important", "text"};
        int[] to = {R.id.date, R.id.important, R.id.text};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), noteList, R.layout.activity_progress_note_list, from, to);
        noteListView.setAdapter(simpleAdapter);

        setListViewHeightBasedOnChildren(noteListView);
    }


    private void addNoteData(int i){
        HashMap<String, String> hm = new HashMap<>();
        //date
        hm.put("date", dateFormatFilter.format(Global.parseDate(Global.notes.get(i).date)));
        //important
        if (Global.notes.get(i).important){
            hm.put("important", "PENTING!");
        }else{
            hm.put("important", "");
        }

        //text
        hm.put("text", Global.notes.get(i).noteValue);
        noteList.add(hm);
    }
}

