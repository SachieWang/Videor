package com.videor.apigateway;

import com.alibaba.cloudapi.sdk.constant.HttpConstant;
import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.util.HttpCommonUtil;
import com.alibaba.cloudapi.sdk.util.SignUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

public class MyApiRequestMaker {
	public static void make(ApiRequest request) {

		request.setPath(combinePathParam(request.getPath(), request.getPathParams()));

		StringBuilder url = new StringBuilder().append(request.getScheme().getValue()).append(request.getHost())
				.append(request.getPath());

		if (null != request.getQuerys() && request.getQuerys().size() > 0) {
			url.append("?").append(HttpCommonUtil.buildParamString(request.getQuerys()));
		}

		request.setUrl(url.toString());

		Date current = request.getCurrentDate() == null ? new Date() : request.getCurrentDate();

		if (null == request.getFirstHeaderValue(HttpConstant.CLOUDAPI_HTTP_HEADER_DATE)) {
			request.addHeader(HttpConstant.CLOUDAPI_HTTP_HEADER_DATE, getHttpDateHeaderValue(current));
		}

		request.addHeader(SdkConstant.CLOUDAPI_X_CA_TIMESTAMP, String.valueOf(current.getTime()));

		request.addHeader(SdkConstant.CLOUDAPI_X_CA_NONCE, UUID.randomUUID().toString());

		request.addHeader(HttpConstant.CLOUDAPI_HTTP_HEADER_USER_AGENT, SdkConstant.CLOUDAPI_USER_AGENT);

		request.addHeader(HttpConstant.CLOUDAPI_HTTP_HEADER_HOST, request.getHost());

		// request.addHeader(SdkConstant.CLOUDAPI_X_CA_KEY, appKey);

		request.addHeader(SdkConstant.CLOUDAPI_X_CA_VERSION, SdkConstant.CLOUDAPI_CA_VERSION_VALUE);

		if (null == request.getFirstHeaderValue(HttpConstant.CLOUDAPI_HTTP_HEADER_CONTENT_TYPE)) {
			request.addHeader(HttpConstant.CLOUDAPI_HTTP_HEADER_CONTENT_TYPE,
					request.getMethod().getRequestContentType());
		}

		if (null == request.getFirstHeaderValue(HttpConstant.CLOUDAPI_HTTP_HEADER_ACCEPT)) {
			request.addHeader(HttpConstant.CLOUDAPI_HTTP_HEADER_ACCEPT, request.getMethod().getAcceptContentType());
		}

		if (!HttpCommonUtil.isEmpty(request.getSignatureMethod())) {
			request.addHeader(SdkConstant.CLOUDAPI_X_CA_SIGNATURE_METHOD, request.getSignatureMethod());
		}

		if (null != request.getBody() && request.getBody().length > 0) {
			request.addHeader(HttpConstant.CLOUDAPI_HTTP_HEADER_CONTENT_MD5, SignUtil.base64AndMD5(request.getBody()));
		}

		// String signature = SignUtil.sign(request, appSecret);
		// request.addHeader(SdkConstant.CLOUDAPI_X_CA_SIGNATURE, signature);

		for (String key : request.getHeaders().keySet()) {
			List<String> values = request.getHeaders().get(key);
			if (null != values && values.size() > 0) {
				for (int i = 0; i < values.size(); i++) {
					byte[] temp = values.get(i).getBytes(SdkConstant.CLOUDAPI_ENCODING);
					values.set(i, new String(temp, SdkConstant.CLOUDAPI_HEADER_ENCODING));
				}
			}
			request.getHeaders().put(key, values);
		}

	}

	private static String combinePathParam(String path, Map<String, String> pathParams) {
		if (pathParams == null) {
			return path;
		}

		for (String key : pathParams.keySet()) {
			path = path.replace("[" + key + "]", pathParams.get(key));
		}
		return path;
	}

	private static String getHttpDateHeaderValue(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}

}
