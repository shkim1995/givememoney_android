package com.teamverman.givememoney;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by ickhyun on 2017-02-14.
 */

public class ResetActivity extends Activity{

    ListView listView;
    TextView tv;

    ArrayList<String> fileName = new ArrayList<String>();
    MyListAdapter myAdapter;

    Intent selfInten;

    public void onBackPressed() {
        final Intent intent = new Intent(this, ModeActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reset);

        listView = (ListView)findViewById(R.id.reset_list);
        tv = (TextView)findViewById(R.id.reset_text);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/TmonMonsori.ttf");
        tv.setTypeface(typeFace);

        selfInten = new Intent(this, ResetActivity.class);

        fileNameInit();

        myAdapter = new MyListAdapter(this, R.layout.file_list, fileName);
        listView.setAdapter(myAdapter);

        //배너광고

        AdView mAdView_name = (AdView) findViewById(R.id.adView_reset);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A66417BC5450C8887755FEB37D48889").build();
        mAdView_name.loadAd(adRequest);

    }

    void fileNameInit(){
        fileName = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput("name_list.txt");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String str = buffer.readLine();
            while (str != null) {
                fileName.add(str);
                str = buffer.readLine();
            }
            Log.v("DEBUG_SAVE", "good");
            buffer.close();
        } catch (Exception e) {
            Log.v("DEBUG_SAVE", "exception");
            e.printStackTrace();
        }
    }

    class  MyListAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<String> arrlist;
        int layout;

        public MyListAdapter(Context c, int l, ArrayList<String> arr){
            context = c;
            layout = l;
            arrlist = arr;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount(){
            return arrlist.size();
        }

        public String getItem(int pos){
            return arrlist.get(pos)+"";
        }

        public long getItemId(int pos){
            return pos;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            final int pos = position;
            if(convertView==null){
                convertView = inflater.inflate(layout, parent, false);
            }

            //set name text
            final TextView txt = (TextView) convertView.findViewById(R.id.filelist_name);
            txt.setText(""+arrlist.get(pos));
            Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/TmonMonsori.ttf");
            txt.setTypeface(typeFace);
            if(pos%2==0)
                txt.setTextColor(Color.BLACK);
            else
                txt.setTextColor(Color.GRAY);

            //sel button
            Button btnSel = (Button)convertView.findViewById(R.id.filelist_btn_sel);
            btnSel.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){

                }
            });
            //del button
            Button btnDel = (Button)convertView.findViewById(R.id.filelist_btn_del);
            btnDel.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                    final String str = txt.getText().toString();

                    String data = "";
                    for(int i=0; i<fileName.size(); i++)
                        data = data+" "+fileName.get(i);
                    String alertTitle = "삭제 하시겠습니까?";
                    String buttonMessage = "'"+str+"'을(를) 삭제합니다.";
                    String btnYes = "네";
                    String btnNo = "아니오";

                    new AlertDialog.Builder(ResetActivity.this)
                            .setTitle(alertTitle)
                            .setMessage(buttonMessage)
                            .setPositiveButton(btnYes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deleteItem(str);
                                    startActivity(selfInten);
                                    finish();
                                }
                            })
                            .setNegativeButton(btnNo, null)
                            .show();

                }
            });
            return convertView;
        }

    }

    void deleteItem(String itemName){
        fileName = new ArrayList<String>();
        //backup
        try {
            FileInputStream fis = openFileInput("name_list.txt");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String str = buffer.readLine();
            while (str != null) {
                if(!str.equals(itemName)) {
                    fileName.add(str);
                    Log.v("STRING", str);
                }
                str = buffer.readLine();
            }
            buffer.close();
        } catch (Exception e) {
            Log.v("DEBUG1", "exception");
            e.printStackTrace();
        }


        //delete
        try {
            deleteFile("name_list.txt");
        }
        catch (Exception e) {
            Log.v("DEBUG1", "exception");
            e.printStackTrace();
        }
        try {
            deleteFile(itemName+".txt");
        }
        catch (Exception e) {
            Log.v("DEBUG1", "exception");
            e.printStackTrace();
        }
        //write new
        try {
            FileOutputStream fos = openFileOutput("name_list.txt", Context.MODE_APPEND);
            PrintWriter out = new PrintWriter(fos);
            for(int i=0; i<fileName.size(); i++) {
                out.println(fileName.get(i));
            }
            out.close();
        } catch (Exception e) {
            Log.v("DEBUG2", "exception");
            e.printStackTrace();
        }

    }

}
