package com.teamverman.givememoney;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ickhyun on 2017-02-13.
 */

public class TutPopup extends Activity {

    Button front;
    Button back;
    ImageView img;
    TextView txt;
    int pageNum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.tutorial_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w = dm.widthPixels;
        int h = dm.heightPixels;

        getWindow().setLayout((int) (w * 0.91), (int) (h * 0.70));

        front = (Button)findViewById(R.id.tut_front_btn);
        back = (Button)findViewById(R.id.tut_back_btn);
        img = (ImageView)findViewById(R.id.tut_image);
        txt = (TextView)findViewById(R.id.tut_page);

        back.setVisibility(View.INVISIBLE);

        back.setOnClickListener(changeImg);
        front.setOnClickListener(changeImg);

        pageNum=1;

    }

    View.OnClickListener changeImg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.tut_front_btn){
                Log.v("ASDSDA", ""+pageNum);
                if(pageNum!=9) {
                    pageNum++;

                }
                changePage();
            }
            if(v.getId()==R.id.tut_back_btn){
                if(pageNum!=1)
                    pageNum--;
                changePage();
            }
        }
    };

    int imgID(){
        switch (pageNum){
            case 1:
                return R.drawable.tut1;
            case 2:
                return R.drawable.tut2;
            case 3:
                return R.drawable.tut3;
            case 4:
                return R.drawable.tut4;
            case 5:
                return R.drawable.tut5;
            case 6:
                return R.drawable.tut6;
            case 7:
                return R.drawable.tut7;
            case 8:
                return R.drawable.tut8;
            case 9:
                return R.drawable.tut9;
        }
        return 0;
    }

    void changePage(){
        if(pageNum==1) {
            back.setVisibility(View.INVISIBLE);
            front.setVisibility(View.VISIBLE);
        }
        else if(pageNum==9) {
            back.setVisibility(View.VISIBLE);
            front.setVisibility(View.INVISIBLE);
        }
        else {
            back.setVisibility(View.VISIBLE);
            front.setVisibility(View.VISIBLE);
        }
        txt.setText(""+pageNum+" / 9");
        img.setImageResource(imgID());
    }
}
