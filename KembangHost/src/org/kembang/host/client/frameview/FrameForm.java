package org.kembang.host.client.frameview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kembang.host.client.mvp.MainFrame;
import org.kembang.host.shared.MenuDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;

public class FrameForm extends Composite implements MainFrame {

	private static MainFormUiBinder uiBinder = GWT
			.create(MainFormUiBinder.class);

	interface MainFormUiBinder extends UiBinder<Widget, FrameForm> {
	}

	@UiField
	HeaderForm headerForm;

	@UiField
	SidebarForm sidebarForm;

	@UiField
	DockLayoutPanel appFormPanel;

	List<MenuDv> listUserMenu = new ArrayList<MenuDv>();

	List<MenuDv> listHeaderMenu = new ArrayList<MenuDv>();

	boolean hasCalled = false;

	public FrameForm() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		headerForm.addClickHandler(new HeaderClickHandler() {

			@Override
			public void onClick(HeaderForm headerForm) {
				initSidebar(headerForm.getSelectedIndex());
			}
		});
		//
		sidebarForm.addClickHandler(new SideBarClickHandler() {

			@Override
			public void onClick(String url) {
				appFormPanel.clear();
				Frame myFrame = new Frame(url);
				myFrame.setWidth("100%");
				myFrame.setHeight("100%");
				appFormPanel.add(myFrame);
			}
		});
	}

	@Override
	public Widget getWidget() {
		return this;
	}

	void initSidebar(int index) {
		//
		sidebarForm.clear();
		//
		List<MenuDv> listSidebar = listHeaderMenu.get(index).getChildren();
		for (MenuDv menuDv : listSidebar) {
			sidebarForm.addHeader(menuDv.getTitle());
			if (menuDv.getChildren().size() > 0) {
				List<MenuDv> listItem = menuDv.getChildren();
				for (MenuDv itemDv : listItem) {
					sidebarForm.addItem(itemDv.getTitle(), itemDv.getLink());
				}
			}
		}
	}

	public void setHeaderMenu(String placeName) {
		boolean menuFound = false;
		String headerMenu = "";
		Iterator<MenuDv> iterMenu = listUserMenu.iterator();
		while (iterMenu.hasNext() && !menuFound) {
			MenuDv menu = iterMenu.next();
			if (placeName.equalsIgnoreCase(menu.getPlace())) {
				menuFound = true;
				headerMenu = menu.getGrandParentTitle();
			}
		}
		if (menuFound) {
			boolean groupFound = false;
			int index = 0;
			Iterator<MenuDv> iterGroup = listHeaderMenu.iterator();
			while (iterGroup.hasNext() && !groupFound) {
				MenuDv menuGroup = iterGroup.next();
				if (headerMenu.equalsIgnoreCase(menuGroup.getTitle())) {
					groupFound = true;
				} else {
					index++;
				}
			}
			if ((listHeaderMenu.size() > 0) && groupFound) {
				headerForm.setItemSelected(index);
				initSidebar(index);
				hasCalled = true;
			}
		}
	}

	@Override
	public void setUserMenu(List<MenuDv> listUserMenu) {
		this.listUserMenu.clear();
		listHeaderMenu.clear();
		//
		this.listUserMenu.addAll(listUserMenu);
		//
		MenuDv serambi = new MenuDv();
		serambi.setTitle("Serambi");
		serambi.setPlace("");
		listHeaderMenu.add(serambi);
		//
		createMenuData();
		//
		for (MenuDv menuDv : listHeaderMenu) {
			headerForm.addItem(menuDv);
		}
	}

	@Override
	public void setFastMenu(List<MenuDv> listFastMenu) {
		List<MenuDv> myFastMenu = listHeaderMenu.get(0).getChildren();
		myFastMenu.clear();
		//
		MenuDv fastMenu = new MenuDv();
		fastMenu.setTitle("Menu cepat");
		fastMenu.getChildren().addAll(listFastMenu);
		//
		myFastMenu.add(fastMenu);
		if ((listHeaderMenu.size() > 0) && !hasCalled) {
			headerForm.setItemSelected(0);
			initSidebar(0);
		}
	}

	private void createMenuData() {
		// Lihat dari list user menu
		for (MenuDv menu : listUserMenu){
			//
			// Cari nama menu
			boolean found = false;
			MenuDv selMenuGroup = null;
			Iterator<MenuDv> iterGroup = listHeaderMenu.iterator();
			while (iterGroup.hasNext() && !found) {
				MenuDv menuGroup = iterGroup.next();
				if (menuGroup.getTitle().equalsIgnoreCase(
						menu.getGrandParentTitle())) {
					found = true;
					selMenuGroup = menuGroup;
				}
			}
			if (!found) {
				selMenuGroup = new MenuDv();
				selMenuGroup.setTitle(menu.getGrandParentTitle());
				selMenuGroup.setPlace(menu.getPlace());
				listHeaderMenu.add(selMenuGroup);
				//
			}
			boolean sbFound = false;
			MenuDv selMenuSb = null;
			Iterator<MenuDv> iterSb = selMenuGroup.getChildren().iterator();
			while (iterSb.hasNext() && !sbFound) {
				MenuDv menuSb = iterSb.next();
				if (menuSb.getTitle().equalsIgnoreCase(menu.getParentTitle())) {
					sbFound = true;
					selMenuSb = menuSb;
				}
			}
			if (!sbFound) {
				selMenuSb = new MenuDv();
				selMenuSb.setTitle(menu.getParentTitle());
				selMenuGroup.getChildren().add(selMenuSb);
			}
			selMenuSb.getChildren().add(menu);
		}
	}

	public void clearUi() {
		sidebarForm.clear();
		listUserMenu.clear();
		listHeaderMenu.clear();
		appFormPanel.clear();
		headerForm.clear();
	}

	public void addUserConfigHandler(ClickHandler clickHandler) {
		headerForm.addUserConfigHandler(clickHandler);
	}

	public Widget getConfigButton() {
		return headerForm.getConfigButton();
	}
}
