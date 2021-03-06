package com.jumplife.movieinfo;

import java.util.ArrayList;

import com.google.analytics.tracking.android.EasyTracker;
import com.jumplife.imageload.ImageLoader;
import com.jumplife.movieinfo.api.MovieAPI;
import com.jumplife.movieinfo.entity.AppProject;
import com.jumplife.movieinfo.promote.PromoteAPP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class AboutUsActivity extends Activity {
	
	private LinearLayout llAboutUs;
	private ProgressBar pbInit;
	private ArrayList<AppProject> appProject;
	private ImageLoader imageLoader;
	
	private Activity mActivity;
	
	private LoadDataTask loadtask;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutme);
		
		mActivity = this;
		
		initView();
        loadtask = new LoadDataTask();
	    if(Build.VERSION.SDK_INT < 11)
	    	loadtask.execute();
        else
        	loadtask.executeOnExecutor(LoadDataTask.THREAD_POOL_EXECUTOR, 0);
	    
		//setClickListener();
	}
	
	@Override
    public void onStart() {
      super.onStart();
      EasyTracker.getInstance().activityStart(mActivity);
    }
    
    @Override
    public void onStop() {
      super.onStop();
      EasyTracker.getInstance().activityStop(mActivity);
    }

	private void initView(){
		imageLoader = new ImageLoader(mActivity);
		
		pbInit = (ProgressBar)findViewById(R.id.pb_about_us);
		llAboutUs = (LinearLayout)findViewById(R.id.ll_aboutus);
        
		initBasicView();
	}
	
	@SuppressLint("InlinedApi")
	private void initBasicView() {
		TableRow Schedule_row_first = new TableRow(mActivity);
		//TableRow Schedule_row_second = new TableRow(mActivity);
		

        
        DisplayMetrics displayMetrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels / 3;
		TableRow.LayoutParams Params = new TableRow.LayoutParams
				(screenWidth, LayoutParams.MATCH_PARENT, 0.33f);				
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels / 6;
        LinearLayout.LayoutParams llIvParams = new LinearLayout.LayoutParams(width, width);
        //rlIvParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        //rlIvParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        llIvParams.setMargins(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin));				
		
		
		TextView tvFeed = new TextView(mActivity);
		ImageView ivFeed = new ImageView(mActivity);
		LinearLayout llFeed = new LinearLayout(mActivity);		
			
		ivFeed.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivFeed.setImageResource(R.drawable.feedback);
        llFeed.addView(ivFeed, llIvParams);
		
        LinearLayout.LayoutParams llTvFeedParams = new LinearLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//rlTvFeedParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		//rlTvFeedParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		llTvFeedParams.setMargins(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				0, 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin));
		tvFeed.setText(mActivity.getResources().getString(R.string.advice_and_feedback));
		tvFeed.setTextSize(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_title));
		//tvFeed.setTextColor(mActivity.getResources().getColor(R.color.about_us_tv));
		llFeed.addView(tvFeed, llTvFeedParams);		
		
		llFeed.setBackgroundResource(R.drawable.button_aboutus_bg);
		llFeed.setOrientation(LinearLayout.VERTICAL);
		llFeed.setGravity(Gravity.CENTER_HORIZONTAL);
		llFeed.setLayoutParams(Params);
		llFeed.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				//EasyTracker.getTracker().sendEvent("關於我們", "點擊", "建議回饋", (long)0);
				Uri uri = Uri.parse("mailto:jumplives@gmail.com");  
				String[] ccs={"abooyaya@gmail.com, raywu07@gmail.com, supermfb@gmail.com, form.follow.fish@gmail.com"};
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);
				it.putExtra(Intent.EXTRA_CC, ccs); 
				it.putExtra(Intent.EXTRA_SUBJECT, "[電影時刻表] 建議回饋"); 
				startActivity(it);  
			}			
		});
		Schedule_row_first.addView(llFeed);
		
		
		
		TextView tvNews = new TextView(mActivity);
		ImageView ivNews= new ImageView(mActivity);
		LinearLayout llNews = new LinearLayout(mActivity);		
			
		ivNews.setScaleType(ImageView.ScaleType.CENTER_CROP);
		ivNews.setImageResource(R.drawable.news);
		llNews.addView(ivNews, llIvParams);
		
		LinearLayout.LayoutParams llTvNewsParams = new LinearLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //rlTvNewsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //rlTvNewsParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        llTvNewsParams.setMargins(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				0, 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin));
        tvNews.setText(mActivity.getResources().getString(R.string.entertainment_news));
        tvNews.setTextSize(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_title));
        llNews.addView(tvNews, llTvNewsParams);		
		
        llNews.setBackgroundResource(R.drawable.button_aboutus_bg);
        llNews.setOrientation(LinearLayout.VERTICAL);
        llNews.setGravity(Gravity.CENTER_HORIZONTAL);
        llNews.setLayoutParams(Params);
        llNews.setOnClickListener(new OnClickListener(){
        	public void onClick(View arg0) {
        		EasyTracker.getTracker().trackEvent("關於我們", "影劇報", "", (long)0);
				Intent newAct = new Intent();
				newAct.setClass( AboutUsActivity.this, NewsActivity.class );
                startActivity( newAct );
			}				
		});
        Schedule_row_first.addView(llNews);
		
		
		
		
		TextView tvFacebook = new TextView(mActivity);
		ImageView ivFacebook = new ImageView(mActivity);
		LinearLayout llFacebook = new LinearLayout(mActivity);		
			
		ivFacebook.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivFacebook.setImageResource(R.drawable.facebook);
        llFacebook.addView(ivFacebook, llIvParams);
		
        LinearLayout.LayoutParams llTvFacebookParams = new LinearLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //rlTvFacebookParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //rlTvFacebookParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        llTvFacebookParams.setMargins(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				0, 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin));
		tvFacebook.setText(mActivity.getResources().getString(R.string.facebook));
		tvFacebook.setTextSize(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_title));
		llFacebook.addView(tvFacebook, llTvFacebookParams);		
		
		llFacebook.setBackgroundResource(R.drawable.button_aboutus_bg);
		llFacebook.setOrientation(LinearLayout.VERTICAL);
		llFacebook.setGravity(Gravity.CENTER_HORIZONTAL);
		llFacebook.setLayoutParams(Params);
		llFacebook.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				//EasyTracker.getTracker().sendEvent("關於我們", "點擊", "FB粉絲團", (long)0);
				Uri uri = Uri.parse("http://www.facebook.com/movietalked");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
			}			
		});
		Schedule_row_first.addView(llFacebook);
		Schedule_row_first.setLayoutParams(new LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		llAboutUs.addView(Schedule_row_first);


		/*TextView tvClear = new TextView(mActivity);
		ImageView ivClear = new ImageView(mActivity);
		RelativeLayout rlClear = new RelativeLayout(mActivity);		
			
		ivClear.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivClear.setImageResource(R.drawable.delete);
        rlClear.addView(ivClear, rlIvParams);
		
        RelativeLayout.LayoutParams rlTvClearParams = new RelativeLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rlTvClearParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlTvClearParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rlTvClearParams.setMargins(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				0, 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
				mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin));
		tvClear.setText(mActivity.getResources().getString(R.string.clear));
		tvClear.setTextSize(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_title));
		tvClear.setTextColor(mActivity.getResources().getColor(R.color.about_us_tv));
		rlClear.addView(tvClear, rlTvClearParams);		
		
		rlClear.setBackgroundResource(R.drawable.about_us_item_background);
		rlClear.setLayoutParams(Params);
		rlClear.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				imageLoader.clearMemoryCache();
				imageLoader.clearDiscCache();
				Toast toast = Toast.makeText(mActivity, 
	            		mActivity.getResources().getString(R.string.clear_finish), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
			}			
		});
		Schedule_row_second.addView(rlClear);

		RelativeLayout rltmps1 = new RelativeLayout(mActivity);
		rltmps1.setBackgroundResource(R.drawable.button_aboutus_bg);	
		rltmps1.setLayoutParams(Params);
		Schedule_row_second.addView(rltmps1);
		RelativeLayout rltmps2 = new RelativeLayout(mActivity);
		rltmps2.setBackgroundResource(R.drawable.button_aboutus_bg);	
		rltmps2.setLayoutParams(Params);
		Schedule_row_second.addView(rltmps2);
		
        
        Schedule_row_second.setLayoutParams(new LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		llAboutUs.addView(Schedule_row_second);*/
		
		
	}
	
	private String fetchData() {
		MovieAPI api = new MovieAPI();
		appProject = api.getAppProjectList(mActivity);
		return "progress end";
	}
	
	private void setView(){
		
		for(int i=0; i<appProject.size(); i+=3){
			TableRow Schedule_row = new TableRow(mActivity);
			for(int j=0; j<3; j++){
				int index = i + j;
				
				TextView tv = new TextView(mActivity);
				ImageView iv = new ImageView(mActivity);
				LinearLayout ll = new LinearLayout(mActivity);
				
				if(index < appProject.size()) {
					
					DisplayMetrics displayMetrics = new DisplayMetrics();
					mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			        int width = displayMetrics.widthPixels / 6;
			        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			        LinearLayout.LayoutParams llIvParams = new LinearLayout.LayoutParams(width, width);
			        //rlIvParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			        //rlIvParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
			        llIvParams.setMargins(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin),
			        		mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
							mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
							mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin));
					ll.addView(iv, llIvParams);
			        iv.getLayoutParams().width = width;
			        iv.getLayoutParams().height = width;
			        imageLoader.DisplayImage(appProject.get(index).getIconUrl(), iv);
					
			        LinearLayout.LayoutParams llTvParams = new LinearLayout.LayoutParams
							(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					//rlTvParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					//rlTvParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
					llTvParams.setMargins(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
							0, 
							mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin), 
							mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_margin));
					tv.setText(appProject.get(index).getName());
					tv.setTextSize(mActivity.getResources().getDimensionPixelSize(R.dimen.about_us_title));
					ll.addView(tv, llTvParams);
				} else
					ll.setVisibility(View.INVISIBLE);
				
				ll.setBackgroundResource(R.drawable.button_aboutus_bg);
				ll.setOrientation(LinearLayout.VERTICAL);
				ll.setGravity(Gravity.CENTER_HORIZONTAL);
				DisplayMetrics displayMetrics = new DisplayMetrics();
				mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		        int screenWidth = displayMetrics.widthPixels / 3;
				TableRow.LayoutParams Params = new TableRow.LayoutParams
						(screenWidth, LayoutParams.MATCH_PARENT, 0.33f);				
				ll.setLayoutParams(Params);
				ll.setOnClickListener(new ItemButtonClick(index, mActivity));
				Schedule_row.addView(ll);
			}
			Schedule_row.setLayoutParams(new LayoutParams
					(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			llAboutUs.addView(Schedule_row);
		}        
	}
	
	class ItemButtonClick implements OnClickListener {
		private int position;
		private Activity mActivity;
		
		ItemButtonClick(int pos, Activity mActivity) {
			position = pos;
			this.mActivity = mActivity;
		}

		public void onClick(View v) {
			PackageManager pm = mActivity.getPackageManager();
		    Intent appStartIntent = pm.getLaunchIntentForPackage(appProject.get(position).getPack());
		    if(null != appStartIntent) {
		    	appStartIntent.addCategory("android.intent.category.LAUNCHER");
		    	appStartIntent.setComponent(new ComponentName(appProject.get(position).getPack(),
		    			appProject.get(position).getClas()));
		    	appStartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    	mActivity.startActivity(appStartIntent);
		    } 
		    else {
		    	startActivity(new Intent(Intent.ACTION_VIEW, 
			    		Uri.parse("market://details?id=" + appProject.get(position).getPack())));
		    }
		}
	}
	
	class LoadDataTask extends AsyncTask<Integer, Integer, String>{  
        
    	@Override  
        protected void onPreExecute() {
    		pbInit.setVisibility(View.VISIBLE);
    		super.onPreExecute();
        }  
          
        @Override  
        protected String doInBackground(Integer... params) {
        	Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            return fetchData();  
        }  
  
        @Override  
        protected void onProgressUpdate(Integer... progress) {    
            super.onProgressUpdate(progress);  
        }  
  
        @Override  
        protected void onPostExecute(String result) {
        	pbInit.setVisibility(View.GONE);
        	if(appProject != null){
        		setView();		
    		}

	        super.onPostExecute(result);  
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

        	PromoteAPP promoteAPP = new PromoteAPP(AboutUsActivity.this);
        	if(!promoteAPP.isPromote) {
	        	new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.leave_app))
	            .setPositiveButton(getResources().getString(R.string.leave), new DialogInterface.OnClickListener() {
	                // do something when the button is clicked
	                public void onClick(DialogInterface arg0, int arg1) {
	                	AboutUsActivity.this.finish();
	                }
	            }).setNegativeButton(getResources().getString(R.string.cancel), null)
	            .show();
		    } else
		    	promoteAPP.promoteAPPExe();

            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }
}
