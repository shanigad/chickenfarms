package com.chickenfarms.escalationmanagement.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketFilterRequest {

  private String status;
  private String tag;
  private String provider;
  private Long problem;
  private String rootCause;

  public TicketFilterRequest(String status, String tag, String provider, String problem,
                             String rootCause) {
    this.status = status;
    this.tag = tag;
    this.provider = provider;
    this.problem = getLongOrNull(problem);
    this.rootCause = rootCause;
  }

  private Long getLongOrNull(String value){
    Long longVal;
    try {
      longVal = Long.valueOf(value);
    }
    catch (Exception e){
      longVal = null;
    }
    return longVal;
  }

}


