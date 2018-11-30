package com.wannabeuk.cctransfer.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerProxy implements IProxy 
{

	@Override
	public void preInit(FMLPreInitializationEvent event) { }

	@Override
	public void init(FMLInitializationEvent event) {}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	@Override
	public void serverStarting(FMLServerStartingEvent event) 
	{		
		//Commands go here.
	}

	@Override
	public EntityPlayer getPlayerFromContext(MessageContext ctx)
	{
		return ctx.getServerHandler().player;
	}

	@Override
	public World getWorldFromContext(MessageContext ctx) 
	{		
		return ((EntityPlayerMP) getPlayerFromContext(ctx)).getServerWorld();
	}

	@Override
	public void addRunnableFromContext(MessageContext ctx, Runnable task) 
	{
		((WorldServer)getWorldFromContext(ctx)).addScheduledTask(task);
	}

}
