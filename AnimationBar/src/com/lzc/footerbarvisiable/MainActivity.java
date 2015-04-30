/*
 * Copyright (c) 2014. mvpleung@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzc.footerbarvisiable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * @author Liangzc
 * @time 2014-11-7下午09:11:50
 */
public class MainActivity extends Activity {

	ListView lv;
	Button header, footer;

	int mMotionY;

	Animation up, down, header_up, header_down;

	final int MIN_DINTANCE_MODELY = 75;
	final int MIN_DINTANCE_ORDERY = 150;

	LeftGesture leftGesture;
	GestureDetector mGestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.listView1);
		header = (Button) findViewById(R.id.button2);
		footer = (Button) findViewById(R.id.button1);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			list.add("动画" + i);
		}
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
		up = AnimationUtils.loadAnimation(MainActivity.this, R.anim.up);
		down = AnimationUtils.loadAnimation(MainActivity.this, R.anim.down);
		header_up = AnimationUtils.loadAnimation(MainActivity.this, R.anim.header_up);
		header_down = AnimationUtils.loadAnimation(MainActivity.this, R.anim.header_down);
		lv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				mGestureDetector.onTouchEvent(arg1);
				return false;
			}
		});
		leftGesture = new LeftGesture();// 手势监听类
		mGestureDetector = new GestureDetector(this, leftGesture);
	}

	class LeftGesture extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (e1 != null && e2 != null)
				if ((e1.getY() - e2.getY()) > MIN_DINTANCE_MODELY) {
					// 向上滑动
					if ((e1.getY() - e2.getY()) > MIN_DINTANCE_ORDERY) {
						if (header.getVisibility() == View.VISIBLE) {
							header.startAnimation(header_down);
							header.setVisibility(View.GONE);
						}
						if (footer.getVisibility() == View.VISIBLE) {
							footer.startAnimation(down);
							footer.setVisibility(View.GONE);
						}
					}
				} else if ((e2.getY() - e1.getY()) > MIN_DINTANCE_MODELY) {
					// 向下滑动
					if ((e2.getY() - e1.getY()) > MIN_DINTANCE_ORDERY) {
						if (header.getVisibility() == View.GONE) {
							header.startAnimation(header_up);
							header.setVisibility(View.VISIBLE);
						}
						if (footer.getVisibility() == View.GONE) {
							footer.startAnimation(up);
							footer.setVisibility(View.VISIBLE);
						}
					}
				}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}
}
