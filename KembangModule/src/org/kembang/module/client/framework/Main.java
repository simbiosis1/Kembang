package org.kembang.module.client.framework;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;

public class Main extends FormWidget implements IMain {
	Activity activity;

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setActivity(activity, appStatus);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}
}
