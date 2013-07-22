package com.zafaralam.xmlparser;

import com.zafaralam.utils.ParserType;

public abstract class FeedParserFactory {
	//static String feedUrl = "http://www.androidster.com/feed/";//"http://www.androidster.com/android_news.rss";//"http://static.cricinfo.com/rss/livescores.xml";
	static String feedUrl = "http://free.worldweatheronline.com/feed/weather.ashx?q=melbourne&format=xml&num_of_days=5&key=8cb1d7878b154559122412";
	
	public static FeedParser getParser(){
		return getParser(ParserType.XML_PULL,feedUrl);
	}
	
	public static FeedParser getParser(ParserType type,String feedUrl){
		switch (type){
			case SAX:
				//return new SaxFeedParser(feedUrl);
			case DOM:
				//return new DomFeedParser(feedUrl);
			case ANDROID_SAX:
				//return new AndroidSaxFeedParser(feedUrl);
			case XML_PULL:
				//System.out.println("Came Here " + feedUrl);
				return new XmlPullFeedParser(feedUrl);
			default: return null;
		}
	}
}
