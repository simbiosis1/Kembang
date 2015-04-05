package org.kembang.module.client.mvp;

import org.kembang.module.client.framework.AppForm;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

/*
 * Kelas untuk mendefinisikan widget form
 * Memiliki kekhususan yaitu memiliki toolbar
 */
public class FormWidget extends Composite {
	PushButton btnNew = null;
	boolean hasNew = false;
	boolean showNew = true;
	PushButton btnEdit = null;
	boolean hasEdit = false;
	boolean showEdit = true;
	PushButton btnSave = null;
	boolean hasSave = false;
	boolean showSave = true;
	PushButton btnView = null;
	boolean hasView = false;
	boolean showView = true;
	PushButton btnDelete = null;
	boolean hasDelete = false;
	boolean showDelete = true;
	PushButton btnReload = null;
	boolean hasReload = false;
	boolean showReload = true;
	PushButton btnUpload = null;
	boolean hasUpload = false;
	boolean showUpload = true;
	PushButton btnSearch = null;
	boolean hasSearch = false;
	boolean showSearch = true;
	PushButton btnBack = null;
	boolean hasBack = false;
	boolean showBack = true;
	PushButton btnExportPdf = null;
	boolean hasExportPdf = false;
	boolean showExportPdf = true;
	PushButton btnExportXls = null;
	boolean hasExportXls = false;
	boolean showExportXls = true;
	//
	AppForm appForm = null;
	FormActivity formActivity;
	// Flag
	boolean initialized = false;
	AppStatus appStatus;

	public FormWidget() {
	}

	public void showLoading() {
		DOM.getElementById("dataLoading").getStyle().setDisplay(Display.BLOCK);
	}

	public void hideLoading() {
		DOM.getElementById("dataLoading").getStyle().setDisplay(Display.NONE);
	}

	public void initView(String title) {
		int block1 = 0;
		int block2 = 0;
		if (!initialized) {
			// Title
			appForm.setTitle(title);
			// Toolbar
			if (hasNew) {
				btnNew = appForm.addToolbarItem("Baru", "images/new.png",
						new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity.dispatch(FormActivityType.FA_NEW);
							}
						});
				if (!showNew)
					btnNew.setVisible(showNew);
				block1++;
			}
			if (hasView) {
				btnView = appForm.addToolbarItem("Lihat", "images/view.png",
						new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity.dispatch(FormActivityType.FA_VIEW);
							}
						});
				if (!showView)
					btnView.setVisible(showView);
				block1++;
			}
			if (hasEdit) {
				btnEdit = appForm.addToolbarItem("Ubah", "images/edit.png",
						new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity.dispatch(FormActivityType.FA_EDIT);
							}
						});
				if (!showEdit)
					btnEdit.setVisible(showEdit);
				block1++;
			}
			if (hasSave) {
				btnSave = appForm.addToolbarItem("Simpan", "images/save.png",
						new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity.dispatch(FormActivityType.FA_SAVE);
							}
						});
				if (!showSave)
					btnSave.setVisible(showSave);
				block1++;
			}
			if (hasDelete) {
				btnDelete = appForm.addToolbarItem("Hapus",
						"images/delete.png", new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity
										.dispatch(FormActivityType.FA_DELETE);
							}
						});
				if (!showDelete)
					btnDelete.setVisible(showDelete);
				block1++;
			}
			if (hasReload) {
				if (block1 > 0) {
					appForm.addSpace();
				}
				btnReload = appForm.addToolbarItem("Reload",
						"images/reload.png", new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity
										.dispatch(FormActivityType.FA_RELOAD);
							}
						});
				if (!showReload)
					btnReload.setVisible(showReload);
				block2++;
			}
			if (hasUpload) {
				if (block1 > 0 && block2 == 0) {
					appForm.addSpace();
				}
				btnUpload = appForm.addToolbarItem("Unggah",
						"images/upload.png", new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity
										.dispatch(FormActivityType.FA_UPLOAD);
							}
						});
				if (!showUpload)
					btnUpload.setVisible(showUpload);
				block2++;
			}
			if (hasSearch) {
				if (block1 > 0 && block2 == 0) {
					appForm.addSpace();
				}
				btnSearch = appForm.addToolbarItem("Cari", "images/search.png",
						new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity
										.dispatch(FormActivityType.FA_SEARCH);
							}
						});
				if (!showSearch)
					btnSearch.setVisible(showSearch);
				block2++;
			}
			if (hasExportPdf) {
				if (block1 > 0 && block2 == 0) {
					appForm.addSpace();
				}
				btnExportPdf = appForm.addToolbarItem("Ekspor PDF",
						"images/pdf.png", new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity
										.dispatch(FormActivityType.FA_EXPORTPDF);
							}
						});
				if (!showExportPdf)
					btnExportPdf.setVisible(showExportPdf);
				block2++;
			}
			if (hasExportXls) {
				if (block1 > 0 && block2 == 0) {
					appForm.addSpace();
				}
				btnExportXls = appForm.addToolbarItem("Ekspor XLS",
						"images/xls.png", new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity
										.dispatch(FormActivityType.FA_EXPORTXLS);
							}
						});
				if (!showExportXls)
					btnExportXls.setVisible(showExportXls);
				block2++;
			}
			//
			if (hasBack) {
				btnBack = appForm.addToolbarBack("Kembali", "images/back.png",
						new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formActivity.dispatch(FormActivityType.FA_BACK);
							}
						});
				if (!showBack)
					btnBack.setVisible(showBack);
			}
			//
			initialized = true;
		}
	}

	public void clearView() {
	}

	public void setFormActivity(FormActivity formActivity) {
		this.formActivity = formActivity;
	}

	@Override
	public void initWidget(Widget widget) {
		super.initWidget(widget);
	}

	public void setHasNew(boolean showNew) {
		this.hasNew = true;
		this.showNew = showNew;
	}

	public void showNew(boolean show) {
		btnNew.setVisible(show);
	}

	public void setHasEdit(boolean showEdit) {
		this.hasEdit = true;
		this.showEdit = showEdit;
	}

	public void showEdit(boolean show) {
		btnEdit.setVisible(show);
	}

	public void setHasView(boolean showView) {
		this.hasView = true;
		this.showView = showView;
	}

	public void showView(boolean show) {
		btnView.setVisible(show);
	}

	public void setHasSave(boolean showSave) {
		this.hasSave = true;
		this.showSave = showSave;
	}

	public void showSave(boolean show) {
		btnSave.setVisible(show);
	}

	public void setHasDelete(boolean showDelete) {
		this.hasDelete = true;
		this.showDelete = showDelete;
	}

	public void showDelete(boolean show) {
		btnDelete.setVisible(show);
	}

	public void setHasReload(boolean showReload) {
		this.hasReload = true;
		this.showReload = showReload;
	}

	public void showReload(boolean show) {
		btnReload.setVisible(show);
	}

	public void setHasSearch(boolean showSearch) {
		this.hasSearch = true;
		this.showSearch = showSearch;
	}

	public void showSearch(boolean show) {
		btnSearch.setVisible(show);
	}

	public void setHasExportPdf(boolean showExportPdf) {
		this.hasExportPdf = true;
		this.showExportPdf = showExportPdf;
	}

	public void showExportPdf(boolean show) {
		btnExportPdf.setVisible(show);
	}

	public void setHasExportXls(boolean showExportXls) {
		this.hasExportXls = true;
		this.showExportXls = showExportXls;
	}

	public void showExportXls(boolean show) {
		btnExportXls.setVisible(show);
	}

	public void setHasBack(boolean showBack) {
		this.hasBack = true;
		this.showBack = showBack;
	}

	public void showBack(boolean show) {
		btnBack.setVisible(show);
	}

	public AppStatus getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(AppStatus appStatus) {
		this.appStatus = appStatus;
	}

	public AppForm getAppForm() {
		return appForm;
	}

	public void setAppForm(AppForm appForm) {
		this.appForm = appForm;
	}

	public boolean isInitialized() {
		return initialized;
	}

}
