package org.kembang.editor.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KembangDialogBox extends PopupPanel {

	VerticalPanel vp = new VerticalPanel();
	VerticalPanel area = new VerticalPanel();
	HorizontalPanel buttonArea = new HorizontalPanel();
	HorizontalPanel innerButtonArea = new HorizontalPanel();

	public KembangDialogBox() {
		super();
		setGlassEnabled(true);
		setAnimationEnabled(true);
		//
		vp.setStyleName("kembangDialogBox");
		//
		Label caption = new Label("Caption");
		caption.setStyleName("kembangDialogBoxCaption");
		vp.add(caption);
		area.setStyleName("kembangDialogBoxArea");
		vp.add(area);
		buttonArea.setStyleName("kembangDialogBoxButton");
		buttonArea.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		buttonArea.add(innerButtonArea);
		vp.add(buttonArea);
		super.setWidget(vp);
	}

	public void setText(String text) {
		Label caption = (Label) vp.getWidget(0);
		caption.setText(text);
	}

	public void addButton(Button button) {
		button.setStyleName("formButton");
		innerButtonArea.add(button);
		innerButtonArea.add(new HTML("&nbsp"));
	}

	@Override
	public void setWidget(Widget w) {
		area.add(w);
	}
}
