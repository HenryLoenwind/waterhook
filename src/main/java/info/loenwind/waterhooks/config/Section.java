package info.loenwind.waterhooks.config;

import javax.annotation.Nonnull;

public enum Section {
  WATERHOOK("waterhook");

  @Nonnull
  public final String name;
  public final boolean sync;

  private Section(@Nonnull String name) {
    this.name = name;
    this.sync = true;
  }

  private Section(@Nonnull String name, boolean sync) {
    this.name = name;
    this.sync = sync;
  }

}
