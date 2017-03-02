package com.hn658.${projectName}.common.entity;

import java.util.LinkedHashSet;

import com.hn658.framework.shared.entity.BaseEntity;


/**
 * 
 * 树节点的实体对象
 * @author ztjie
 * @date 2013-4-8 下午2:11:53
 * @since
 * @version
 */
@SuppressWarnings("rawtypes")
public class TreeNode<T extends BaseEntity,K extends TreeNode> {

	/**
	 * 树节点ID
	 */
	private Long id;
	
	/**
	 * 树节点文本显示
	 */
	private String text;
	
	/**
	 * 是否叶子节点
	 */
	private Boolean leaf;
	
	/**
	 * 是否展开
	 */
	private Boolean expanded;
	
	/**
	 * 父节点ID
	 */
	private Long parentId;
	
	/**
	 * 显示是否选择
	 */
	private Boolean checked;

	/**
	 * 节点对象数据
	 */
	private T entity;
	
	/**
	 * 菜单位置
	 */
	private Integer index;
	
	/**
	 * 菜单链接
	 */
	private String uri;
	
	/**
	 * 孩子节点
	 */
	private LinkedHashSet<K> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param  text  
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return leaf
	 */
	public Boolean getLeaf() {
		return leaf;
	}

	/**
	 * @param  leaf  
	 */
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
	 * @return checked
	 */
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * @param  checked  
	 */
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * @param  entity  
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

	/**
	 * @return children
	 */
	public LinkedHashSet<K> getChildren() {
		return children;
	}

	/**
	 * @param  children  
	 */
	public void setChildren(LinkedHashSet<K> children) {
		this.children = children;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
}