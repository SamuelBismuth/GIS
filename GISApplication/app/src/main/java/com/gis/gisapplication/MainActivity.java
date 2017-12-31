package com.gis.gisapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import cast.Cast;
import cast.CastFromCsvFileToSampleScan;
import libraries.DataBase;
import objects.CsvFile;
import objects.SampleScan;
import read.ReadCombo;
import read.ReadCsv;
import read.ReadWigleWifi;
import runs.CallableCast;
import runs.RunRead;

/**
 * TODO : Detect the format of the file : wigle wifi, combo, combo no gps ?
 * TODO : reorganize the functions
 * TODO : filter
 * TODO : ASSES LOCALISATION
 * TODO : SEE IF THREAD IN A GOOD PLACE
 * TODO : EXEPTION EXPORT (FILENAME EMPTY OR PLACE,ARKS EMPTY)
 * TODO : DESIGN !!
 * TODO : read folder.
 * TODO : Toast message about the import (successful or algo2)
 */
public class MainActivity extends AppCompatActivity {

    Button importFile;
    TextView numberOfSampleScan, numberOfWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        }

        importFile = (Button) findViewById(R.id.importFile);
        numberOfSampleScan = (TextView) findViewById(R.id.numberOfSampleScan);
        numberOfSampleScan.setText("0");
        numberOfWifi = (TextView) findViewById(R.id.numberOfWifi);
        numberOfWifi.setText("0");

        importFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                    .withActivity(MainActivity.this)
                    .withRequestCode(1000)
                    .withFilter(Pattern.compile(".*\\.csv$")) // Filtering files and directories by file name using regexp
                    .withHiddenFiles(true) // Show hidden files and folders
                    .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            ArrayList<SampleScan> arraySampleScan =  new ArrayList<SampleScan>();
            ArrayList<CsvFile> arrayCsvFile = new ArrayList<CsvFile>();
            Thread threadRead;
            if (read(filePath)) {
                ReadCsv<CsvFile> readWigleWifi = new ReadWigleWifi(filePath, arrayCsvFile);
                threadRead = new Thread(new RunRead<CsvFile>(readWigleWifi, filePath));
            }
            else {
                ReadCsv<SampleScan> readCombo= new ReadCombo(filePath, arraySampleScan);
                threadRead = new Thread(new RunRead<SampleScan>(readCombo, filePath));
            }
            threadRead.start();
            try {
                threadRead.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (arrayCsvFile.size() != 0)  {
                DataBase.addArrayCsvFile(arrayCsvFile);
                Cast castCsv = new CastFromCsvFileToSampleScan();
                ExecutorService execut = (ExecutorService) Executors.newSingleThreadExecutor();
                Future<ArrayList<SampleScan>> future = execut.submit(new CallableCast<CsvFile, SampleScan>(castCsv, arrayCsvFile));
                while (!future.isDone());
                try {
                    arraySampleScan = future.get();
                }
                catch (InterruptedException | ExecutionException e1) {
                    e1.printStackTrace();
                }
            }
            Toast.makeText(this, "File have been successfully read!", Toast.LENGTH_SHORT).show();
            DataBase.addArraySampleScan(arraySampleScan);
            numberOfSampleScan.setText(Integer.toString(DataBase.getArraySampleScan().size()));
            numberOfWifi.setText(Integer.toString(DataBase.numberOfWifi()));
        }
    }

    private boolean read(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String firstLine = br.readLine();
            if(firstLine.contains("WigleWifi"))
                return true;
           return  false;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1001:{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission granted !", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Permission not granted !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public void exportFile(View view) {
        startActivity(new Intent(MainActivity.this, ExportActivity.class));
    }

    public void filter(View view) {
        startActivity(new Intent(MainActivity.this, FilterActivity.class));
    }

    public void clearDataBase(View view) {
        DataBase.clear();
        numberOfSampleScan.setText("0");
        numberOfWifi.setText("0");
    }

}
