package com.aisouji.bean;

public class Ticket {
    private Integer id;

    private Integer memberid;

    private String pinstid;

    private String status;

    private String autocode;

    private String pstep;

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

    public String getPinstid() {
        return pinstid;
    }

    public void setPinstid(String pinstid) {
        this.pinstid = pinstid == null ? null : pinstid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAutocode() {
        return autocode;
    }

    public void setAutocode(String autocode) {
        this.autocode = autocode == null ? null : autocode.trim();
    }

    public String getPstep() {
        return pstep;
    }

    public void setPstep(String pstep) {
        this.pstep = pstep == null ? null : pstep.trim();
    }
    
    /** 
     *@param memberid   member id
     *@param status 0 个人 1 企业
     *@param pinstid  流程实例id 
     *@param autocode  验证码
     *@param pstep  当前的访问路径
     */
	public Ticket(Integer memberid, String status, String pinstid , String autocode, String pstep) {
		super();
		this.memberid = memberid;
		this.pinstid = pinstid;
		this.status = status;
		this.autocode = autocode;
		this.pstep = pstep;
	}

	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}