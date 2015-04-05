package org.kembang.module.client.framework;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class AppForm extends Composite {

	private static AppFormUiBinder uiBinder = GWT.create(AppFormUiBinder.class);

	interface AppFormUiBinder extends UiBinder<Widget, AppForm> {
	}

	@UiField
	HorizontalPanel toolbar;
	@UiField
	HorizontalPanel toolbarBack;
	@UiField
	Label appTitle;
	@UiField
	DockLayoutPanel appPanel;

	public AppForm() {
		initWidget(uiBinder.createAndBindUi(this));
		addPanelSpacer();
	}

	public AppForm(Widget appPanel) {
		initWidget(uiBinder.createAndBindUi(this));
		setAppPanel(appPanel);
	}

	private void addPanelSpacer() {
		appPanel.addNorth(new Label(), 5);
		appPanel.addWest(new Label(), 5);
		appPanel.addEast(new Label(), 5);
		appPanel.addSouth(new Label(), 5);
	}

	public void addSpace() {
		HTMLPanel spacer = new HTMLPanel("&nbsp;&nbsp;&nbsp;");
		toolbar.add(spacer);
	}

	private PushButton createToolbarButton(String text, String icon,
			ClickHandler clickHandler) {
		Image image = new Image(icon);
		PushButton item = new PushButton();
		item.addClickHandler(clickHandler);
		item.setStyleName("kembang-toolbarItem");
		//
		String definedStyles = image.getElement().getAttribute("style");
		image.getElement().setAttribute("style",
				definedStyles + "; vertical-align:middle;");
		//
		Element span = DOM.createElement("span");
		span.setInnerText(text);
		span.setAttribute("style", "padding-left:3px; vertical-align:middle;");
		//
		DOM.insertChild(item.getElement(), span, 0);
		DOM.insertBefore(item.getElement(), image.getElement(),
				DOM.getFirstChild(item.getElement()));
		return item;
	}

	public PushButton addToolbarItem(String text, String icon,
			ClickHandler clickHandler) {
		PushButton item = createToolbarButton(text, icon, clickHandler);
		//
		toolbar.add(item);
		return item;
	}

	public PushButton addToolbarBack(String text, String icon,
			ClickHandler clickHandler) {
		PushButton item = createToolbarButton(text, icon, clickHandler);
		//
		toolbarBack.add(item);
		return item;
	}

	public void setAppPanel(Widget appPanel) {
		this.appPanel.clear();
		addPanelSpacer();
		this.appPanel.add(appPanel);
	}

	public void setTitle(String title) {
		appTitle.setText(title);
	}
}
