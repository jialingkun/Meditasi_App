package com.bekkostudio.meditasidanretret;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bekkostudio.compactWebview.DefaultSetting;
import com.bekkostudio.compactWebview.SmartWebViewCompact;

public class CommunityFragment extends Fragment {

    SmartWebViewCompact smartWebViewCompact = new SmartWebViewCompact();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        smartWebViewCompact.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_community_fragment, container, false);


        ProgressBar progressBar = view.findViewById(R.id.msw_progress);
        Global.currentWebview =view.findViewById(R.id.msw_view);

        //Permission variables
        smartWebViewCompact.ASWP_JSCRIPT     = true;     //enable JavaScript for webview
        smartWebViewCompact.ASWP_FUPLOAD     = true;     //upload file from webview
        smartWebViewCompact.ASWP_CAMUPLOAD   = true;     //enable upload from camera for photos
        smartWebViewCompact.ASWP_ONLYCAM        = false;	//incase you want only camera files to upload
        smartWebViewCompact.ASWP_MULFILE     = false;    //upload multiple files in webview
        smartWebViewCompact.ASWP_LOCATION    = false;     //track GPS locations
        smartWebViewCompact.ASWP_RATINGS     = false;     //show ratings dialog; auto configured, edit method get_rating() for customizations
        smartWebViewCompact.ASWP_PBAR        = true;     //show progress bar in app
        smartWebViewCompact.ASWP_ZOOM        = false;    //zoom control for webpages view
        smartWebViewCompact.ASWP_SFORM       = true;    //save form cache and auto-fill information
        smartWebViewCompact.ASWP_OFFLINE     = false;    //whether the loading webpages are offline or online
        smartWebViewCompact.ASWP_EXTURL      = false;     //open external url with default browser instead of app webview

        //Configuration variables
        smartWebViewCompact.ASWV_URL          = "https://www.facebook.com/groups/2146742412010762/"; //complete URL of your website or webpage
        smartWebViewCompact.ASWV_F_TYPE       = "*/*";  //to upload any file type using "*/*"; check file type references for more

        //Rating system variables
        DefaultSetting.ASWR_DAYS            = 3;        //after how many days of usage would you like to show the dialoge
        DefaultSetting.ASWR_TIMES           = 10;       //overall request launch times being ignored
        DefaultSetting.ASWR_INTERVAL        = 2;        //reminding users to rate after days interval

        smartWebViewCompact.onCreate(getActivity(),Global.currentWebview,progressBar);

        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        smartWebViewCompact.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            smartWebViewCompact.onRestoreInstanceState(savedInstanceState);
        }
    }
}
