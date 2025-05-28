package com.whatsthatglyph;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.InteractingChanged;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(name = "What's That Glyph?", description = "Send a notification and display the correct attack method when the glyph above demons in the Chasm of Fire changes.", tags = {"notifications"}, enabledByDefault = false)
public class WhatsThatGlyphPlugin extends Plugin {

    private static final int CHASM_OF_FIRE_REGION = 5789;

    @Inject
    private Client client;

    @Inject
    private InfoBoxManager infoBoxManager;

    @Inject
    private WhatsThatGlyphConfig config;

    @Inject
    private Notifier notifier;

    @Inject
    private SpriteManager spriteManager;

    @Getter(AccessLevel.PACKAGE)
    private boolean isInChasmOfFire;

    private int currentSpriteId;

    private RegionInfoBox infoBox;

    @Override
    protected void startUp() {
        BufferedImage icon = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        infoBox = new RegionInfoBox(icon, this);
        infoBoxManager.addInfoBox(infoBox);
        spriteManager.getSpriteAsync(6346, 0, infoBox);
    }

    @Override
    protected void shutDown() {
        infoBoxManager.removeInfoBox(infoBox);
        infoBox = null;
    }

    @Subscribe
    public void onInteractingChanged(InteractingChanged event) {
        if (!isInChasmOfFire) {
            return;
        }

        if (event.getSource() != client.getLocalPlayer()) {
            return;
        }

        if (event.getTarget() == null) {
            return;
        }

        NPC opponent = (NPC) event.getTarget();
        int[] archiveIds = opponent.getOverheadArchiveIds();

        if (archiveIds == null) {
            return;
        }

        if (archiveIds[0] == currentSpriteId) {
            return;
        }

        currentSpriteId = archiveIds[0];

        spriteManager.getSpriteAsync(currentSpriteId, 0, infoBox);
        infoBox.setTooltip(Glyph.fromSpriteId(currentSpriteId).getAttackMethod());
        infoBoxManager.updateInfoBoxImage(infoBox);
        notifier.notify(config.glyphNotifications(), "The glyph has changed!");
    }

    @Subscribe
    public void onGameTick(GameTick gameTick) {
        if (!isInChasmOfFireRegion()) {
            if (isInChasmOfFire) {
                log.debug("Left Chasm of Fire!");
                isInChasmOfFire = false;
            }
            return;
        }

        if (!isInChasmOfFire) {
            log.debug("Entered Chasm of Fire!");
            isInChasmOfFire = true;
        }
    }

    private boolean isInChasmOfFireRegion() {
        if (client.getLocalPlayer() != null) {
            return client.getLocalPlayer().getWorldLocation().getRegionID() == CHASM_OF_FIRE_REGION;
        }

        return false;
    }

    @Provides
    WhatsThatGlyphConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(WhatsThatGlyphConfig.class);
    }
}
