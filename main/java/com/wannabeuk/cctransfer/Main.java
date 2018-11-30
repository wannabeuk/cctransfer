package com.wannabeuk.cctransfer;

import com.wannabeuk.cctransfer.proxy.CommonProxy;
import com.wannabeuk.cctransfer.proxy.IProxy;
import com.wannabeuk.cctransfer.util.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main 
{
	@Instance
	public static Main instance;
	   
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	   
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		   
	}
	   
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		//ModRecipes.init();
	}
	   
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		   
	}
}
