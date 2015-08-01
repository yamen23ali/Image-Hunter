package com.safeyP.myUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

import com.safeyP.imagehunter.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageHunter {
	Context context;
	MyActivity parent;
	ArrayList<String> images;
	int currentImage=0;
	int imageViewId;
	ProgressDialog dialog;
	String suffix;
	
	//======== Constructor ============//
	public ImageHunter(Context context,MyActivity parent,int imageViewId,ProgressDialog dialog) {
	    this.context=context;
	    this.parent=parent;
	    this.imageViewId=imageViewId;
	    this.dialog=dialog;
	    
	    boolean tabletSize = parent.getResources().getBoolean(R.bool.isTablet);
	    if (tabletSize) {
	    	 suffix=" tablet wallpapers";

	    } else {
	    	 suffix=" mobile wallpapers";
	    }
	    
	}
	//======== Constructor ============//
	    
	//============ Parse The Returned  HTML To Find Photos ============//
	public void parse(String html)
	{
		String[] urls=html.split(Pattern.quote("imgres?imgurl="));
	  	images=new ArrayList<String>();
	   	
	  	//======= Get Images URLS
	  	ArrayList<String> beforeProcessingImages=new ArrayList<String>();
	   	for(int i=1;i<urls.length;i++)
	   	{
	   		int delm=urls[i].indexOf("&");
	   		beforeProcessingImages.add(urls[i].substring(0,delm));
	   	}
	   	
	   	//======== Process Images URLS
	   	for (String url : beforeProcessingImages) {
			if(!url.contains(".svg") && 
					( url.contains(".jpeg") || url.contains(".jpg") || url.contains(".png")))
			{
				images.add(url);
			}
			
		}
	   	getNextImage();
	   	dialog.dismiss();
	}
	//============ Parse The Returned  HTML To Find Photos ============//
	
	
	//============= Get Image from URL And Put It In ImageView =========//
	public void getNextImage()
	{
		currentImage++;
		if(currentImage==images.size()) 
			currentImage=0;
		getImage();
	}
	
	public void getPrevImage()
	{
		currentImage--;
		if(currentImage==-1)
			currentImage=images.size()-1;
		getImage();
	}
	public void getImage()
	{
		try {
			parent.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					TextView loading=(TextView) parent.findViewById(R.id.loading);
					loading.setVisibility(View.VISIBLE);
				}
			});
			
			Thread getImage=new Thread(new MyRunnable(images.get(currentImage),parent,context,imageViewId));
			getImage.start();
	    } catch (Exception e) {
	        Log.e("Err",e.getMessage());
	    }
	
	}
	//============= Get Image from URL And Put It In ImageView =========//
	
	//================================//
	public void hunt(String word)
	{
		WebView webView = new WebView(context);
		//WebView webView=(WebView) parent.findViewById(R.id.web_view);
		
		String newUA= "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
		webView.getSettings().setUserAgentString(newUA);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new MyJavaScriptInterface(this), "HTMLOUT");
		
		/* WebViewClient must be set BEFORE calling loadUrl! */
		webView.setWebViewClient(new MyWebViewClient(parent));

		//webView.loadUrl("https://www.google.com.my/search?q="+word+suffix+"&tbs=isz:m&tbm=isch");
		webView.loadUrl("https://www.google.com.my/search?q="+word+suffix+"&tbm=isch");
		
	}
}
