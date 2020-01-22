package com.aling.core.bean;

import java.io.Serializable;

public class Pager implements Serializable {
	private static final long serialVersionUID = 1L;

	private int pageNo = 0; //页码
	private int rows = 20; //页大小
	private int start; //开始行号
	private long total; //总需的行数
	private String order; //排序方向
	private String orderBy; //排序字段

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
