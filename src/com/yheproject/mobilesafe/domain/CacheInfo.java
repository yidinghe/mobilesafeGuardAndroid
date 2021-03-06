package com.yheproject.mobilesafe.domain;

import android.graphics.drawable.Drawable;

public class CacheInfo {
	private Drawable icon;
	private String name;
	private String packname;
	private String code_size;
	private String data_size;
	private String cache_size;

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getPackname() {
		return packname;
	}

	public void setPackname(String packname) {
		this.packname = packname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode_size() {
		return code_size;
	}

	public void setCode_size(String code_size) {
		this.code_size = code_size;
	}

	public String getData_size() {
		return data_size;
	}

	public void setData_size(String data_size) {
		this.data_size = data_size;
	}

	public String getCache_size() {
		return cache_size;
	}

	public void setCache_size(String cache_size) {
		this.cache_size = cache_size;
	}

	public CacheInfo(Drawable icon,String name, String code_size, String data_size,
			String cache_size) {
		this.icon = icon;
		this.name = name;
		this.code_size = code_size;
		this.data_size = data_size;
		this.cache_size = cache_size;
	}

	public CacheInfo() {
	}
}
