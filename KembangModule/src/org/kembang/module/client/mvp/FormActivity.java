package org.kembang.module.client.mvp;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public abstract class FormActivity extends AbstractActivity {

	KembangClientFactory mainFactory;

	protected void setMainFactory(KembangClientFactory appFactory) {
		mainFactory = appFactory;
	}

	public abstract void start(AcceptsOneWidget panel, EventBus eventBus);

	public void start(AcceptsOneWidget panel,
			com.google.gwt.event.shared.EventBus eventBus) {
		start(panel, (EventBus) eventBus);
	}

	public void showLoading() {
		DOM.getElementById("dataLoading").getStyle().setDisplay(Display.BLOCK);
	}

	public void hideLoading() {
		DOM.getElementById("dataLoading").getStyle().setDisplay(Display.NONE);
	}

	public String getKey() {
		return Cookies.getCookie(mainFactory.getAppStatus().getCookiesName());
	}

	public abstract void dispatch(FormActivityType formActivityType);

}
