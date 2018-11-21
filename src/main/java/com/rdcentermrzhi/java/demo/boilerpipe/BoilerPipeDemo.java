package com.rdcentermrzhi.java.demo.boilerpipe;

import java.io.IOException;
import java.net.URL;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLFetcher;

public class BoilerPipeDemo {

	// 具体文档可以查看下边地址
	// https://github.com/rdcentermrzhi/boilerpipe
	public static void main(String[] args) throws IOException, SAXException, BoilerpipeProcessingException {

		URL url = new URL("http://www.cnblogs.com/TM0831/p/9974718.html");
		final InputSource in = HTMLFetcher.fetch(url).toInputSource();

		final BoilerpipeSAXInput sax = new BoilerpipeSAXInput(in);

		final TextDocument text = sax.getTextDocument();
		System.out.println(ArticleExtractor.INSTANCE.getText(text));
	}
	
	
	
	
}
