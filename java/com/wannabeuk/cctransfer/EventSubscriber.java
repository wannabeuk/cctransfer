package com.wannabeuk.cctransfer;

import com.wannabeuk.cctransfer.blocks.machines.station.BlockStation;
import com.wannabeuk.cctransfer.blocks.machines.station.BlockTube;
import com.wannabeuk.cctransfer.init.ModBlocks;
import com.wannabeuk.cctransfer.items.ItemStation;
import com.wannabeuk.cctransfer.tile.TileEntityBlockStation;
import com.wannabeuk.cctransfer.tile.TileEntityTube;
import com.wannabeuk.cctransfer.util.Reference;
import com.wannabeuk.cctransfer.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventSubscriber
{
	private static int entityId = 0;

	/* register blocks */
	@SubscribeEvent
	public static void onRegisterBlocksEvent(final RegistryEvent.Register<Block> event)
	{
		final IForgeRegistry<Block> registry = event.getRegistry();

		registry.register(new BlockStation("tube_station"));
		registry.register(new BlockTube("tube"));

		Main.info("Registered blocks");

		registerTileEntities();

		Main.debug("Registered tile entities");

	}

	private static void registerTileEntities()
	{
		registerTileEntity(TileEntityBlockStation.class);
		registerTileEntity(TileEntityTube.class);
	}

	private static void registerTileEntity(final Class<? extends TileEntity> clazz)
	{
		try
		{
			GameRegistry.registerTileEntity(clazz,
					new ResourceLocation(Reference.MOD_ID, Utils.getRegistryNameForClass(clazz, "TileEntity")));
		}
		catch (final Exception e)
		{
			Main.error("Error registering Tile Entity " + clazz.getSimpleName());
			e.printStackTrace();
		}
	}

	/* register items */
	@SubscribeEvent
	public static void onRegisterItemsEvent(final RegistryEvent.Register<Item> event)
	{
		final IForgeRegistry<Item> registry = event.getRegistry();

		// item blocks
		registry.register(Utils.setRegistryNames(new ItemStation(), ModBlocks.BLOCK_STATION.getRegistryName()));
		registry.register(
				Utils.setRegistryNames(new ItemBlock(ModBlocks.BLOCK_TUBE), ModBlocks.BLOCK_TUBE.getRegistryName()));
		// items

		Main.info("Registered items");

	}

	/* register entities */
	@SubscribeEvent
	public static void onRegisterEntitiesEvent(final RegistryEvent.Register<EntityEntry> event)
	{
		final IForgeRegistry<EntityEntry> registry = event.getRegistry();

		// event.getRegistry().register(buildEntityEntryFromClass(Entity___.class,
		// hasEgg, range, updateFrequency, shouldSendVelocityUpdates));

		Main.info("Registered entities");

	}
}
