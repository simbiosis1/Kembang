package org.kembang.host.client.mvp;

import org.kembang.grid.client.AdvancedGrid;
import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.ColumnValue;
import org.kembang.host.shared.MenuDv;

public class RoleMenuEditorTable extends AdvancedGrid<MenuDv> {
	ColumnDef<MenuDv, String> colNr = new ColumnDef<MenuDv, String>(
			ColumnType.LABEL, "No", "34px", "30px") {

		@Override
		public String getDataValue(MenuDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<MenuDv, Boolean> colStatus = new ColumnDef<MenuDv, Boolean>(
			ColumnType.CHECK, "Stat", "40px", "36px") {

		@Override
		public Boolean getDataValue(MenuDv data) {
			return data.getStatus();
		}
	};

	ColumnDef<MenuDv, String> colName = new ColumnDef<MenuDv, String>(
			ColumnType.LABEL, "Nama") {

		@Override
		public String getDataValue(MenuDv data) {
			return data.getTitle();
		}
	};

	public RoleMenuEditorTable() {
		addColumn(colNr);
		colStatus.setCheckBoxHandler(new ColumnValue<MenuDv>() {

			@Override
			public void setDataValue(MenuDv data) {
				data.setStatus(isChecked());
			}
		});
		addColumn(colStatus);
		addColumn(colName);
	}

}
