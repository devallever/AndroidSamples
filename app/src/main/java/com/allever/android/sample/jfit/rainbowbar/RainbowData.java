package com.allever.android.sample.jfit.rainbowbar;

/**
 * Created by CHARWIN.
 */

public class RainbowData {
	private int id;
	private String title;
	private String value;
	private String unit;
	
	public RainbowData(int id, String title, String value, String unit) {
		this.id = id;
		this.title = title;
		this.value = value;
		this.unit = unit;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
