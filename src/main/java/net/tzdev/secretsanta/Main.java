package net.tzdev.secretsanta;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import net.tzdev.secretsanta.config.Configurator;


/**
 * Created by ahelac on 27-Dec-15.
 */
public class Main {

  private final static Logger logger = Logger.getLogger(String.valueOf(Main.class));

  public static void main(String[] args) {
    SecretSantaMatcher matcher = new SecretSantaMatcher(getParticipantsFromConfig());
    informParticipants(matcher.matchParticipants());
  }

  private static void informParticipants(Map<Participant, Participant> fromToResult) {
    for (Map.Entry<Participant, Participant> fromToMatch : fromToResult.entrySet()) {
      logger.debug(fromToMatch);
    }
  }

  public static Set<Participant> getParticipantsFromConfig() {
    Properties properties = Configurator.getProperties("participants.properties");
    HashSet<Participant> participants = new HashSet<Participant>();
    for (Map.Entry<Object, Object> configEntry : properties.entrySet()) {
      participants.add(new Participant(configEntry.getKey().toString(), configEntry.getValue().toString()));
    }
    return participants;
  }

}
