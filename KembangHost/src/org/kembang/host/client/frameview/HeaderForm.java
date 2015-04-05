package org.kembang.host.client.frameview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kembang.host.shared.MenuDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class HeaderForm extends Composite {

	HeaderClickHandler headerClickHandler;

	List<Button> itemList = new ArrayList<Button>();
	int indexSelected = 0;

	private static HeaderFormUiBinder uiBinder = GWT
			.create(HeaderFormUiBinder.class);

	@UiField
	HorizontalPanel mainMenu;

	@UiField
	PushButton userConfig;

	interface HeaderFormUiBinder extends UiBinder<Widget, HeaderForm> {
	}

	HeaderForm headerForm;

	public HeaderForm() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		Image image = new Image("images/userconfig.png");
		image.setStyleName("kembang-menuUserConfigImage");
		DOM.insertChild(userConfig.getElement(), image.getElement(), 0);
		headerForm = this;
	}

	public void addItem(MenuDv menuDv) {
		Button item = new Button(menuDv.getTitle());
		item.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onClickHandler((Button) event.getSource());
				headerClickHandler.onClick(headerForm);
			}
		});
		item.setStyleName("kembang-menuMainItem");
		itemList.add(item);
		mainMenu.add(item);
	}

	public void setItemSelected(int index) {
		Button itemDeselected = (Button) mainMenu.getWidget(indexSelected);
		itemDeselected.setStyleName("kembang-menuMainItem");
		Button itemSelected = (Button) mainMenu.getWidget(index);
		itemSelected.setStyleName("kembang-menuMainItemSelected");
		indexSelected = index;
	}

	public void onClickHandler(Button source) {
		boolean found = false;
		int index = 0;
		Iterator<Button> iter = itemList.iterator();
		while (iter.hasNext() && !found) {
			Button currentItem = iter.next();
			if (source == currentItem) {
				found = true;
				setItemSelected(index);
			}
			index++;
		}
	}

	public void addClickHandler(HeaderClickHandler headerClickHandler) {
		this.headerClickHandler = headerClickHandler;
	}

	public int getSelectedIndex() {
		return indexSelected;
	}

	public void addUserConfigHandler(ClickHandler clickHandler) {
		userConfig.addClickHandler(clickHandler);
	}

	public void clear() {
		itemList.clear();
		mainMenu.clear();
		indexSelected = 0;
	}

	public Widget getConfigButton() {
		return userConfig;
	}
}
