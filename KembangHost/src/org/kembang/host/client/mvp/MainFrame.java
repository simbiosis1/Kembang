package org.kembang.host.client.mvp;

import java.util.List;

import org.kembang.host.shared.MenuDv;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

public interface MainFrame {
	Widget getWidget();

	Widget getConfigButton();

	void setUserMenu(List<MenuDv> listUserMenu);

	void setFastMenu(List<MenuDv> listFastMenu);

	void addUserConfigHandler(ClickHandler clickHandler);

	void clearUi();
}
