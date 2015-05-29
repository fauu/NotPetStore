package com.github.fauu.notpetstore.model.support;

public class PageRequest {

  private int pageNo;

  private int pageSize;

  public PageRequest(int pageNo, int pageSize) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
  }

  public int getPageNo() {
    return pageNo;
  }

  public int getPageSize() {
    return pageSize;
  }

}
