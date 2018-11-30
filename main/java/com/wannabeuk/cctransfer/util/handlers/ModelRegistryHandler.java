package com.wannabeuk.cctransfer.util.handlers;

import com.wannabeuk.cctransfer.init.ModBlocks;
import com.wannabeuk.cctransfer.init.ModItems;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class ModelRegistryHandler
{
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		registerModel(ModItems.BASIC_ITEM);
		registerModel(Item.getItemFromBlock(ModBlocks.BASIC_BLOCK));
	}
	
	private static void registerModel(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
