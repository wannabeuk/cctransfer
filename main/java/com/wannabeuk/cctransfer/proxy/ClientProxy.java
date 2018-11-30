package com.wannabeuk.cctransfer.proxy;

import com.wannabeuk.cctransfer.Main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy implements IProxy
{

	@Override
	public void preInit(FMLPreInitializationEvent event) 
	{
			//Registering things on clientside such as entityrenders/key binding
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
	
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) 
	{
		
		
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event) {}

	@Override
	public EntityPlayer getPlayerFromContext(MessageContext ctx)
	{
		return (ctx.side.isClient() ? Minecraft.getMinecraft().player : Main.proxy.getPlayerFromContext(ctx));
	}

	@Override
	public World getWorldFromContext(MessageContext ctx)
	{
		return (ctx.side.isClient() ? Minecraft.getMinecraft().world : Main.proxy.getWorldFromContext(ctx));
	}

	@Override
	public void addRunnableFromContext(MessageContext ctx, Runnable task) 
	{
		if(ctx.side.isClient())
		{
			Minecraft.getMinecraft().addScheduledTask(task);
		}
		else
		{
			Main.proxy.addRunnableFromContext(ctx, task);
		}
	}

}
