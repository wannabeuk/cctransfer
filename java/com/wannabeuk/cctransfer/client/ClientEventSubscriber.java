package com.wannabeuk.cctransfer.client;

/**you can also import constants directly*/
import static com.wannabeuk.cctransfer.util.Reference.MOD_ID;
import static net.minecraftforge.fml.relauncher.Side.CLIENT;

import com.wannabeuk.cctransfer.Main;
import com.wannabeuk.cctransfer.init.ModBlocks;
import com.wannabeuk.cctransfer.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MOD_ID, value = CLIENT)
public class ClientEventSubscriber 
{
	private static final String DEFAULT_VARIANT = "normal";
	
	@SubscribeEvent
	public static void onRegisterModelsEvent(final ModelRegistryEvent event) 
	{
		registerTileEntitySpecialRenderers();
		Main.info("Registed tile entity special renderers");
		
		registerEntityRenderers();
		Main.info("Registered entity renderers");
		
		
		/* item blocks */
		registerItemBlockModel(ModBlocks.BLOCK_STATION);
		registerItemBlockModel(ModBlocks.BLOCK_TUBE);
		
		/* items */
		//registerItemModel(ModItems.EXAMPLE_ITEM);

		Main.info("Registered models");
	}
	
	private static void registerTileEntitySpecialRenderers() {
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity___.class, new TileEntity___Renderer());
	}

	private static void registerEntityRenderers() {
//		RenderingRegistry.registerEntityRenderingHandler(Entity___.class, renderManager -> new Entity___Renderer(renderManager));
	}

	private static void registerItemModel(final Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), DEFAULT_VARIANT));
	}

	private static void registerItemBlockModel(final Block block) 
	{
		if(block == null)
		{
			Main.error("registerItemBlockModel was passed a null block!");
			return;
		}
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), DEFAULT_VARIANT));
	}
}
