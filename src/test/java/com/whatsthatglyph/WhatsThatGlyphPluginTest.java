package com.whatsthatglyph;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class WhatsThatGlyphPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(WhatsThatGlyphPlugin.class);
		RuneLite.main(args);
	}
}