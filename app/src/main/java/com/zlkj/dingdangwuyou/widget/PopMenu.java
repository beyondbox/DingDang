package com.zlkj.dingdangwuyou.widget;

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

public class PopMenu extends PopupWindow {
	
	public PopMenu(int width, int height, View contentView, ListView listView, BaseAdapter adapter) {
		this.setContentView(contentView);
		this.setWidth(width);
		this.setHeight(height);
		this.setBackgroundDrawable(new ColorDrawable(0x00000000));
		this.setOutsideTouchable(true);
		this.setFocusable(true);
		
		listView.setAdapter(adapter);
	}
	
}
