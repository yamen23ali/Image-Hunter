package com.safeyP.imagehunter;


import java.io.IOException;

import com.safeyP.myUtils.MyTextResizer;

import com.safeyP.myUtils.ImageHunter;
import com.safeyP.myUtils.MyActivity;
import com.safeyP.myUtils.MyProgressBar;
import com.safeyP.myUtils.MyRunnable;
import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends MyActivity implements android.view.View.OnClickListener {
	ImageHunter hunter;
	ImageView image;
	//public Bitmap wallpaper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageButton next=(ImageButton) findViewById(R.id.next);
		next.setOnClickListener(this);
		
		ImageButton prev=(ImageButton) findViewById(R.id.prev);
		prev.setOnClickListener(this);
		
		//=============== Text Size And Font ==========//
		MyTextResizer textResizer=new MyTextResizer(this);
		float size=textResizer.calcSize(6,0.5f);
		float size2=textResizer.calcSize(3,0.15f);
		Typeface face = Typeface.createFromAsset(getAssets(),"Fonts/CevicheOne.ttf");
		Typeface face2 = Typeface.createFromAsset(getAssets(),"Fonts/HennyPenny.ttf");
		//=============== Text Size And Font ==========//
				
		//========== Styles ===========//
		TextView tv=(TextView) findViewById(R.id.loading);
		tv.setTypeface(face);
		tv.setTextColor(Color.BLACK);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
		    	
				
		Button hunt=(Button) findViewById(R.id.hunt);
		hunt.setOnClickListener(this);
		hunt.setTypeface(face2,Typeface.BOLD);
		hunt.setTextSize(TypedValue.COMPLEX_UNIT_PX,size2);
		hunt.setTextColor(Color.BLACK);
		
		
		//=============================//
		image=(ImageView) findViewById(R.id.image);
		image.setOnClickListener(this);

	}
	
	//============== Click Listener For Buttons ===========//
	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.hunt)
		{
			ProgressDialog ringProgressDialog = ProgressDialog.show(this, "Please wait ", "Hunting Wallpapers ...", true);
			ringProgressDialog.setCancelable(true);
			
			hunter=new ImageHunter(getApplicationContext(),this,R.id.image,ringProgressDialog);
			EditText et=(EditText) findViewById(R.id.searchBox);
			hunter.hunt(et.getText().toString());
			
			ImageButton next=(ImageButton) findViewById(R.id.next);
			next.setVisibility(View.VISIBLE);
			ImageButton prev=(ImageButton) findViewById(R.id.prev);
			prev.setVisibility(View.VISIBLE);
		}
		if(v.getId()==R.id.next || v.getId()==R.id.prev )
		{		
			ImageView not_found=(ImageView)findViewById(R.id.imageNotFound);
			image.setVisibility(View.VISIBLE);
			not_found.setVisibility(View.GONE);
			
			if(v.getId()==R.id.next)
				hunter.getNextImage();
			else
				hunter.getPrevImage();
				
			//======== To Skip Failed Images 
		}
		if(v.getId()==R.id.image)
		{
			try {
				WallpaperManager wm=WallpaperManager.getInstance(this);
				wm.setBitmap(wallpaper);
				
				DisplayMetrics displayMetrics = new DisplayMetrics();
			    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
				int width=displayMetrics.widthPixels;
				int height=displayMetrics.heightPixels;
				
				wm.suggestDesiredDimensions(width,height);
				//========= Release Image ======//
				wallpaper.recycle();
			    wallpaper=null;
			} catch (Exception e) {
			    Log.e("er",e.getMessage());
			}
		}
		
	}
	//============== Click Listener For Buttons ===========//

}
