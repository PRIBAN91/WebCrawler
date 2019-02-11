package com.crawler.common;

/******************************************************************************
 *  Compilation:  javac WebCrawler.java In.java
 *  Execution:    java WebCrawler url
 *  
 *  Example URL: https://www.mkyong.com/
 *  This might run long for URLs with many sub domains
 *****************************************************************************/

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

	public static void main(String[] args) {

		// timeout connection after 500 miliseconds
		System.setProperty("sun.net.client.defaultConnectTimeout", "500");
		System.setProperty("sun.net.client.defaultReadTimeout", "1000");

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter URL to be crawled: ");

		// initial web page
		String s = sc.nextLine();

		// list of web pages to be examined
		Queue<String> queue = new ArrayDeque<String>();
		queue.add(s);

		// set of examined web pages
		Set<String> marked = new HashSet<String>();
		marked.add(s);

		// breadth first search crawl of web
		while (!queue.isEmpty()) {
			String v = queue.poll();
			System.out.println(v);

			String input = null;
			try {
				In in = new In(v);
				input = in.readAll().toLowerCase();
			} catch (IllegalArgumentException e) {
				System.out.println("[Could not open " + v + "]");
				continue;
			}

			// Matching only URLs in the subdomain
			String regexp = v + "(\\w+)*";
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(input);

			// find and print all matches
			while (matcher.find()) {
				String w = matcher.group() + "/";
				if (!marked.contains(w)) {
					queue.add(w);
					marked.add(w);
				}
			}

		}
		sc.close();
	}
}