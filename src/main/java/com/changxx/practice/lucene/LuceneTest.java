package com.changxx.practice.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * @author changxx on 15-8-13.
 */
public class LuceneTest {

    public static void main(String[] args) throws IOException, ParseException {
        // 建立索引
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
        File file = new File("~/lucene");
        Directory index = new SimpleFSDirectory(file);

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_44, analyzer);

        IndexWriter w = new IndexWriter(index, config);
        // 把文档添加到索引中
        addDoc(w, "Lucene in Action", "193398817");
        addDoc(w, "Lucene for Dummies", "55320055Z");
        addDoc(w, "Managing Gigabytes", "55063554A");
        addDoc(w, "The Art of Computer Science", "9900333X");
        addDoc(w, "并且使用上面创建的Query对象来进行搜索", "9900333X");
        w.close();

        // 搜索请求
        String querystr = "Lucene";
        Query q = new QueryParser(Version.LUCENE_44, "title", analyzer).parse(querystr);

        // 搜索
        int hitsPerPage = 10;
        IndexReader reader = IndexReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }

    }

    /**
     * 注意，对于需要分词的内容使用TextField，对于像id这样不需要分词的内容使用StringField。
     *
     * @param w
     * @param title
     * @param isbn
     * @throws IOException
     */
    private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        w.addDocument(doc);
    }

}
