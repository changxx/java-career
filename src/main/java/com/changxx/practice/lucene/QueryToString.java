package com.changxx.practice.lucene;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.TermQuery;

/**
 * @author changxx on 15-8-16.
 */
public class QueryToString {

    public static void main(String[] args) throws Exception {
        BooleanQuery query = new BooleanQuery();
        query.add(new FuzzyQuery(new Term("field", "kountry")), BooleanClause.Occur.MUST);
        query.add(new TermQuery(new Term("title", "western")), BooleanClause.Occur.SHOULD);
        System.out.println(query.toString());
        System.out.println(query.toString("field"));
    }

}
