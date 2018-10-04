package com.bekkostudio.meditasidanretret.Article;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bekkostudio.meditasidanretret.Global;
import com.bekkostudio.meditasidanretret.R;

public class ShopContentActivity extends AppCompatActivity {
    int contentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_shop_content);

        //get index number
        Intent intent = getIntent();
        contentIndex = intent.getIntExtra("contentIndex",0);

        //Set click shop thumbnail
        ImageView shopThumbnailWidget = findViewById(R.id.shopThumbnail);
        shopThumbnailWidget.setImageResource(Global.shopThumbnail[contentIndex]);

        //set title
        TextView shopTitleWidget = findViewById(R.id.shopTitle);
        shopTitleWidget.setText(Global.shopTitle[contentIndex]);

        //set description
        TextView shopDescriptionWidget = findViewById(R.id.shopDescription);
        shopDescriptionWidget.setText(Global.shopDescription[contentIndex]);

        //set contact
        TextView shopContactWidget = findViewById(R.id.shopContact);
        shopContactWidget.setText("Contact Person: "+Global.shopContact);

        Button shopSaveContactWidget = findViewById(R.id.shopSaveContact);
        shopSaveContactWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+Global.shopContact));
                startActivity(intent);
            }
        });
    }
}
