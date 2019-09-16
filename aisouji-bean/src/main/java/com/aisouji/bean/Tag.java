package com.aisouji.bean;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    private Integer id;

    private Integer pid;

    private String name;

   private boolean open;//是否打开
    
    private boolean checked;//是否选中
    
    private int level;//显示等级
    
	private List<Tag> children = new ArrayList<Tag>();
    
    private String icon;
    
    
    public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<Tag> getChildren() {
		return children;
	}

	public void setChildren(List<Tag> children) {
		this.children = children;
	}
    
}