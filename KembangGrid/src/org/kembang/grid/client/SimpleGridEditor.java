package org.kembang.grid.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SimpleGridEditor<T> extends FlexTable implements
		LeafValueEditor<List<T>> {

	List<T> dataList = new ArrayList<T>();

	List<String> columnSize = new ArrayList<String>();
	List<ColumnDef<T, Object>> columns = new ArrayList<ColumnDef<T, Object>>();
	public int rowSelected, columnSelected;

	@SuppressWarnings("rawtypes")
	public SimpleGridEditor table;

	GridSelectionHandler selectionHandler = null;

	public SimpleGridEditor() {
		this.table = this;
		//
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				processClickHandler(event);
				if (selectionHandler != null) {
					selectionHandler.onSelection();
				}
			}
		});
	}

	public void setSelectionHandler(GridSelectionHandler selectionHandler) {
		this.selectionHandler = selectionHandler;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addColumn(ColumnDef columnDef) {
		//
		columns.add(columnDef);
		// Tambahkan ke tampilan
	}

	private void processClickHandler(ClickEvent event) {
		RowFormatter rowFormat = table.getRowFormatter();
		rowFormat.removeStyleName(rowSelected, "Global-FlexTable-SelectedRow");
		Cell cell = table.getCellForEvent(event);
		rowSelected = cell.getRowIndex();
		columnSelected = cell.getCellIndex();
		rowFormat.addStyleName(rowSelected, "Global-FlexTable-SelectedRow");
	}

	private void processValueSelected(ColumnType type, String text,
			boolean check, int index, Date date) {
		processValueRowCol(rowSelected, columnSelected, type, text, check,
				index, date);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void processValueRowCol(int row, int col, ColumnType type,
			String text, boolean check, int index, Date date) {
		ColumnValue columnValue = columns.get(col).getColumnValue();
		if (columnValue != null) {
			if (type == ColumnType.CHECK) {
				columnValue.setChecked(check);
			}
			columnValue.setDataValue(dataList.get(row));
		}
	}

	public void setValue(List<T> value) {
		// Data
		dataList.clear();
		dataList.addAll(value);
		// Tampilan
		super.removeAllRows();
		int row = 0;
		for (T rowData : value) {
			setColumn(row, rowData);
			setRowStyle(row);
			row++;
		}
	}

	private void setRowStyle(int row) {
		HTMLTable.RowFormatter rf = getRowFormatter();
		rf.addStyleName(row, ((row % 2) == 0) ? "Global-FlexTable-EvenRow"
				: "Global-FlexTable-OddRow");
	}

	public List<T> getValue() {
		return dataList;
	}

	public void addRow(T data) {
		int row = dataList.size();
		setColumn(row, data);
		setRowStyle(row);
		//
		dataList.add(data);
	}

	public void remove(int index) {
		removeRow(index);
		//
		dataList.remove(index);
	}

	@Override
	public void clear() {
		dataList.clear();
		super.removeAllRows();
	}

	public int removeSelected() {
		remove(rowSelected);
		return rowSelected;
	}

	private void setColumn(int row, T rowData) {
		int col = 0;
		Iterator<ColumnDef<T, Object>> iter = columns.iterator();
		while (iter.hasNext()) {
			ColumnDef<T, Object> column = iter.next();
			HorizontalPanel vp = new HorizontalPanel();
			if (column.getType() == ColumnType.LABEL) {
				Label label = new Label((String) column.getDataValue(rowData));
				label.setStyleName("Global-FlexTable-LabelCell");
				column.setLabel(label);
				vp.add(column.getLabel());
			} else if (column.getType() == ColumnType.CHECK) {
				CheckBox checkBox = new CheckBox();
				column.setCheckBox(checkBox);
				checkBox.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {
						RowFormatter rowFormat = table.getRowFormatter();
						rowFormat.removeStyleName(rowSelected,
								"Global-FlexTable-SelectedRow");
						Cell cell = table.getCellForEvent(event);
						rowSelected = cell.getRowIndex();
						columnSelected = cell.getCellIndex();
						rowFormat.addStyleName(rowSelected,
								"Global-FlexTable-SelectedRow");
					}
				});
				checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

					public void onValueChange(ValueChangeEvent<Boolean> event) {
						HorizontalPanel thisHp = (HorizontalPanel) table
								.getWidget(rowSelected, columnSelected);
						CheckBox checkBox = (CheckBox) thisHp.getWidget(0);
						processValueSelected(ColumnType.CHECK, null,
								checkBox.getValue(), 0, null);
					}
				});
				checkBox.setValue((Boolean) column.getDataValue(rowData));
				vp.add(checkBox);
			}
			setWidget(row, col, vp);
			if (column.getAlign() != null) {
				getCellFormatter().setHorizontalAlignment(row, col,
						column.getAlign());
			}
			if (columnSize.size() > 0) {
				getCellFormatter().setWidth(row, col, columnSize.get(col));
			}
			col++;
		}
	}

	public void addColumnSize(String columnSize) {
		this.columnSize.add(columnSize);
	}

	public void setColumnSize(String[] columnSize) {
		for (int i = 0; i < columnSize.length; i++) {
			this.columnSize.add(columnSize[i]);
		}
	}

	public int getSelectedIndex() {
		return rowSelected;
	}

	public T getSelectedData() {
		T data = dataList.get(rowSelected);
		return data;
	}

	public Widget getSelectedWidget(int col) {
		HorizontalPanel thisHp = (HorizontalPanel) table.getWidget(rowSelected,
				col);
		return thisHp.getWidget(0);
	}

	public Widget getGridWidget(int row, int col) {
		HorizontalPanel thisHp = (HorizontalPanel) table.getWidget(row, col);
		return thisHp.getWidget(0);
	}

	public void refreshColumn(int index) {
		if (columns.get(index).getType() == ColumnType.CHECK) {
			ColumnDef<T, Object> columnDef = columns.get(index);
			int row = 0;
			for (T data : dataList) {
				HorizontalPanel thisHp = (HorizontalPanel) table.getWidget(row,
						index);
				CheckBox checkBox = (CheckBox) thisHp.getWidget(0);
				checkBox.setValue((Boolean) columnDef.getDataValue(data));
				row++;
			}
		}
	}

}
