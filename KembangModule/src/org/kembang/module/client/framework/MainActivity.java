package org.kembang.module.client.framework;

import org.kembang.module.client.framework.IMain.Activity;
import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.mvp.KembangClientFactory;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class MainActivity extends Activity {

	MainPlace myPlace;
	KembangClientFactory appFactory;

	public MainActivity(MainPlace myPlace, KembangClientFactory appFactory) {
		super();
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IMain myForm = appFactory.getMain();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
	}

}
