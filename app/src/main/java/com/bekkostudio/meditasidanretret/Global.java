package com.bekkostudio.meditasidanretret;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;

import com.bekkostudio.meditasidanretret.Course.Retret.BillingParameter;
import com.bekkostudio.meditasidanretret.Course.Retret.RetretDays;
import com.bekkostudio.meditasidanretret.Course.Retret.RetretDetail;
import com.bekkostudio.meditasidanretret.Timer.TimerCountdown;
import com.bekkostudio.meditasidanretret.Timer.TimerFragment;
import com.danikula.videocache.HttpProxyCacheServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Global {
    //timer
    public static TimerFragment lastTimerFragmentObject;

    //recent meditation
    public static ArrayList<String> recentMeditation;

    public static void getRecentMeditation(Context context){
        try {
            FileInputStream inputStream = context.openFileInput("recentMeditation.txt");
            ObjectInputStream input = new ObjectInputStream(inputStream);
            recentMeditation = (ArrayList<String>) input.readObject();
            input.close();
        } catch (FileNotFoundException e){
            recentMeditation = new ArrayList<>();
            recentMeditation.add(0,"Kosong");
            recentMeditation.add(0,"Kosong");
            recentMeditation.add(0,"Kosong");
            Log.d("getRecentMeditation", "Exception: " + e);

        }catch (Exception e){
            Log.d("getRecentMeditation", "Exception: " + e);
        }

    }

    public static void setRecentMeditation (Context context, String timeToSave){
        recentMeditation.remove(2);
        recentMeditation.add(0, timeToSave);

        try {
            FileOutputStream outputStream = context.openFileOutput("recentMeditation.txt", Context.MODE_PRIVATE);
            //Log.d("Context Directory", "Path: "+context.getFilesDir());
            ObjectOutputStream output = new ObjectOutputStream(outputStream);
            output.writeObject(recentMeditation);
            output.close();
        }catch (Exception e){
            Log.d("SetRecentMeditation", "Exception: " + e);
        }

    }

    public static final int[] ambientImageItem = {R.drawable.ambient0,R.drawable.ambient1,R.drawable.ambient2,R.drawable.ambient3};
    public static final int[] ambientMusicItem = {0,R.raw.mt_airy,R.raw.weaving,R.raw.butterfly_space};

    //Article
    public static WebView currentWebview;


    //course
    //tutorial
    // Array of strings for ListView
    public static final String[] videoTitle ={
            "How to Meditate for dummies", "How to Meditate for dummies 2", "How to Meditate for dummies 3",
    };
    public static final int[] videoThumbnail ={
            R.drawable.ambient0, R.drawable.ambient0, R.drawable.ambient0,
    };
    public static final String[] listviewShortDescription ={
            "00:13:30", "00:13:30", "00:13:30",
    };
    public static final String[] videoUrl ={
            "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media",
            "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media",
            "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media",
    };
    public static final String[] videoDescription ={
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
    };
    public static final String[] videoEbookUrl ={
            "https://www.bekkostudio.com/",
            "https://www.bekkostudio.com/",
            "https://www.bekkostudio.com/",
    };

    //Video Cache
    private static HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        return proxy == null ? (proxy = newProxy(context.getApplicationContext())) : proxy;
    }

    private static HttpProxyCacheServer newProxy(Context context) {
        return new HttpProxyCacheServer(context);
    }


    //retret
    public static Map<String,RetretDetail> courseRetret = new HashMap<>();
    static {
        initializeRetretDetail();
    }
    public static  void initializeRetretDetail(){
        String tempId = BillingParameter.courseSKUList.get(0);
        RetretDetail tempRetretDetail = new RetretDetail();
        tempRetretDetail.title = "Belajar Meditasi untuk Pemula";
        tempRetretDetail.thumbnailImage = R.drawable.ambient0;
        tempRetretDetail.retretDays = new RetretDays[5];
        //day 1
        tempRetretDetail.retretDays[0] = new RetretDays();
        tempRetretDetail.retretDays[0].videoUrl = "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media";
        tempRetretDetail.retretDays[0].videoThumbnail = R.drawable.ambient0;
        tempRetretDetail.retretDays[0].morningDuration = 60;
        tempRetretDetail.retretDays[0].morningBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[0].nightDuration = 120;
        tempRetretDetail.retretDays[0].nightBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[0].description = "Perhatikan nafas dengan sangat teliti \n Tarik nafas dan keluarkan secara terus menerus";
        //day 2
        tempRetretDetail.retretDays[1] = new RetretDays();
        tempRetretDetail.retretDays[1].videoUrl = "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media";
        tempRetretDetail.retretDays[1].videoThumbnail = R.drawable.ambient0;
        tempRetretDetail.retretDays[1].morningDuration = 100;
        tempRetretDetail.retretDays[1].morningBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[1].nightDuration = 5;
        tempRetretDetail.retretDays[1].nightBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[1].description = "Perhatikan nafas dengan sangat teliti \n Tarik nafas dan keluarkan secara terus menerus";

        //day 3
        tempRetretDetail.retretDays[2] = new RetretDays();
        tempRetretDetail.retretDays[2].videoUrl = "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media";
        tempRetretDetail.retretDays[2].videoThumbnail = R.drawable.ambient0;
        tempRetretDetail.retretDays[2].morningDuration = 3;
        tempRetretDetail.retretDays[2].morningBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[2].nightDuration = 10;
        tempRetretDetail.retretDays[2].nightBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[2].description = "Perhatikan nafas dengan sangat teliti \n Tarik nafas dan keluarkan secara terus menerus";

        //day 4
        tempRetretDetail.retretDays[3] = new RetretDays();
        tempRetretDetail.retretDays[3].videoUrl = "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media";
        tempRetretDetail.retretDays[3].videoThumbnail = R.drawable.ambient0;
        tempRetretDetail.retretDays[3].morningDuration = 1;
        tempRetretDetail.retretDays[3].morningBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[3].nightDuration = 4;
        tempRetretDetail.retretDays[3].nightBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[3].description = "Perhatikan nafas dengan sangat teliti \n Tarik nafas dan keluarkan secara terus menerus";

        //day 5
        tempRetretDetail.retretDays[4] = new RetretDays();
        tempRetretDetail.retretDays[4].videoUrl = "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media";
        tempRetretDetail.retretDays[4].videoThumbnail = R.drawable.ambient0;
        tempRetretDetail.retretDays[4].morningDuration = 1;
        tempRetretDetail.retretDays[4].morningBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[4].nightDuration = 2;
        tempRetretDetail.retretDays[4].nightBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[4].description = "Perhatikan nafas dengan sangat teliti \n Tarik nafas dan keluarkan secara terus menerus";

        courseRetret.put(tempId,tempRetretDetail);
    }

    //active retret save data
    public static String activeRetretId;
    public static String activeRetretEndDate;

    //universal pattern for date
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE,dd-MM-yyyy");


    public static void getActiveRetret(Context context){
        try {
            //get id
            FileInputStream inputStream = context.openFileInput("activeRetretId.txt");
            ObjectInputStream input = new ObjectInputStream(inputStream);
            activeRetretId = (String) input.readObject();
            input.close();
            //get end date
            inputStream = context.openFileInput("activeRetretEndDate.txt");
            input = new ObjectInputStream(inputStream);
            activeRetretEndDate = (String) input.readObject();
            input.close();

        } catch (FileNotFoundException e){
            activeRetretId = "";
            activeRetretEndDate = "";
            Log.d("getActiveRetret", "Exception: " + e);
        }catch (Exception e){
            Log.d("getActiveRetret", "Exception: " + e);
        }

    }


    public static void setActiveRetret (Context context, String idToSave, String endDateToSave){
        activeRetretId = idToSave;
        activeRetretEndDate = endDateToSave;
        try {
            //save id
            FileOutputStream outputStream = context.openFileOutput("activeRetretId.txt", Context.MODE_PRIVATE);
            //Log.d("Context Directory", "Path: "+context.getFilesDir());
            ObjectOutputStream output = new ObjectOutputStream(outputStream);
            output.writeObject(activeRetretId);
            output.close();

            //save end date
            outputStream = context.openFileOutput("activeRetretEndDate.txt", Context.MODE_PRIVATE);
            output = new ObjectOutputStream(outputStream);
            output.writeObject(activeRetretEndDate);
            output.close();
        }catch (Exception e){
            Log.d("setActiveRetret", "Exception: " + e);
        }
    }

    public static String calculateEndDate(){
        int numberofDays = courseRetret.get(activeRetretId).retretDays.length;
        Date endDate = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis( numberofDays ));
        return simpleDateFormat.format(endDate);
    }

    public static String calculateRetretDaysDate(int currentIndex){
        int numberofDays = courseRetret.get(activeRetretId).retretDays.length;
        try {
            Date date = new Date(simpleDateFormat.parse(activeRetretEndDate).getTime() - TimeUnit.DAYS.toMillis( numberofDays-currentIndex-1 ));
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error: "+e;
        }
    }

    public static int checkEndDateDifference(){
        if (activeRetretId == ""){
            return -1;
        }else if (activeRetretEndDate==""){
            return 0; //Counted as active course, but not being activated yet
        }else{
            try {
                Date endDate = simpleDateFormat.parse(activeRetretEndDate);

                //To neutralize the clock, we have to parse from String format
                Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

                int dayDiff = (int) getDateDiff(currentDate,endDate,TimeUnit.DAYS);
                return dayDiff;
            } catch (ParseException e) {
                e.printStackTrace();
                return -1;
            }

        }
    }

    public static boolean checkActiveRetretStatus(){
        if (checkEndDateDifference()>=0){
            return true;
        }else{
            return false;
        }
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }





    public static void StartTimer(Context context, int meditationDuration, int warmupDuration, int ambientMusic){
        Intent intent = new Intent(context, TimerCountdown.class);
        intent.putExtra("meditationDuration", meditationDuration);
        intent.putExtra("warmupDuration", warmupDuration);
        intent.putExtra("ambientMusic", ambientMusic);
        context.startActivity(intent);
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }


    public static String formatMinutesToHoursMinutes(int rawMinutes){
        int minutes =rawMinutes % 60;
        int hours =(rawMinutes / 60) % 24;
        String result ="";
        if (hours>0){
            result = hours + " jam ";
        }

        if (minutes>0){
            result = result+minutes + " menit";
        }

        return result;
    }


    public static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }


}
