package org.kembang.editor.client;

import java.util.List;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.client.ui.ListBox;

public abstract class ListBoxListEditor<T, P> extends ListBox implements
		LeafValueEditor<T> {

	List<P> listData = null;

	public abstract String convertItemId(P data);

	public String createItemString(P data) {
		return data.toString();
	}

	public void setList(List<P> listData) {
		clear();
		//
		this.listData = listData;
		for (P data : listData) {
			addItem(createItemString(data), convertItemId(data));
		}
	}

	public abstract int compareData(T value, P data);

	@Override
	public void setValue(T value) {
		if (listData == null) {
			setSelectedIndex(0);
		} else {
			int index = 0;
			for (P data : listData) {
				if (compareData(value, data) == 0) {
					break;
				}
				index++;
			}
			setSelectedIndex(index < getItemCount() ? index : 0);
		}
	}

	public abstract T convertData(int index, String value);

	@Override
	public T getValue() {
		return convertData(getSelectedIndex(), getValue(getSelectedIndex()));
	}

}
