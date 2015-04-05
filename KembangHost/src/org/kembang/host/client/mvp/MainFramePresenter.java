package org.kembang.host.client.mvp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kembang.host.client.frameview.FrameForm;
import org.kembang.host.client.rpc.HostService;
import org.kembang.host.client.rpc.HostServiceAsync;
import org.kembang.host.shared.CheckModuleDv;
import org.kembang.host.shared.ListMenuDv;
import org.kembang.host.shared.MenuDv;
import org.kembang.host.shared.UserDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class MainFramePresenter {

	private HostServiceAsync hostSrv = GWT.create(HostService.class);

	AppStatus appStatus = new AppStatus();
	MainFrameView mainView = new MainFrameView(new FrameForm());
	RootLayoutPanel rootPanel;

	String cookiesName;
	String cookiesPath;
	boolean viewLoaded = false;

	MainFramePresenter presenter = null;

	public MainFramePresenter(RootLayoutPanel rootPanel) {
		this.rootPanel = rootPanel;
		this.cookiesName = appStatus.getCookiesName();
		this.cookiesPath = appStatus.getCookiesPath();
		presenter = this;
	}

	public void onModuleLoad() {
		loadApp();
	}

	public List<MenuDv> getUserMenu() {
		return appStatus.getUserMenuList();
	}

	public List<MenuDv> getFastMenu() {
		return appStatus.getFastMenuList();
	}

	public void onLogin(String user, String password) {
		hostSrv.login(user, password, new AsyncCallback<CheckModuleDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : login");
				loginNotOk();
			}

			@Override
			public void onSuccess(CheckModuleDv result) {
				if (result.getSuccess()) {
					// set cookie
					createCookie(result.getUser());
					// bentuk tampilan
					appStatus.setLogin(true);
					appStatus.setUser(result.getUser());
					mainView.setNeedLoadMenu(true);
					//
					appStatus.setUserMenuList(result.getMenus());
					loadUserFastMenu();
				} else {
					hideLoading();
					Window.alert("Login gagal");
					//
					loginNotOk();
					//
				}
			}
		});
	}

	public void onLogout() {
		showLoading();
		// Ambil session id
		String sessionID = Cookies.getCookie(cookiesName);
		hostSrv.logout(sessionID, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				hideLoading();
				loginNotOk();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : logout");
			}
		});
	}

	public void loadUser() {
		showLoading();
		String signature = Cookies.getCookie(cookiesName);
		hostSrv.getUser(signature, new AsyncCallback<UserDv>() {

			@Override
			public void onSuccess(UserDv user) {
				hideLoading();
				PasswordDialog password = new PasswordDialog(presenter);
				password.setUser(user);
				password.center();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : logout");
			}
		});
	}

	public void loadFastMenu() {
		showLoading();
		String signature = Cookies.getCookie(cookiesName);
		hostSrv.listFastMenu(signature, new AsyncCallback<List<MenuDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : loadUserFastMenu");
			}

			@Override
			public void onSuccess(List<MenuDv> result) {
				hideLoading();
				FastMenuDialog fastMenuDialog = new FastMenuDialog(presenter);
				fastMenuDialog.setFastMenus(new ListMenuDv(result));
				fastMenuDialog.center();
			}
		});
	}

	private void createCookie(UserDv userDv) {
		String sessionId = userDv.getSignature();
		final long DURATION = 1000 * 60 * 60 * 24 * 14;
		// duration remembering login. 2 weeks in this example.
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie(cookiesName, sessionId, expires, null, cookiesPath,
				false);
		//
		appStatus.setUser(userDv);
		appStatus.setLogin(true);
	}

	private void loginOk() {
		mainView.setPresenter(this);
		mainView.setAppStatus(appStatus);
		mainView.setNeedLoadMenuIfNotInitialized(true);
		mainView.initView();
		showView();
	}

	private void loginNotOk() {
		Cookies.removeCookie(cookiesName, cookiesPath);
		//
		appStatus.setLogin(false);
		mainView.setAppStatus(appStatus);
		mainView.setPresenter(this);
		mainView.setNeedLoadMenu(true);
		mainView.onAfterLogout();
		showView();
	}

	private void showView() {
		if (!viewLoaded) {
			rootPanel.add(mainView.getWidget());
			viewLoaded = true;
		}
		hideLoading();
	}

	private void loadApp() {
		String sessionId = Cookies.getCookie(appStatus.getCookiesName());
		if (sessionId == null) {
			sessionId = "";
		}
		// Check session id
		if (!sessionId.isEmpty()) {
			hostSrv.chekSignature(sessionId,
					new AsyncCallback<CheckModuleDv>() {

						@Override
						public void onFailure(Throwable caught) {
							hideLoading();
							Window.alert("Error : checkSignature");
						}

						@Override
						public void onSuccess(CheckModuleDv result) {
							if (result.getSuccess()) {
								appStatus.setLogin(true);
								appStatus.setUser(result.getUser());
								appStatus.setUserMenuList(result.getMenus());
								//
								loadUserFastMenu();
							} else {
								onLogout();
							}
						}
					});
		} else {
			appStatus.setLogin(false);
			appStatus.setUser(null);
			loginNotOk();
		}
	}

	public void savePassword(String password) {
		showLoading();
		String sessionId = Cookies.getCookie(appStatus.getCookiesName());
		hostSrv.savePassword(sessionId, password, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				hideLoading();
				Window.alert("Perubahan kata kunci telah tersimpan");
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : savePassword");
			}
		});
	}

	public void saveFastMenu(ListMenuDv listMenu) {
		showLoading();
		String sessionId = Cookies.getCookie(appStatus.getCookiesName());
		hostSrv.saveFastMenu(sessionId, listMenu.getMenus(),
				new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						hideLoading();
						Window.alert("Menu cepat telah tersimpan");
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : saveFastMenu");
					}
				});
	}

	private void loadUserFastMenu() {
		List<MenuDv> fastMenuList = new ArrayList<MenuDv>();
		for (MenuDv menu : appStatus.getUserMenuList()) {
			if (menu.getFastMenu()) {
				fastMenuList.add(menu);
			}
		}
		appStatus.setFastMenuList(fastMenuList);
		loginOk();
	}

	private void showLoading() {
		DOM.getElementById("appLoading").getStyle().setDisplay(Display.BLOCK);
	}

	private void hideLoading() {
		DOM.getElementById("appLoading").getStyle().setDisplay(Display.NONE);
	}

}
