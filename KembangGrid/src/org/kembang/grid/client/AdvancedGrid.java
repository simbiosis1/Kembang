package org.kembang.grid.client;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class AdvancedGrid<T> extends Composite implements
		LeafValueEditor<List<T>> {
	private GridHeader gridHeader;
	private ScrollPanel scrollPanel;
	private AdvancedGridEditor<T> advancedGridEditor;
	private DockLayoutPanel panel;
	private int columnAdded = 0;

	public AdvancedGrid() {
		panel = new DockLayoutPanel(Unit.PX);
		panel.setStyleName("Global-FlexTable-Outer");

		gridHeader = new GridHeader();
		gridHeader.setStyleName("Global-FlexTable-Header");
		gridHeader.setCellPadding(0);
		gridHeader.setCellSpacing(0);
		advancedGridEditor = new AdvancedGridEditor<T>();
		advancedGridEditor.setStyleName("Global-FlexTable-Table");
		scrollPanel = new ScrollPanel();
		scrollPanel.add(advancedGridEditor);
		panel.addNorth(gridHeader, 30);
		panel.add(scrollPanel);
		initWidget(panel);
	}

	public void setValue(List<T> value) {
		advancedGridEditor.setValue(value);
	}

	public List<T> getValue() {
		return advancedGridEditor.getValue();
	}

	public T getSelectedData() {
		return advancedGridEditor.getSelectedData();
	}

	@SuppressWarnings("rawtypes")
	protected void addColumn(ColumnDef columnDef) {
		// setup table
		advancedGridEditor.addColumn(columnDef);
		advancedGridEditor.addColumnSize(columnDef.getColumnWidth());
		// Set header
		gridHeader.setWidget(columnAdded, new Label(columnDef.getTitle()));
		gridHeader.getCellFormatter().setWidth(0, columnAdded,
				columnDef.getTitleWidth());
		// Set
		columnAdded++;
	}

	public Widget getSelectedWidget(int col) {
		return advancedGridEditor.getSelectedWidget(col);
	}

	public int getRowCount() {
		return advancedGridEditor.getRowCount();
	}

	public void removeRow(int row) {
		advancedGridEditor.removeRow(row);
	}

	public void removeSelected() {
		advancedGridEditor.removeSelected();
	}

	public void addRow(T data) {
		advancedGridEditor.addRow(data);
	}

	public void refreshColumn(int index) {
		advancedGridEditor.refreshColumn(index);
	}

	public Widget getGridWidget(int row, int col) {
		return advancedGridEditor.getGridWidget(row, col);
	}

	public void setTextSuggest(int col, String text) {
		advancedGridEditor.setTextSuggest(col, text);
	}

	public void clear() {
		advancedGridEditor.clear();
	}
}
