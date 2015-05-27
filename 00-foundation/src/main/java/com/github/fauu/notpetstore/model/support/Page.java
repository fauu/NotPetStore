package com.github.fauu.notpetstore.model.support;

import java.util.List;

public class Page<T> {

  private int no;

  private List<T> items;

  private int maxSize;

  private int numItemsTotal;

  public Page(int no, List<T> items, int maxSize, int numItemsTotal) {
    this.no = no;
    this.items = items;
    this.maxSize = maxSize;
    this.numItemsTotal = numItemsTotal;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

  public int getMaxSize() {
    return maxSize;
  }

  public void setMaxSize(int maxSize) {
    this.maxSize = maxSize;
  }

  public int getNumItemsTotal() {
    return numItemsTotal;
  }

  public void setNumItemsTotal(int numItemsTotal) {
    this.numItemsTotal = numItemsTotal;
  }

  public int getNumPagesTotal() {
    return numItemsTotal == 0 ? 1 : (int) Math.ceil((double) numItemsTotal /
                                                    (double) maxSize);
  }

}
