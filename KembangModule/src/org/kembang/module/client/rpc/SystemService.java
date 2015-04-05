package org.kembang.module.client.rpc;

import java.util.List;

import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.CheckModuleDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("system")
public interface SystemService extends RemoteService {
	CheckModuleDv chekSignature(String signature, String place)
			throws IllegalArgumentException;

	List<SimpleBranchDv> listSimpleBranch(String session) throws IllegalArgumentException;
}
