package net.tzdev.secretsanta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Secret santa magic model.
 *
 * @author ahelac
 */
public class SecretSantaMatcher {
  private final static Logger logger = Logger.getLogger(String.valueOf(SecretSantaMatcher.class));

  private Set<Participant> originalParticipantsSet_;


  private Random random_ = new Random();

  /**
   * Construct SecretSanta out of Set<Participant>.
   * @param participants Set<Participant>
   */
  public SecretSantaMatcher(Set<Participant> participants) {
    originalParticipantsSet_ = participants;
  }

  public Map<Participant, Participant> matchParticipants() {
    List<Participant> giver_pool = new ArrayList<Participant>(originalParticipantsSet_);
    List<Participant> taker_pool = new ArrayList<Participant>(originalParticipantsSet_);

    Collections.shuffle(giver_pool);

    Map<Participant, Participant> fromToResult_ = new HashMap<Participant, Participant>();

    for (Participant giver : giver_pool) {

      Participant taker = getRandomElement(taker_pool);

      if (taker_pool.size() == 1 && giver.equals(taker)) {
        //one participant is stuck with himself, try again recursively
        return matchParticipants();
      }

      while(giver.equals(taker)) {
        taker = getRandomElement(taker_pool);
      }

      fromToResult_.put(giver, taker);
      if (logger.isDebugEnabled()) {
        logger.debug(giver + "->" + taker);
      }

      taker_pool.remove(taker);
    }
    return fromToResult_;
  }

  private <T> T getRandomElement(List<T> list) {
    return list.get(random_.nextInt(list.size()));
  }
}
