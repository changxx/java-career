package com.changxx.practice.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author changxx on 15-8-13.
 */
public class IndexerTest {

    private String indexDir;

    private IndexWriter writer;

    public IndexerTest(String indexDir) throws IOException {
        this.indexDir = indexDir;

        Directory dir = FSDirectory.open(new File(indexDir));

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_44, analyzer);

        writer = new IndexWriter(dir, config);
    }

    public int index(String dataDir, FileFilter filter) throws Exception {
        File[] files = new File(dataDir).listFiles();

        for (File f : files) {
            if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead() && (filter == null || filter.accept(f))) {
                indexFile(f);
            }
        }

        return writer.numDocs();
    }

    private void indexFile(File f) throws Exception {
        System.out.println("Indexing " + f.getCanonicalPath());
        Document doc = getDocument(f);
        writer.addDocument(doc);
    }

    private Document getDocument(File f) throws Exception {
        Document doc = new Document();
        doc.add(new TextField("contents", new FileReader(f)));
        doc.add(new StringField("filename", f.getName(), Field.Store.YES));
        doc.add(new StringField("fullpath", f.getCanonicalPath(), Field.Store.YES));
        return doc;
    }

    private static class TextFilesFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            return pathname.getName().toLowerCase().endsWith(".txt");
        }
    }

    public void close() throws IOException {
        writer.close();
    }

    public static void main(String[] args) throws Exception {
        String indexDir = "/home/changxx/develop/lucene/index";
        String dataDir = "/home/changxx/develop/lucene/data";

        long start = System.currentTimeMillis();

        IndexerTest indexerTest = new IndexerTest(indexDir);
        int numIndexed = indexerTest.index(dataDir, new TextFilesFilter());
        indexerTest.close();

        long end = System.currentTimeMillis();
        System.out.println("Indexing " + numIndexed + " fileds took " + (end - start) + " milliseconds");
    }

}
