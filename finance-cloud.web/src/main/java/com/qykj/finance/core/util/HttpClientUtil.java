package com.qykj.finance.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

/**
 *  HttpClient的工具类
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public class HttpClientUtil {
	protected static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HttpClientUtil.class);
	/**
	 * 单独为某个站点设置最大连接个数
	 */
	public static final int MAX_CONN_PER_ROUTE = 500;
	/**
	 * 最大总连接数
	 */
	public static final int MAX_CONN_TOTAL = 10000;
	/**
	 * 一分钟的毫秒数
	 */
	public static final int MINS = 60 * 1000;
	/**
	 * 响应超时时间，超过此时间不再读取响应
	 */
	public static final int SOCKET_TIMEOUT = 5 * MINS;
	/**
	 * 链接建立的超时时间
	 */
	public static final int CONNECT_TIMEOUT = 10 * MINS;
	/**
	 * httpclilent中从connetcion pool中获得一个connection的超时时间
	 */
	public static final int CONNECTION_REQUEST_TIMEOUT = 5 * MINS;
	/**
	 * 文本MIME类型
	 */
	public static final String[] TEXT_MIMES = new String[] { "text/html", "text/plain", "text/xml", "application/json",
			"application/xhtml+xml", "application/xml", "application/x-javascript", "application/javascript",
			"text/javascript", "text/css" };
	/**
	 * 默认编码
	 */
	public static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 是否文本类型
	 * @param mime 类型字符串
	 * @param defaultBoolean 不能判断，默认返回类型
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static boolean isTextMimeType(String mime, boolean defaultBoolean) {
		if (StringUtils.isEmpty(mime))
			return defaultBoolean;
		return ArrayUtils.contains(TEXT_MIMES, mime);
	}

	/**
	 * 根据请求配置创建CloseableHttpClient
	 * @param config 请求配置
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static CloseableHttpClient createDefaultHttpClient(RequestConfig config) {
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(config)
				.setMaxConnPerRoute(MAX_CONN_PER_ROUTE).setMaxConnTotal(MAX_CONN_TOTAL).build();
		return httpclient;
	}

	/**
	 * 创建默认请求配置
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static RequestConfig createDefaultHttpClientConfig() {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
				.setRedirectsEnabled(false).build();
		return requestConfig;
	}

	/**
	 * 创建默认CloseableHttpClient
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static CloseableHttpClient createDefault() {
		return createDefaultHttpClient(createDefaultHttpClientConfig());
	}

	/**
	 * 获取默认编码
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static String getDefaultCharset() {
		return DEFAULT_CHARSET;
	}

	/**
	 * 将参数添加到httpRequest中
	 * @param httpRequest 
	 * @param params
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static void addParameters(HttpRequestBase httpRequest, Map<String, String> params) {
		addParameters(httpRequest, params, null);
	}

	/**
	 * 将参数添加到httpRequest中 指定编码
	 * @param httpRequest
	 * @param params
	 * @param charset
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static void addParameters(HttpRequestBase httpRequest, Map<String, String> params, String charset) {
		charset = charset == null ? getDefaultCharset() : charset;

		if (params == null || params.size() == 0)
			return;

		if (httpRequest instanceof HttpGet) {
			StringBuffer param = new StringBuffer();

			for (Entry<String, String> entry : params.entrySet()) {
				try {
					param.append(entry.getKey()).append('=')
							.append(URLEncoder.encode(ObjectUtils.toString(entry.getValue()), charset)).append("&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			String paramStr = param.substring(0, param.length());
			if (StringUtils.isEmpty(paramStr))
				return;

			String url = httpRequest.getURI().toString();
			httpRequest.setURI(URI.create(url + (url.indexOf("?") == -1 ? "?" : "&") + paramStr));
		} else if (httpRequest instanceof HttpEntityEnclosingRequestBase) {
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			HttpEntity entity = null;
			try {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), ObjectUtils.toString(entry.getValue())));
				}

				entity = new UrlEncodedFormEntity(formParams, charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			((HttpEntityEnclosingRequestBase) httpRequest).setEntity(entity);
		}
	}

	/**
	 * 创建文件请求体
	 * @param module 模块名
	 * @param file 文件
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static HttpEntity createMutiPartEntit(String module, File file) {
		HttpEntity entity = null;
		try {
			String fileName = file.getName();
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("file", new FileInputStream(file), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
			ContentType contentType = ContentType.create("text/html", Charset.forName(DEFAULT_CHARSET));
			builder.addTextBody("filename", fileName, contentType);// 类似浏览器表单提交，对应input的name和value
			builder.addTextBody("module", module);
			entity = builder.setCharset(CharsetUtils.get(DEFAULT_CHARSET)).setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.build();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return entity;
	}

	/**
	 * 判断响应是否正确
	 * @param response
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static boolean isSC_OK(HttpResponse response) {
		return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
	}

	/**
	 * 发送get请求 响应文本
	 * @param url
	 * @param params
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static String request(String url, Map<String, String> params) {
		HttpClient httpClient = HttpClientUtil.createDefault();
		HttpRequestBase httpRequest = new HttpGet(url);
		HttpClientUtil.addParameters(httpRequest, params);
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpRequest);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			log.info("ClientProtocolException {} ", e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.info("IOException {} ", e.getMessage());
		}finally {
			HttpClientUtils.closeQuietly(response);
		}
		
		return "";
	}

}
