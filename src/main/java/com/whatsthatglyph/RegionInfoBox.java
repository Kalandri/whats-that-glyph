package com.whatsthatglyph;
import net.runelite.client.ui.overlay.infobox.InfoBox;
import net.runelite.client.ui.overlay.infobox.InfoBoxPriority;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RegionInfoBox extends InfoBox
{
    private final WhatsThatGlyphPlugin plugin;

    public RegionInfoBox(BufferedImage image, WhatsThatGlyphPlugin plugin)
    {
        super(image, plugin);
        this.plugin = plugin;
        setTooltip("Attack a demon to see the current glyph.");
        setPriority(InfoBoxPriority.MED);
    }

    @Override
    public String getText()
    {
        return "";
    }

    @Override
    public Color getTextColor()
    {
        return Color.WHITE;
    }

    @Override
    public boolean render() {
        if(!plugin.isInChasmOfFire()){
            return false;
        }
        return super.render();
    }
}