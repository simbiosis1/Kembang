package org.kembang.grid.client;

import com.google.gwt.user.client.ui.Widget;

public abstract class ButtonGridHandler {
	Widget widget;
	String text;
	Integer index;

	public ButtonGridHandler() {

	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
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

	public abstract void onClick();

}
