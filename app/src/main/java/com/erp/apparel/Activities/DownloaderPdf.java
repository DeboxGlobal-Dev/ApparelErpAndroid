package com.erp.apparel.Activities;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class DownloaderPdf extends AsyncTask<String, Void, Void> {

    Context ctx;

    @Override
    protected Void doInBackground(String... strings) {

        String fileUrl=strings[0];
        String fileName=strings[1];   //create file and url

        // Getting refernce for external storage

        String extStorageDirectory= Environment.getExternalStorageDirectory().toString();

        // File location

        File folder = new File(extStorageDirectory,"PDF DOWNLOAD");
        folder.mkdir();

        File pdfFile = new File(folder,fileName);

        try {
            pdfFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        // File Downloader class

        PdfDownloader.downloadfile(fileUrl, pdfFile);
        Log.e("mylog","fileurl are"+fileUrl);
        return null;
    }
}
