package org.kembang.grid.client;

import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

public abstract class ColumnDef<P, Q> {
	private String title;
	private String titleWidth;
	private String columnWidth;
	@SuppressWarnings("rawtypes")
	ColumnValue columnValue = null;
	Label label;
	TextBox textBox = null;
	CheckBox checkBox = null;
	ListBox listBox = null;
	MultiWordSuggestOracle oracle = null;
	SuggestBox suggestBox = null;
	DateBox dateBox = null;
	@SuppressWarnings("rawtypes")
	List dataSource = null;
	boolean hasButton;
	String buttonTitle;
	ButtonGridHandler btnEvent;
	int visibleLength;
	HasHorizontalAlignment.HorizontalAlignmentConstant align = null;
	ColumnType type;
	DateTimeFormat dateTimeFormat = null;

	public abstract Q getDataValue(P data);

	public ColumnDef(ColumnType type, String title) {
		this.type = type;
		visibleLength = 0;
		this.title = title;
		this.titleWidth = "";
		this.columnWidth = "";
	}

	public ColumnDef(ColumnType type, String title, String width) {
		this.type = type;
		visibleLength = 0;
		this.title = title;
		this.titleWidth = width;
		this.columnWidth = width;
	}

	public ColumnDef(ColumnType type, String title, String titleWidth,
			String columnWidth) {
		this.type = type;
		visibleLength = 0;
		this.title = title;
		this.titleWidth = titleWidth;
		this.columnWidth = columnWidth;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleWidth() {
		return titleWidth;
	}

	public String getColumnWidth() {
		return columnWidth;
	}

	public int getVisibleLength() {
		return visibleLength;
	}

	public void setVisibleLength(int visibleLength) {
		this.visibleLength = visibleLength;
	}

	public ColumnType getType() {
		return type;
	}

	public void setType(ColumnType type) {
		this.type = type;
	}

	public Label getLabel() {
		return label;
	}

	public ListBox getListBox() {
		return listBox;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public void setListBox(ListBox listBox) {
		this.listBox = listBox;
	}

	@SuppressWarnings("rawtypes")
	public List getDataSource() {
		return dataSource;
	}

	@SuppressWarnings("rawtypes")
	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}

	public SuggestBox getSuggestBox() {
		return suggestBox;
	}

	public void setSuggestBox(SuggestBox suggestBox) {
		this.suggestBox = suggestBox;
	}

	public TextBox getTextBox() {
		return textBox;
	}

	public void setTextBox(TextBox textBox) {
		this.textBox = textBox;
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public DateBox getDateBox() {
		return dateBox;
	}

	public void setDateBox(DateBox dateBox) {
		this.dateBox = dateBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}

	@SuppressWarnings("rawtypes")
	public void setTextBoxHandler(ColumnValue value) {
		columnValue = value;
	}

	@SuppressWarnings("rawtypes")
	public void setListBoxHandler(ColumnValue value) {
		columnValue = value;
	}

	@SuppressWarnings("rawtypes")
	public void setSuggestBoxHandler(ColumnValue value) {
		columnValue = value;
	}

	@SuppressWarnings("rawtypes")
	public void setCheckBoxHandler(ColumnValue value) {
		columnValue = value;
	}

	@SuppressWarnings("rawtypes")
	public void setDateBoxHandler(ColumnValue value) {
		columnValue = value;
	}

	public void setOracle(MultiWordSuggestOracle oracle) {
		this.oracle = oracle;
	}

	public MultiWordSuggestOracle getOracle() {
		return oracle;
	}

	@SuppressWarnings("rawtypes")
	public ColumnValue getColumnValue() {
		return columnValue;
	}

	public void addButton(String buttonTitle, ButtonGridHandler event) {
		this.buttonTitle = buttonTitle;
		hasButton = true;
		btnEvent = event;
	}

	public String getButtonTitle() {
		return buttonTitle;
	}

	public void setButtonTitle(String buttonTitle) {
		this.buttonTitle = buttonTitle;
	}

	public boolean isHasButton() {
		return hasButton;
	}

	public ButtonGridHandler getBtnEvent() {
		return btnEvent;
	}

	public HasHorizontalAlignment.HorizontalAlignmentConstant getAlign() {
		return align;
	}

	public void setAlign(
			HasHorizontalAlignment.HorizontalAlignmentConstant align) {
		this.align = align;
	}

	public void setDateTimeFormat(DateTimeFormat dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	public DateTimeFormat getDateTimeFormat() {
		return dateTimeFormat;
	}

}
