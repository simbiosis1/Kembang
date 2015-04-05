package org.kembang.host.client.frameview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SidebarForm extends Composite {

	private static SidebarFormUiBinder uiBinder = GWT
			.create(SidebarFormUiBinder.class);

	interface SidebarFormUiBinder extends UiBinder<Widget, SidebarForm> {
	}

	@UiField
	VerticalPanel sidebarPanel;

	SideBarClickHandler clickHandler;

	public SidebarForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	void addClickHandler(SideBarClickHandler clickHandler) {
		this.clickHandler = clickHandler;
	}

	public void addHeader(String text) {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.setStyleName("kembang-sidebarHeader");
		Label textLabel = new Label(text);
		textLabel.setStyleName("kembang-sidebarHeaderText");
		hp.add(textLabel);
		sidebarPanel.add(hp);
	}

	public Anchor addItem(String text, final String link) {
		//
		VerticalPanel item = new VerticalPanel();
		Anchor anchor = new Anchor(text, link);
		item.setStyleName("kembang-sidebarItem");
		anchor.setStyleName("kembang-sidebarItemText");
		anchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickHandler.onClick(link);
				event.preventDefault(); // If you want the href present
			}
		});
		item.add(anchor);
		sidebarPanel.add(item);
		return anchor;
	}

	public void clear() {
		sidebarPanel.clear();
	}

}
