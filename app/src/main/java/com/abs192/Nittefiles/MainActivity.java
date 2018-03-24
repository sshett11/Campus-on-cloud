package com.abs192.Nittefiles;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.abs192.Nittefiles.frags.FragFolder;
import com.abs192.Nittefiles.frags.FragHome;
import com.abs192.Nittefiles.frags.FragSettings;
import com.abs192.Nittefiles.frags.FragTeacher;
import com.abs192.Nittefiles.frags.FragWebsis;
import com.abs192.Nittefiles.frags.Fragforum;
import com.abs192.Nittefiles.misc.NavDrawerItem;
import com.abs192.Nittefiles.misc.NavDrawerListAdapter;
import com.abs192.Nittefiles.misc.logtheshit;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.abs192.Nittefiles.TeacherLogin;
import com.pushbots.push.Pushbots;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;


public class MainActivity extends SherlockFragmentActivity {
	String collector="";
	//TeacherLogin ln = new TeacherLogin();
	//int mx = TeacherLogin.c
	private String[] drawerListItems = {"","Files", "Enter Forum", "Offline Files",
			"Settings", "Teacher"};
	private int[] drawerIconItems = {24,R.drawable.ic_home, R.drawable.ic_mu,
			R.drawable.ic_my_folder, R.drawable.ic_settings, R.drawable.ic_error_left};
	String data = "null";
	ImageView mImageview;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	private ActionBarDrawerToggle mDrawerToggle;
	private static int position;
	Stack<Integer> stackFrags;
	private NavDrawerListAdapter adapter;
	private ArrayList<NavDrawerItem> navDrawerItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Pushbots.sharedInstance().init(this);
		Bundle extras = getIntent().getExtras();
		View header=getLayoutInflater().inflate(R.layout.header, null);
		//collector=extras.getString("Nitte-notify");
		GoogleAnalytics.getInstance(this).setDryRun(true);

		stackFrags = new Stack<Integer>();

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		 //mImageview=(ImageView)header.findViewById(R.id.iview);
      /*try {
		   mDrawerList.setAdapter(new ArrayAdapter<String>(
				   getActionBar().getThemedContext(),
				   R.layout.colorchange, //new Layout created
				   R.id.label, drawerListItems                 //TextView inside that new Layout

		   ));
	   }
	   catch(NullPointerException e)
	   {

	   }*/

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		mDrawerLayout.setScrimColor(Color.parseColor("#E6d4ffea"));


		int rdrawer = 1;
		if (Build.VERSION.SDK_INT >= 11)
			rdrawer = R.layout.nav_drawer_list;
		else
			rdrawer = R.layout.drawer_list_item_old;

		// mDrawerList.setAdapter(new ArrayAdapter<String>(this, rdrawer,
		// drawerListItems));

		navDrawerItems = new ArrayList<NavDrawerItem>();


		for (int i = 1; i < drawerListItems.length; i++)
		{
			navDrawerItems.add(new NavDrawerItem(drawerListItems[i],
					drawerIconItems[i]));
		}


		adapter = new NavDrawerListAdapter(this, rdrawer, navDrawerItems);

		mDrawerList.setAdapter(adapter);
		mDrawerList.addHeaderView(header);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		// enable ActionBar app icon to behave as action to toggle nav drawer


		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */mDrawerLayout, /* DrawerLayout object */R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open, /* "open drawer" description for accessibility */
				R.string.drawer_close /* "close drawer" description for accessibility */
		) {

			public void onDrawerClosed(View view)
			{
				getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		Pushbots.sharedInstance().init(this);
		//Bundle extras = getIntent().getExtras();
		//collector=extras.getString("Nitte-notify");
		change();
	}

	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);

	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	public void change() {
		Bundle b = getIntent().getExtras();
		if (b == null) {
			Log.i("MAIN TAG", "bundle null");
			int a = new logtheshit(getApplicationContext()).getDefaultPage();
			selectItem1(a);
		} else {
			String a = b.getString("FORCE");
			Log.i("MAIN TAG", "force " + a);
			data = b.getString("url");
			Log.i("MAIN TAG", "data= " + data);
			if (a != null && a.equals("FOLDERFORCE")) {
				selectItem1(3);
			} else if (a != null && a.equals("HOMEFORCE")) {
				selectItem1(1);
			}
		}
		invalidateOptionsMenu();
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			invalidateOptionsMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
		//Pushbots.sharedInstance().init(this);
	}

	public static void setPosition(int pos) {
		position = pos;
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.refresh).setVisible(!drawerOpen && position == 1);
		//menu.findItem(R.id.notify);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.

		// Handle action buttons
		switch (item.getItemId()) {
			case R.id.refresh:
				try {
					((FragHome) getSupportFragmentManager().findFragmentByTag(
							"HOME")).refresh();
				} catch (Exception e) {
					e.printStackTrace();

				}
				return true;
			case android.R.id.home:

				if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					mDrawerLayout.openDrawer(mDrawerList);
				}
				return true;

			case R.id.notify:
				   Intent nn = new Intent(this,Collected.class);
				   //nn.putExtra("Nitte-notify",collector);
				   startActivity(nn);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	class DrawerItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
								long arg3) {

			if (pos == position)
			{
				mDrawerLayout.closeDrawer(mDrawerList);
				//Global.pos = pos;
			}

			//else if(mx>1)
				//selectItem1(pos);
			else {

				selectItem1(pos);
			}
			//else
				//selectItem(pos);
		}
	}


	public void selectItem1(int position) {
		String TAG = "HOME";
		setPosition(position);
		pushhhinStack(position);
		Log.i("selectTAG", "stack:" + stackFrags.toString());
		Fragment fragment;


		if(position==0)
		{
			fragment = new FragHome();
			TAG = "HOME";
			FragmentManager fragmentManager = getSupportFragmentManager();

			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment, TAG).commit();
			mDrawerList.setItemChecked(position, true);
			setTitle(drawerListItems[position]);
			mDrawerLayout.closeDrawer(mDrawerList);

		}
		if(position==1)
		{
			fragment = new FragHome();
			TAG = "HOME";
			FragmentManager fragmentManager = getSupportFragmentManager();

			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment, TAG).commit();
			mDrawerList.setItemChecked(position, true);
			setTitle(drawerListItems[position]);
			mDrawerLayout.closeDrawer(mDrawerList);

		}
		if(position==2)
		{
			fragment = new Fragforum();
			TAG = "Websis";
			FragmentManager fragmentManager = getSupportFragmentManager();

			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment, TAG).commit();
			mDrawerList.setItemChecked(position, true);
			setTitle(drawerListItems[position]);
			mDrawerLayout.closeDrawer(mDrawerList);

		}

		if(position==3)
		{
			fragment = new FragFolder();
			TAG = "HOME";
			FragmentManager fragmentManager = getSupportFragmentManager();

			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment, TAG).commit();
			mDrawerList.setItemChecked(position, true);
			setTitle(drawerListItems[position]);
			mDrawerLayout.closeDrawer(mDrawerList);

		}
		if(position==4)
		{
			fragment = new FragSettings();
			TAG = "HOME";
			FragmentManager fragmentManager = getSupportFragmentManager();

			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment, TAG).commit();
			mDrawerList.setItemChecked(position, true);
			setTitle(drawerListItems[position]);
			mDrawerLayout.closeDrawer(mDrawerList);

		}

		if(position==5)
		{
			if(Global.posfin==0)
			{
				Intent tent = new Intent(this,TeacherLogin.class);
				startActivity(tent);
				selectItem1(Global.pos);
				// Global.posfin++;
				/*position=Global.pos;
				Bundle args = new Bundle();
				Log.i("SELECT ITEM DATA", data);
				args.putString("url", data);
				fragment.setArguments(args);
				FragmentManager fragmentManager = getSupportFragmentManager();

				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment, TAG).commit();
				*/
				//mDrawerList.setItemChecked(Global.pos, true);
				//setTitle(drawerListItems[Global.pos]);
				//mDrawerLayout.closeDrawer(mDrawerList);
			}
			else if(Global.posfin>=1)
			{	fragment = new FragTeacher();


				Bundle args = new Bundle();
				Log.i("SELECT ITEM DATA", data);
				args.putString("url", data);
				fragment.setArguments(args);
				FragmentManager fragmentManager = getSupportFragmentManager();

				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment, TAG).commit();
				mDrawerList.setItemChecked(position, true);
				setTitle(drawerListItems[position]);
				mDrawerLayout.closeDrawer(mDrawerList);
			}



		}


		Global.pos = position;

	}




	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {

			if (mDrawerLayout.isDrawerOpen(mDrawerList))
				mDrawerLayout.closeDrawer(mDrawerList);
			else
				mDrawerLayout.openDrawer(mDrawerList);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mDrawerLayout.isDrawerOpen(mDrawerList))
				mDrawerLayout.closeDrawer(mDrawerList);
			else
				back();
			return true;

		} else {

			return super.onKeyDown(keyCode, event);
		}
	}

	private void pushhhinStack(int pos) {

		while (stackFrags.contains(pos))
			stackFrags.removeElement(pos);

		stackFrags.push(pos);

	}

	private void back() {
		Log.i("BackTAG", "start" + stackFrags.toString());
		try {
			if (stackFrags.size() == 1) {
				finish();
			} else {
				stackFrags.pop();
				int pos = stackFrags.pop();
				selectItem1(pos);
			}
		} catch (Exception e) {
			finish();
		}
		Log.i("BackTAG", "end" + stackFrags.toString());
	}

}
