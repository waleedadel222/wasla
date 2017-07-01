package com.example.wasel.Stuff;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wasel.R;

public class StuffTab extends FragmentActivity implements TabListener,
		OnPageChangeListener {
	
	//public static final int logout_menu = Menu.FIRST;
	
	ViewPager viewpager;
	ActionBar actionbar;
	android.support.v4.app.FragmentManager FragMang;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_stuff);

		viewpager = (ViewPager) findViewById(R.id.stuffpager);
		viewpager.setOnPageChangeListener(this);

		FragMang = getSupportFragmentManager();

		Adapter adapter = new Adapter(FragMang);
		viewpager.setAdapter(adapter);
		actionbar = getActionBar();

		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab StuffTab1 = actionbar.newTab();
		StuffTab1.setText("News");
		StuffTab1.setTabListener(this);
		actionbar.addTab(StuffTab1);

		ActionBar.Tab StuffTab2 = actionbar.newTab();
		StuffTab2.setText("Exams");
		StuffTab2.setTabListener(this);
		actionbar.addTab(StuffTab2);

		ActionBar.Tab StuffTab3 = actionbar.newTab();
		StuffTab3.setText("MyNews");
		StuffTab3.setTabListener(this);
		actionbar.addTab(StuffTab3);

		ActionBar.Tab StuffTab4 = actionbar.newTab();
		StuffTab4.setText("MyExams");
		StuffTab4.setTabListener(this);
		actionbar.addTab(StuffTab4);
	}

	class Adapter extends FragmentPagerAdapter {

		public Adapter(android.support.v4.app.FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int position) {

			if (position == 0) {
				return new StuffFrag();

			} else if (position == 1) {
				return new StuffFrag2();

			} else if (position == 2) {
				return new StuffFrag3();

			} else {
				return new StuffFrag4();
			}
		}

		@Override
		public int getCount() {

			return 4;
		}

	}

	@Override
	public void onPageScrollStateChanged(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		actionbar.setSelectedNavigationItem(position);

	}

	@Override
	public void onTabReselected(Tab tab2, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab2, FragmentTransaction ft) {
		viewpager.setCurrentItem(tab2.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab2, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
				/*getMenuInflater().inflate(R.menu.stuffmenu, menu);
		return true;*/
		
		 menu.add(1, 1, 0, "Profile");
		 menu.add(1, 2, 1, "LogOut");
		return true;	
				
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		 switch (item.getItemId()) {

		 case 1 :Intent i = new Intent(StuffTab.this, ProfileActivity.class);
			startActivity(i); ;
             break;
         case 2 :finish();
               break;
     }
     return super.onOptionsItemSelected(item);
	}

}
