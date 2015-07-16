package com.atguigu.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * 自定义侧滑菜单
 * 
 * @author 张晓飞
 *
 */
public class MySlideView extends FrameLayout {

	private int menuWidth;
	private View menuView;

	private Scroller scroller;
	private boolean isShowMenu = false;//默认是不显示的

	public MySlideView(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(context);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		menuView = getChildAt(1);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		menuWidth = menuView.getMeasuredWidth();
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		menuView.layout(-menuWidth, 0, 0, bottom);
	}

	private int lastX;//上一次事件的x坐标
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getRawX();
			int distanceX = lastX-moveX;
			
			//得到最新需要的偏移量
			int newScrollX = getScrollX()+distanceX;
			if(newScrollX<=0 && newScrollX>=-menuWidth) {
				//立即滚动
				scrollTo(getScrollX()+distanceX, getScrollY());
			}
			lastX = moveX;
			break;
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if(scrollX>-menuWidth/2) { //关闭(不显示菜单)
				isShowMenu = false;
			} else {//打开(显示菜单)
				isShowMenu = true;
			}
			//刷新状态
			flushState();
			break;

		default:
			break;
		}

		return true;
	}

	/**
	 * 刷新状态
	 * 
	 */
	private void flushState() {
		if(isShowMenu) {
			//开始滚动到打开
			scroller.startScroll(getScrollX(), 0, -getScrollX()-menuWidth, 0, 1000);
		} else {
			//开始滚动到关闭
			scroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 1000);
		}
		//强制重绘
		invalidate();
	}
	
	@Override
	public void computeScroll() {
		super.computeScroll();
		if(scroller.computeScrollOffset()) {
			//立即滚动一点
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			//强制重绘
			invalidate();
		}
	}

	public void changeState() {
		//改状态标识
		isShowMenu = !isShowMenu;
		//刷新状态(平滑滚动)
		flushState();
	}

}
