package com.safeyP.myUtils;

import com.safeyP.imagehunter.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient{
    boolean timeout;
    MyActivity parent;

    
    //============ Constructor ================//
    public MyWebViewClient(MyActivity parent) {
        timeout = true;
        this.parent=parent;
    }
    //============ Constructor ================//

    //========== When Page Loading Start Set Time Out ========//
    @Override
    public void onPageStarted(final WebView view, String url, Bitmap favicon) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(timeout) {
                    // do what you want
                	view.stopLoading();
                	parent.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							AlertDialog alert = new AlertDialog.Builder(parent).create();
							alert.setMessage("Problem With Internet Connection !");
							alert.setButton(DialogInterface.BUTTON_POSITIVE,"OK",parent);
							alert.show();
						}
					});
                	
                }
            }
        }).start();
    }
    //========== When Page Loading Start Set Time Out ========//

    //============= After Loading Finish ==============//
    @Override
    public void onPageFinished(WebView view, String url)
    {
        /* This call inject JavaScript into the page which just finished loading. */
    	view.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    	timeout = false;
    }
    //============= After Loading Finish ==============//
}