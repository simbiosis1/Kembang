package org.kembang.host.client.mvp;

import org.kembang.editor.client.KembangDialogBox;
import org.kembang.host.shared.ListMenuDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class FastMenuDialog extends KembangDialogBox implements Editor<ListMenuDv> {

	private static FastMenuDialogUiBinder uiBinder = GWT
			.create(FastMenuDialogUiBinder.class);

	interface FastMenuDialogUiBinder extends UiBinder<Widget, FastMenuDialog> {
	}

	interface Driver extends SimpleBeanEditorDriver<ListMenuDv, FastMenuDialog> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	RoleMenuEditorTable menus;

	MainFramePresenter presenter;

	public FastMenuDialog(MainFramePresenter presenter) {
		super();
		//
		this.presenter = presenter;
		//
		setWidget(uiBinder.createAndBindUi(this));
		setText("Konfigurasi menu cepat");
		//
		Button save = new Button("Simpan");
		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				save();
			}
		});
		addButton(save);
		//
		Button cancel = new Button("Batal");
		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		addButton(cancel);
		//
		driver.initialize(this);
		driver.edit(new ListMenuDv());
	}

	public void save() {
		presenter.saveFastMenu(driver.flush());
		hide();
	}

	public void setFastMenus(ListMenuDv fastMenus) {
		driver.edit(fastMenus);
	}
}
