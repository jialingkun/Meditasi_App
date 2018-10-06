package com.bekkostudio.meditasidanretret;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;

import com.bekkostudio.meditasidanretret.Chart.Duration;
import com.bekkostudio.meditasidanretret.Chart.Mood;
import com.bekkostudio.meditasidanretret.Course.Retret.BillingParameter;
import com.bekkostudio.meditasidanretret.Course.Retret.RetretDays;
import com.bekkostudio.meditasidanretret.Course.Retret.RetretDetail;
import com.bekkostudio.meditasidanretret.Timer.MeditationCountdown;
import com.bekkostudio.meditasidanretret.Timer.TimerFragment;
import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.exoplayer2.SimpleExoPlayer;

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
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Global {
    //Meditation timer
    public static final int[] ambientImageItem = {R.drawable.ambient0,R.drawable.ambient1,R.drawable.ambient2,R.drawable.ambient3};
    public static final int[] ambientMusicItem = {0,R.raw.mt_airy,R.raw.weaving,R.raw.butterfly_space};
    public static final int[] nianFoMusicItem = {0,R.raw.nian_fo_1,R.raw.nian_fo_2,R.raw.nian_fo_3};

    //Article
    public static WebView currentWebview;


    //course
    //shop
    // Array of strings for ListView
    public static final String[] shopTitle ={
            "Bantal Meditasi", "Guling meditasi", "Kasur meditasi",
    };
    public static final int[] shopThumbnail ={
            R.drawable.bantal, R.drawable.bantal, R.drawable.bantal,
    };
    public static final String[] listviewShopPrice ={
            "Rp 200.000", "Rp 250.000", "Rp 1.350.000",
    };
    public static final String[] shopDescription ={
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
    };
    public static final String shopContact = "082331602198";

    //tutorial
    // Array of strings for ListView
    public static final String[] videoTitle ={
            "How to Meditate for dummies", "How to Meditate for dummies 2", "How to Meditate for dummies 3",
    };
    public static final int[] videoThumbnail ={
            R.drawable.thumbnail_dummy, R.drawable.thumbnail_dummy, R.drawable.thumbnail_dummy,
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


    //Global Exoplayer Instance
    public static SimpleExoPlayer exoPlayer;


    //retret
    public static Map<String,RetretDetail> courseRetret = new HashMap<>();
//    static {
//        initializeRetretDetail();
//    }
    public static  void initializeRetretDetail(){
        String tempId = BillingParameter.courseSKUList.get(0);
        RetretDetail tempRetretDetail = new RetretDetail();
        tempRetretDetail.title = "Belajar Meditasi untuk Pemula";
        tempRetretDetail.thumbnailImage = R.drawable.course_thumbnail_dummy;
        tempRetretDetail.description = "Panduan :\nAkan ada 5 hari retret yang akan dimulai sesuai tanggal yang tertera di bawah. Pastikan akan kembali ke halaman ini di pagi hari dan menekan menu sesi yang aktif sesuai tanggal. Akan ada 2 sesi di pagi hari dan malam hari. Anda bisa menonton video panduan dan memulai sesi meditasi sesuai timer yang disediakan.";
        tempRetretDetail.retretDays = new RetretDays[5];
        //day 1
        tempRetretDetail.retretDays[0] = new RetretDays();
        tempRetretDetail.retretDays[0].videoUrl = "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media";
        tempRetretDetail.retretDays[0].videoThumbnail = R.drawable.thumbnail_dummy;
        tempRetretDetail.retretDays[0].morningDuration = 60;
        tempRetretDetail.retretDays[0].morningBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[0].nightDuration = 120;
        tempRetretDetail.retretDays[0].nightBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[0].description = "Perhatikan nafas dengan sangat teliti \n Tarik nafas dan keluarkan secara terus menerus";
        //day 2
        tempRetretDetail.retretDays[1] = new RetretDays();
        tempRetretDetail.retretDays[1].videoUrl = "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media";
        tempRetretDetail.retretDays[1].videoThumbnail = R.drawable.thumbnail_dummy;
        tempRetretDetail.retretDays[1].morningDuration = 100;
        tempRetretDetail.retretDays[1].morningBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[1].nightDuration = 5;
        tempRetretDetail.retretDays[1].nightBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[1].description = "Perhatikan nafas dengan sangat teliti \n Tarik nafas dan keluarkan secara terus menerus";

        //day 3
        tempRetretDetail.retretDays[2] = new RetretDays();
        tempRetretDetail.retretDays[2].videoUrl = "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media";
        tempRetretDetail.retretDays[2].videoThumbnail = R.drawable.thumbnail_dummy;
        tempRetretDetail.retretDays[2].morningDuration = 3;
        tempRetretDetail.retretDays[2].morningBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[2].nightDuration = 10;
        tempRetretDetail.retretDays[2].nightBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[2].description = "Perhatikan nafas dengan sangat teliti \n Tarik nafas dan keluarkan secara terus menerus";

        //day 4
        tempRetretDetail.retretDays[3] = new RetretDays();
        tempRetretDetail.retretDays[3].videoUrl = "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media";
        tempRetretDetail.retretDays[3].videoThumbnail = R.drawable.thumbnail_dummy;
        tempRetretDetail.retretDays[3].morningDuration = 1;
        tempRetretDetail.retretDays[3].morningBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[3].nightDuration = 4;
        tempRetretDetail.retretDays[3].nightBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[3].description = "Perhatikan nafas dengan sangat teliti \n Tarik nafas dan keluarkan secara terus menerus";

        //day 5
        tempRetretDetail.retretDays[4] = new RetretDays();
        tempRetretDetail.retretDays[4].videoUrl = "https://firebasestorage.googleapis.com/v0/b/bekko-studio.appspot.com/o/6.%20transfer%20aplikasi%20ke%20akun%20developer%20lain%20-%20YouTube.MP4?alt=media";
        tempRetretDetail.retretDays[4].videoThumbnail = R.drawable.thumbnail_dummy;
        tempRetretDetail.retretDays[4].morningDuration = 1;
        tempRetretDetail.retretDays[4].morningBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[4].nightDuration = 2;
        tempRetretDetail.retretDays[4].nightBGM = R.raw.butterfly_space;
        tempRetretDetail.retretDays[4].description = "Perhatikan nafas dengan sangat teliti \n Tarik nafas dan keluarkan secara terus menerus";

        courseRetret.put(tempId,tempRetretDetail);
    }

    //To refresh completed status back to false
    public static void refreshRetretDetail(String tempId, Context context){
        RetretDetail tempRetretDetail = courseRetret.get(tempId);
        for (int i = 0; i < tempRetretDetail.retretDays.length; i++) {
            tempRetretDetail.retretDays[i].morningCompleted = false;
            tempRetretDetail.retretDays[i].nightCompleted = false;
        }

        setActiveRetretDetail(context);
    }

    public static void getActiveRetretDetail(Context context){
        try {
            //get retret detail
            FileInputStream inputStream = context.openFileInput("activeRetretDetail.txt");
            ObjectInputStream input = new ObjectInputStream(inputStream);
            courseRetret = (HashMap<String,RetretDetail>) input.readObject();
            input.close();
        } catch (FileNotFoundException e){
            initializeRetretDetail();
            Log.d("getActiveRetretDetail", "Exception: " + e);
        }catch (Exception e){
            Log.d("getActiveRetretDetail", "Exception: " + e);
        }

    }


    public static void setActiveRetretDetail (Context context){
        try {
            //save retret detail
            FileOutputStream outputStream = context.openFileOutput("activeRetretDetail.txt", Context.MODE_PRIVATE);
            //Log.d("Context Directory", "Path: "+context.getFilesDir());
            ObjectOutputStream output = new ObjectOutputStream(outputStream);
            output.writeObject(courseRetret);
            output.close();
        }catch (Exception e){
            Log.d("setActiveRetretDetail", "Exception: " + e);
        }
    }

    //active retret save data
    public static String activeRetretId;
    public static String activeRetretEndDate;

    //temp variable to check timer completed status
    public static boolean tempIsCompleted;
    //To mark if the last timer represent morning or night
    public static boolean isMorningTimer;

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
        int numberofDays = courseRetret.get(activeRetretId).retretDays.length; //cheat -1
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

    public static void startTimer(Activity activity, int meditationDuration, int warmupDuration, int ambientMusic){
        Intent intent = new Intent(activity, MeditationCountdown.class);
        intent.putExtra("meditationDuration", meditationDuration);
        intent.putExtra("warmupDuration", warmupDuration);
        intent.putExtra("ambientMusic", ambientMusic);

        activity.startActivityForResult(intent,1);
    }


    //Mood and medicine database
    public static ArrayList<Mood> moods;


    public static void getMood(Context context){
        try {
            FileInputStream inputStream = context.openFileInput("mood.txt");
            ObjectInputStream input = new ObjectInputStream(inputStream);
            moods = (ArrayList<Mood>) input.readObject();
            input.close();
        } catch (FileNotFoundException e){
            moods = new ArrayList<>();
            Log.d("getMood", "Exception: " + e);
        }catch (Exception e){
            Log.d("getMood", "Exception: " + e);
        }

    }


    public static void setMood (Context context, Mood mood){
        moods.add(mood);
        try {
            FileOutputStream outputStream = context.openFileOutput("mood.txt", Context.MODE_PRIVATE);
            ObjectOutputStream output = new ObjectOutputStream(outputStream);
            output.writeObject(moods);
            output.close();
        }catch (Exception e){
            Log.d("setMood", "Exception: " + e);
        }
    }


    //Meditation duration database
    public static ArrayList<Duration> durations;


    public static void getDuration(Context context){
        try {
            FileInputStream inputStream = context.openFileInput("duration.txt");
            ObjectInputStream input = new ObjectInputStream(inputStream);
            durations = (ArrayList<Duration>) input.readObject();
            input.close();
        } catch (FileNotFoundException e){
            durations = new ArrayList<>();
            Log.d("getDuration", "Exception: " + e);
        }catch (Exception e){
            Log.d("getDuration", "Exception: " + e);
        }

    }


    public static void setDuration (Context context, Duration duration){
        durations.add(duration);
        try {
            FileOutputStream outputStream = context.openFileOutput("duration.txt", Context.MODE_PRIVATE);
            ObjectOutputStream output = new ObjectOutputStream(outputStream);
            output.writeObject(durations);
            output.close();
        }catch (Exception e){
            Log.d("setDuration", "Exception: " + e);
        }
    }

    public static String getTodayDate(){
        Date date = new Date();
        return simpleDateFormat.format(date);
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

    // formatting millisecond to hour, minute, second
    public static String getDateFormat(long durasi) {
        long hours = TimeUnit.MILLISECONDS.toHours(durasi);
        durasi -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(durasi);
        durasi -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(durasi);

        String hourFinal = String.valueOf(hours);
        String minuteFinal = String.valueOf(minutes);
        String secondFinal = String.valueOf(seconds);

        if ((int) hours < 10)
            hourFinal = "0" + hourFinal;
        if ((int) minutes < 10)
            minuteFinal = "0" + minuteFinal;
        if ((int) seconds < 10)
            secondFinal = "0" + secondFinal;

        return hourFinal + ":" + minuteFinal + ":" + secondFinal;
    }
}
