package org.kembang.module.client.mvp;

import org.kembang.module.client.framework.AppForm;
import org.kembang.module.client.framework.IMain;
import org.kembang.module.client.framework.Main;
import org.kembang.module.shared.MenuDv;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class KembangClientFactoryImpl implements KembangClientFactory {
	AppStatus APP_STATUS;
	static final EventBus eventBus = new SimpleEventBus();
	static final PlaceController placeController = new PlaceController(eventBus);
	static final Main MAIN = new Main();

	String placeName;
	String menuName;
	AcceptsOneWidget currentPanel;

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public AppStatus getAppStatus() {
		return APP_STATUS;
	}

	@Override
	public void setAppStatus(AppStatus appStatus) {
		APP_STATUS = appStatus;
	}
	
	@Override
	public void showApplication(AcceptsOneWidget panel, FormWidget activeForm) {
		if (activeForm.getAppForm() == null) {
			activeForm.setAppForm(new AppForm());
			activeForm.getAppForm().setAppPanel(activeForm);
		}
		if (APP_STATUS.isLogin()) {
			if (activeForm != null) {
				if (!activeForm.isInitialized()) {
					activeForm.initView(menuName);
				}
			}
			if (panel != null) {
				currentPanel = panel;
				panel.setWidget(activeForm.getAppForm());
			} else {
				currentPanel.setWidget(activeForm.getAppForm());
			}
		} else {
			if (panel != null) {
				panel.setWidget(activeForm.getAppForm());
			}
		}

	}

	@Override
	public IMain getMain() {
		return MAIN;
	}

	@Override
	public boolean isUserAllowedGoto(Place place) {
		placeName = place.getClass().getName();
		for (MenuDv menu : APP_STATUS.getMenuList()){
			if (menu.getPlace().equalsIgnoreCase(placeName)){
				menuName = menu.getTitle();
				return true;
			}
		}
		return false;
	}

}