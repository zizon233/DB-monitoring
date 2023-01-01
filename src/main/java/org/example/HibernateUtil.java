package org.example;

import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

  public static SessionFactory sessionFactory;
  public static Properties properties = new Properties();
  public static Session session;

  public HibernateUtil() {
    setProperties("hibernate.properties", "paymentHistory.hbm.xml");
  }

  public static void setProperties(String propertiesFileName, String hbmFileName) {
    try {
      properties.load(
          PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesFileName));
      Configuration configuration = new Configuration()
          .setProperties(properties)
          .addResource(hbmFileName);
      sessionFactory = configuration.buildSessionFactory();
      session = sessionFactory.openSession();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
