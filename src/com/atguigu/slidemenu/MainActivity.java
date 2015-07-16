package com.atguigu.slidemenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	private MySlideView msv;

	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_title = (TextView) findViewById(R.id.tv_title);
		msv = (MySlideView) findViewById(R.id.msv);

		findViewById(R.id.main_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//改变菜单状态
				msv.changeState();
			}
		});
	}

	/**
	 * 点击菜单项
	 * @param v
	 */
	public void clickMenuItem(View v) {

		TextView tv = (TextView) v;
		tv_title.setText(tv.getText());
		msv.changeState();
	}
}
