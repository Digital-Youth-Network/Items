package com.dyn.item.proxy;

import com.dyn.item.items.Flags;
import com.dyn.server.packets.PacketDispatcher;
import com.dyn.server.packets.client.ReturnFlagMessage;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Server implements Proxy {

	@SubscribeEvent
	public void dropEvent(ItemTossEvent event) {
		if ((event.player != null) && (event.entityItem != null) && (event.entityItem.getEntityItem() != null)) {
			ItemStack is = event.entityItem.getEntityItem();
			if (is.getItem() instanceof Flags) {
				event.setCanceled(true);
				PacketDispatcher.sendTo(new ReturnFlagMessage(is), (EntityPlayerMP) event.player);
			}
		}
	}

	@Override
	public void init() {
		FMLCommonHandler.instance().bus().register(this);

		MinecraftForge.EVENT_BUS.register(this);
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Actions on render GUI for the server (logging)

	}

}