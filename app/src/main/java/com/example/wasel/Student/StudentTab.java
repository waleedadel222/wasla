package com.example.wasel.Student;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wasel.R;

public class StudentTab extends FragmentActivity implements TabListener,
		OnPageChangeListener {

	ViewPager vpager;
	ActionBar abar;
	android.support.v4.app.FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		vpager = (ViewPager) findViewById(R.id.pager);
		vpager.setOnPageChangeListener(this);

		fm = getSupportFragmentManager();

		MyAdapter adapter = new MyAdapter(fm);
		vpager.setAdapter(adapter);
		abar = getActionBar();

		abar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab tab1 = abar.newTab();
		tab1.setText("News");
		tab1.setTabListener(this);
		abar.addTab(tab1);

		ActionBar.Tab tab2 = abar.newTab();
		tab2.setText("Exams");
		tab2.setTabListener(this);
		abar.addTab(tab2);

		ActionBar.Tab tab3 = abar.newTab();
		tab3.setText("Profile");
		tab3.setTabListener(this);
		abar.addTab(tab3);

	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		vpager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {

			if (position == 0) {
				return new Fragment1();
			} else if (position == 1) {
				return new Fragment2();
			} else {
				return new Fragment3();
			}
		}

		@Override
		public int getCount() {
			return 3;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {

		abar.setSelectedNavigationItem(position);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		/*getMenuInflater().inflate(R.menu.studentmenu, menu);
		return true;*/
		 menu.add(1, 1, 0, "LogOut");
	    
		
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		 switch (item.getItemId()) {
         case 1 :finish();

             break; 
		 }
		return super.onOptionsItemSelected(item);
	}
		
}

