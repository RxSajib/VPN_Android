
package app.boomboomvpn;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.ErrorReporter;

import app.boomboomvpn.R;
import app.boomboomvpn.core.OpenConnectManagementThread;
import app.boomboomvpn.core.OpenVpnService;
import app.boomboomvpn.core.ProfileManager;
import app.boomboomvpn.core.VPNConnector;

//import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.unity3d.ads.IUnityAdsListener;
//import com.unity3d.ads.UnityAds;
//import com.facebook.ads.*;

public class MainActivity extends Activity {


	public static final String TAG = "OpenConnect";

	private ActionBar mBar;

//	private ArrayList<TabContainer> mTabList = new ArrayList<TabContainer>();

//	private TabContainer mConnectionTab;
	private int mLastTab;
	private boolean mTabsActive;

	private int mConnectionState = OpenConnectManagementThread.STATE_DISCONNECTED;
	private VPNConnector mConn;


	/// top layout bottom
	private LinearLayout estislatebutton, dubutton, wifibutton;
	/// top layout bottom


	private PrefManager prefManager;
	private Dialog LoadingDialog,LocationDialog,ConnectedDialog,DisconnectDialog,RateDialog,CloseDialog, ProDialog;
	private Animation PlayButtonAnim;
	private ImageView PlayButton;
	String SelectedUUID = "";
	private int SpinerIndex = 0;
	private InterstitialAd interstitialAd;
	private OpenVpnService OpenService;
	private boolean ConnectCommand = false;
	//private FirebaseAnalytics mFirebaseAnalytics;
//	private FirebaseFirestore db;
	private Boolean OnOpen = false;
	private String[] FlagIds;
	private Boolean ActiveAnimation;
	private int RateIndex = 0;
	private Boolean CanClose = false;

	private TextView UpInfo, TimeInfo, DownInfo;
	private ImageView Status;
	private ImageView Status2;
	private ImageView Img_Flg;
	private ImageView board;
//	private ImageView LocationSelector;
	private TextView LocationView;
//	private ImageView ProShield, HighSpeed;
	private RecyclerView RV;
	private ImageView Tap_Cnct;
	private int Call_Index;
	Button Dis_disconnect;
	Button Dis_Cancle;
	FrameLayout Dis_FrameLayout;
	ImageView img;
	FrameLayout Con_FrameLayout;
	Button activate;
	Button skip;

	//ad section


	//unity ads
//	private static final String UnityGameID = "";
//	private static final String UnityInPlacementID = "";
//	private static final String UnityRewardPlacementID = "";
	//unity ads

	//facebook ads
//	private com.facebook.ads.InterstitialAd FbInterstitialAd;
//	private static final String FbInPlacementID = "";
//	private static final String FbRewardPlacementID = "";
//	private com.facebook.ads.RewardedVideoAd FbRewardedVideoAd;
	//facebook ads


	//admob ads
	private static final String AdmobAppID = "ca-app-pub-1358361564833828/2915396311";
	private com.google.android.gms.ads.InterstitialAd AdmobInterstitialAd;
	private static final String AdmobInID = "ca-app-pub-1358361564833828/2915396311";
	private com.google.android.gms.ads.reward.RewardedVideoAd AdmobRewardAd;
	private static final String AdmobRewardID = "";
	private com.google.android.gms.ads.AdView mAdView;
//	private static final String NativeAdId = "";
//	private com.google.android.gms.ads.formats.UnifiedNativeAd nativeAd = null;
	com.google.android.gms.ads.AdView LC_Banner;
	//admob ads


	private boolean InAdShown = false;
	private int AdNetwork = 0;
	private boolean RequestForDisconnect = false;
	private boolean PusedByAd = false;
	private boolean AllowInAd = false;
	private int OpencCount = 0;

	private RelativeLayout sharebutton;
	//ad section ends
//	private FirebaseAnalytics mFirebaseAnalytics;

//	public static MainActivity mainActivity;


	private AdView adViewtwo;


//	public void ShowRewardAd(){
//		Random rand = new Random();
//		int n = rand.nextInt(4);
//		n = 1;
//		if(n == 3){
////			if(UnityAds.isReady(UnityRewardPlacementID)){
////				UnityAds.show(this,UnityRewardPlacementID);
////			}else RewardSequanceAd();
//		}else {
//			if(AdmobRewardAd.isLoaded()){
//				AdmobRewardAd.show();
//			}else {
////				AdmobRewardAd.loadAd(AdmobRewardID, new AdRequest.Builder().build());
//				RewardSequanceAd();
//			}
//		}
//	}



//	public void RewardSequanceAd(){
//
//		if(AdmobRewardAd.isLoaded()){
//
//			AdmobRewardAd.show();
//		}else {
////			AdmobRewardAd.loadAd(AdmobRewardID, new AdRequest.Builder().build());
////			if(UnityAds.isReady(UnityRewardPlacementID)){
////				UnityAds.show(this,UnityRewardPlacementID);
////			}else {
////				ConnectedDialog.dismiss();
////			}
//		}
//	}

//	public void InSequanceAd(){
//
//		if(AdmobInterstitialAd.isLoaded()){
//			AdmobInterstitialAd.show();
//			InAdShown = true;
//		}else if(FbInterstitialAd.isAdLoaded() && !FbInterstitialAd.isAdInvalidated()){
//			//AdmobInterstitialAd.loadAd(new AdRequest.Builder().build());
//			FbInterstitialAd.show();
//			InAdShown = true;
//		}else {
//			//FbInterstitialAd.loadAd();
//			//AdmobInterstitialAd.loadAd(new AdRequest.Builder().build());
////			if(UnityAds.isReady(UnityInPlacementID)){
////				Random rand = new Random();
////				int n = rand.nextInt(20);
////				if(n >5 && n<10){
////					UnityAds.show(this,UnityInPlacementID);
////					InAdShown = true;
////				}
////
////			}else {
////				if(RequestForDisconnect){
////					RequestForDisconnect = false;
////					//Disconnect();
////				}
////			}
//		}
//	}



	public void ShowInAd(){


//		Random rand = new Random();
//		if(AdNetwork == 0){
//			AdNetwork = rand.nextInt(2);
//			AdNetwork++;
//		}else {
//			if(AdNetwork == 1){
//				AdNetwork = 2;
//			}else if(AdNetwork == 2){
//				AdNetwork = 1;
//			}
//		}
//		if(AdNetwork == 1){
//			if(AdmobInterstitialAd.isLoaded()){
//				AdmobInterstitialAd.show();
//				InAdShown = true;
//			}else {
//				InSequanceAd();
//			}
//		}else if(AdNetwork == 2){
//			if(FbInterstitialAd.isAdLoaded() && !FbInterstitialAd.isAdInvalidated()){
//				FbInterstitialAd.show();
//				InAdShown = true;
//			}else {
//				InSequanceAd();
//			}
//		}

		if(AdmobInterstitialAd.isLoaded()){
			AdmobInterstitialAd.show();
			InAdShown = true;
		}
	}



	// Implement the IUnityAdsListener interface methods:
//	private class UnityAdsListener implements IUnityAdsListener {
//
//		@Override
//		public void onUnityAdsReady (String placementId) {
//			// Implement functionality for an ad being ready to show.
//		}
//
//		@Override
//		public void onUnityAdsStart (String placementId) {
//			// Implement functionality for a user starting to watch an ad.
//		}
//
//		@Override
//		public void onUnityAdsFinish (String placementId, UnityAds.FinishState finishState) {
//			// Implement functionality for a user finishing an ad.
//			if(finishState == UnityAds.FinishState.COMPLETED && (finishState != UnityAds.FinishState.SKIPPED || finishState != UnityAds.FinishState.ERROR)){
//				HighSpeed.setImageResource(R.drawable.high_speed);
//			}
//		}
//
//		@Override
//		public void onUnityAdsError (UnityAds.UnityAdsError error, String message) {
//			// Implement functionality for a Unity Ads service error occurring.
//		}
//	}

	public void CatchProfile(){

	}


	public void StopWelcome(){
		if(CanClose){
			LoadingDialog.dismiss();
		}else {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					LoadingDialog.dismiss();

				}
			}, 500);
		}


	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);




		sharebutton = findViewById(R.id.ShareButtonID);
		sharebutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");

				String shareMessage = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID ;

				String sharebody = shareMessage;
				String sharesubject = "Download BOOM BOOM VPN and make free whatsaap and imo calls"+"\n"+sharebody;

				intent.putExtra(Intent.EXTRA_TEXT, sharesubject);
				//  intent.putExtra(Intent.EXTRA_SUBJECT, sharebody);
				startActivity(Intent.createChooser(intent, "share with"));
			}
		});


		interstitialAd = new InterstitialAd(getApplicationContext());
		interstitialAd.setAdUnitId("ca-app-pub-1358361564833828/2915396311");
		interstitialAd.loadAd(new AdRequest.Builder().build());


		interstitialAd.setAdListener(new AdListener(){
			@Override
			public void onAdClosed() {
				super.onAdClosed();
			}
		});


		/// top button
		estislatebutton = findViewById(R.id.EtislateID);
		dubutton = findViewById(R.id.DuID);
		wifibutton = findViewById(R.id.WifiID);
		/// top button

		/// new admob
		adViewtwo = findViewById(R.id.AdviewTwo);
		AdRequest adRequest = new AdRequest.Builder().build();
		adViewtwo.loadAd(adRequest);
		/// new admob

		/// top button click

		estislatebutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				estislatebutton.setBackgroundResource(R.drawable.presstochangecolor);
				dubutton.setBackgroundResource(R.drawable.threebuttonbackground);
				wifibutton.setBackgroundResource(R.drawable.threebuttonbackground);

				if(interstitialAd.isLoaded()){
					interstitialAd.show();
					interstitialAd.loadAd(new AdRequest.Builder().build());
				}
				else {

				}
			}
		});

		dubutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				estislatebutton.setBackgroundResource(R.drawable.threebuttonbackground);
				dubutton.setBackgroundResource(R.drawable.presstochangecolor);
				wifibutton.setBackgroundResource(R.drawable.threebuttonbackground);

				if(interstitialAd.isLoaded()){
					interstitialAd.show();
					interstitialAd.loadAd(new AdRequest.Builder().build());
				}
				else {

				}
			}
		});

		wifibutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				estislatebutton.setBackgroundResource(R.drawable.threebuttonbackground);
				dubutton.setBackgroundResource(R.drawable.threebuttonbackground);
				wifibutton.setBackgroundResource(R.drawable.presstochangecolor);
				if(interstitialAd.isLoaded()){
					interstitialAd.show();
					interstitialAd.loadAd(new AdRequest.Builder().build());
				}
				else {

				}
			}
		});
		/// top button click


//		mainActivity = this;
//		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
		OnOpen = true;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		int height=dm.heightPixels;
		double wi=(double)width/(double)dm.xdpi;
		double hi=(double)height/(double)dm.ydpi;

//		if(hi < 4.5){
//			ImageView top_image = (ImageView)findViewById(R.id.header);
//			top_image.setImageResource(R.drawable.top_header_smaller);
//		}else if(hi < 5.0){
//			ImageView top_image = (ImageView)findViewById(R.id.header);
//			top_image.setImageResource(R.drawable.top_header_small);
//		}

		mBar = getActionBar();
		mBar.hide();
		LoadingDialog = new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		LoadingDialog.setContentView(R.layout.loading_window);
		LoadingDialog.show();
		if(Build.VERSION.SDK_INT > 20){
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
		}

//		new Timer().schedule(new TimerTask() {
//			@Override
//			public void run() {
//				CanClose = true;
//
//			}
//		}, 3000);
//

//		ProDialog = new Dialog(this);
//		ProDialog.setContentView(R.layout.pro_window);
//		ProDialog.setCancelable(false);
//		ProDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//		Button ProButton = ProDialog.findViewById(R.id.btn_show_pro);
//		Button CloseButton = ProDialog.findViewById(R.id.btn_close);

//		CloseButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				ProDialog.dismiss();
//			}
//		});
//
//		ProButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//
//				GoPro();
//			}
//		});

		new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                LoadingDialog.dismiss();
            }
        }, 2000);

		PlayButton = (ImageView)findViewById(R.id.play);
		Status = (ImageView)findViewById(R.id.img_stts);
//		Status2 = (ImageView)findViewById(R.id.imageView6);
		DownInfo = (TextView)findViewById(R.id.textView7);
//		TimeInfo = (TextView)findViewById(R.id.time_info);
		UpInfo = (TextView)findViewById(R.id.textView22);
//		LocationSelector = (ImageView)findViewById(R.id.location_selector);
		LocationView = (TextView) findViewById(R.id.location_view);
		Img_Flg = (ImageView) findViewById(R.id.img_flg);
		board = (ImageView) findViewById(R.id.imageView7);
//		ProShield = (ImageView)findViewById(R.id.pro_shield);
//		HighSpeed = (ImageView)findViewById(R.id.high_speed);
//		Tap_Cnct = (ImageView)findViewById(R.id.tap_cnct);

		InitiateLocationWindow();
		InitiateDiscountDialog();
		InitiateConnectedDialog();
//		ActivityManager actManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//		ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
//		actManager.getMemoryInfo(memInfo);
//		long totalMemory = (memInfo.totalMem/1024)/1024;
//
//		if(totalMemory <= 1024){
//			ActiveAnimation = false;
//		}else {
//			ActiveAnimation = true;
//			PlayButtonAnimation(0);
//		}
//

		ActiveAnimation = false;
//		AudienceNetworkAds.initialize(this);
		MobileAds.initialize(this,AdmobAppID );
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {

				InitAds();

			}
		}, 1000);

//		ProfileManager.mProfiles.clear();
//		DataManager.Server_UUIDS = new String[DataManager.Server_IPS.length];
//		for ( int j=0; j<DataManager.Server_IPS.length; j++) {
//			//FeedbackFragment.recordProfileAdd(getBaseContext());
//			String s = ProfileManager.create(DataManager.Server_IPS[j]).getUUID().toString();
//			DataManager.Server_UUIDS[j] = s;
//		}

//		BackTask backTask = new BackTask(this);
//		backTask.execute();

		//connection info listener



		//connection info listener end


		//location selection spinner

//		LocationSelector.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				ShowLocationsWindow();
//			}
//		});



//		LocationView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				ShowLocationsWindow();
//			}
//		});

//		Img_Flg.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				ShowLocationsWindow();
//			}
//		});

		board.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShowLocationsWindow();
			}
		});
		//location selection spinner end

		//connect button
		PlayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ShowInAd();
				if(OpenService.getConnectionState() == OpenConnectManagementThread.STATE_CONNECTED){
					Disconnect();
				}else {
//					Tap_Cnct.setVisibility(View.GONE);
					ConnectCommand = true;
					if(ActiveAnimation){
						PlayButtonAnimation(1);
					}else {
						Toast.makeText(getBaseContext(), "Wait Connecting!" ,
								Toast.LENGTH_LONG).show();
					}
					startVPN();
				}
			}
		});

//		Status2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
////				ShowInAd();
////				if(OpenService.getConnectionState() == OpenConnectManagementThread.STATE_CONNECTED){
////					Disconnect();
////				}else {
//////					Tap_Cnct.setVisibility(View.GONE);
////					ConnectCommand = true;
////					if(ActiveAnimation){
////						PlayButtonAnimation(1);
////					}else {
////						Toast.makeText(getBaseContext(), "Wait Connecting!" ,
////								Toast.LENGTH_LONG).show();
////					}
////					startVPN();
////				}
//			}
//		});
		//connect button end


		prefManager = new PrefManager(getBaseContext(),PrefManager.PRF_APP_DATA,PrefManager.MODE_READ);
		OpencCount = prefManager.ReadInt(PrefManager.KEY_OPEN_COUNT);
		RateIndex  = prefManager.ReadInt(PrefManager.KEY_RATE_INDEX);
		if(OpencCount != 0){
//			Tap_Cnct.setVisibility(View.GONE);
		}
		OpencCount++;
//		if(OpencCount>=3){
//			PackageManager pm = getPackageManager();
//			if(!isPackageInstalled("app.speedvpnpro", pm))
//				ProDialog.show();
//		}
//		if(OpencCount == 10){
//			Bundle params = new Bundle();
//			params.putString("rate_index", Integer.toString(RateIndex));
////			mFirebaseAnalytics.logEvent("ten_open", params);
//		}
		prefManager = new PrefManager(getBaseContext(),PrefManager.PRF_APP_DATA,PrefManager.MODE_WRITE);
		prefManager.SaveIntData(PrefManager.KEY_OPEN_COUNT,OpencCount);
		AllowInAd = true;
//		if(OpencCount>3){
//			AllowInAd = true;
//		}

//		InitiaterateDialog();
//		final UnityAdsListener myAdsListener = new UnityAdsListener ();
//		//unity ads
//		UnityAds.initialize(this, UnityGameID, myAdsListener);




	}

	private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
		try {
			packageManager.getPackageInfo(packageName, 0);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}


	public void InitAds(){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				//fb ads
				// Initialize the Audience Network SDK

//				FbInterstitialAd = new com.facebook.ads.InterstitialAd(getBaseContext(), FbInPlacementID);
//				FbInterstitialAd.setAdListener(new com.facebook.ads.InterstitialAdListener() {
//					@Override
//					public void onInterstitialDisplayed(Ad ad) {
//
//					}
//
//					@Override
//					public void onInterstitialDismissed(Ad ad) {
//
//						FbInterstitialAd.loadAd();
//						if(RequestForDisconnect){
//							RequestForDisconnect = false;
//							//Disconnect();
//						}
//					}
//
//					@Override
//					public void onError(Ad ad, AdError adError) {
//
//					}
//
//					@Override
//					public void onAdLoaded(Ad ad) {
//						//FbInterstitialAd.show();
//					}
//
//					@Override
//					public void onAdClicked(Ad ad) {
//
//					}
//
//					@Override
//					public void onLoggingImpression(Ad ad) {
//
//					}
//				});
//				FbInterstitialAd.loadAd();
				//admb ads

				AdmobInterstitialAd = new com.google.android.gms.ads.InterstitialAd(getBaseContext());
				AdmobInterstitialAd.setAdUnitId(AdmobInID);
				AdmobInterstitialAd.loadAd(new AdRequest.Builder().build());
				AdmobInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener(){
					@Override
					public void onAdLoaded() {
						// Code to be executed when an ad finishes loading.
					}

					@Override
					public void onAdFailedToLoad(int errorCode) {
						// Code to be executed when an ad request fails.
					}

					@Override
					public void onAdOpened() {
						// Code to be executed when the ad is displayed.
					}

					@Override
					public void onAdClicked() {
						// Code to be executed when the user clicks on an ad.
					}

					@Override
					public void onAdLeftApplication() {
						// Code to be executed when the user has left the app.
					}

					@Override
					public void onAdClosed() {
						// Code to be executed when the interstitial ad is closed.
						AdmobInterstitialAd.loadAd(new AdRequest.Builder().build());
						if(RequestForDisconnect){
							RequestForDisconnect = false;
							//Disconnect();
						}
					}
				});

//				AdmobRewardAd = MobileAds.getRewardedVideoAdInstance(getBaseContext());
//				AdmobRewardAd.setRewardedVideoAdListener(new com.google.android.gms.ads.reward.RewardedVideoAdListener() {
//					@Override
//					public void onRewardedVideoAdLoaded() {
//
//					}
//
//					@Override
//					public void onRewardedVideoAdOpened() {
//
//					}
//
//					@Override
//					public void onRewardedVideoStarted() {
//
//					}
//
//					@Override
//					public void onRewardedVideoAdClosed() {
//						AdmobRewardAd.loadAd(AdmobRewardID, new AdRequest.Builder().build());
//					}
//
//					@Override
//					public void onRewarded(RewardItem rewardItem) {
//
//					}
//
//					@Override
//					public void onRewardedVideoAdLeftApplication() {
//
//					}
//
//					@Override
//					public void onRewardedVideoAdFailedToLoad(int i) {
//
//					}
//
//					@Override
//					public void onRewardedVideoCompleted() {
//
//						HighSpeed.setImageResource(R.drawable.high_speed);
//
//					}
//				});
//				AdmobRewardAd.loadAd(AdmobRewardID, new AdRequest.Builder().build());
				mAdView = findViewById(R.id.adView);
				mAdView.loadAd(new AdRequest.Builder().build());
//				LoadNativeAd();
			}
		});

	}

	public void InitiateLocationWindow(){
		LocationDialog = new Dialog(this,android.R.style.Theme_Black_NoTitleBar);
		LocationDialog.setContentView(R.layout.location_window);

		RV = (RecyclerView) LocationDialog.findViewById(R.id.rv) ;
		RV.setLayoutManager(new LinearLayoutManager(this));
		RV.setAdapter(new LocationRecyclerAdapter(DataManager.Server_NameS,DataManager.FlagIds, new LocationRecyclerAdapter.OnItemListener() {
			@Override
			public void OnItemClick(int index) {
				prefManager = new PrefManager(getBaseContext(),PrefManager.PRF_APP_DATA,PrefManager.MODE_WRITE);
				SpinerIndex = index;
				prefManager.SaveIntData(PrefManager.KEY_SPINER_INDEX,SpinerIndex);

				LocationView.setText(DataManager.Server_NameS[SpinerIndex]);
				Img_Flg.setImageResource(DataManager.FlagIds[SpinerIndex]);
				ConnectCommand = true;
				if(ActiveAnimation){
					PlayButtonAnimation(1);
				}else {
					Toast.makeText(getBaseContext(), "Wait Connecting!" ,
							Toast.LENGTH_LONG).show();
				}
				startVPN();
				LC_Banner.destroy();
				LocationDialog.dismiss();

			}
		}));


		LC_Banner = LocationDialog.findViewById(R.id.lc_banner);
	}

	public void ShowLocationsWindow(){


		LC_Banner.loadAd(new AdRequest.Builder().build());
		LocationDialog.show();

//		if(AllowInAd){
//			ShowInAd();
//			InAdShown = true;
//		}

	}

	public void GoPro(){
//		final String appPackageName = "app.speedvpnpro"; // getPackageName() from Context or Activity object
//		try {
//			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//		} catch (android.content.ActivityNotFoundException anfe) {
//			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//		}

	}

	public void Rate(){
//		final String appPackageName = "app.speedvpn"; // getPackageName() from Context or Activity object
//		try {
//			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//		} catch (android.content.ActivityNotFoundException anfe) {
//			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//		}
//		return;
	}

	public void InitiaterateDialog(){
		if(OpencCount <2 || RateIndex == 420)
			return ;


		RateDialog = new Dialog(this);
		RateDialog.setContentView(R.layout.rating_window);
		Button rateButton = (Button)RateDialog.findViewById(R.id.btn_rt);
		TextView laterButton = (TextView) RateDialog.findViewById(R.id.btn_later);
		rateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				prefManager = new PrefManager(getBaseContext(),PrefManager.PRF_APP_DATA,PrefManager.MODE_WRITE);
				prefManager.SaveIntData(PrefManager.KEY_RATE_INDEX,420);
				RateIndex = 420;
				RateDialog.dismiss();
				Rate();
			}
		});

		laterButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				RateDialog.dismiss();
				if(Call_Index == 1)
					finish();
			}
		});

		RateDialog.setCancelable(false);
		RateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}


	public boolean ShowRateDialog(final int call_index){

//		if(OpencCount <2 || RateIndex == 420)
//			return false;
//
//		Call_Index = call_index;
//		RateDialog.show();
		return true;
	}



	public void InitiateDiscountDialog(){

		DisconnectDialog = new Dialog(this);
		DisconnectDialog.setContentView(R.layout.disconnect_window);
		Dis_disconnect = (Button) DisconnectDialog.findViewById(R.id.btn_dscnt);
		Dis_Cancle = (Button) DisconnectDialog.findViewById(R.id.btn_cncl);
//		Dis_FrameLayout = DisconnectDialog.findViewById(R.id.dscnt_ad);

		Dis_disconnect.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mConn.service.stopVPN();
//					Random rand = new Random();
//					int n = rand.nextInt(4);
//					if(n<2){
//						ShowRateDialog(0);
//					}

					DisconnectDialog.dismiss();
				}
			});
		Dis_Cancle.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					DisconnectDialog.dismiss();
				}
			});

		DisconnectDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		DisconnectDialog.setCancelable(false);
	}


	public void Disconnect(){

//		if(nativeAd != null ){
//			Dis_disconnect.setVisibility(View.GONE);
//			Dis_Cancle.setVisibility(View.GONE);
//			com.google.android.gms.ads.formats.UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
//					.inflate(R.layout.ntv_adview, null);
//			populateUnifiedNativeAdView(nativeAd, adView , 1);
//			Dis_FrameLayout.removeAllViews();
//			Dis_FrameLayout.addView(adView);
//		}

		DisconnectDialog.show();
	}


//	public void LoadNativeAd(){
//
//		AdLoader.Builder builder = new AdLoader.Builder(this, NativeAdId);
//		builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
//			// OnUnifiedNativeAdLoadedListener implementation.
//			@Override
//			public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//				// You must call destroy on old ads when you are done with them,
//				// otherwise you will have a memory leak.
//				if (nativeAd != null) {
//					nativeAd.destroy();
//					nativeAd = null;
//				}
//				nativeAd = unifiedNativeAd;
////				FrameLayout frameLayout =
////						findViewById(R.id.fl_adplaceholder);
////				UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
////						.inflate(R.layout.ad_unified, null);
////				populateUnifiedNativeAdView(unifiedNativeAd, adView);
////				frameLayout.removeAllViews();
////				frameLayout.addView(adView);
//			}
//
//		});
//
//		VideoOptions videoOptions = new VideoOptions.Builder()
//				.setStartMuted(true)
//				.build();
//
//		NativeAdOptions adOptions = new NativeAdOptions.Builder()
//				.setVideoOptions(videoOptions)
//				.build();
//
//		builder.withNativeAdOptions(adOptions);
//
//		AdLoader adLoader = builder.withAdListener(new com.google.android.gms.ads.AdListener() {
//			@Override
//			public void onAdFailedToLoad(int errorCode) {
//
//			}
//		}).build();
//
//		adLoader.loadAd(new AdRequest.Builder().build());
//	}


//	private void populateUnifiedNativeAdView(com.google.android.gms.ads.formats.UnifiedNativeAd native_Ad, com.google.android.gms.ads.formats.UnifiedNativeAdView adView, int DialogIndex) {
//
//		if(native_Ad == null)
//			return;
//
//
//
//		TextView activate = (TextView) adView.findViewById(R.id.hi_speed_btn);
//		TextView skip = (TextView) adView.findViewById(R.id.nrml_btn);
//
//		if(DialogIndex == 0){
//			activate.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					ShowRewardAd();
//					ConnectedDialog.dismiss();
//					LoadNativeAd();
//				}
//			});
//			skip.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					ConnectedDialog.dismiss();
//					LoadNativeAd();
//				}
//			});
//
//		}else if(DialogIndex == 1){
//			activate.setText("DISCONNECT");
//			skip.setText("CANCEL");
//			activate.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					mConn.service.stopVPN();
//					ShowRateDialog(0);
//					DisconnectDialog.dismiss();
////					LoadNativeAd();
//				}
//			});
//			skip.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					DisconnectDialog.dismiss();
////					LoadNativeAd();
//				}
//			});
//		}else if(DialogIndex == 2){
//
//			activate.setText("CLOSE");
//			skip.setText("STAY");
//			activate.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					finish();
//				}
//			});
//			skip.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					CloseDialog.dismiss();
////					LoadNativeAd();
//				}
//			});
//		}
//
//
//
//		com.google.android.gms.ads.formats.MediaView mediaView = adView.findViewById(R.id.ad_media);
//		adView.setMediaView(mediaView);
//
//
//		// Set other ad assets.
//		adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
//		adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
//		adView.setIconView(adView.findViewById(R.id.ad_app_icon));
//		adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
//		adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
//
//		// The headline is guaranteed to be in every UnifiedNativeAd.
//		((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
//
//		// These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
//		// check before trying to display them.
//
//
//		if (native_Ad.getCallToAction() == null) {
//			adView.getCallToActionView().setVisibility(View.INVISIBLE);
//		} else {
//			adView.getCallToActionView().setVisibility(View.VISIBLE);
//			((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
//		}
//
//		if (native_Ad.getIcon() == null) {
//			adView.getIconView().setVisibility(View.GONE);
//		} else {
//			((ImageView) adView.getIconView()).setImageDrawable(
//					nativeAd.getIcon().getDrawable());
//			adView.getIconView().setVisibility(View.VISIBLE);
//		}
//
//
//
//		if (nativeAd.getStarRating() == null) {
//			adView.getStarRatingView().setVisibility(View.INVISIBLE);
//		} else {
//			((RatingBar) adView.getStarRatingView())
//					.setRating(nativeAd.getStarRating().floatValue());
//			adView.getStarRatingView().setVisibility(View.VISIBLE);
//		}
//
//		if (nativeAd.getAdvertiser() == null) {
//			adView.getAdvertiserView().setVisibility(View.INVISIBLE);
//		} else {
//			((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
//			adView.getAdvertiserView().setVisibility(View.VISIBLE);
//		}
//
//		// This method tells the Google Mobile Ads SDK that you have finished populating your
//		// native ad view with this native ad. The SDK will populate the adView's MediaView
//		// with the media content from this native ad.
//		adView.setNativeAd(nativeAd);
//
//		// Get the video controller for the ad. One will always be provided, even if the ad doesn't
//		// have a video asset.
//		VideoController vc = nativeAd.getVideoController();
//
//	}



	public void InitiateConnectedDialog(){

//		ConnectedDialog = new Dialog(this);
//		ConnectedDialog.setContentView(R.layout.connected_window);
//		img = (ImageView)ConnectedDialog.findViewById(R.id.hi_speed_icon);
//		Con_FrameLayout = ConnectedDialog.findViewById(R.id.ad_layout);
//		activate = (Button) ConnectedDialog.findViewById(R.id.btn_actv);
//		skip = (Button) ConnectedDialog.findViewById(R.id.btn_skp);
//
//
//
//		activate.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					ShowRewardAd();
//					ConnectedDialog.dismiss();
//				}
//		});
//		skip.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					ConnectedDialog.dismiss();
//				}
//		});
//
//		ConnectedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//		ConnectedDialog.setCancelable(false);
	}


	public void ShowConnectedDialog(){


//		if(OpencCount>3){
//			img.setImageResource(R.drawable.nottick);
//		}else {
//			HighSpeed.setImageResource(R.drawable.high_speed);
//		}
//
//		if(nativeAd != null ){
//			com.google.android.gms.ads.formats.UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
//					.inflate(R.layout.ntv_adview, null);
//			populateUnifiedNativeAdView(nativeAd, adView, 0);
//			Con_FrameLayout.removeAllViews();
//			Con_FrameLayout.addView(adView);
//		}else {
//			activate.setVisibility(View.VISIBLE);
//			skip.setVisibility(View.VISIBLE);
//		}
//		ConnectedDialog.show();

	}

	public void UpdateUI(OpenVpnService service){
		int state = service.getConnectionState();
		service.startActiveDialog(this);

		if (mConnectionState != state) {
			if (state == OpenConnectManagementThread.STATE_DISCONNECTED) {
				PlayButton.setImageResource(R.drawable.connect_button);
				Status.setImageResource(R.drawable.appicon);
//				Status2.setImageResource(R.drawable.top_header_smaller_off);
//				ProShield.setImageResource(R.drawable.shield_off);
//				HighSpeed.setImageResource(R.drawable.high_speed_off);
				PlayButton.setEnabled(true);
//				Status2.setEnabled(true);
				if(!ActiveAnimation)
					PlayButton.setImageAlpha(255);

				LocationView.setEnabled(true);
//				LocationSelector.setImageAlpha(255);
				Img_Flg.setEnabled(true);
				Img_Flg.setImageAlpha(255);

			} else if (state == OpenConnectManagementThread.STATE_CONNECTED) {

				if(ConnectCommand){
//					if(!ActiveAnimation)
//						PlayButton.setImageAlpha(255);

					Status.setImageResource(R.drawable.appicon);
//					Status2.setImageResource(R.drawable.top_header_smaller);
					PlayButton.setEnabled(true);
//					Status2.setEnabled(true);
//					ProShield.setImageResource(R.drawable.sheild);
					//HighSpeed.setImageResource(R.drawable.high_speed);
					ConnectCommand = false;
//					if(!InAdShown && AllowInAd)
//					ShowInAd();
//					ShowConnectedDialog();
					LocationView.setEnabled(true);
//					LocationView.setImageAlpha(255);
					Img_Flg.setEnabled(true);
					Img_Flg.setImageAlpha(255);


					if(ActiveAnimation)
						PlayButtonAnimation(0);
				}
				PlayButton.setImageAlpha(255);
				PlayButton.setImageResource(R.drawable.disconnect_button);
				//PlayButton.setImageResource(R.drawable.puse);

			} else if(state == OpenConnectManagementThread.STATE_AUTHENTICATING || state ==  OpenConnectManagementThread.STATE_CONNECTING ){
				PlayButton.setEnabled(false);
//				Status2.setEnabled(false);
				PlayButton.setImageAlpha(100);
				//Status2.setImageAlpha(100);
				LocationView.setEnabled(false);
//				LocationSelector.setImageAlpha(100);
				Img_Flg.setEnabled(false);
				Img_Flg.setImageAlpha(100);
			}
			mConnectionState = state;
		}

		if (state == OpenConnectManagementThread.STATE_CONNECTED) {


			DownInfo.setText(
					OpenVpnService.humanReadableByteCount(mConn.deltaStats.rxBytes, true) +
							"/s\n" +
					OpenVpnService.humanReadableByteCount(mConn.newStats.rxBytes, false)
			);
//			TimeInfo.setText(OpenVpnService.formatElapsedTime(service.startTime.getTime()));
			UpInfo.setText(
					OpenVpnService.humanReadableByteCount(mConn.deltaStats.txBytes, true) +
							"/s\n" +
					OpenVpnService.humanReadableByteCount(mConn.newStats.txBytes, false));

		}else if (mConnectionState == OpenConnectManagementThread.STATE_DISCONNECTED){

			//PlayButtonAnimation(0);

		}

	}



	public void PlayButtonAnimation(int index){
		if(index == 0){
			PlayButtonAnim = new ScaleAnimation(1f,1.02f,1f,1.02f,Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 1f);
			PlayButtonAnim.setDuration(350);
			PlayButtonAnim.setRepeatCount(Animation.INFINITE);
			PlayButtonAnim.setRepeatMode(Animation.REVERSE);
		}else if(index == 1){
			PlayButtonAnim = new RotateAnimation(0, 360,
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
					0.5f);
			PlayButtonAnim.setDuration(500);
			PlayButtonAnim.setRepeatCount(Animation.INFINITE);
		}

		PlayButton.startAnimation(PlayButtonAnim);

	}






	private void reportBadRom(Exception e) {
		ACRAConfiguration cfg = ACRA.getConfig();
		cfg.setResDialogText(R.string.bad_rom_text);
		cfg.setResDialogCommentPrompt(R.string.bad_rom_comment_prompt);
		ACRA.setConfig(cfg);

		ErrorReporter er = ACRA.getErrorReporter();
		er.putCustomData("cause", "reportBadRom");
		er.handleException(e);
	}


	public void  s( String s){

		Toast.makeText( getApplicationContext() , s,
				Toast.LENGTH_SHORT).show();

	}

	private void startVPN() {

		Intent prepIntent;
		try {
			prepIntent = VpnService.prepare(this);
		} catch (Exception e) {
			reportBadRom(e);
			//finish();
			return;
		}

		if (prepIntent != null) {
			try {
				startActivityForResult(prepIntent, 0);
			} catch (Exception e) {
				reportBadRom(e);
				//finish();
				return;
			}
		} else {
			onActivityResult(0, RESULT_OK, null);
		}
	}

	/* Called by Android OS after user clicks "OK" on VpnService.prepare() dialog */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		setResult(resultCode);

		//SelectedUUID = ProfileManager.
		if (resultCode == RESULT_OK) {
			//PlayButton.setEnabled(false);
			//LocationSelector.setEnabled(false);
			//LocationSelector.setImageAlpha(100);

//			if(!ActiveAnimation)
//				PlayButton.setImageAlpha(100);

//			if(SpinerIndex == 0){
//				Random rand = new Random();
//				int n = rand.nextInt(DataManager.Server_UUIDS.length);
//				SelectedUUID = DataManager.Server_UUIDS[n];
//
//			}else if(SpinerIndex > 0 && SpinerIndex < 5){
//				Random rand = new Random();
//				int n = rand.nextInt(4);
//				SelectedUUID = DataManager.Server_UUIDS[n];
//			}else {
//				SelectedUUID = DataManager.Server_UUIDS[SpinerIndex - 1];
//			}



			int n;
			if(SpinerIndex == 0){
				Random rand = new Random();
				n = rand.nextInt(DataManager.Server_IPS.length);

			}
//			else if(SpinerIndex > 0 && SpinerIndex < 5){
//				Random rand = new Random();
//				n = rand.nextInt(4);
//			}
			else {
				n = SpinerIndex - 1;
			}

			ProfileManager.mProfiles.clear();

			String s = ProfileManager.create(DataManager.Server_IPS[n]).getUUID().toString();



			Intent intent = new Intent(getBaseContext(), OpenVpnService.class);
			intent.putExtra(OpenVpnService.EXTRA_UUID, s);
			startService(intent);

		}else {

			if(ActiveAnimation){
				PlayButtonAnimation(0);
			}else {

			}


		}
		// finish();
	}




	@Override
	protected void onSaveInstanceState(Bundle b) {
		super.onSaveInstanceState(b);
		b.putInt("active_tab", mLastTab);
	}



	@Override
	protected void onResume() {
		super.onResume();

		mConn = new VPNConnector(this, false) {
			@Override
			public void onUpdate(OpenVpnService service) {
				OpenService = service;
				UpdateUI(service);
				if(OnOpen){
					if(OpenService.getConnectionState() == OpenConnectManagementThread.STATE_CONNECTED){
						prefManager = new PrefManager(getBaseContext(),PrefManager.PRF_APP_DATA,PrefManager.MODE_READ);
						SpinerIndex = prefManager.ReadInt(PrefManager.KEY_SPINER_INDEX);
//						ProShield.setImageResource(R.drawable.sheild);
					}else {
						prefManager = new PrefManager(getBaseContext(),PrefManager.PRF_APP_DATA,PrefManager.MODE_WRITE);
						SpinerIndex = 0;
						prefManager.SaveIntData(PrefManager.KEY_SPINER_INDEX,SpinerIndex);
//						ProShield.setImageResource(R.drawable.shield_off);
					}
					LocationView.setText(DataManager.Server_NameS[SpinerIndex]);
					Img_Flg.setImageResource(DataManager.FlagIds[SpinerIndex]);
					OnOpen = false;
				}
			}
		};


	}


	@Override
	protected void onDestroy() {
//		if (FbInterstitialAd != null) {
//			FbInterstitialAd.destroy();
//		}
//		if (nativeAd != null) {
//			nativeAd.destroy();
//		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mConn.stopActiveDialog();
		mConn.unbind();
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		//super.onBackPressed();
//		if(!ShowRateDialog(1)){
//			if(AllowInAd)
//				ShowInAd();
			CloseDialog = new Dialog(this);
			CloseDialog.setContentView(R.layout.close_window);

			Button Close = (Button)CloseDialog.findViewById(R.id.btn_close);
			Button Stay = (Button)CloseDialog.findViewById(R.id.btn_stay);


//			if(nativeAd != null ){
//				Close.setVisibility(View.GONE);
//				Stay.setVisibility(View.GONE);
//				FrameLayout frameLayout = CloseDialog.findViewById(R.id.ad_bye);
//				com.google.android.gms.ads.formats.UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
//						.inflate(R.layout.ntv_adview, null);
//				populateUnifiedNativeAdView(nativeAd, adView , 2);
//				frameLayout.removeAllViews();
//				frameLayout.addView(adView);
//			}else {


				Close.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						finish();
					}
				});
				Stay.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						CloseDialog.dismiss();
					}
				});

//			}



			CloseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			CloseDialog.show();


//		}

	}

//	protected class TabContainer implements ActionBar.TabListener {
//		private Fragment mFragment;
//		private boolean mActive;
//		public Tab tab;
//		public int idx;
//
//		public void replace(int titleResId, Fragment frag) {
//			if (mActive) {
//				getFragmentManager().beginTransaction().remove(mFragment).commit();
//			}
//
//			mFragment = frag;
//			tab.setText(titleResId);
//
//			if (idx == mLastTab) {
//				getFragmentManager().beginTransaction()
//					.setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
//					.replace(android.R.id.content, mFragment)
//					.commit();
//				mActive = true;
//			} else {
//				mActive = false;
//			}
//		}
//
//		public TabContainer(int idx, int titleResId, Fragment frag) {
//			this.idx = idx;
//			this.mFragment = frag;
//			tab = getActionBar().newTab()
//					.setText(titleResId)
//					.setTabListener(this);
//		}
//
//		@Override
//		public void onTabSelected(Tab tab, FragmentTransaction ft) {
//			if (mTabsActive) {
//				if (idx < mLastTab) {
//					ft.setCustomAnimations(R.animator.fragment_slide_right_enter,
//							R.animator.fragment_slide_right_exit);
//				} else if (idx > mLastTab) {
//					ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
//							R.animator.fragment_slide_left_exit);
//				}
//			}
//
//			mLastTab = idx;
//			ft.replace(android.R.id.content, mFragment);
//			mActive = true;
//		}
//
//		@Override
//		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//			if (mActive) {
//				ft.remove(mFragment);
//				mActive = false;
//			}
//		}
//
//		@Override
//		public void onTabReselected(Tab tab, FragmentTransaction ft) {
//		}
//	}




}
