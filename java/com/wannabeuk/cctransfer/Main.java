package com.wannabeuk.cctransfer;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wannabeuk.cctransfer.util.IProxy;
import com.wannabeuk.cctransfer.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main 
{
	@Instance(Reference.MOD_ID)
	public static Main instance;
	   
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	private static Logger logger;
	   
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		   logger = event.getModLog();
		   proxy.logPhysicalSide();
	}
	   
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
	}
	   
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		   
	}
	
	public static void SendChatMessage(String msg)
	{
		Minecraft mc = Minecraft.getMinecraft();
		
		mc.player.sendChatMessage(msg);
	}
	
	private static Logger getLogger()
	{
		if(logger == null)
		{
			final Logger tempLogger = LogManager.getLogger();
			tempLogger.error("[" + Main.class.getSimpleName() + "]: getLogger called before logger has been initalised! Providing default logger\"); ");
			return tempLogger;
		}
		return logger;
	}
	
	public static void debug(final Object... messages)
	{
		for(final Object msg : messages)
		{
			getLogger().debug(msg);
		}
	}
	
	public static void info(final Object... messages)
	{
		for(final Object msg : messages)
		{
			getLogger().info(msg);
		}
	}
	
	public static void warn(final Object... messages)
	{
		for(final Object msg : messages)
		{
			getLogger().warn(msg);
		}
	}
	
	public static void error(final Object... messages)
	{
		for(final Object msg : messages)
		{
			getLogger().error(msg);
		}
	}
	
	public static void fatal(final Object... messages)
	{
		for(final Object msg: messages)
		{
			getLogger().fatal(msg);
		}
	}
	
	public static void dump(final Object...objects)
	{
		for(final Object obj : objects)
		{
			final Field[] fields = obj.getClass().getDeclaredFields();
			info("Dump of " + obj + ":");
			for(int i = 0; i < fields.length; i++)
			{
				try
				{
					fields[i].setAccessible(true);
					info(fields[i].getName() + " - " + fields[i].get(obj));
				}
				catch(IllegalArgumentException | IllegalAccessException e)
				{
					info("Error getting field " + fields[i].getName());
					info(e.getLocalizedMessage());
				}
			}
		}
	}
}
