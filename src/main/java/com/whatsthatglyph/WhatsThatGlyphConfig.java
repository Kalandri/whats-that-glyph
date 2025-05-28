package com.whatsthatglyph;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Notification;

@ConfigGroup(WhatsThatGlyphConfig.GROUP)
public interface WhatsThatGlyphConfig extends Config {
    String GROUP = "whatsthatglyph";

    @ConfigItem(keyName = "glyphnotification",
            name = "Glyph notifications",
            description = "Configures if glyph change notifications are enabled.",
            position = 1
    )
    default Notification glyphNotifications() {
        return Notification.ON;
    }

}
