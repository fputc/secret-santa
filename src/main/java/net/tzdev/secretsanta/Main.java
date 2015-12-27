package net.tzdev.secretsanta;


import org.apache.log4j.Logger;

/**
 * Created by ahelac on 27-Dec-15.
 */
public class Main {

  private final static Logger logger = Logger.getLogger(String.valueOf(Main.class));

  public static void main(String[] args) {
    System.out.println("vata faka");

    if(logger.isDebugEnabled()){
      logger.debug("This is debug");
    }

    if(logger.isInfoEnabled()){
      logger.info("This is info");
    }

    logger.warn("This is warn");
    logger.error("This is error");
    logger.fatal("This is fatal");

  }
}
