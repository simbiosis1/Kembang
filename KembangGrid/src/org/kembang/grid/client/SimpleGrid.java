package org.kembang.grid.client;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class SimpleGrid<T> extends Composite implements
		LeafValueEditor<List<T>> {

	private GridHeader gridHeader;
	private ScrollPanel scrollPanel;
	private SimpleGridEditor<T> simpleGridEditor;
	private DockLayoutPanel panel;
	private int columnAdded = 0;

	public SimpleGrid() {
		panel = new DockLayoutPanel(Unit.PX);
		panel.setStyleName("Global-FlexTable-Outer");

		gridHeader = new GridHeader();
		gridHeader.setStyleName("Global-FlexTable-Header");
		gridHeader.setCellPadding(0);
		gridHeader.setCellSpacing(0);
		simpleGridEditor = new SimpleGridEditor<T>();
		simpleGridEditor.setStyleName("Global-FlexTable-Table");
		scrollPanel = new ScrollPanel();
		scrollPanel.add(simpleGridEditor);
		panel.addNorth(gridHeader, 30);
		panel.add(scrollPanel);
		initWidget(panel);
	}
	
	public void setSelectionHandler(GridSelectionHandler handler){
		simpleGridEditor.setSelectionHandler(handler);
	}

	public void setValue(List<T> value) {
		simpleGridEditor.setValue(value);
	}

	public List<T> getValue() {
		return simpleGridEditor.getValue();
	}

	public T getSelectedData() {
		return simpleGridEditor.getSelectedData();
	}

	@SuppressWarnings("rawtypes")
	protected void addColumn(ColumnDef columnDef) {
		// setup table
		simpleGridEditor.addColumn(columnDef);
		simpleGridEditor.addColumnSize(columnDef.getColumnWidth());
		// Set header
		gridHeader.setWidget(columnAdded, new Label(columnDef.getTitle()));
		gridHeader.getCellFormatter().setWidth(0, columnAdded,
				columnDef.getTitleWidth());
		// Set
		columnAdded++;
	}

	public void addRow(T data) {
		simpleGridEditor.addRow(data);
	}

	public int getRowCount() {
		return simpleGridEditor.getRowCount();
	}

	public void removeSelected() {
		simpleGridEditor.removeSelected();
	}

	public void removeRow(int row) {
		simpleGridEditor.removeRow(row);
	}

	public void refreshColumn(int index) {
		simpleGridEditor.refreshColumn(index);
	}

	public Widget getGridWidget(int row, int col) {
		return simpleGridEditor.getGridWidget(row, col);
	}

	public void clear() {
		simpleGridEditor.clear();
	}
}
