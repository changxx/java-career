package com.changxx.practice.reflect;

/**
 * @author changxiangxiang
 * @date 2014年9月1日 下午6:05:59
 * @description 分析目标
 * @since sprint2
 */
public class QualityTarget {

    private Long    sku_id;

    private Long    spu_id;

    private String  batch;

    private Integer cat_1;

    private String  cat_1_name;

    private Integer cat_2;

    private String  cat_2_name;

    private Integer cat_3;

    private String  cat_3_Name;

    // 评价
    private Double  cmt_cnt_for_fake;

    private Integer cmt_hit_cnt;

    private Integer cmt_cnt;

    // 客户投诉指标
    private Double  complaint_cnt_for_fake;

    private Integer complaint_hit_cnt;

    private Integer complaint_cnt;

    // 工单投诉指标
    private Double  ord_cnt_for_fake_from_cs;

    private Integer ord_hit_cnt_from_cs;

    private Integer ord_cnt_from_cs;

    // 退货指标
    private Double  return_app_cnt_for_fake;

    private Integer return_app_hit_cnt;

    private Integer return_app_cnt;

    // 换货指标
    private Double  exchange_app_cnt_for_fake;

    private Integer exchange_app_hit_cnt;

    private Integer exchange_app_cnt;

    @Override
    public String toString() {
        return "QualityTarget [sku_id=" + sku_id + ", spu_id=" + spu_id + ", batch=" + batch + ", cat_1=" + cat_1 + ", cat_1_name=" + cat_1_name + ", cat_2=" + cat_2
                + ", cat_2_name=" + cat_2_name + ", cat_3=" + cat_3 + ", cat_3_Name=" + cat_3_Name + ", cmt_cnt_for_fake=" + cmt_cnt_for_fake + ", cmt_hit_cnt=" + cmt_hit_cnt
                + ", cmt_cnt=" + cmt_cnt + ", complaint_cnt_for_fake=" + complaint_cnt_for_fake + ", complaint_hit_cnt=" + complaint_hit_cnt + ", complaint_cnt=" + complaint_cnt
                + ", ord_cnt_for_fake_from_cs=" + ord_cnt_for_fake_from_cs + ", ord_hit_cnt_from_cs=" + ord_hit_cnt_from_cs + ", ord_cnt_from_cs=" + ord_cnt_from_cs
                + ", return_app_cnt_for_fake=" + return_app_cnt_for_fake + ", return_app_hit_cnt=" + return_app_hit_cnt + ", return_app_cnt=" + return_app_cnt
                + ", exchange_app_cnt_for_fake=" + exchange_app_cnt_for_fake + ", exchange_app_hit_cnt=" + exchange_app_hit_cnt + ", exchange_app_cnt=" + exchange_app_cnt + "]";
    }

    public Long getSku_id() {
        return sku_id;
    }

    public void setSku_id(Long sku_id) {
        this.sku_id = sku_id;
    }

    public Long getSpu_id() {
        return spu_id;
    }

    public void setSpu_id(Long spu_id) {
        this.spu_id = spu_id;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getCat_1() {
        return cat_1;
    }

    public void setCat_1(Integer cat_1) {
        this.cat_1 = cat_1;
    }

    public String getCat_1_name() {
        return cat_1_name;
    }

    public void setCat_1_name(String cat_1_name) {
        this.cat_1_name = cat_1_name;
    }

    public Integer getCat_2() {
        return cat_2;
    }

    public void setCat_2(Integer cat_2) {
        this.cat_2 = cat_2;
    }

    public String getCat_2_name() {
        return cat_2_name;
    }

    public void setCat_2_name(String cat_2_name) {
        this.cat_2_name = cat_2_name;
    }

    public Integer getCat_3() {
        return cat_3;
    }

    public void setCat_3(Integer cat_3) {
        this.cat_3 = cat_3;
    }

    public String getCat_3_Name() {
        return cat_3_Name;
    }

    public void setCat_3_Name(String cat_3_Name) {
        this.cat_3_Name = cat_3_Name;
    }

    public Double getCmt_cnt_for_fake() {
        return cmt_cnt_for_fake;
    }

    public void setCmt_cnt_for_fake(Double cmt_cnt_for_fake) {
        this.cmt_cnt_for_fake = cmt_cnt_for_fake;
    }

    public Integer getCmt_hit_cnt() {
        return cmt_hit_cnt;
    }

    public void setCmt_hit_cnt(Integer cmt_hit_cnt) {
        this.cmt_hit_cnt = cmt_hit_cnt;
    }

    public Integer getCmt_cnt() {
        return cmt_cnt;
    }

    public void setCmt_cnt(Integer cmt_cnt) {
        this.cmt_cnt = cmt_cnt;
    }

    public Double getComplaint_cnt_for_fake() {
        return complaint_cnt_for_fake;
    }

    public void setComplaint_cnt_for_fake(Double complaint_cnt_for_fake) {
        this.complaint_cnt_for_fake = complaint_cnt_for_fake;
    }

    public Integer getComplaint_hit_cnt() {
        return complaint_hit_cnt;
    }

    public void setComplaint_hit_cnt(Integer complaint_hit_cnt) {
        this.complaint_hit_cnt = complaint_hit_cnt;
    }

    public Integer getComplaint_cnt() {
        return complaint_cnt;
    }

    public void setComplaint_cnt(Integer complaint_cnt) {
        this.complaint_cnt = complaint_cnt;
    }

    public Double getOrd_cnt_for_fake_from_cs() {
        return ord_cnt_for_fake_from_cs;
    }

    public void setOrd_cnt_for_fake_from_cs(Double ord_cnt_for_fake_from_cs) {
        this.ord_cnt_for_fake_from_cs = ord_cnt_for_fake_from_cs;
    }

    public Integer getOrd_hit_cnt_from_cs() {
        return ord_hit_cnt_from_cs;
    }

    public void setOrd_hit_cnt_from_cs(Integer ord_hit_cnt_from_cs) {
        this.ord_hit_cnt_from_cs = ord_hit_cnt_from_cs;
    }

    public Integer getOrd_cnt_from_cs() {
        return ord_cnt_from_cs;
    }

    public void setOrd_cnt_from_cs(Integer ord_cnt_from_cs) {
        this.ord_cnt_from_cs = ord_cnt_from_cs;
    }

    public Double getReturn_app_cnt_for_fake() {
        return return_app_cnt_for_fake;
    }

    public void setReturn_app_cnt_for_fake(Double return_app_cnt_for_fake) {
        this.return_app_cnt_for_fake = return_app_cnt_for_fake;
    }

    public Integer getReturn_app_hit_cnt() {
        return return_app_hit_cnt;
    }

    public void setReturn_app_hit_cnt(Integer return_app_hit_cnt) {
        this.return_app_hit_cnt = return_app_hit_cnt;
    }

    public Integer getReturn_app_cnt() {
        return return_app_cnt;
    }

    public void setReturn_app_cnt(Integer return_app_cnt) {
        this.return_app_cnt = return_app_cnt;
    }

    public Double getExchange_app_cnt_for_fake() {
        return exchange_app_cnt_for_fake;
    }

    public void setExchange_app_cnt_for_fake(Double exchange_app_cnt_for_fake) {
        this.exchange_app_cnt_for_fake = exchange_app_cnt_for_fake;
    }

    public Integer getExchange_app_hit_cnt() {
        return exchange_app_hit_cnt;
    }

    public void setExchange_app_hit_cnt(Integer exchange_app_hit_cnt) {
        this.exchange_app_hit_cnt = exchange_app_hit_cnt;
    }

    public Integer getExchange_app_cnt() {
        return exchange_app_cnt;
    }

    public void setExchange_app_cnt(Integer exchange_app_cnt) {
        this.exchange_app_cnt = exchange_app_cnt;
    }

}
