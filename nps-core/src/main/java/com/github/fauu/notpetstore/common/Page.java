package com.github.fauu.notpetstore.common;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

public @Data class Page<T> {

  private @NonNull int no;

  private @NonNull List<T> items;

  private @NonNull int maxSize;

  private @NonNull int numItemsTotal;

  public int getNumPagesTotal() {
    return numItemsTotal == 0 ? 1 : (int) Math.ceil((double) numItemsTotal /
                                                    (double) maxSize);
  }

  public boolean isMoreAvailable() {
    return getNumPagesTotal() > 1;
  }

  public boolean isPreviousAvailable() {
    return no > 1;
  }

  public boolean isNextAvailable() {
    return no < getNumPagesTotal();
  }

  public int getFirstNo() {
    return 1;
  }

  public int getPreviousNo() {
    return isPreviousAvailable() ? no - 1 : -1;
  }

  public int getNextNo() {
    return isNextAvailable() ? no + 1 : -1;
  }

  public int getLastNo() {
    return getNumPagesTotal();
  }

}
