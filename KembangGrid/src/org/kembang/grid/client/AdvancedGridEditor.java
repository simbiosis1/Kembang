package org.kembang.grid.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class AdvancedGridEditor<T> extends FlexTable implements
		LeafValueEditor<List<T>> {

	List<T> dataList = new ArrayList<T>();

	List<String> columnSize = new ArrayList<String>();

	List<ColumnDef<T, Object>> columns = new ArrayList<ColumnDef<T, Object>>();
	public int rowSelected, columnSelected;
	@SuppressWarnings("rawtypes")
	public AdvancedGridEditor table;

	public AdvancedGridEditor() {
		this.table = this;
		//
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				processClickHandler(event);
			}
		});
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
			} else if (type == ColumnType.COMBO) {
				columnValue.setIndex(index);
			} else if (type == ColumnType.TEXT) {
				columnValue.setText(text);
			} else if (type == ColumnType.SUGGEST) {
				columnValue.setText(text);
				columnValue.setIndex(index);
			} else if (type == ColumnType.DATE) {
				columnValue.setDate(date);
			}
			columnValue.setDataValue(dataList.get(row));
		}
	}

	public void setValue(List<T> value) {
		// Data
		dataList.clear();
		dataList.addAll(value);
		// Tampilan
		removeAllRows();
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
		dataList.remove(index);
		removeRow(index);
	}

	public int removeSelected() {
		remove(rowSelected);
		return rowSelected;
	}

	@Override
	public void clear() {
		dataList.clear();
		super.removeAllRows();
	}

	private void setColumn(int row, T rowData) {
		int col = 0;
		Iterator<ColumnDef<T, Object>> iter = columns.iterator();
		while (iter.hasNext()) {
			ColumnDef<T, Object> column = iter.next();
			HorizontalPanel vp = new HorizontalPanel();
			if (column.getType() == ColumnType.NONE) {
				if (column.isHasButton()) {
					Button button = new Button(column.getButtonTitle());
					button.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							//
							processClickHandler(event);
							//
							ColumnDef<T, Object> column = columns
									.get(columnSelected);
							ButtonGridHandler btnEvent = column.getBtnEvent();
							btnEvent.setIndex(rowSelected);
							btnEvent.onClick();
						}
					});
					vp.add(button);
				}
			} else if (column.getType() == ColumnType.LABEL) {
				column.setLabel(new Label((String) column.getDataValue(rowData)));
				vp.add(column.getLabel());
			} else if (column.getType() == ColumnType.TEXT) {
				TextBox textBox = new TextBox();
				textBox.setStyleName("formText");
				column.setTextBox(textBox);
				textBox.setText((String) column.getDataValue(rowData));
				if (column.getVisibleLength() != 0) {
					textBox.setVisibleLength(column.getVisibleLength());
				}
				textBox.addKeyUpHandler(new KeyUpHandler() {

					public void onKeyUp(KeyUpEvent event) {
						HorizontalPanel thisHp = (HorizontalPanel) table
								.getWidget(rowSelected, columnSelected);
						TextBox textBox = (TextBox) thisHp.getWidget(0);
						//
						processValueSelected(ColumnType.TEXT,
								textBox.getText(), false, 0, null);
					}
				});
				vp.add(textBox);
			} else if (column.getType() == ColumnType.COMBO) {
				ListBox listBox = new ListBox();
				listBox.setStyleName("formCombobox");
				column.setListBox(listBox);
				if (column.getDataSource() != null) {
					for (Object data : column.getDataSource()) {
						listBox.addItem(data.toString());
					}
				}
				listBox.addChangeHandler(new ChangeHandler() {

					public void onChange(ChangeEvent event) {
						HorizontalPanel thisHp = (HorizontalPanel) table
								.getWidget(rowSelected, columnSelected);
						ListBox listBox = (ListBox) thisHp.getWidget(0);
						processValueSelected(ColumnType.COMBO, null, false,
								listBox.getSelectedIndex(), null);
					}
				});
				listBox.setItemSelected((Integer) column.getDataValue(rowData),
						true);
				vp.add(listBox);
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
			} else if (column.getType() == ColumnType.SUGGEST) {
				MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
				if (column.getDataSource() != null) {
					for (Object data : column.getDataSource()) {
						oracle.add(data.toString());
					}
				}
				SuggestBox suggestBox = new SuggestBox(oracle);
				column.setOracle(oracle);
				column.setSuggestBox(suggestBox);
				suggestBox
						.addSelectionHandler(new SelectionHandler<Suggestion>() {

							public void onSelection(
									SelectionEvent<Suggestion> event) {
								processValueSelected(ColumnType.TEXT, event
										.getSelectedItem()
										.getReplacementString(), false, 0, null);
							}
						});
				suggestBox.getValueBox().addChangeHandler(new ChangeHandler() {

					public void onChange(ChangeEvent event) {
						HorizontalPanel thisHp = (HorizontalPanel) table
								.getWidget(rowSelected, columnSelected);
						SuggestBox suggestBox = (SuggestBox) thisHp
								.getWidget(0);
						processValueSelected(ColumnType.TEXT, suggestBox
								.getValueBox().getText(), false, 0, null);
					}
				});
				suggestBox.setText((String) column.getDataValue(rowData));
				//
				vp.add(column.getSuggestBox());
				if (column.isHasButton()) {
					Button button = new Button(column.getButtonTitle());
					button.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							//
							processClickHandler(event);
							//
							ColumnDef<T, Object> column = columns
									.get(columnSelected);
							ButtonGridHandler btnEvent = column.getBtnEvent();
							HorizontalPanel thisHp = (HorizontalPanel) table
									.getWidget(rowSelected, columnSelected);
							btnEvent.setIndex(rowSelected);
							btnEvent.setWidget(thisHp.getWidget(0));
							btnEvent.onClick();
						}
					});
					vp.add(button);
				}
				//
			} else if (column.getType() == ColumnType.DATE) {
				DateBox dateBox = new DateBox();
				dateBox.setWidth("75px");
				column.setDateBox(dateBox);
				dateBox.setValue((Date) column.getDataValue(rowData));
				dateBox.setFormat(new DateBox.DefaultFormat(column
						.getDateTimeFormat()));

				dateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {

					public void onValueChange(ValueChangeEvent<Date> event) {
						HorizontalPanel thisHp = (HorizontalPanel) table
								.getWidget(rowSelected, columnSelected);
						DateBox dateBox = (DateBox) thisHp.getWidget(0);
						//
						processValueSelected(ColumnType.DATE, null, false, 0,
								dateBox.getValue());
					}
				});
				dateBox.setValue((Date) column.getDataValue(rowData));
				vp.add(dateBox);
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

	public void setTextSuggest(int col, String text) {
		HorizontalPanel thisHp = (HorizontalPanel) table.getWidget(rowSelected,
				col);
		SuggestBox suggestBox = (SuggestBox) thisHp.getWidget(0);
		suggestBox.getValueBox().setText(text);
		processValueRowCol(rowSelected, col, ColumnType.TEXT, text, false, 0,
				null);
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
