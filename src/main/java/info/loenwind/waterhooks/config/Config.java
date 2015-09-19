package info.loenwind.waterhooks.config;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

import javax.annotation.Nonnull;

import net.minecraftforge.common.config.Configuration;

public enum Config {

  disableInfiniteWaterGlobally(Section.WATERHOOK, false, "Disable vanilla infinite water forming completely"), // 

  ;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Nothing to see beyond this point. End of configuration values.
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Nonnull
  private final Section section;
  @Nonnull
  private final Boolean defaultValue;
  @Nonnull
  private final String description;
  @Nonnull
  private Boolean currentValue;

  private Config(@Nonnull Section section, @Nonnull Boolean defaultValue, @Nonnull String description) {
    this.section = section;
    this.description = description;
    this.currentValue = this.defaultValue = defaultValue;
  }

  void load(Configuration config) {
    setField(config.get(section.name, name(), (Boolean) defaultValue, description).getBoolean((Boolean) defaultValue));
  }

  private void setField(Boolean value) {
    if (value != null) {
      currentValue = value;
    }
  }

  void store(ByteBuf buf) {
    buf.writeBoolean(getBoolean());
  }

  void read(ByteBuf buf) {
    setField(buf.readBoolean());
  }

  protected void resetToDefault() {
    setField(defaultValue);
  }

  public Section getSection() {
    return section;
  }

  //

  public boolean getDefaultBoolean() {
    return defaultValue;
  }

  //

  public boolean getBoolean() {
    return currentValue;
  }

}
