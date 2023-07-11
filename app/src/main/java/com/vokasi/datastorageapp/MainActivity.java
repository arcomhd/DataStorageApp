package com.vokasi.datastorageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String PREFNAME="com.vokasi.datastorageapp.PREF";
    public static final String KEYNAME="SAVETEXT";
    public static final String FILENAME="savetext.txt";
    private TextView textBaca;
    private EditText editText;
    private static final String[] PERMISSION={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textBaca= findViewById(R.id.text_baca);
        editText= findViewById(R.id.edit_text);

    }

    private static boolean hasPermission(Context context, String... permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && permissions!=null){
            for(String permission:permissions){
                if(ActivityCompat.checkSelfPermission(context,permission)
                        != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    public void simpanES(){
        if(hasPermission(this, PERMISSION)) {
            String input = editText.getText().toString();
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path.toString(), FILENAME);
            FileOutputStream outputStream = null;
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, false);
                outputStream.write(input.getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            ActivityCompat.requestPermissions(this, PERMISSION, 100);
        }
    }

    public void bacaES(){
        File path=Environment.getExternalStorageDirectory();
        File file=new File(path.toString(),FILENAME);
        if(file.exists()){
            StringBuilder text=new StringBuilder();
            try {
                BufferedReader br=new BufferedReader(new FileReader(file));
                String line=br.readLine();
                while (line!=null){
                    text.append(line);
                    line=br.readLine();
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            textBaca.setText(text.toString());
        }else {
            textBaca.setText("");
        }
    }

    public void deleteES(){
        File path=Environment.getExternalStorageDirectory();
        File file=new File(path.toString(),FILENAME);
        if(file.exists()){
            file.delete();
        }
    }



    public void hapus(View view) {
        deleteES();
    }

    public void baca(View view) {
        bacaES();
    }


    public void simpan(View view) {
        simpanES();
    }

    public void simpanIS(){
        String input=editText.getText().toString();
        File path=getDir("FOLDER",MODE_PRIVATE);
        File file=new File(path.toString(), FILENAME);
        FileOutputStream outputStream=null;
        try {
            file.createNewFile();
            outputStream=new FileOutputStream(file, false);
            outputStream.write(input.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bacaIS(){
        File path=getDir("FOLDER",MODE_PRIVATE);
        File file=new File(path.toString(), FILENAME);
        if(file.exists()){
            StringBuilder text=new StringBuilder();
            try {
                BufferedReader br=new BufferedReader(new FileReader(file));
                String line=br.readLine();
                while (line!=null){
                    text.append(line);
                    line=br.readLine();
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            textBaca.setText(text.toString());
        }else {
            textBaca.setText("");
        }
    }

    public void deleteIS(){
        File path=getDir("FOLDER",MODE_PRIVATE);
        File file=new File(path.toString(), FILENAME);
        if(file.exists()){
            file.delete();
        }
    }

    public void simpanSP(){
        String input=editText.getText().toString();
        SharedPreferences sharedPreferences=getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEYNAME, input);
        editor.commit();
    }

    public void bacaSP(){
        SharedPreferences sharedPreferences=getSharedPreferences(PREFNAME, MODE_PRIVATE);
        if(sharedPreferences.contains(KEYNAME)){
            String myinput= sharedPreferences.getString(KEYNAME,"");
            textBaca.setText(myinput);
        }else {
            textBaca.setText("");
        }
    }

    public void deleteSP(){
        SharedPreferences sharedPreferences=getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }


}