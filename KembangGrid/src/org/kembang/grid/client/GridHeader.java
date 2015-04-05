package org.kembang.grid.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class GridHeader extends FlexTable {

	int style;

	public GridHeader() {
		style = 0;
	}

	/*
	 * public GridHeader(String[] titles, String[] widths) { style=1;
	 * setTitlesWidths(titles,widths); }
	 * 
	 * public GridHeader(String[] titles, int index) { style=2;
	 * setTitles(titles, index); }
	 */

	public void setWidget(int index, Widget widget) {
		widget.setStyleName("Global-FlexTable-LabelCell");
		this.setWidget(0, index, widget);
	}

	/*
	 * public void setTitlesWidths(String[] titles, String[] widths) { int
	 * column = titles.length; for (int i = 0; i < column; i++) { setWidget(0,
	 * i, new Label(titles[i])); if (!widths[i].isEmpty()) {
	 * getCellFormatter().setWidth(0, i, widths[i]); } } }
	 * 
	 * public void setTitles(String[] titles) { setTitles(titles,0); }
	 * 
	 * public void setTitles(String[] titles, int index) { int column =
	 * titles.length; for (int i = 0; i < column; i++) { setWidget(0, i+index,
	 * new Label(titles[i])); } }
	 * 
	 * public void setWidths(String[] widths) { int column = widths.length; for
	 * (int i = 0; i < column; i++) { if (!widths[i].isEmpty()) {
	 * getCellFormatter().setWidth(0, i, widths[i]); } } }
	 */
}
