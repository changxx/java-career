package com.changxx.practice.http;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 */
public class JoneSystem implements Serializable {

    private static final long serialVersionUID = -8390144375499286088L;
    private Integer           id;
    private String            name;
    private Integer           parentId;
    private Integer           productionId;
    private Integer           level;
    private String            levelName;
    private String            leader;
    private String            leaderName;
    private String            firstBranch;
    private String            secondBranch;
    private Integer           privilege;
    private String            remark;
    private Date              syncTime;
    private List<JoneSystem>  childs;

    public JoneSystem() {
        // 默认无参构造方法
        super();
    }

    public JoneSystem(Integer id, Integer parentId, Integer productionId) {
        this(id, null, parentId, productionId, null, null, null, null, null, null, null, null, null);
    }

    public JoneSystem(Integer id, String name, Integer level, String levelName, String leader,
            String leaderName, String firstBranch, String secondBranch, Integer privilege, String remark) {
        this(id, name, null, null, level, levelName, leader, leaderName, firstBranch, secondBranch,
                privilege, remark, null);
    }

    public JoneSystem(Integer id, String name, Integer parentId, Integer productionId, Integer level,
            String levelName, String leader, String leaderName, String firstBranch, String secondBranch,
            Integer privilege, String remark, Date syncTime) {
        super();
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.productionId = productionId;
        this.level = level;
        this.levelName = levelName;
        this.leader = leader;
        this.leaderName = leaderName;
        this.firstBranch = firstBranch;
        this.secondBranch = secondBranch;
        this.privilege = privilege;
        this.remark = remark;
        this.syncTime = syncTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getProductionId() {
        return productionId;
    }

    public void setProductionId(Integer productionId) {
        this.productionId = productionId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getFirstBranch() {
        return firstBranch;
    }

    public void setFirstBranch(String firstBranch) {
        this.firstBranch = firstBranch;
    }

    public String getSecondBranch() {
        return secondBranch;
    }

    public void setSecondBranch(String secondBranch) {
        this.secondBranch = secondBranch;
    }

    public Integer getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public List<JoneSystem> getChilds() {
        return childs;
    }

    public void setChilds(List<JoneSystem> childs) {
        this.childs = childs;
    }

}