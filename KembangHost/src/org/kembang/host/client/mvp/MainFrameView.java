package org.kembang.host.client.mvp;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainFrameView {

	MainFrame mainFrame;
	MainFramePresenter presenter;
	AppStatus appStatus;

	boolean needLoad;
	boolean initialized = false;

	class UserMenu extends PopupPanel {
		String width;
		String height;
		Label userName;
		Label company;
		Label preferences;
		Label logout;

		public UserMenu(int left, int top, String width, String height) {
			super(true);
			this.width = width;
			this.height = height;
			addWidget();
			setPopupPosition(left, top);
		}

		void addWidget() {
			VerticalPanel fp = new VerticalPanel();
			fp.setStyleName("callOutOuter");
			Image image = new Image("images/arrow_up.png");
			fp.add(image);
			image.setStyleName("callOutArrow");
			VerticalPanel vp = new VerticalPanel();
			vp.setWidth(width);
			vp.setStyleName("callOutPanel");
			//
			VerticalPanel vpName = new VerticalPanel();
			vpName.setStyleName("callOutVpName");
			vpName.add(userName = new Label(""));
			userName.setStyleName("formUserNameLabel");
			vpName.add(company = new Label(""));
			company.setStyleName("formUserInfoLabel");
			vp.add(vpName);
			//
			vp.add(preferences = new Label("Ganti kata kunci"));
			preferences.setStyleName("formUserItemLabel");
			preferences.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					hide();
					showChangePassword();
				}
			});
			//
			vp.add(preferences = new Label("Edit menu cepat"));
			preferences.setStyleName("formUserItemLabel");
			preferences.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					hide();
					showEditFastMenu();
				}
			});
			//
			vp.add(logout = new Label("Keluar"));
			logout.setStyleName("formUserItemLabel");
			logout.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					hide();
					showLogout();
				}
			});
			fp.add(vp);
			add(fp);
		}

		void setUserName(String userName) {
			this.userName.setText(userName);
		}

		void setCompany(String company) {
			this.company.setText(company);
		}
	}

	public MainFrameView(MainFrame theMainFrame) {
		this.mainFrame = theMainFrame;
		mainFrame.addUserConfigHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Widget pb = mainFrame.getConfigButton();
				int width = 250;
				int height = 200;
				int left = pb.getAbsoluteLeft() - width + pb.getOffsetWidth()
						- 5;
				int top = pb.getAbsoluteTop() + pb.getOffsetHeight() - 6;
				UserMenu userMenu = new UserMenu(left, top, width + "px",
						height + "px");
				userMenu.setUserName(appStatus.getUser().getRealName());
				userMenu.setCompany(appStatus.getUser().getStrCompany() + " - "
						+ appStatus.getUser().getStrBranch());
				userMenu.show();
			}
		});
	}

	public void setAppStatus(AppStatus appStatus) {
		this.appStatus = appStatus;
	}

	public void setPresenter(MainFramePresenter presenter) {
		this.presenter = presenter;
	}

	public Widget getWidget() {
		return mainFrame.getWidget();
	}

	public void initView() {
		if (appStatus.isLogin()) {
			mainFrame.setUserMenu(presenter.getUserMenu());
			mainFrame.setFastMenu(presenter.getFastMenu());
		}
	}

	public void setNeedLoadMenu(boolean needLoad) {
		this.needLoad = needLoad;
	}

	public void setNeedLoadMenuIfNotInitialized(boolean needLoad) {
		if (!initialized) {
			this.needLoad = needLoad;
			initialized = true;
		}
	}

	public boolean isNeedLoad() {
		return needLoad;
	}

	private void showLogin() {
		LoginDialog login = new LoginDialog(presenter);
		login.showPopup();
	}

	private void showLogout() {
		LogoutDialog logout = new LogoutDialog(presenter);
		logout.center();
	}

	private void showChangePassword() {
		presenter.loadUser();
	}

	private void showEditFastMenu() {
		presenter.loadFastMenu();
	}

	public void onAfterLogout() {
		initialized = false;
		mainFrame.clearUi();
		setNeedLoadMenu(true);
		showLogin();
	}
}
