package com.vips.vipsoftwares;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
/*import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;*/
import com.vips.vipsoftwares.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.RenderPriority;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends SherlockActivity implements OnTouchListener {
	
	WebView w;
	 ProgressDialog pd;
	 ProgressBar p;
	 MenuItem menuitem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		try
		{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		w=(WebView)findViewById(R.id.webView1);
		p=(ProgressBar)findViewById(R.id.progressBar1);
		p.setVisibility(View.INVISIBLE);
		
		/**
		 * 
		 * Initailize ProgressDialog Class with properties
		 * 
		 */
		
		pd=new ProgressDialog(MainActivity.this);
		pd.setTitle("");
		pd.setMessage("Loading.........");
		pd.setCancelable(true);
	
		//Set Progress Images and set Progress limit
	pd.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_horizontal_myapp));
		pd.setProgress(0);
		pd.setMax(10);
				
				com.actionbarsherlock.app.ActionBar ab=getSupportActionBar();
				ab.setDisplayHomeAsUpEnabled(true);
				ab.setHomeButtonEnabled(true);
			
				getSupportActionBar().setTitle("VIPSoftwares Official App");
				getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#2c5200'><b><u>POSTS</u></b></font>"));
				
				 w.setBackgroundColor(Color.parseColor("#ffffff"));
				
				 
				 //Save Webpages for offline OF Maxmimum 5MB cache
				w.getSettings().setAppCacheMaxSize( 8 * 1024 * 1024 ); // 5MB
				w.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
				w.getSettings().setAllowFileAccess( true );
		        w.getSettings().setAppCacheEnabled( true );
				w.getSettings().setJavaScriptEnabled(true);
				

				
				//Enable Webview zoom control
		      w.getSettings().setBuiltInZoomControls(true);
		        
		  
		        w.getSettings().setLoadWithOverviewMode(true);
		        w.getSettings().setUseWideViewPort(true);
		
		        
				w.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

				
				if (savedInstanceState == null)
				{
					 w.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
				}
				
				if ( !isNetworkAvailable() ) {
					
					// if Data is  not available then retrieve stored data from cache
				    w.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
				}

			
				 w.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
				 
				 
				  w.loadUrl("http://www.vipsoftwareszz.blogspot.com"); //Load WebURL
				 
			
				 w.setWebChromeClient(new WebChromeClient(){

			         @Override
					public void onProgressChanged(WebView view, int progress) {
			                getSupportActionBar().setSubtitle(progress+"");
			                Toast.makeText(getApplicationContext(), progress*100+"",Toast.LENGTH_LONG).show();
			                 }
			         
			         
			});
				
				     
				        
			 w.setWebViewClient(new WebViewClient() {
			        @Override
			       public boolean shouldOverrideUrlLoading(WebView view, String url) {
					        	   
					        	
					        	   w.loadUrl(url);
					        	   w.setDownloadListener(new DownloadListener() {
					          @Override
								public void onDownloadStart(String url, String userAgent,String contentDisposition, String mimetype,long contentLength)
					        		   {
					        	  
					        	  /**
					        	   * 
					        	   * 
					        	   * Enable Download Intent for Start Download from App
					        	   * 
					        	   */
					        		        Intent i = new Intent(Intent.ACTION_VIEW);
					        		        i.setData(Uri.parse(url));
					        		        startActivity(i);
					        		      }
					        		  });
								return true;
					        	   
					           }
					           
					           @Override
					        public void onPageFinished(WebView view, String url) {
					        	// TODO Auto-generated method stub
					        	super.onPageFinished(view, url);
					        
					        	p.setVisibility(View.INVISIBLE); //invisible progressbar on page load Finished
					        	
					        }
					           
					           @Override
					        public void onPageStarted(WebView view, String url,
					        		Bitmap favicon) {
					        	// TODO Auto-generated method stub
					        	super.onPageStarted(view, url, favicon);
					        
					        	p.setVisibility(View.VISIBLE); //enable visibility of progressabar on pagestart
					        }
					           
					          @Override
					        public void onReceivedError(WebView view, int errorCode,
					        		String description, String failingUrl) {
					        	// TODO Auto-generated method stub
					        	  Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
					        	super.onReceivedError(view, errorCode, description, failingUrl);
					        	
					        	/**
					        	 * On Receive Error during page load on webview redirect Intent to MyError class
					        	 */
					  
					        	
					        	Intent in=new Intent(MainActivity.this,MyError.class);
					        	startActivity(in);
					        	 
					        }
					       });  
				    	   
				    	 
			 		w.getSettings().setRenderPriority(RenderPriority.HIGH);
				     
		}  
				    
		 catch(Exception ex)
		{
			Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		
		
		/*
		 * Inflate Menus from Menu files
		 */
		
		 MenuInflater inflater =getSupportMenuInflater();
	       inflater.inflate(R.menu.main, menu);
	        
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub
		 
		
		/*
		 * 
		 * Set Menus actions on particular item ID
		 * 
		 * 
		 */
		switch(item.getItemId())
		{
		case R.id.bk:
			
			if(w.canGoBack())
			{
				w.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
				w.goBack();
			}
			
			return true;
			
		case R.id.fr:
			if(w.canGoForward())
			{
				w.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
				w.goForward();
			}
			return true;
			
		case R.id.refresh:
			
			
			w.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
			w.reload();
			return true;
			
		case 3:
			finish();
			return true;
			
		case android.R.id.home:
			finish();
			return true;
			
		case 2:
			AlertDialog.Builder ab=new AlertDialog.Builder(MainActivity.this);
			ab.setTitle("About Developer");
			ab.setMessage("App Design and Develop By VIPSoftwares");
			ab.setNeutralButton("Okay",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			
			ab.show();
			
		}

		
		return super.onOptionsItemSelected(item);
	}
    
	
	private boolean isNetworkAvailable() {
		
		/*
		 * Check Internet connection
		 * 
		 */
	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
 @Override
 protected void onSaveInstanceState(Bundle outState)
 {
   super.onSaveInstanceState(outState);

   // Save the state of the WebView
   super.onSaveInstanceState(outState);
   w.saveState(outState);
 }
  
 @Override
 protected void onRestoreInstanceState(Bundle savedInstanceState)
 {
   super.onRestoreInstanceState(savedInstanceState);

   // Restore the state of the WebView
   super.onSaveInstanceState(savedInstanceState);
   w.restoreState(savedInstanceState);
 }
	

 
@Override
protected void onResume()
{
	// TODO Auto-generated method stub
	super.onResume();
	w.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
	w.reload();
}

@Override
public boolean onTouch(View v, MotionEvent event) {
	// TODO Auto-generated method stub
	return false;
}

}
