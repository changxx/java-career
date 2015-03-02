package com.changxx.practice.test.db;

import java.util.Date;

/**
 * 汇总用户天各类工时
 * @author changxiangxiang
 */
public class domain {

    /** 非重点项目工时（非重点+重点=项目工时） */
    protected Double  unImportantProjectHours;
    /** (Primary Key) */
    protected Integer id;
    /** 添加日期 */
    protected Date    createrDate;
    /** 统计日的部门管理工时(日常表中类型为部门管理的工时) */
    protected Double  orgManageHours;
    /** 未填工时 */
    protected Double  ufilledHours;
    /** 日常项目工时 */
    protected Double  dailyProjectHours;
    /** 所属部门链 */
    protected String  orgTierCode;
    /** ERP帐号 */
    protected String  erpCode;
    /** 重点项目工时(非重点+重点=项目工时） */
    protected Double  importantProjectHours;
    /** 是否正常工作日 */
    protected Integer workDay;
    /** 企业项目工时 */
    protected Double  enterpriseProjectHours;
    /** 部门项目工时 */
    protected Double  orgProjectHours;
    /** 总的项目工时 */
    protected Double  projectHours;
    /** 统计日投入总工时 */
    protected Double  totalHours;
    /** 人员所属部门ID */
    protected String  orgId;
    /** 统计的日期 */
    protected Date    statsDay;

    public domain() {}

    public domain(Date createrDate, String orgTierCode, String erpCode, String orgId, Date statsDay) {
        super();
        this.createrDate = createrDate;
        this.orgTierCode = orgTierCode;
        this.erpCode = erpCode;
        this.orgId = orgId;
        this.statsDay = statsDay;
    }

    public Double getUnImportantProjectHours() {
        return unImportantProjectHours;
    }

    public void setUnImportantProjectHours(Double unImportantProjectHours) {
        this.unImportantProjectHours = unImportantProjectHours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreaterDate() {
        return createrDate;
    }

    public void setCreaterDate(Date createrDate) {
        this.createrDate = createrDate;
    }

    public Double getOrgManageHours() {
        return orgManageHours;
    }

    public void setOrgManageHours(Double orgManageHours) {
        this.orgManageHours = orgManageHours;
    }

    public Double getUfilledHours() {
        return ufilledHours;
    }

    public void setUfilledHours(Double ufilledHours) {
        this.ufilledHours = ufilledHours;
    }

    public Double getDailyProjectHours() {
        return dailyProjectHours;
    }

    public void setDailyProjectHours(Double dailyProjectHours) {
        this.dailyProjectHours = dailyProjectHours;
    }

    public String getOrgTierCode() {
        return orgTierCode;
    }

    public void setOrgTierCode(String orgTierCode) {
        this.orgTierCode = orgTierCode;
    }

    public String getErpCode() {
        return erpCode;
    }

    public void setErpCode(String erpCode) {
        this.erpCode = erpCode;
    }

    public Double getImportantProjectHours() {
        return importantProjectHours;
    }

    public void setImportantProjectHours(Double importantProjectHours) {
        this.importantProjectHours = importantProjectHours;
    }

    public Integer getWorkDay() {
        return workDay;
    }

    public void setWorkDay(Integer workDay) {
        this.workDay = workDay;
    }

    public Double getEnterpriseProjectHours() {
        return enterpriseProjectHours;
    }

    public void setEnterpriseProjectHours(Double enterpriseProjectHours) {
        this.enterpriseProjectHours = enterpriseProjectHours;
    }

    public Double getOrgProjectHours() {
        return orgProjectHours;
    }

    public void setOrgProjectHours(Double orgProjectHours) {
        this.orgProjectHours = orgProjectHours;
    }

    public Double getProjectHours() {
        return projectHours;
    }

    public void setProjectHours(Double projectHours) {
        this.projectHours = projectHours;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Date getStatsDay() {
        return statsDay;
    }

    public void setStatsDay(Date statsDay) {
        this.statsDay = statsDay;
    }

}