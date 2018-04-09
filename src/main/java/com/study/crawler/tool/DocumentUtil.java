package com.study.crawler.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DocumentUtil {
	private static final Log logger = LogFactory.getLog(DocumentUtil.class);

	/**
	 * @introduction: 通过Jsoup直接获取页面
	 * @param url
	 * @return
	 */
	public static Document getDocumentWithJsoup(String url) {
		Document document = null;
		try {
			document = Jsoup.connect(url).timeout(35000).get();
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			logger.error(sw.toString());
		}
		return document;
	}

	/**
	 * @introduction: 获取页面，不用代理,获取一次
	 * @param urlStr
	 * @return
	 */
	public static Document getDocument(String urlStr) {
		return repeatAcquireDocument(urlStr, false, 1);
	}

	public static Document getDocumentWithProxy(String urlStr) {
		return repeatAcquireDocument(urlStr, true, 30);
	}

	/**
	 * @introduction: 多次尝试获取页面
	 * @param urlStr
	 * @param useProxy
	 * @param times
	 * @return
	 */
	public static Document repeatAcquireDocument(String urlStr, boolean useProxy, int times) {
		// 这里只是简单的用是否包含title做个判断
		VerifyDocument verifyDocument = new VerifyDocument() {
			@Override
			boolean tryAgain(Document document) {
				if (document != null) {
					Elements elements = document.select("title");
					if (elements.size() > 0) {
						return false;
					}
				}
				return true;
			}
		};
		return repeatAcquireDocument(urlStr, useProxy, times, verifyDocument);
	}

	/**
	 * @introduction: 多次尝试获取页面（需重写验证页面的方法）
	 * @param urlStr
	 * @param useProxy
	 * @param times
	 * @param verifyDocument
	 * @return
	 */
	public static Document repeatAcquireDocument(String urlStr, boolean useProxy, int times,
			VerifyDocument verifyDocument) {
		int tryTime = 0;
		Document document = getDocument(urlStr, useProxy);
		while (verifyDocument.tryAgain(document)) {
			document = getDocument(urlStr, useProxy);
			tryTime++;
			if (tryTime == times) {
				break;
			}
		}
		return document;
	}

	/**
	 * @introduction: 获取页面
	 * @param urlStr
	 * @param useProxy
	 * @return
	 */
	public static Document getDocument(String urlStr, boolean useProxy) {
		try {
			HttpURLConnection connection = null;
			URL url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();

			connection.setConnectTimeout(7000);
			connection.setReadTimeout(7000);
			if (HttpURLConnection.HTTP_OK != connection.getResponseCode()) {
				logger.info("==========获取页面出错  响应的Code是：" + connection.getResponseCode() + "============");
				return null;
			}
			Document document = deCodingConnection(connection);
			connection.disconnect();
			return document;
		} catch (IOException e) {
			logger.info("==========获取页面出错的Url为：" + urlStr + "============");
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			logger.error(sw.toString());
			return null;
		}
	}

	/**
	 * @introduction: 正确解码获取页面
	 * @param connection
	 * @return
	 */
	private static Document deCodingConnection(HttpURLConnection connection) {
		try {
			connection.connect();
			// 避免乱码的处理
			String charset = connection.getHeaderField("Content-Type");
			charset = detectCharset(charset);
			InputStream input = getInputStream(connection);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			int count;
			byte[] buffers = new byte[4096];
			while ((count = input.read(buffers, 0, buffers.length)) > 0) {
				output.write(buffers, 0, count);
			}
			input.close();
			// 若已通过请求头得到charset，则不需要去html里面继续查找
			if (charset == null || "".equals(charset)) {
				charset = detectCharset(output.toString());
				// 若在html里面还是未找到charset，则设置默认编码为utf-8
				if (charset == null || "".equals(charset)) {
					charset = "utf-8";
				}
			}
			String result = output.toString(charset);
			output.close();
			return Jsoup.parse(result);
		} catch (Exception e) {
			logger.info("==========解析页面出错：" + connection.getURL().toString() + "============");
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			logger.error(sw.toString());
			return null;
		}

	}

	private static String detectCharset(String input) {
		Pattern pattern = Pattern.compile("charset=\"?([\\w\\d-]+)\"?;?", Pattern.CASE_INSENSITIVE);
		if (input != null && !"".equals(input)) {
			Matcher matcher = pattern.matcher(input);
			if (matcher.find()) {
				return matcher.group(1);
			}
		}
		return null;
	}

	private static InputStream getInputStream(HttpURLConnection conn) throws Exception {
		String contentEncoding = conn.getHeaderField("Content-Encoding");
		if (contentEncoding != null) {
			contentEncoding = contentEncoding.toLowerCase();
			if (contentEncoding.indexOf("gzip") != -1) {
				return new GZIPInputStream(conn.getInputStream());
			} else if (contentEncoding.indexOf("deflate") != -1) {
				return new DeflaterInputStream(conn.getInputStream());
			}
		}
		return conn.getInputStream();
	}
}

/**
 *
 * @Project: TAGLIB-CRAWLER
 * @File: DocumentUtil.java
 * @Date: 2016年11月28日
 * @Author: caiqibin
 * @introduction:判断页面是否需要重新获取抽象类
 */
abstract class VerifyDocument {

	/**
	 * @introduction: 根据页面验证是否要重新获取
	 * @param document
	 * @return
	 */
	abstract boolean tryAgain(Document document);
}
