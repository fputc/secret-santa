package net.tzdev.secretsanta;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import net.tzdev.secretsanta.utils.Configurator;
import net.tzdev.secretsanta.utils.Mail;
import net.tzdev.secretsanta.utils.Mailer;


/**
 * Created by ahelac on 27-Dec-15.
 */
public class Main {

  private final static Logger logger = Logger.getLogger(String.valueOf(Main.class));

  public static void main(String[] args) {
    logger.info("DCCS Secret Santa Bot started.");
    Set<Participant> participants = getParticipantsFromConfig();
    logger.info("Loaded " + participants.size() +" participants.");
    SecretSantaMatcher matcher = new SecretSantaMatcher(participants);
    informParticipants(matcher.matchParticipants());
    logger.info("DCCS Secret Santa Bot completed successfully.");
  }

  private static void informParticipants(Map<Participant, Participant> fromToResult) {
    Mailer mailer = new Mailer();
    for (Map.Entry<Participant, Participant> fromToMatch : fromToResult.entrySet()) {
      Mail mail = new Mail();
      mail.setTo(fromToMatch.getKey().getEmail());
      mail.setSubject("DCCS Secret Santa 2017");
      mail.setBody("Pozdrav " + fromToMatch.getKey().getName() + ",\n\n"
              + "Trebas kupiti poklon za " + fromToMatch.getValue().getName() + ".\n"
              + "Zapamti - neka ovo ostane tajna!\n\n"
              + "DCCS Secret Santa Bot"
      );
      mailer.send(mail);
      logger.info("Mail successfully sent to "
              + fromToMatch.getKey().getName() + "<" + fromToMatch.getKey().getEmail()  + ">");
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
