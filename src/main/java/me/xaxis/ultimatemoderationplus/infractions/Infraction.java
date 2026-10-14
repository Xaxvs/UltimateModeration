package me.xaxis.ultimatemoderationplus.infractions;

import java.util.UUID;

public interface Infraction {

  String getReason();

  UUID playerId();

  String staffName();

  UUID staffId();

  UUID infractionId();

  long timestamp();

}

