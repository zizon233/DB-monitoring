package org.example;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payment_history")
public class PaymentHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "history_id")
  private Long historyId;

  @Column(name = "buisness_no")
  private String buisnessNo;

  @Column(name = "price")
  private Long price;

  @Column(name = "car_no")
  private String cardNo;

  @Column(name = "payment_dt", columnDefinition = "DATETIME")
  private LocalDateTime paymentDt;

  @Column(name = "cancel_dt", columnDefinition = "DATETIME")
  private LocalDateTime cancelDt;

  public Long getHistoryId() {
    return historyId;
  }

  public void setHistoryId(Long historyId) {
    this.historyId = historyId;
  }

  public String getBuisnessNo() {
    return buisnessNo;
  }

  public void setBuisnessNo(String buisnessNo) {
    this.buisnessNo = buisnessNo;
  }

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }

  public LocalDateTime getPaymentDt() {
    return paymentDt;
  }

  public void setPaymentDt(LocalDateTime paymentDt) {
    this.paymentDt = paymentDt;
  }

  public LocalDateTime getCancelDt() {
    return cancelDt;
  }

  public void setCancelDt(LocalDateTime cancelDt) {
    this.cancelDt = cancelDt;
  }
}
