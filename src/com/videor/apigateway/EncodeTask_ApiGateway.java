package com.videor.apigateway;

import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.cloudapi.sdk.model.ApiCallback;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;
import com.videor.dao.dto.task;

import java.io.IOException;

import java.security.SecureRandom;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class EncodeTask_ApiGateway {
	private static String auth = "c83f88a143d9c5cab1124460194616adcfd7c539581700f28a714ebaba8bf255";
	static {
		// // // HTTP Client init
		// HttpClientBuilderParams httpParam = new HttpClientBuilderParams();
		// httpParam.setAppKey("");
		// httpParam.setAppSecret("");
		// HttpApiClient_Encode.getInstance().init(httpParam);

		// HTTPS Client init
		HttpClientBuilderParams httpsParam = new HttpClientBuilderParams();
		// httpsParam.setAppKey("");
		// httpsParam.setAppSecret("");

		/**
		 * HTTPS request use DO_NOT_VERIFY mode only for demo Suggest verify for
		 * security
		 */
		X509TrustManager xtm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				X509Certificate[] x509Certificates = new X509Certificate[0];
				return x509Certificates;
			}
		};

		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { xtm }, new SecureRandom());

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		}
		HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		httpsParam.setSslSocketFactory(sslContext.getSocketFactory());
		httpsParam.setX509TrustManager(xtm);
		httpsParam.setHostnameVerifier(DO_NOT_VERIFY);

		MyHttpsApiClient_Encode.getInstance().init(httpsParam);
		// HttpsApiClient_Encode.getInstance().init(httpsParam);

	}

	public static void CreateEncodeHttpsTest(task t) {

		MyHttpsApiClient_Encode.getInstance().CreateEncode(auth, t.getFiles().get(0).getFileName().split("\\.")[0],
				t.getTaskId(), new ApiCallback() {
					@Override
					public void onFailure(ApiRequest request, Exception e) {
						e.printStackTrace();
					}

					@Override
					public void onResponse(ApiRequest request, ApiResponse response) {
						try {
							System.out.println(getResultString(response));
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
		// HttpsApiClient_Encode.getInstance().CreateEncode(auth,
		// t.getFiles().get(0).getFileName().split("\\.")[0],
		// t.getTaskId(), new ApiCallback() {
		// @Override
		// public void onFailure(ApiRequest request, Exception e) {
		// e.printStackTrace();
		// }
		//
		// @Override
		// public void onResponse(ApiRequest request, ApiResponse response) {
		// try {
		// System.out.println(getResultString(response));
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// }
		// });
	}

	// public static void StopEncodeHttpsTest() {
	// HttpsApiClient_Encode.getInstance().StopEncode("", "", new ApiCallback() {
	// @Override
	// public void onFailure(ApiRequest request, Exception e) {
	// e.printStackTrace();
	// }
	//
	// @Override
	// public void onResponse(ApiRequest request, ApiResponse response) {
	// try {
	// System.out.println(getResultString(response));
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }
	// });
	// }

	private static String getResultString(ApiResponse response) throws IOException {
		StringBuilder result = new StringBuilder();
		result.append("Response from backend server").append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
		result.append("ResultCode:").append(SdkConstant.CLOUDAPI_LF).append(response.getCode())
				.append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
		if (response.getCode() != 200) {
			result.append("Error description:").append(response.getHeaders().get("X-Ca-Error-Message"))
					.append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
		}

		result.append("ResultBody:").append(SdkConstant.CLOUDAPI_LF)
				.append(new String(response.getBody(), SdkConstant.CLOUDAPI_ENCODING));

		return result.toString();
	}
}
