package org.example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class DataGeneratorMain {

  public static void main(String[] args) throws InterruptedException {
    new HibernateUtil();

    while (true) {
      PaymentHistory paymentHistory = new PaymentHistory();

      String buisnessNo = String.valueOf(getRandomNum(2));
      Long price = (long) getRandomNum(3) * 100;
      String cardNo =
          String.format("%04d", getRandomNum(4)) + "-" + String.format("%04d", getRandomNum(4))
              + "-"
              + String.format("%04d", getRandomNum(4)) + "-" + String.format("%04d",
              getRandomNum(4));
      int dataInterval = getRandomNum(1);
      int cancelPercentage = 10;
      LocalDateTime currentDateTime = LocalDateTime.now();

      paymentHistory.setBuisnessNo(buisnessNo);
      paymentHistory.setPrice(price);
      paymentHistory.setCardNo(cardNo);
      paymentHistory.setPaymentDt(currentDateTime);

      HibernateUtil.session.beginTransaction();
      HibernateUtil.session.save(paymentHistory);
      HibernateUtil.session.getTransaction().commit();

      System.out.println("Payment Data Generation : " + buisnessNo + " " + price + " " + cardNo);
      Thread.sleep(dataInterval);

      if (getRandomNum(2) <= cancelPercentage) {
        Random random = new Random();
        List<Long> historyIdList = HibernateUtil.session.createQuery(
            "SELECT historyId FROM PaymentHistory WHERE cancelDt is null").list();
        Long cancelHistoryId = historyIdList.get(random.nextInt(historyIdList.size()));
        PaymentHistory cancelPaymentHistory = HibernateUtil.session.get(PaymentHistory.class,
            cancelHistoryId);

        LocalDateTime cancelDateTime = LocalDateTime.now();
        cancelPaymentHistory.setCancelDt(cancelDateTime);

        HibernateUtil.session.beginTransaction();
        HibernateUtil.session.save(paymentHistory);
        HibernateUtil.session.getTransaction().commit();
        System.out.println("Cancel Payment History Id : " + cancelHistoryId);
      }
    }
  }

  public static int getRandomNum(int digitNum) {
    int digit = (int) Math.pow(10, digitNum);
    int randomNum = (int) (Math.random() * digit) + 1;
    return randomNum;
  }
}
