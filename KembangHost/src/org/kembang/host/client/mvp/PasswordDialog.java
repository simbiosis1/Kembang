package org.kembang.host.client.mvp;

import org.kembang.editor.client.KembangDialogBox;
import org.kembang.host.shared.UserDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PasswordDialog extends KembangDialogBox implements Editor<UserDv> {

	private static PasswordDialogUiBinder uiBinder = GWT
			.create(PasswordDialogUiBinder.class);

	interface PasswordDialogUiBinder extends UiBinder<Widget, PasswordDialog> {
	}

	interface Driver extends SimpleBeanEditorDriver<UserDv, PasswordDialog> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label completeName;
	@UiField
	TextBox password;
	@UiField
	TextBox passwordConfirm;

	MainFramePresenter presenter;

	public PasswordDialog(MainFramePresenter presenter) {
		super();
		//
		this.presenter = presenter;
		//
		setWidget(uiBinder.createAndBindUi(this));
		setText("Ganti kata kunci");
		setWidth("250px");
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
		driver.edit(new UserDv());
	}

	public void setUser(UserDv user) {
		driver.edit(user);
	}

	private void save() {
		UserDv user = driver.flush();
		if (user.getPassword().equalsIgnoreCase(user.getPasswordConfirm())) {
			presenter.savePassword(user.getPassword());
			hide();
		} else {
			Window.alert("Kata kunci tidak sama dengan kata kunci konfirmasi");
		}
	}
}
