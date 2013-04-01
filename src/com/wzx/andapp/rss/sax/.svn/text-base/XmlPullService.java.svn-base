package com.wzx.andapp.rss.sax;

import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Xml;

import com.wzx.andapp.rss.bo.RSSFeed;
import com.wzx.andapp.rss.bo.RSSItem;

public class XmlPullService {
	public static RSSFeed ReadXmlByPull3(InputStream inputStream)
			throws Exception {
		RSSFeed feed = null;
		/**
		 * android给我们提供了xml 用来得到xmlpull解析器
		 */
		XmlPullParser xmlpull = // XmlPullParserFactory.newInstance().newPullParser();//
		Xml.newPullParser();
		/**
		 * 将输入流传入 设定编码方式
		 * 
		 */
		xmlpull.setInput(inputStream, null);
		// xmlpull.defineEntityReplacementText( "nbsp", "\u00a0" );
		// xmlpull.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
		/**
		 * pull读到xml后 返回数字 读取到xml的声明返回数字0 START_DOCUMENT; 读取到xml的结束返回数字1
		 * END_DOCUMENT ; 读取到xml的开始标签返回数字2 START_TAG 读取到xml的结束标签返回数字3 END_TAG
		 * 读取到xml的文本返回数字4 TEXT
		 */
		int eventCode = xmlpull.getEventType();
		/**
		 * 只要这个事件返回的不是1 我们就一直读取xml文件
		 */
		RSSItem item = null;
		boolean nitem = false;
		while (eventCode != XmlPullParser.END_DOCUMENT) {

			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT: {// 开始文档 new数组
				feed = new RSSFeed();
				break;
			}
			case XmlPullParser.START_TAG: {
				if ("item".equals(xmlpull.getName())) {
					item = new RSSItem();
					nitem = true;
				} else if (nitem) {
					if (("title".equals(xmlpull.getName()))) {
						/**
						 * 如果当前元素的下一个元素是文本节点 就可以直接用nextText()这个方法来得到文本节点的内容
						 */
						item.setTitle(xmlpull.nextText());
					} else if (("link".equals(xmlpull.getName()))) {
						/**
						 * 如果当前元素的下一个元素是文本节点 就可以直接用nextText()这个方法来得到文本节点的内容
						 */
						item.setLink(xmlpull.nextText());
					} else if (("description".equals(xmlpull.getName()))) {
						/**
						 * 如果当前元素的下一个元素是文本节点 就可以直接用nextText()这个方法来得到文本节点的内容
						 */
						item.setDescription(xmlpull.nextText());
					} else if (("category".equals(xmlpull.getName()))) {
						/**
						 * 如果当前元素的下一个元素是文本节点 就可以直接用nextText()这个方法来得到文本节点的内容
						 */
						item.setCategory(xmlpull.nextText());
					} else if (("pubDate".equals(xmlpull.getName()))) {
						/**
						 * 如果当前元素的下一个元素是文本节点 就可以直接用nextText()这个方法来得到文本节点的内容
						 */
						item.setPubDate(xmlpull.nextText());
					}
				}
				break;
			}

			case XmlPullParser.END_TAG: {
				if ("item".equals(xmlpull.getName())) {
					if (item != null) {
						feed.addItem(item);
						nitem = false;
					}
					break;
				}
				break;
			}
			}

			eventCode = xmlpull.next();// 没有结束xml文件就推到下个进行解析

		}

		return feed;
	}
}
