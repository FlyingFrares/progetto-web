package services.config;

import java.util.Calendar;
import java.util.logging.Level;

import model.dao.DAOFactory;

public class Configuration {
  
  /* Database Configruation */
  public static final String DAO_IMPL=DAOFactory.MYSQLJDBCIMPL;
  public static final String DATABASE_DRIVER="com.mysql.cj.jdbc.Driver";
  public static final String SERVER_TIMEZONE=Calendar.getInstance().getTimeZone().getID();
  public static final String 
    DATABASE_URL="jdbc:mysql://localhost/CP?user=CP&password=&useSSL=false&serverTimezone="+SERVER_TIMEZONE;
  
  /* Session Configuration */
  public static final String COOKIE_IMPL=DAOFactory.COOKIEIMPL;
  
  /* Logger Configuration */
  public static final String GLOBAL_LOGGER_NAME="CarniPadane";
  public static final String GLOBAL_LOGGER_FILE="D:\\Documenti\\UniFE\\III Anno\\Progetto\\Zambrini\\log\\log.%g.%u.txt";
  public static final Level GLOBAL_LOGGER_LEVEL=Level.ALL;

}

