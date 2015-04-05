package org.kembang.module.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MenuDv implements IsSerializable {
	/**
	 * 
	 */
	long id;
	String title;
	String place;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
