package com.github.fauu.notpetstore.common;

import lombok.Data;
import lombok.NonNull;

public @Data class PageRequest {

  private @NonNull int pageNo;

  private @NonNull int pageSize;

}
