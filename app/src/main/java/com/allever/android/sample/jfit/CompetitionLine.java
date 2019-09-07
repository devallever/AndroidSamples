package com.allever.android.sample.jfit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.allever.android.sample.R;
import com.allever.android.sample.customview.linecharview.BezierUtil;
import com.allever.android.sample.customview.linecharview.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allever on 17-9-19.
 */

public class CompetitionLine extends View {
    private static final String TAG = "CompetitionLine";
    private Context mContext;
    private Paint mLinePaint;//
    private Paint mImagePaint;//
    private Paint mCirclePaint;//
    private Paint mRankPaint;
    private float mWidth;
    private float mHeight;
    private final float mMarginLeftRight = 40f;//左右边界值,
    private final float mMarginTopBottom = 10f;//上下边界值
    private Path mBezierPath;

    private int[] mGradientColor;

    private float mMaxDistance = 500f;
    private List<CompetitionData> mCompetitionDataList = new ArrayList<>();//全部数据
    private List<CompetitionData> mShowedDataList = new ArrayList<>();//可以展示的数据,
    private int mCurDataIndex = 0;//用来记录当前最大距离
    private float mNextDistance;//下一目标者运动距离

    //private float mDx = 0;//曲线每次移动偏移量

    private List<PointF> mPointFList = new ArrayList<>();
    private List<BezierLineData> mBezierLineDataList = new ArrayList<>();

    private static final int MESSAGE_MOVE = 0x02;
    private static final int MESSAGE_SCROLL = 0x03;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_SCROLL:
                    if (mMaxDistance < mNextDistance) {
                        mMaxDistance++;
                        invalidate();
                        sendEmptyMessage(MESSAGE_SCROLL);
                    } else {
                        mMaxDistance = mNextDistance;
                        if (mCurDataIndex < mCompetitionDataList.size() - 1) {
                            mCurDataIndex++;
                        }
                        getShowedDataList();//显示4个数据
                        invalidate();
                    }
                    break;
                /*
                case MESSAGE_MOVE:
                    if (mDx <= (mWidth-2*mMarginLeftRight)/2f){
                        //if (mDx == 0) mCurDataIndex++;
                        mDx  = mDx + 10;
                        mMaxDistance =mMaxDistance+ mMaxDistance/((mWidth-2*mMarginLeftRight)/2f);
                        mShowedDataList.clear();//
                        invalidate();
                        sendEmptyMessageDelayed(MESSAGE_MOVE,10);
                    }else {
                        if (mCurDataIndex < mCompetitionDataList.size()-1){
                            mCurDataIndex++;
                        }
                        mDx = 0;
                        mMaxDistance = mNextDistance + mMaxDistance * 0.01f;
                        getShowedDataList();//显示4个数据
                        invalidate();
                    }
                    break;
                    */
            }
        }
    };

    public CompetitionLine(Context context) {
        super(context);
        init(context);
    }

    public CompetitionLine(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public CompetitionLine(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mLinePaint = new Paint();
        mImagePaint = new Paint();
        mCirclePaint = new Paint();
        mRankPaint = new Paint();

        mBezierPath = new Path();

        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(mContext.getResources().getColor(R.color.color_default_blue));
        mCirclePaint.setAntiAlias(true);//去除锯齿
        mCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        mRankPaint.setColor(mContext.getResources().getColor(R.color.color_white));
        mRankPaint.setAntiAlias(true);//去除锯齿
        mRankPaint.setStrokeJoin(Paint.Join.ROUND);
        mRankPaint.setStrokeCap(Paint.Cap.ROUND);
        mRankPaint.setTextSize(DensityUtil.sp2px(mContext, 10f));

        mGradientColor = new int[]{
                mContext.getResources().getColor(R.color.color_default_light),
                mContext.getResources().getColor(R.color.color_default_light),
                mContext.getResources().getColor(R.color.color_default_blue),
                mContext.getResources().getColor(R.color.color_default_blue),
                mContext.getResources().getColor(R.color.color_default_blue),
                mContext.getResources().getColor(R.color.color_default_blue),
                mContext.getResources().getColor(R.color.color_default_blue),
                mContext.getResources().getColor(R.color.color_default_blue),
                mContext.getResources().getColor(R.color.color_default_blue),
                mContext.getResources().getColor(R.color.color_default_blue),
                mContext.getResources().getColor(R.color.competition_line_alpha_gray)};

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        Log.d(TAG, "onMeasure: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initPointList();
        drawBezier(canvas);
        drawMark(canvas);
    }

    private void initPointList() {
        mPointFList.clear();

        mPointFList.add(new PointF(mMarginLeftRight, mHeight / 2));
        mPointFList.add(new PointF(mWidth - mMarginLeftRight, mHeight / 2));

        mBezierLineDataList = getLineData(mPointFList);
    }


    private void drawBezier(Canvas canvas) {
        mLinePaint.setColor(mContext.getResources().getColor(R.color.competition_line_alpha_gray));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(DensityUtil.dip2px(mContext, 1.5f));//设置线宽
        mLinePaint.setAntiAlias(true);//去除锯齿
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        //绘制曲线
        //Path lightLinePath = new Path();
        mBezierPath.reset();
        mBezierPath.moveTo(mBezierLineDataList.get(0).getStartP().x, mBezierLineDataList.get(0).getStartP().y);
        for (int i = 0; i < mBezierLineDataList.size(); i++) {
            mBezierPath.cubicTo(
                    mBezierLineDataList.get(i).getCp1().x, mBezierLineDataList.get(i).getCp1().y,
                    mBezierLineDataList.get(i).getCp2().x, mBezierLineDataList.get(i).getCp2().y,
                    mBezierLineDataList.get(i).getEndP().x, mBezierLineDataList.get(i).getEndP().y
            );
        }

        float gradientFactor = 0f;//比例,根据用户当前位置设置渐变色的起始,用户左边为蓝色,右边为灰色
        //求用户所在位置占总距离的比例
        for (CompetitionData data : mCompetitionDataList) {
            if (data.getCompetitionUserType() == CompetitionUserType.ME) {
                gradientFactor = data.getDistance() / mMaxDistance;
                break;
            }
        }
        LinearGradient linearGradient = new LinearGradient(
                0,
                0,
                mWidth * gradientFactor,
                0,
                mGradientColor,
                null,
                Shader.TileMode.CLAMP
        );
        mLinePaint.setShader(linearGradient);
        canvas.drawPath(mBezierPath, mLinePaint);
    }

    private void drawMark(Canvas canvas) {
        if (mCompetitionDataList.size() == 0) return;
        //绘制圆形与排行
        Bitmap bitmap;
        CompetitionData competitionData;
        Log.d(TAG, "onDraw: mMaxDistance = " + mMaxDistance);
        float t;//点在曲线上的比例
        BezierLineData lineData;
        PointF linePoint;
        for (int i = 0; i < mShowedDataList.size(); i++) {
            competitionData = mShowedDataList.get(i);
            lineData = mBezierLineDataList.get(0);//
            t = competitionData.getDistance() / mMaxDistance;
            linePoint = BezierUtil.calculateBezierPointForCubic(
                    t,
                    lineData.getStartP(),
                    lineData.getCp1(),
                    lineData.getCp2(),
                    lineData.getEndP());

            if (competitionData.getCompetitionUserType() == CompetitionUserType.ME) {
                //是当前用户,绘制Bitmap
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.run_video_piont);
                canvas.drawBitmap(bitmap,
                        linePoint.x - (bitmap.getWidth() / 2),
                        linePoint.y - (bitmap.getHeight() / 2),
                        mImagePaint);
            } else {
                //其他用户绘制圆点和排名
                float r = DensityUtil.dip2px(mContext, 10f);
                mLinePaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(
                        linePoint.x,
                        linePoint.y,
                        r, mLinePaint);
                //绘制排名
                String rank = competitionData.getRank() + "";
                float dx;
                float dy = DensityUtil.dip2px(mContext, 4f);
                if (competitionData.getRank() < 10) {
                    dx = DensityUtil.dip2px(mContext, 3f);
                } else if (competitionData.getRank() >= 10 && competitionData.getRank() < 100) {
                    dx = DensityUtil.dip2px(mContext, 6f);
                } else {
                    dx = DensityUtil.dip2px(mContext, 8f);
                }
                canvas.drawText(rank, 0, rank.length(),
                        linePoint.x - dx,
                        linePoint.y + dy,
                        mRankPaint);
            }

        }



        /*
        for (int i= 0; i<mShowedDataList.size(); i++){
            competitionData = mShowedDataList.get(i);
            Log.d(TAG, "onDraw: mDx = " + mDx);
            //判断在那段曲线上0:最左(隐藏)，1：左边(显示)，2：右边(显示)，3：最右(隐藏)
            if (competitionData.getDistance() <= mMaxDistance*0.5f){
                if (mDx==0){
                    lineData = mBezierLineDataList.get(1);//
                }else {
                    //正在移动中,在下一条曲线绘制
                    lineData = mBezierLineDataList.get(2);//
                }
                t = competitionData.getDistance()/(mMaxDistance*0.5f);
            }else {
                if (mDx == 0){
                    lineData = mBezierLineDataList.get(2);//
                }else {
                    //正在移动中,在下一条曲线绘制
                    lineData = mBezierLineDataList.get(3);//
                }
                t = (competitionData.getDistance()-mMaxDistance*0.5f)/(mMaxDistance*0.5f);
            }
            linePoint = BezierUtil.calculateBezierPointForCubic(
                    t,
                    lineData.getStartP(),
                    lineData.getCp1(),
                    lineData.getCp2(),
                    lineData.getEndP());

            if (competitionData.getCompetitionUserType() == CompetitionUserType.ME){
                //是当前用户,绘制Bitmap
                bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.run_video_piont);
                canvas.drawBitmap(bitmap,
                        linePoint.x - (bitmap.getWidth()/2),
                        linePoint.y -(bitmap.getHeight()/2),
                        mImagePaint);
            }else {
                //其他用户绘制圆点和排名
                float r = DensityUtil.dip2px(mContext,10f);
                mLinePaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(
                        linePoint.x,
                        linePoint.y,
                        r,mLinePaint);
                //绘制排名
                String rank = competitionData.getRank()+"";
                float dx;
                float dy = DensityUtil.dip2px(mContext,4f);
                if (competitionData.getRank() <10){
                    dx = DensityUtil.dip2px(mContext,3f);
                }else if (competitionData.getRank() >= 10 && competitionData.getRank() < 100){
                    dx = DensityUtil.dip2px(mContext,6f);
                }else {
                    dx = DensityUtil.dip2px(mContext,8f);
                }
                canvas.drawText(rank,0,rank.length(),
                        linePoint.x - dx,
                        linePoint.y + dy,
                        mRankPaint);
            }
        }
        */
    }

    /**
     * 只获取显示四个数据
     */
    private void getShowedDataList() {
        mShowedDataList.clear();
        mShowedDataList.add(mCompetitionDataList.get(0));//第一个,用户
        int fromIndex = 1;
        Log.d(TAG, "getShowedDataList: mCurDataIndex = " + mCurDataIndex);
        int counter = 0;
        for (int i = mCurDataIndex; i > 0; i--) {
            if (counter < 4) {
                fromIndex = i;
                counter++;
            }
        }
        Log.d(TAG, "getShowedDataList: fromIndex = " + fromIndex);
        for (int j = fromIndex; j <= mCurDataIndex; j++) {
            if (j < mCompetitionDataList.size()) {
                mShowedDataList.add(mCompetitionDataList.get(j));
                Log.d(TAG, "getShowedDataList: j = " + j);
            }
        }
    }

    /**
     * 设置曲线数据
     *
     * @param competitionDataList 竞赛数据列表
     *                            用户数据排在第一
     */
    public void setLineData(List<CompetitionData> competitionDataList) {
        //获取最大值
        mCompetitionDataList = competitionDataList;
        if (mCompetitionDataList.size() <= 4) {
            mCurDataIndex = mCompetitionDataList.size() - 1;
        } else {
            mCurDataIndex = 4;
        }
        mMaxDistance = mCompetitionDataList.get(mCurDataIndex).getDistance();
        mMaxDistance = mMaxDistance + mMaxDistance * 0.1f;
        getShowedDataList();//显示4个数据
        postInvalidateDelayed(50);
    }

    /**
     * 更新运动进度
     *
     * @param distance 用户当前运动距离
     */
    public void updateMyPoint(float distance) {
        CompetitionData myData = null;
        for (int i = 0; i < mCompetitionDataList.size(); i++) {
            myData = mCompetitionDataList.get(i);
            if (myData.getCompetitionUserType() == CompetitionUserType.ME) {
                myData.setDistance(distance);
                mCompetitionDataList.set(i, myData);
                break;
            }
        }
        //postInvalidateDelayed(50);
        if (myData == null) return;
        if (myData.getDistance() > mCompetitionDataList.get(mCurDataIndex).getDistance()) {
            //我的距离大于 当前显示的用户的距离 触发滚动逻辑
            //获取下一目标者的运动距离
            if (mCurDataIndex + 1 < mCompetitionDataList.size()) {
                mNextDistance = mCompetitionDataList.get(mCurDataIndex + 1).getDistance();
                //触发滚动
                //mHandler.sendEmptyMessageDelayed(MESSAGE_MOVE,100);
                mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL, 100);
            } else {
                //此时,用户超越当前列表所有数据
                Log.d(TAG, "updateMyPoint: max = " + mMaxDistance + "\t" + "mNextMax = " + mNextDistance);
                if (myData.getDistance() > mNextDistance) {
                    mNextDistance = myData.getDistance() + mMaxDistance * 0.3F;//当用户超越当前排行榜列表用户,再运动一定距离后,向后滚动一定距离
                    //触发滚动
                    //mHandler.sendEmptyMessageDelayed(MESSAGE_MOVE,100);
                    mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL, 100);
                } else {
                    postInvalidateDelayed(50);
                }
            }
        } else {
            //我的距离 小于 当前显示的用户的距离 更新逻辑
            postInvalidateDelayed(50);
        }
    }

    public enum CompetitionUserType {
        ME, OTHER
    }


    /**
     * 获取每一段曲线所需要的点集
     */
    private List<BezierLineData> getLineData(List<PointF> pointList) {
        float t = 0.5f;
        List<BezierLineData> lineDataList = new ArrayList<>();
        PointF startP;
        PointF endP;
        PointF cp1;
        PointF cp2;
        BezierLineData lineData;
        for (int i = 0; i < pointList.size() - 1; i++) {
            startP = pointList.get(i);
            endP = pointList.get(i + 1);
            cp1 = new PointF();
            cp1.x = startP.x + (endP.x - startP.x) * t;
            cp1.y = mMarginTopBottom - DensityUtil.dip2px(mContext, 30f);
            cp2 = new PointF();
            cp2.x = startP.x + (endP.x - startP.x) * (1 - t);
            cp2.y = mHeight - mMarginTopBottom + DensityUtil.dip2px(mContext, 30f);
            lineData = new BezierLineData(startP, endP, cp1, cp2);
            lineDataList.add(lineData);
        }
        return lineDataList;
    }

}
