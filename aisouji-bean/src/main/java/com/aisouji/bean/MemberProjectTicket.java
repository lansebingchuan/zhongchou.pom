package com.aisouji.bean;

public class MemberProjectTicket {
    private Integer id;

    private Integer memberid;
    
    private Integer projectid;

    private String proinstid;

    private String autocode;

    private String status;

    private String currenturl;

    
    
    
    public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getProinstid() {
        return proinstid;
    }

    public void setProinstid(String proinstid) {
        this.proinstid = proinstid == null ? null : proinstid.trim();
    }

    public String getAutocode() {
        return autocode;
    }

    public void setAutocode(String autocode) {
        this.autocode = autocode == null ? null : autocode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCurrenturl() {
        return currenturl;
    }

    public void setCurrenturl(String currenturl) {
        this.currenturl = currenturl == null ? null : currenturl.trim();
    }
}