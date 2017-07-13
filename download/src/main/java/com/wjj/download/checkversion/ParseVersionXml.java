//package com.wjj.download.checkversion;
//
//import android.util.Xml;
//
//import com.wjj.download.entity.Version;
//
//import org.xmlpull.v1.XmlPullParser;
//
//import java.io.InputStream;
//
//public class ParseVersionXml {
//	public Version parseXml(InputStream is) {
//		Version chkResult = new Version();
//		long start = 0L, end = 0L, time = 0L;
//
//		XmlPullParser pullParser = Xml.newPullParser();
//		try {
//			pullParser.setInput(is, "UTF-8");
//			int event = pullParser.getEventType();
//			while (event != XmlPullParser.END_DOCUMENT) {
//				switch (event) {
//				case XmlPullParser.START_DOCUMENT:
//					start = System.currentTimeMillis();
//					System.out.println("----------------- start pare xml at "
//							+ start + "---------------");
//					break;
//				case XmlPullParser.START_TAG:
//					if ("verCode".equals(pullParser.getName())) {
//						chkResult.setVerCode(pullParser.nextText());
//						break;
//					}
//					if ("verName".equals(pullParser.getName())) {
//						chkResult.setVerName(pullParser.nextText());
//						break;
//					}
//					if ("verInfo".equals(pullParser.getName())) {
//						chkResult.setVerInfo(pullParser.nextText());
//						break;
//					}
//					if ("md5Str".equals(pullParser.getName())) {
//						chkResult.setMd5Str(pullParser.nextText());
//						break;
//					}
//					if ("isForce".equals(pullParser.getName())) {
//						chkResult.setIsForce(pullParser.nextText());
//						break;
//					}
//					if ("apkUrl".equals(pullParser.getName())) {
//						chkResult.setApkUrl(pullParser.nextText());
//						break;
//					}
//				case XmlPullParser.END_TAG:
//					if ("apkVersion".equals(pullParser.getName())) {
//					}
//				}
//				event = pullParser.next();
//			}
//			end = System.currentTimeMillis();
//		} catch (Exception e) {
//			System.out.println("------------ xml error -----------");
//			chkResult.setSucc(false);
//			chkResult.setError("解析xml出现错误");
//			e.printStackTrace();
//		}
//		time = end - start;
//		System.out.println("--------------- parse time " + time
//				+ " millisecond---------------");
//		return chkResult;
//	}
//}
