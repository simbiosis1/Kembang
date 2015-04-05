package org.kembang.grid.client;

import java.util.Date;

public abstract class ColumnValue<V> {
	String text;
	Date date;
	Integer index;
	Boolean checked;

	public ColumnValue() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Boolean isChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public abstract void setDataValue(V data);
}
