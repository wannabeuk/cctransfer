package com.wannabeuk.cctransfer.creativetab;

import com.wannabeuk.cctransfer.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry.ItemStackHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTabs 
{
	public static final String TAB_ICON_ITEM_REGISTRY_NAME = Reference.MOD_ID + ":" + "example_block";

	@ItemStackHolder(value = TAB_ICON_ITEM_REGISTRY_NAME)
	public static final ItemStack TAB_ICON_ITEMSTACK = null;

	/** instantiate creative tabs */
	public static final CustomCreativeTab CREATIVE_TAB = new CustomCreativeTab(Reference.MOD_ID, true) 
	{
		@Override
		public ItemStack getTabIconItem() 
		{
			return TAB_ICON_ITEMSTACK;
		}

	};

	/**
	 * This class is used for an extra tab in the creative inventory. Many mods like to group their special items and blocks in a dedicated tab although it is also perfectly acceptable to put them in the vanilla tabs where it makes sense.
	 */
	public abstract static class CustomCreativeTab extends CreativeTabs {

		private final boolean hasSearchBar;

		public CustomCreativeTab(final String name, final boolean hasSearchBar) {
			super(name);
			this.hasSearchBar = hasSearchBar;
		}

		/**
		 * Useful for adding extra items such as full variants of energy related items
		 */
		@SideOnly(Side.CLIENT)
		@Override
		public void displayAllRelevantItems(final NonNullList<ItemStack> items) {
			super.displayAllRelevantItems(items);
		}

		@Override
		public boolean hasSearchBar() {
			return this.hasSearchBar;
		}

		@Override
		public String getBackgroundImageName() {
			if (this.hasSearchBar) {
				return "item_search.png";
			} else {
				return super.getBackgroundImageName();
			}
		}

	}
}

