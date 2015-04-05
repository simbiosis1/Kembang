package org.kembang.host.client.mvp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginDialog extends PopupPanel {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LoginDialog> {
	}

	MainFramePresenter presenter;

	@UiField
	TextBox user;
	@UiField
	PasswordTextBox password;

	public LoginDialog(MainFramePresenter presenter) {
		setWidget(uiBinder.createAndBindUi(this));
		//
		this.presenter = presenter;
		setGlassEnabled(true);
	}

	@UiHandler("login")
	public void onLogin(ClickEvent event) {
		DOM.getElementById("appLoading").getStyle().setDisplay(Display.BLOCK);
		presenter.onLogin(user.getText(), password.getText());
		hide();
	}

	public void showPopup() {
		setPopupPositionAndShow(new PopupPanel.PositionCallback() {
			public void setPosition(int offsetWidth, int offsetHeight) {
				int left = (Window.getClientWidth() - offsetWidth - 40);
				int top = (Window.getClientHeight() - offsetHeight) / 3;
				setPopupPosition(left, top);
			}
		});
	}
}
