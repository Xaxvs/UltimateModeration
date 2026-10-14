package me.xaxis.ultimatemoderationplus.player;

import java.util.List;
import me.xaxis.ultimatemoderationplus.infractions.Infraction;
import java.util.ArrayList;

public class ModerationHistory {
  private final List<Infraction> infractions = new ArrayList<>();

  public ModerationHistory() {
  }

  public List<Infraction> getInfractions() {
    return infractions.stream().toList();
  }
}
