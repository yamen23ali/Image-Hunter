package com.safeyP.myUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.safeyP.imagehunter.MainActivity;
import com.safeyP.imagehunter.R;
import com.squareup.picasso.Picasso;

public class MyRunnable implements Runnable {
	
	Context context;
	String url;
	int imageId;
	MyActivity parent;
	//========= Constructor =============//
	public MyRunnable(String url,MyActivity parent,Context context,int imageId)
	{
		this.context=context;
		this.url=url;
		this.imageId=imageId;
		this.parent=parent;
	}
	//========= Constructor =============//

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			parent.wallpaper=Picasso.with(context).load(url).get();
			final BitmapDrawable bmp= new BitmapDrawable(parent.wallpaper);
			parent.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ImageView im=(ImageView)parent.findViewById(imageId);
					if(bmp.getBitmap().getHeight()==0)
					{
						switchImages();
					}
					else
					{
						im.setBackgroundDrawable(bmp);
					}
					TextView loading=(TextView) parent.findViewById(R.id.loading);
					loading.setVisibility(View.INVISIBLE);
				}
			});
			
			
		}
		catch(Exception e)
		{
			Log.e("err",e.getMessage());
			parent.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					switchImages();
				}
			});
		
		}
	}
	
	public void switchImages()
	{
		ImageView im=(ImageView)parent.findViewById(imageId);
	    ImageView notFound=(ImageView)parent.findViewById(R.id.imageNotFound);
		im.setVisibility(View.GONE);
		im.setBackgroundResource(0);
		notFound.setVisibility(View.VISIBLE);
		TextView loading=(TextView) parent.findViewById(R.id.loading);
		loading.setVisibility(View.INVISIBLE);
	}
			

}
