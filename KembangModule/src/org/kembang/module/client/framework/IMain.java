package org.kembang.module.client.framework;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;


public interface IMain {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	public abstract class Activity extends FormActivity {
	}
}
