package com.allever.android.sample.customview.linecharview;//package com.allever.customviewcollection.linecharview;
//
//import android.content.Context;
//import com.allever.customviewcollection.R;
//
///**
// * 运动分析数据类型（运动过程主界面用到）
// * Created by CHARWIN.
// */
//
//public enum SportAnalysisType {
////	SPEED(1001, "速度", ""), // 速度
////	SPEED_ALLOCATION(1002, "配速", ""), // 配速
////	RPM(1003, "转速", "rpm"), // 转速
////	WATT(1004, "瓦特", "w"), // 瓦特
////	DISTANCE(1005, "距离", "km"), // 距离
////	TIME(1006, "时间", ""), // 时间
////	GRADIENT(1007, "坡度", ""), // 坡度
////	RESISTANCE(1008, "阻力", ""), // 阻力
////	CALORIE(1009, "卡路里", "kcal"), // 卡路里
////	HEART_RATE(1010, "心率", "bpm"); // 心率
//
//
//
//	SPEED(1001,
//			R.string.sport_speed, "km/h", "mph", R.mipmap.ic_launcher), // 速度
//
//	SPEED_ALLOCATION(1002,
//			R.string.sport_patch_speed, "", R.drawable.ic_launcher), // 配速
//
//	RPM(1003,
//			R.string.sport_rpm, "rpm", R.drawable.ic_launcher), // 转速
//
//	WATT(1004,
//			R.string.sport_power, "w", R.drawable.ic_launcher), // 瓦特
//
//	DISTANCE(1005,
//			R.string.sport_distance, "km", "mile", R.drawable.ic_launcher), // 距离
//
//	TIME(1006,
//			R.string.sport_time, "", R.drawable.ic_launcher), // 时间
//
//	GRADIENT(1007,
//			R.string.sport_slope, "", R.drawable.ic_launcher), // 坡度
//
//	RESISTANCE(1008,
//			R.string.sport_resistance, "", R.drawable.ic_launcher), // 阻力
//
//	CALORIE(1009,
//			R.string.sport_calorie, "kcal", R.drawable.ic_launcher), // 卡路里
//
//	HEART_RATE(1010,
//			R.string.sport_heart_rate, "bpm", R.drawable.ic_launcher); // 心率
//
//
//
//	private int id; // id
//	private String metricUnit; // 公制单位
//	private String britishUnit; // 英制单位
//	private int nameStringId;
//	private int iconDrawableId;
//
//	private SportAnalysisType(int id, int nameResId, String unit, int iconDrawableId) {
//		this.id = id;
//		this.nameStringId = nameResId;
//		this.metricUnit = britishUnit = unit;
//		this.iconDrawableId = iconDrawableId;
//	}
//
//	private SportAnalysisType(int id, int nameResId, String metricUnit, String britishUnit, int iconDrawableId) {
//		this.id = id;
//		this.nameStringId = nameResId;
//		this.metricUnit = metricUnit;
//		this.britishUnit = britishUnit;
//		this.iconDrawableId = iconDrawableId;
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public String getUnit() {
//		return metricUnit;
//	}
//
//	public String getUnit(boolean isMetric){
//		return isMetric ? metricUnit : britishUnit;
//	}
//
//	public String getName(Context context){
//		if (nameStringId != 0) {
//			return context.getResources().getString(nameStringId);
//		}
//		return "";
//	}
//
//	public int getIconDrawableId() {
//		return iconDrawableId;
//	}
//
//	public static SportAnalysisType valueOf(int id){
//		for (SportAnalysisType t : values()) {
//			if (t.getId() == id){
//				return t;
//			}
//		}
//		return null;
//	}
//
//}
