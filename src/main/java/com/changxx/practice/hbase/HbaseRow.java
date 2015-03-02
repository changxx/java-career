package com.changxx.practice.hbase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author changxiangxiang
 * @date 2014年8月15日 上午9:59:17
 * @description hbase行对象
 * @since sprint2
 */
public class HbaseRow implements Serializable {

    private static final long   serialVersionUID = -376159837460101822L;

    private String              rowKey;                                 // 需指定

    private Map<String, String> keyValue;                               // 不能是null，取出来，没类型

    private Map<String, Object> keyValueInsert;                         // 不能是null，插入的，有类型

    public HbaseRow() {
        super();
    }

    public HbaseRow(String rowKey, Map<String, String> keyValue) {
        super();
        this.rowKey = rowKey;
        this.keyValue = keyValue;
    }

    public HbaseRow(Result r) {
        if (r == null || r.getRow() == null) {
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        for (KeyValue kv : r.raw()) {
            map.put(Bytes.toString(kv.getQualifier()), Bytes.toString(kv.getValue()));
        }
        this.rowKey = Bytes.toString(r.getRow());
        this.keyValue = map;
    }

    public static List<HbaseRow> resultsToHbaseRow(ResultScanner results) {
        List<HbaseRow> hbaseRows = new ArrayList<HbaseRow>();

        if (results == null) {
            return hbaseRows;
        }

        for (Result r : results) {

            Map<String, String> map = new HashMap<String, String>();
            HbaseRow hbaseRow = new HbaseRow(Bytes.toString(r.getRow()), map);

            for (KeyValue kv : r.raw()) {
                map.put(Bytes.toString(kv.getQualifier()), Bytes.toString(kv.getValue()));
            }

            hbaseRows.add(hbaseRow);
        }
        return hbaseRows;
    }

    @Override
    public String toString() {
        return "HbaseRow [rowKey=" + rowKey + ", keyValue=" + keyValue + ", keyValueInsert=" + keyValueInsert + "]";
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public Map<String, String> getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(Map<String, String> keyValue) {
        this.keyValue = keyValue;
    }

    public Map<String, Object> getKeyValueInsert() {
        return keyValueInsert;
    }

    public void setKeyValueInsert(Map<String, Object> keyValueInsert) {
        this.keyValueInsert = keyValueInsert;
    }

}
