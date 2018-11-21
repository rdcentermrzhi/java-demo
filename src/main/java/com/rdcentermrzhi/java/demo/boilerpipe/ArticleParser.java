package com.rdcentermrzhi.java.demo.boilerpipe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.jasongoodwin.monads.Futures;
import com.jasongoodwin.monads.Try;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLFetcher;

public class ArticleParser {
	public static Try<String> apply(String html) {
		return Try.ofFailable(() -> de.l3s.boilerpipe.extractors.ArticleExtractor.INSTANCE.getText(html));
	}

	public static Try<String> apply(TextDocument html) {
		return Try.ofFailable(() -> de.l3s.boilerpipe.extractors.ArticleExtractor.INSTANCE.getText(html));
	}

	public static void main(String[] args) throws Throwable {
		List<String> articleUrl = new ArrayList<>();
		
		articleUrl.add("http://www.cnblogs.com/TM0831/p/9974718.html");
		articleUrl.add("http://www.cnblogs.com/lijizhan/p/9994552.html");
		articleUrl.add("http://www.cnblogs.com/pengfei-nie/p/9994226.html");
		articleUrl.add("http://www.cnblogs.com/milicool/p/9993918.html");

		List<CompletableFuture<Try<String>>> futureList = articleUrl.stream().map(artice -> {

			try {
				URL url = new URL(artice);
				final InputSource input = HTMLFetcher.fetch(url).toInputSource();

				final BoilerpipeSAXInput sax = new BoilerpipeSAXInput(input);
				final TextDocument text = sax.getTextDocument();
				return CompletableFuture.supplyAsync(() -> ArticleParser.apply(text));
			} catch (IOException | SAXException | BoilerpipeProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}).collect(Collectors.toList());

		Future<List<Try<String>>> ListFutre = Futures.sequence(futureList);
		
		for(Try<String> t: ListFutre.get()) {
			System.out.println(t.get());
		}

	}

}
