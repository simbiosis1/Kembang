package org.kembang.module.client.mvp;

import org.kembang.module.client.framework.MainPlace;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.CheckModuleDv;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

public abstract class KembangEntryPoint {
	private SystemServiceAsync systemService = GWT.create(SystemService.class);
	private Place defaultPlace;
	private AppStatus appStatus;
	private SimpleLayoutPanel appWidget = new SimpleLayoutPanel();
	private KembangClientFactory clientFactory;
	private ActivityMapper activityMapper;
	private KembangHistoryMapper historyMapper;

	public KembangEntryPoint() {
		defaultPlace = new MainPlace("");
	}

	public KembangEntryPoint(Place defaultPlace) {
		this.defaultPlace = defaultPlace;
	}

	public abstract void initComponent();

	public void setClientFactory(KembangClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public KembangClientFactory getClientFactory() {
		return clientFactory;
	}

	public void setActivityMapper(ActivityMapper activityMapper) {
		this.activityMapper = activityMapper;
	}

	public void setHistoryMapper(KembangHistoryMapper historyMapper) {
		this.historyMapper = historyMapper;
	}

	public void start() {
		//
		//
		Resources.INSTANCE.css().ensureInjected();
		//
		initComponent();
		//
		appStatus = new AppStatus();
		String sessionId = Cookies.getCookie(appStatus.getCookiesName());
		if (sessionId == null) {
			sessionId = "";
		}
		//
		clientFactory.setAppStatus(appStatus);
		ActivityManager activityManager = new ActivityManager(activityMapper,
				clientFactory.getEventBus());
		activityManager.setDisplay(appWidget);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(clientFactory.getPlaceController(),
				clientFactory.getEventBus(), defaultPlace);

		RootLayoutPanel.get().add(appWidget);
		//
		//
		// Ambil data place dan cari ke server proposal
		// checkSignature(key,place)
		// result ok = String module description
		// result failed = blank string
		//
		if (!sessionId.isEmpty()) {
			systemService.chekSignature(sessionId, getClass().getName(),
					new AsyncCallback<CheckModuleDv>() {

						@Override
						public void onFailure(Throwable caught) {
							DOM.getElementById("appLoading").getStyle()
									.setDisplay(Display.NONE);
							Window.alert("Error : checkSignature");
						}

						@Override
						public void onSuccess(CheckModuleDv result) {
							if (result.getSuccess()) {
								appStatus.setLogin(true);
								appStatus.setUser(result.getUser());
								appStatus.setMenuList(result.getMenus());
								//
								DOM.getElementById("appLoading").getStyle()
										.setDisplay(Display.NONE);
								//
								historyHandler.handleCurrentHistory();
							} else {
								appStatus.setLogin(false);
							}
						}
					});
		}
	}

}
