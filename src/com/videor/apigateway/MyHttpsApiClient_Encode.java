package com.videor.apigateway;

import com.alibaba.cloudapi.sdk.enums.HttpMethod;
import com.alibaba.cloudapi.sdk.enums.ParamPosition;
import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.model.ApiCallback;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;

public class MyHttpsApiClient_Encode extends MyHttpsClient {
	
	// TODO modify apihost name
	public final static String HOST = "HOST";
	static MyHttpsApiClient_Encode instance = new MyHttpsApiClient_Encode();

	public static MyHttpsApiClient_Encode getInstance() {
		return instance;
	}

	public void init(HttpClientBuilderParams httpClientBuilderParams) {
		httpClientBuilderParams.setScheme(Scheme.HTTPS);
		httpClientBuilderParams.setHost(HOST);
		super.init(httpClientBuilderParams);
	}

	public void CreateEncode(String X_encode_auth, String X_encode_file, String X_encode_id, ApiCallback callback) {
		String path = "/createencode";
		ApiRequest request = new ApiRequest(HttpMethod.GET, path);
		request.addParam("X-encode-auth", X_encode_auth, ParamPosition.HEAD, true);
		request.addParam("X-encode-file", X_encode_file, ParamPosition.HEAD, true);
		request.addParam("X-encode-id", X_encode_id, ParamPosition.HEAD, true);
		sendAsyncRequest(request, callback);
	}

	public void StopEncode(String X_encode_auth, String X_encode_id, ApiCallback callback) {
		String path = "/stopencode";
		ApiRequest request = new ApiRequest(HttpMethod.GET, path);
		request.addParam("X-encode-auth", X_encode_auth, ParamPosition.HEAD, true);
		request.addParam("X-encode-id", X_encode_id, ParamPosition.HEAD, true);
		sendAsyncRequest(request, callback);
	}
}
