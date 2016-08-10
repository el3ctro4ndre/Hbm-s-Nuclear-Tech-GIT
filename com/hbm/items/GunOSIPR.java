package com.hbm.items;

import java.util.List;
import java.util.Random;

import com.hbm.entity.EntityBullet;
import com.hbm.entity.EntityCombineBall;
import com.hbm.entity.EntityMiniNuke;
import com.hbm.lib.ModDamageSource;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunOSIPR extends Item {

	Random rand = new Random();

    public GunOSIPR()
    {
        this.maxStackSize = 1;
        this.setMaxDamage(2500);
    }

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		{
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		}

		return p_77659_1_;
	}

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		World world = player.worldObj;
		
		if (!player.isSneaking()) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
			if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_osipr_ammo)) && count % 3 == 0) {
					EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper");
				entityarrow.setDamage(35 + rand.nextInt(45 - 35));

				world.playSoundAtEntity(player, "random.explode", 1.0F, 1.5F + (rand.nextFloat() / 4));

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					player.inventory.consumeInventoryItem(ModItems.gun_osipr_ammo);
				}
				
				if (!world.isRemote) {
					world.spawnEntityInWorld(entityarrow);
				}
			}
		} else {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
			if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_osipr_ammo2)) && count % 50 == 0 && (this.getMaxItemUseDuration(stack) - count) != 0) {
				EntityCombineBall entityarrow = new EntityCombineBall(player.worldObj, player, 3.0F);
				entityarrow.setDamage(35 + rand.nextInt(45 - 35));

				world.playSoundAtEntity(player, "tile.piston.in", 1.0F, 0.75F);

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					player.inventory.consumeInventoryItem(ModItems.gun_osipr_ammo2);
				}
				
				if (!world.isRemote) {
					world.spawnEntityInWorld(entityarrow);
				}
			}
		}
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Hold right mouse button");
		list.add("to shoot,");
		list.add("sneak to shoot a");
		list.add("dark energy ball!");
	}

}