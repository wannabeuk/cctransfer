package com.wannabeuk.cctransfer.util;

import com.wannabeuk.cctransfer.Main;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public interface IProxy 
{
	String localize(String unlocalized);
	
	String localizeAndFormat(String unlocalized, Object... args);

	default void logPhysicalSide() {
		Main.info("Physical Side: " + getPhysicalSide());
	}

	Side getPhysicalSide();
}
