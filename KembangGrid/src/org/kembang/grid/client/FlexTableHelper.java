package org.kembang.grid.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class FlexTableHelper {

	public static void setupHeader(FlexTable header, int column,
			String[] texts, String[] widths) {
		for (int i = 0; i < column; i++) {
			header.setWidget(0, i, new Label(texts[i]));
			if (!widths[i].isEmpty()) {
				header.getCellFormatter().setWidth(0, i, widths[i]);
			}
		}
	}

	public static void setupContent(FlexTable content,int row, int column,
			String[] texts, String[] widths) {
		for (int i = 0; i < column; i++) {
			content.setWidget(row, i, new Label(texts[i]));
			if (!widths[i].isEmpty()) {
				content.getCellFormatter().setWidth(0, i, widths[i]);
			}
		}
	}

	public static void setupFooter(FlexTable footer, int column,
			String[] texts, String[] widths) {
		for (int i = 0; i < column; i++) {
			footer.setWidget(0, i, new Label(texts[i]));
			if (!widths[i].isEmpty()) {
				footer.getCellFormatter().setWidth(0, i, widths[i]);
			}
		}
	}

	public static void setTextFooter(FlexTable footer, int column, String text){
		getFooterLabel(footer,column).setText(text);
	}
	
	public static Label getFooterLabel(FlexTable footer, int column) {
		return (Label) footer.getWidget(0, column);
	}
}
