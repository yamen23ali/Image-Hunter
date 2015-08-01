package com.safeyP.myUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class MyProgressBar extends AsyncTask<Object, Void, String> {
    
	ProgressDialog mDialog;
	Activity parent;
	Context context;
	String word;
	
	//==========================================//
    public MyProgressBar(Context context,Activity parent,String word) {
        this.parent=parent;
        this.context=context;
        this.word=word;
    }
    //==========================================//
    
    //================================//
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(parent);
        mDialog.setMessage("Loading Puzzles .. Please Wait ");        
        mDialog.show();
    }
  //==========================================//    
    @Override
    protected String doInBackground(Object... params) {
    	
    	return "";
    }
  //==========================================//
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mDialog.dismiss();
    }
}

