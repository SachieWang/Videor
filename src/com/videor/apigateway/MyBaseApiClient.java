package com.videor.apigateway;

import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.exception.SdkException;
import com.alibaba.cloudapi.sdk.model.ApiCallback;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.ApiResponse;

public abstract class MyBaseApiClient {

	// String appKey;
	// String appSecret;
	Scheme scheme;
	String host;
	boolean isInit = false;

	protected void checkIsInit() {
		if (!isInit) {
			throw new SdkException("MUST initial client before using");
		}
	}

	protected abstract ApiResponse sendSyncRequest(ApiRequest apiRequest);

	protected abstract void sendAsyncRequest(final ApiRequest apiRequest, final ApiCallback apiCallback);

}
