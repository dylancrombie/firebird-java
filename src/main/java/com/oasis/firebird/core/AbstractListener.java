package com.oasis.firebird.core;

import java.util.List;

@Deprecated
public interface AbstractListener {

	void onError(List<ErrorMessage> errorMessages);
	
}
