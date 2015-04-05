package org.kembang.module.client.mvp;

import org.kembang.module.client.framework.IMain;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public interface KembangClientFactory {
	EventBus getEventBus();

	PlaceController getPlaceController();

	void setAppStatus(AppStatus appStatus);
	
	AppStatus getAppStatus();
	
	boolean isUserAllowedGoto(Place place);

	void showApplication(AcceptsOneWidget panel, FormWidget activeForm);
	
	IMain getMain();
}
