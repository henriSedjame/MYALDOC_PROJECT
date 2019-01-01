package org.myaldoc.core.messaging;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mail {

  private String subject;
  private String sentToEmail;
  private String sentToName;
  private LocalDate sentDate;
  private String userActivationUri;
  private boolean sentSuccessfully;
}
