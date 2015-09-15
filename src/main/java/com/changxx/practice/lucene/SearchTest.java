package com.changxx.practice.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;

/**
 * @author changxx on 15-8-14.
 */
public class SearchTest {

    public static void main(String[] args) throws Exception {
        String indexDir = "/home/changxx/develop/lucene/index";
        SearchTest.search(indexDir, "contents:信息 流程");
    }

    public static void search(String indexDir, String q) throws Exception {
        Directory dir = FSDirectory.open(new File(indexDir));
        IndexSearcher is = new IndexSearcher(IndexReader.open(dir));

        QueryParser parser = new QueryParser(Version.LUCENE_44, "contents", new StandardAnalyzer(Version.LUCENE_44));
        Query query = parser.parse(q);

        long start = System.currentTimeMillis();
        TopDocs hits = is.search(query, 10);
        long end = System.currentTimeMillis();

        System.out.println("Found " + hits.totalHits + " hits.");
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            System.out.println(scoreDoc.score);
            Document doc = is.doc(scoreDoc.doc);
            System.out.println(doc.get("fullpath"));

            Explanation explanation = is.explain(query, scoreDoc.doc);
            System.out.println(explanation);
        }

    }
}
