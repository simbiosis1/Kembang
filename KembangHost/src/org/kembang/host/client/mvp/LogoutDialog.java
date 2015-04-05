package org.kembang.host.client.mvp;

import org.kembang.editor.client.KembangDialogBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class LogoutDialog extends KembangDialogBox {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LogoutDialog> {
	}

	MainFramePresenter presenter;

	public LogoutDialog(MainFramePresenter presenter) {
		super();
		//
		this.presenter = presenter;
		//
		setWidget(uiBinder.createAndBindUi(this));
		setText("Keluar program");
		//
		//
		Button save = new Button("Ya");
		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				yes();
			}
		});
		addButton(save);
		//
		Button cancel = new Button("Tidak");
		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		addButton(cancel);
	}

	private void yes() {
		presenter.onLogout();
		hide();
	}

}
