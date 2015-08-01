package com.safeyP.myUtils;

import com.safeyP.imagehunter.MainActivity;

import android.app.Activity;
import android.webkit.JavascriptInterface;

public class MyJavaScriptInterface {
	
	ImageHunter caller;
	
	//========= Constructor ============//
	public MyJavaScriptInterface(ImageHunter caller)
	{
		this.caller=caller;
	}
	//========= Constructor ============//
	
	//========= After Getting The HTML From Webpage ============//
	@JavascriptInterface
	@SuppressWarnings("unused")
	public void processHTML(String html)
	{
	    caller.parse(html);    	
	}
	//========= After Getting The HTML From Webpage ============//
}
