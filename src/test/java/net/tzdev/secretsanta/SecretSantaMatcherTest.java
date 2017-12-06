package net.tzdev.secretsanta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import net.tzdev.secretsanta.utils.Configurator;

/**
 * Unit test for SecretSantaMatcher.
 */
public class SecretSantaMatcherTest {

  public static final int NUMBER_OF_TRIES = 10000;
  private static Set<Participant> testParticipants_;

  @BeforeClass
  public static void before() {
    Properties properties = Configurator.getProperties("test-participants.properties");
    testParticipants_ = new HashSet<Participant>();
    for (Map.Entry<Object, Object> configEntry : properties.entrySet()) {
      testParticipants_.add(new Participant(configEntry.getKey().toString(), configEntry.getValue().toString()));
    }
  }

  @Test
  public void testMatcherResultListSize() {
    SecretSantaMatcher matcher = new SecretSantaMatcher(testParticipants_);
    Map<Participant, Participant> results = matcher.matchParticipants();
    Assert.assertEquals(results.entrySet().size(), testParticipants_.size());
  }

  @Test
  public void testRandomness() {
    Map<Participant, Map<Participant, Integer>> stats = new HashMap<Participant, Map<Participant, Integer>>();

    //init stats map
    for (Participant participant : testParticipants_) {
      stats.put(participant, new HashMap<Participant, Integer>());
    }

    //generate some data
    for (int i = 0; i < NUMBER_OF_TRIES; i++) {
      Map<Participant, Participant> match_result = new SecretSantaMatcher(testParticipants_).matchParticipants();
      for (Map.Entry<Participant, Participant> single_result : match_result.entrySet()) {

        Participant giver = single_result.getKey();
        Participant receiver = single_result.getValue();

        Map<Participant, Integer> giver_stats_map = stats.get(giver);
        if (!giver_stats_map.containsKey(receiver)) {
          giver_stats_map.put(receiver, 0);
        }
        Integer repeats = giver_stats_map.get(receiver);
        giver_stats_map.put(receiver, repeats + 1);
      }
    }


    double exact = (1.0 / (testParticipants_.size() - 1)) * 100;
    double tolerance = exact * 0.2;
    System.out.println("Exact: " + exact +" Min: " + (exact - tolerance) + " Max: " + (exact + tolerance));

    for (Map.Entry<Participant, Map<Participant, Integer>> stat : stats.entrySet()) {
      String out = "";
      out += stat.getKey();
      out += ":: ";
      for (Map.Entry<Participant, Integer> entry : stat.getValue().entrySet()) {
        double percent = ((double) Math.round(entry.getValue() * 100)) / NUMBER_OF_TRIES;
        out += entry.getKey() + " " + percent + "% ";

        Assert.assertTrue(percent > exact - tolerance);
        Assert.assertTrue(percent < exact + tolerance);
      }
      System.out.println(out);
    }
  }

}
