package com.changxx.practice.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author changxiangxiang
 * @date 2014年8月12日 下午9:22:59
 * @description
 * @since sprint2
 */
public class HBaseTest {
    
    // pop_quality_comment_src,
    // pop_quality_comment_target,
    // pop_quality_work_order_src,
    // pop_quality_complaint_src,
    // pop_quality_returns_src,
    // pop_quality_comment_mark
    private final static String  TABLENAME = "pop_quality_complaint_src";

    private final static String  FAMILY    = "f";

    private static final byte[] FAMILY_NAME_BYTES = Bytes.toBytes(FAMILY);

    private static Configuration conf      = null;

    static {
        conf = HBaseConfiguration.create();
    }

    public static void main(String[] args) throws IOException {
        // baseTest.createTable(cfs);
        // HBaseTest.writeRow();
        // HBaseTest.del();
        // HBaseTest.delRow();
       //HBaseTest.batchPut2();
        HBaseTest.scaner();
        // HBaseTest.selectRow("1123108399_1233025302_0da17733-13fc-42b6-82e8-29ff9e91e8a2");
        // HBaseTest.selectRow("1123108399_1233025302_0da17733-13fc-42b6-82e8-29ff9e91e8a2");
        //HBaseTest.selectRow("row1ssssss");

        // baseTest.createTable(cfs);

        // Job job = new Job(conf, "ExampleSummary");

        //HBaseTest.selectByFilter();

        System.out.println("--------------------");

        // HBaseTest.selectByValueFilter();

        //HBaseTest.selectByPageFilter();

        // HBaseTest.selectByFilterList();

        // Date date = new Date(1409090270057l);

        // System.out.println(new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));

    }

    public static void selectByFilterList() throws IOException {
        HTable htable = new HTable(conf, TABLENAME);

        SingleColumnValueFilter cvfilter = new SingleColumnValueFilter(Bytes.toBytes(FAMILY), Bytes.toBytes("cxx1"), CompareOp.EQUAL, Bytes.toBytes("value_cxx"));
        SingleColumnValueFilter cvfilter2 = new SingleColumnValueFilter(Bytes.toBytes(FAMILY), Bytes.toBytes("cxx"), CompareOp.EQUAL, Bytes.toBytes("value_cxx"));
        // 当参考列不存在的时候如何这一行不被包含在结果中
        cvfilter.setFilterIfMissing(true);
        cvfilter2.setFilterIfMissing(true);

        FilterList filterList = new FilterList();
        filterList.addFilter(cvfilter);
        filterList.addFilter(cvfilter2);

        Scan scan = new Scan();
        scan.setFilter(filterList);

        ResultScanner resultScanner = htable.getScanner(scan);

        HBaseTest.showResultScanner(resultScanner);

        htable.close();
    }

    public static void selectByFilter() throws IOException {
        HTable htable = new HTable(conf, TABLENAME);

        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes(FAMILY));

        Filter filter1 = new RowFilter(CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row1")));
        scan.setFilter(filter1);

        ResultScanner scanner1 = htable.getScanner(scan);

        for (Result res : scanner1) {
            System.out.println(res);
        }

        htable.close();
    }

    /**
     * 字符串匹配只能使用EQUAL和NOT_EQUAL运算符
     */
    public static void selectByValueFilter() throws IOException {
        HTable htable = new HTable(conf, TABLENAME);

        Filter filter = new ValueFilter(CompareOp.EQUAL, new SubstringComparator("value_4"));
        Scan scan = new Scan();
        scan.setFilter(filter);

        ResultScanner scanner = htable.getScanner(scan);

        for (Result res : scanner) {
            System.out.println(res);
        }

        htable.close();
    }

    public static void selectByPageFilter() throws IOException {
        HTable htable = new HTable(conf, TABLENAME);

        long pageSize = 100;
        Filter filter = new PageFilter(pageSize);
        int totalRows = 0;
        byte[] lastRow = null;
        Scan scan = new Scan();
        scan.setFilter(filter);
        while (true) {
            if (lastRow != null) {
                byte[] startRow = Bytes.add(lastRow, Bytes.toBytes(pageSize));
                System.out.println("start row:" + Bytes.toStringBinary(startRow));
                scan.setStartRow(startRow);
            }

            long start = System.currentTimeMillis();
            ResultScanner scanner = htable.getScanner(scan);
            int localRows = 0;
            Result result;
            while ((result = scanner.next()) != null) {
                totalRows++;
                localRows++;
                lastRow = result.getRow();
            }
            scanner.close();
            System.out.println(System.currentTimeMillis() - start);
            if (localRows == 0) {
                break;
            }
        }
        System.out.println(totalRows);
        htable.close();
    }

    public static void createTable(String[] cfs) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (admin.tableExists(TABLENAME)) {
            System.out.println("表已经存在！");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(TABLENAME);
            for (int i = 0; i < cfs.length; i++) {
                tableDesc.addFamily(new HColumnDescriptor(cfs[i]));
            }
            admin.createTable(tableDesc);
            System.out.println("表创建成功！");
        }
        admin.close();
    }

    public static void del() {
        try {
            HTable htable = new HTable(conf, TABLENAME);

            Delete delete = new Delete();
            htable.delete(delete);

            htable.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void delRow() {
        try {
            HTable htable = new HTable(conf, TABLENAME);

            Delete delete = new Delete(Bytes.toBytes("row2"));
            htable.delete(delete);

            Delete delete2 = new Delete(Bytes.toBytes("rowcxx"));
            htable.delete(delete2);

            htable.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void writeRow() {
        try {
            HTable htable = new HTable(conf, TABLENAME);
            Put put = new Put(Bytes.toBytes("cxxinsert9"));
            put.add(Bytes.toBytes(FAMILY), Bytes.toBytes(String.valueOf("cxx1")), Bytes.toBytes("value_cxx"));
            htable.put(put);
            htable.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void selectRow(String rowKey) {
        HTable htable = null;
        try {
            htable = new HTable(conf, TABLENAME);
            long start = System.currentTimeMillis();
            Get g = new Get(rowKey.getBytes());
            Result rs = htable.get(g);
            System.out.println(rs);
            for (KeyValue kv : rs.raw()) {
                HBaseTest.printKeyValue(kv);
            }
            System.out.println("ssssss" + (System.currentTimeMillis() - start));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (htable != null) {
                    htable.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void scaner() {
        try {
            HTable htable = new HTable(conf, TABLENAME);
            Scan s = new Scan();
            s.setCaching(10);
            
            ResultScanner rs = htable.getScanner(s);
            
            for (Result result : rs) {
                
               System.out.print(new String(result.getRow()) + " val{ ");

                for (KeyValue kv : result.raw()) {
                    System.out.print(" " + new String(kv.getQualifier()) + ": ");
                    System.out.print(" " + new String(kv.getValue()) + " ");
                }
                System.out.println();
            }

            htable.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void printKeyValue(KeyValue kv) {
        System.out.print(new String(kv.getRow()) + "          ");
        System.out.print("column=" + new String(kv.getFamily()) + ":");
        System.out.print(new String(kv.getQualifier()) + ",          ");
        System.out.print("timestamp=" + new String(kv.getTimestamp() + ",") + "          ");
        System.out.print("value=" + new String(kv.getValue()));
    }

    public static void showResultScanner(ResultScanner rs) {
        for (Result r : rs) {
            for (KeyValue kv : r.raw()) {
                System.out.print(new String(kv.getFamily()) + ":");
                System.out.print(new String(kv.getQualifier()) + " ");
                System.out.print(kv.getTimestamp() + " ");
                System.out.print(new String(kv.getValue()));
            }
        }
    }
    
    public static void batchPut() throws IOException{
        
        HTable htable = new HTable(conf, TABLENAME);
        
        List<Row> batch = new ArrayList<Row>();
        
        Put put = new Put(Bytes.toBytes("cxx-insert-test111"));
        
        Double bb = new Double(12);
        
        Long log = new Long(102);
        
        put.add(Bytes.toBytes(FAMILY), Bytes.toBytes("doubleVal"), Bytes.toBytes(bb));
        put.add(Bytes.toBytes(FAMILY), Bytes.toBytes("longval"), Bytes.toBytes(log));
        put.add(Bytes.toBytes(FAMILY), Bytes.toBytes("intval"), Bytes.toBytes(12));
        
        Put put2 = new Put(Bytes.toBytes("cxx-insert-test111"));
        
        batch.add(put);
        batch.add(put2);
        
        try {
            htable.batch(batch);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        htable.close();
    }
    
    public static void batchPut2() throws IOException {
        List<HbaseRow> rows = new ArrayList<HbaseRow>();
        
        HbaseRow hbaseRow1 = new HbaseRow();
        hbaseRow1.setRowKey("68_1006011891_1026611590");
        
        Map<String, Object> keyValueInsert = new HashMap<String, Object>();
        keyValueInsert.put("cat_2", 6743);
        keyValueInsert.put("cat_1", 6728);
        keyValueInsert.put("cmt_hit_cnt", 1);
        keyValueInsert.put("cat_3", 11875);
        keyValueInsert.put("cmt_cnt", 1);
        keyValueInsert.put("spu_id", 1006011891l);
        keyValueInsert.put("cmt_cnt_for_fake", 0.096);
        keyValueInsert.put("batch", "67");
        keyValueInsert.put("sku_id", 1026611590l);
        keyValueInsert.put("vendor_id", 29470l);
        hbaseRow1.setKeyValueInsert(keyValueInsert);
        
        rows.add(hbaseRow1);
        

        HTable htable = new HTable(conf, TABLENAME);

        List<Row> batch = new ArrayList<Row>();

        for (HbaseRow hbaseRow : rows) {

            Put put = new Put(Bytes.toBytes(hbaseRow.getRowKey()));

            // 有类型的插入
            if (hbaseRow.getKeyValueInsert() != null) {
                Iterator<Entry<String, Object>> it = hbaseRow.getKeyValueInsert().entrySet().iterator();

                while (it.hasNext()) {
                    Entry<String, Object> entry = it.next();

                    if (entry.getKey() == null || entry.getValue() == null) {
                        continue;
                    }
                    Object val = entry.getValue();

                    if (val instanceof Long) {
                        put.add(FAMILY_NAME_BYTES, Bytes.toBytes(entry.getKey()), Bytes.toBytes((Long) val));
                    } else if (val instanceof String) {
                        put.add(FAMILY_NAME_BYTES, Bytes.toBytes(entry.getKey()), Bytes.toBytes((String) val));
                    } else if (val instanceof Double) {
                        put.add(FAMILY_NAME_BYTES, Bytes.toBytes(entry.getKey()), Bytes.toBytes((Double) val));
                    } else if (val instanceof Integer) {
                        put.add(FAMILY_NAME_BYTES, Bytes.toBytes(entry.getKey()), Bytes.toBytes((Integer) val));
                    } else {
                        put.add(FAMILY_NAME_BYTES, Bytes.toBytes(entry.getKey()), Bytes.toBytes((String) val));
                    }

                }

                batch.add(put);
            }
        }
        
        try {
            htable.batch(batch);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        htable.close();
    }
}
