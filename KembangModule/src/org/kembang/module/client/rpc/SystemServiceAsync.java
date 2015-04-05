package org.kembang.module.client.rpc;

import java.util.List;

import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.CheckModuleDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SystemServiceAsync {
	void chekSignature(String signature, String place,
			AsyncCallback<CheckModuleDv> callback);

	void listSimpleBranch(String session, AsyncCallback<List<SimpleBranchDv>> callback);
}
