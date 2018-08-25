package com.bekkostudio.meditasidanretret.Course.Retret;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;
import com.bekkostudio.meditasidanretret.Timer.CenteringHorizontalScrollView;

public class RetretActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    //BGM image picker
    CenteringHorizontalScrollView beginnerCourseScrollWidget;
    LinearLayout beginnerCourseLinearWidget;
    CenteringHorizontalScrollView intermediateCourseScrollWidget;
    LinearLayout intermediateCourseLinearWidget;
    CenteringHorizontalScrollView advanceCourseScrollWidget;
    LinearLayout advanceCourseLinearWidget;

    //Billing
    BillingProcessor bp;
    boolean isOneTimePurchaseSupported = false;

    //active retret
    TextView activeRetretLabelWidget;
    TextView activeRetretTitleWidget;
    ImageView activeRetretImageWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_retret);

        //Billing
        bp = BillingProcessor.newBillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiFZh1Kt6BOFRUY/YVliO0TAh/kurIA92lrRezYZM6B3y1bbBHZorMg5BIOm7zfjQd5u8vqavSBoewR4KZxdkwfIfpypM7bIdTZPG9AxWedySdUZEds/0y74sdiuMAJiyP9Kx1rpafuHuyobZhpIvx5fKZHfEk0BeQAjWDn4ICu/6ZfB96ijF0dF/NVdg9SG7jant24Ko2+nHu1WRwWUXLox9di7Z/CBPh2WQqS2bLS9ZlPD77zIImhiVhRKGeoLnA+5pE33KCovvT0Uw9sm/xSJKt3jTyKkT0Zg00TNc/q/gP1Z653y4gvTeveVSk/VR0REIlmifs92cBnO3d96TSwIDAQAB", this); // doesn't bind
        bp.initialize(); // binds


        //Scroll item picker
        beginnerCourseScrollWidget = findViewById(R.id.beginnerCourseCenterHorizontalScrollView);
        beginnerCourseLinearWidget = findViewById(R.id.beginnerCourseLinearLayout);
        intermediateCourseScrollWidget = findViewById(R.id.intermediateCourseCenterHorizontalScrollView);
        intermediateCourseLinearWidget = findViewById(R.id.intermediateCourseLinearLayout);
        advanceCourseScrollWidget = findViewById(R.id.advanceCourseCenterHorizontalScrollView);
        advanceCourseLinearWidget = findViewById(R.id.advanceCourseLinearLayout);

        View courseItem;
        LinearLayout.LayoutParams params;

        //Set the width here
        int courseItemWidth = 120;
        int courseItemHeight = 120;
        int courseItemMargin = 20;


        //set gap to center first item and last item
        beginnerCourseScrollWidget.setLeftRightGap(this,courseItemWidth);
        intermediateCourseScrollWidget.setLeftRightGap(this,courseItemWidth);
        advanceCourseScrollWidget.setLeftRightGap(this,courseItemWidth);

        // center thumbnail
        for(int i=0; i<BillingParameter.courseSKUList.size(); i++){
            courseItem= getLayoutInflater().inflate(R.layout.activity_course_retret_item, null);;
            //set height width
            params = new LinearLayout.LayoutParams(Global.dpToPx(this, courseItemWidth), Global.dpToPx(this, courseItemHeight));
            if (i==0){
                //If first item
                params.setMargins(0,0,Global.dpToPx(this,courseItemMargin),0);
            }else if (i==3){
                //if last item
                params.setMargins(Global.dpToPx(this,courseItemMargin),0,0,0);
            }else{
                params.setMargins(Global.dpToPx(this,courseItemMargin),0,Global.dpToPx(this,courseItemMargin),0);
            }
            courseItem.setLayoutParams(params);

            //beginner, intermediate, advance rule here
            if (i<5){
                //add to Linearlayout
                beginnerCourseLinearWidget.addView(courseItem);
            }else if (i<9){
                intermediateCourseLinearWidget.addView(courseItem);
            }else{
                advanceCourseLinearWidget.addView(courseItem);
            }



            //On course item click
            final int index = i;
            courseItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Global.checkActiveRetretStatus()){
                        if(isOneTimePurchaseSupported) {
                            // launch payment flow
                            bp.purchase(RetretActivity.this,BillingParameter.courseSKUList.get(index));
                        }else{
                            Toast.makeText(RetretActivity.this,"Perangkat anda tidak mendukung pembelian",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(RetretActivity.this);
                        alert.setTitle("Peringatan");
                        alert.setMessage("Maaf, anda tidak dapat membeli kursus retret baru karena masih ada kursus retret lain yang anda pesan. Pastikan anda mengaktifkan dan menyelesaikan sesi retret yang ada.");
                        alert.setPositiveButton("OK",null);
                        alert.show();
                    }
                }
            });
        }

        //default first item
        beginnerCourseScrollWidget.setCurrentItemAndCenter(0);


        //active retret
        activeRetretLabelWidget = findViewById(R.id.activeCourseLabel);
        activeRetretTitleWidget = findViewById(R.id.activeCourseTitle);
        activeRetretImageWidget = findViewById(R.id.activeCourseImage);

        setActiveRetretWidget();

    }


    public void setActiveRetretWidget(){
        if (Global.checkActiveRetretStatus()){
            activeRetretLabelWidget.setVisibility(View.VISIBLE);
            activeRetretTitleWidget.setVisibility(View.VISIBLE);
            activeRetretImageWidget.setVisibility(View.VISIBLE);
            activeRetretTitleWidget.setText(Global.courseRetret.get(Global.activeRetretId).title);
            activeRetretImageWidget.setImageResource(Global.courseRetret.get(Global.activeRetretId).thumbnailImage);
        }else{
            activeRetretLabelWidget.setVisibility(View.GONE);
            activeRetretTitleWidget.setVisibility(View.GONE);
            activeRetretImageWidget.setVisibility(View.GONE);
        }
    }

    // IBillingHandler implementation

    @Override
    public void onBillingInitialized() {
        /*
         * Called when BillingProcessor was initialized and it's ready to purchase
         */
         isOneTimePurchaseSupported = bp.isOneTimePurchaseSupported();

    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        /*
         * Called when requested PRODUCT ID was successfully purchased
         */
        Log.d("ID", "onProductPurchased: "+productId);
        bp.consumePurchase(productId);

        Global.setActiveRetret(getApplicationContext(),productId,"");
        setActiveRetretWidget();


    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        /*
         * Called when some error occurred. See Constants class for more details
         *
         * Note - this includes handling the case where the user canceled the buy dialog:
         * errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED
         */
        Toast.makeText(RetretActivity.this,"Pembelian Gagal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {
        /*
         * Called when purchase history was restored and the list of all owned PRODUCT ID's
         * was loaded from Google Play
         */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}
