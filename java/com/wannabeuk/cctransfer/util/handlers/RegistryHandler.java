package com.wannabeuk.cctransfer.util.handlers;

import com.wannabeuk.cctransfer.blocks.BlockBase;
import com.wannabeuk.cctransfer.init.ModBlocks;
import com.wannabeuk.cctransfer.init.ModItems;
import com.wannabeuk.cctransfer.items.ItemBase;
import com.wannabeuk.cctransfer.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler 
{
	
	 @SubscribeEvent
	 public static void registerItems(Register<Item> event) 
	 {
		 final Item[] items = 
			 {
					 new ItemBase("itemBasic", "basic_item")
			 };
	 
		 event.getRegistry().registerAll(items);
	 }
	 
	 @SubscribeEvent
	 public static void registerBlocks(Register<Block> event)
	 {
		 final Block[] blocks = 
		 {
	        new BlockBase(Material.ROCK, "blockBasic", "basic_block")
		 };
	 
		 event.getRegistry().registerAll(blocks);
	 }
}
