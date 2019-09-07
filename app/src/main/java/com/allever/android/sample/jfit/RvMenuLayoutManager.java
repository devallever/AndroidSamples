package com.allever.android.sample.jfit;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by CHARWIN.
 */

public class RvMenuLayoutManager extends RecyclerView.LayoutManager {
	
	private int totalHeight, verticalScrollOffset;
	
	public RvMenuLayoutManager() {
		super();
	}
	
	//	public RvMenuLayoutManager(Context context) {
//		super(context);
//	}
//
//	public RvMenuLayoutManager(Context context, int orientation, boolean reverseLayout) {
//		super(context, orientation, reverseLayout);
//	}
//
//	public RvMenuLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//		super(context, attrs, defStyleAttr, defStyleRes);
//	}
	
	@Override
	public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
		super.onLayoutChildren(recycler, state);
		// 先把所有的View先从RecyclerView中detach掉，然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
		// 实际就是把View放到了Recycler中的一个集合中。
		detachAndScrapAttachedViews(recycler);
		calculateChildrenSite(recycler);
	}
	
	@Override
	public RecyclerView.LayoutParams generateDefaultLayoutParams() {
		return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
				RecyclerView.LayoutParams.WRAP_CONTENT);
	}
	
	private void calculateChildrenSite(RecyclerView.Recycler recycler) {
		totalHeight = 0;
		for (int i = 0; i < getItemCount(); i++) {
			// 遍历Recycler中保存的View取出来
			View view = recycler.getViewForPosition(i);
			addView(view); // 因为刚刚进行了detach操作，所以现在可以重新添加
			measureChildWithMargins(view, 0, 0); // 通知测量view的margin值
			int width = getDecoratedMeasuredWidth(view); // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
			int height = getDecoratedMeasuredHeight(view);
			
			Rect mTmpRect = new Rect();
			//调用这个方法能够调整ItemView的大小，以除去ItemDecorator。
			calculateItemDecorationsForChild(view, mTmpRect);
			
			// 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
			//包括ItemDecorator设置的距离。
			layoutDecorated(view, 0, totalHeight, width, totalHeight + height);
			totalHeight += height;
		}
	}
	
	
	@Override
	public boolean canScrollVertically() {
		//返回true表示可以纵向滑动
		return true;
	}
	
	@Override
	public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
		//列表向下滚动dy为正，列表向上滚动dy为负，这点与Android坐标系保持一致。
		//实际要滑动的距离
		int travel = dy;
		
		//如果滑动到最顶部
		if (verticalScrollOffset + dy < 0) {
			travel = -verticalScrollOffset;
		} else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {//如果滑动到最底部
			travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
		}
		
		//将竖直方向的偏移量+travel
		verticalScrollOffset += travel;
		
		// 调用该方法通知view在y方向上移动指定距离
		offsetChildrenVertical(-travel);
		
		return travel;
	}
	
	
	private int getVerticalSpace() {
		//计算RecyclerView的可用高度，除去上下Padding值
		return getHeight() - getPaddingBottom() - getPaddingTop();
	}
	
	@Override
	public boolean canScrollHorizontally() {
		//返回true表示可以横向滑动
		return false;
	}
}
