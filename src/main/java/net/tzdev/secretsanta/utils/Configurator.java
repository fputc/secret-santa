package net.tzdev.secretsanta.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * Poor man's configuration loader. Works for properties files only. Will load file on every Configurator#getProperties
 * call, no caching in place.
 *
 * @author ahelac
 */
public class Configurator {

  private final static Logger logger = Logger.getLogger(Configurator.class);

  /**
   * Returns Properties instance for given propertiesFileName. Filename can be anywhere in classpath.
   *
   * @param propertiesFileName .properties file filename
   * @return Properties instance
   */
  public static Properties getProperties(String propertiesFileName) {
    Properties properties = null;

    try {
      InputStream input = null;
      input = Configurator.class.getClassLoader().getResourceAsStream(propertiesFileName);
      if(input==null){
        throw new FileNotFoundException();
      }

      properties = new Properties();
      properties.load(input);

      if (logger.isDebugEnabled()) {
        logger.debug("Loaded " + propertiesFileName +  " file.");
      }

      input.close();

    } catch (FileNotFoundException e) {
      logger.fatal(propertiesFileName + " file not found.", e);
    } catch (IOException e) {
      logger.fatal(e);
    }
    return properties;
  }
}