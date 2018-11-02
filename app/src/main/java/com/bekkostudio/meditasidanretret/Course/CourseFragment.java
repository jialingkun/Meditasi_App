package com.bekkostudio.meditasidanretret.Course;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bekkostudio.meditasidanretret.Article.ShopActivity;
import com.bekkostudio.meditasidanretret.Article.TutorialActivity;
import com.bekkostudio.meditasidanretret.Article.TutorialContentActivity;
import com.bekkostudio.meditasidanretret.CenteringHorizontalScrollView;
import com.bekkostudio.meditasidanretret.Course.Retret.AttentionActivity;
import com.bekkostudio.meditasidanretret.Course.Retret.BillingParameter;
import com.bekkostudio.meditasidanretret.Course.Retret.RetretActivity;
import com.bekkostudio.meditasidanretret.Course.Retret.TimelineActivity;
import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

public class CourseFragment extends Fragment implements BillingProcessor.IBillingHandler {

    //BGM image picker
    CenteringHorizontalScrollView freeCourseScrollWidget;
    LinearLayout freeCourseLinearWidget;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_course_fragment, container, false);


        //Billing
        bp = BillingProcessor.newBillingProcessor(getActivity(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiFZh1Kt6BOFRUY/YVliO0TAh/kurIA92lrRezYZM6B3y1bbBHZorMg5BIOm7zfjQd5u8vqavSBoewR4KZxdkwfIfpypM7bIdTZPG9AxWedySdUZEds/0y74sdiuMAJiyP9Kx1rpafuHuyobZhpIvx5fKZHfEk0BeQAjWDn4ICu/6ZfB96ijF0dF/NVdg9SG7jant24Ko2+nHu1WRwWUXLox9di7Z/CBPh2WQqS2bLS9ZlPD77zIImhiVhRKGeoLnA+5pE33KCovvT0Uw9sm/xSJKt3jTyKkT0Zg00TNc/q/gP1Z653y4gvTeveVSk/VR0REIlmifs92cBnO3d96TSwIDAQAB", this); // doesn't bind
        bp.initialize(); // binds


        //Scroll item picker
        freeCourseScrollWidget = view.findViewById(R.id.freeCourseCenterHorizontalScrollView);
        freeCourseLinearWidget = view.findViewById(R.id.freeCourseLinearLayout);
        beginnerCourseScrollWidget = view.findViewById(R.id.beginnerCourseCenterHorizontalScrollView);
        beginnerCourseLinearWidget = view.findViewById(R.id.beginnerCourseLinearLayout);
        intermediateCourseScrollWidget = view.findViewById(R.id.intermediateCourseCenterHorizontalScrollView);
        intermediateCourseLinearWidget = view.findViewById(R.id.intermediateCourseLinearLayout);
        advanceCourseScrollWidget = view.findViewById(R.id.advanceCourseCenterHorizontalScrollView);
        advanceCourseLinearWidget = view.findViewById(R.id.advanceCourseLinearLayout);

        View courseItem;
        LinearLayout.LayoutParams params;

        //Set the width here
        int courseItemWidth = 170;
        int courseItemHeight = 170;
        int courseItemMargin = 0;


        //set gap to center first item and last item
        freeCourseScrollWidget.setLeftRightGap(getActivity(),courseItemWidth);
        beginnerCourseScrollWidget.setLeftRightGap(getActivity(),courseItemWidth);
        intermediateCourseScrollWidget.setLeftRightGap(getActivity(),courseItemWidth);
        advanceCourseScrollWidget.setLeftRightGap(getActivity(),courseItemWidth);

        //free video tutorial
        // center thumbnail
        for(int i = 0; i< Global.videoTitle.length; i++){
            courseItem= getLayoutInflater().inflate(R.layout.activity_course_retret_item, null);
            //set height width
            params = new LinearLayout.LayoutParams(Global.dpToPx(getActivity(), courseItemWidth), Global.dpToPx(getActivity(), courseItemHeight));
            if (i==0){
                //If first item
                params.setMargins(0,0,Global.dpToPx(getActivity(),courseItemMargin),0);
            }else if (i==3){
                //if last item
                params.setMargins(Global.dpToPx(getActivity(),courseItemMargin),0,0,0);
            }else{
                params.setMargins(Global.dpToPx(getActivity(),courseItemMargin),0,Global.dpToPx(getActivity(),courseItemMargin),0);
            }
            courseItem.setLayoutParams(params);

            //set item content
            TextView title = courseItem.findViewById(R.id.itemText);
            title.setText(Global.videoTitle[i]);

            freeCourseLinearWidget.addView(courseItem);

            //On course item click
            final int index = i;
            courseItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), TutorialContentActivity.class);
                    intent.putExtra("contentIndex", index);
                    startActivity(intent);
                }
            });
        }

        // center thumbnail
        for(int i = 0; i< BillingParameter.courseSKUList.size(); i++){
            courseItem= getLayoutInflater().inflate(R.layout.activity_course_retret_item, null);
            //set height width
            params = new LinearLayout.LayoutParams(Global.dpToPx(getActivity(), courseItemWidth), Global.dpToPx(getActivity(), courseItemHeight));
            if (i==0){
                //If first item
                params.setMargins(0,0,Global.dpToPx(getActivity(),courseItemMargin),0);
            }else if (i==3){
                //if last item
                params.setMargins(Global.dpToPx(getActivity(),courseItemMargin),0,0,0);
            }else{
                params.setMargins(Global.dpToPx(getActivity(),courseItemMargin),0,Global.dpToPx(getActivity(),courseItemMargin),0);
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
                            bp.purchase(getActivity(),BillingParameter.courseSKUList.get(index));
                        }else{
                            Toast.makeText(getActivity(),"Perangkat anda tidak mendukung pembelian",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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
        activeRetretLabelWidget = view.findViewById(R.id.activeCourseLabel);
        activeRetretTitleWidget = view.findViewById(R.id.activeCourseTitle);
        activeRetretImageWidget = view.findViewById(R.id.activeCourseImage);
        setActiveRetretWidget();
        activeRetretImageWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.activeRetretEndDate==""){
                    //to Attention page before start activity
                    Intent intent = new Intent(getActivity(), AttentionActivity.class);
                    startActivity(intent);
                }else {
                    //To course timeline
                    Intent intent = new Intent(getActivity(), TimelineActivity.class);
                    startActivity(intent);

                }
            }
        });

        return view;
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

        Global.setActiveRetret(getActivity(),productId,"");
        setActiveRetretWidget();


    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        /*
         * Called when some error occurred. See Constants class for more details
         *
         * Note - getActivity() includes handling the case where the user canceled the buy dialog:
         * errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED
         */
        Toast.makeText(getActivity(),"Pembelian Gagal",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {
        /*
         * Called when purchase history was restored and the list of all owned PRODUCT ID's
         * was loaded from Google Play
         */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
