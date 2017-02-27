package com.dyn.fixins.proxy;

import com.dyn.fixins.blocks.cmdblock.StudentCommandBlockLogic;
import com.dyn.fixins.entity.crash.CrashTestEntity;
import com.dyn.fixins.entity.crash.ModelCrashTestEntity;
import com.dyn.fixins.entity.crash.RenderCrashTestEntity;
import com.dyn.fixins.entity.ghost.GhostEntity;
import com.dyn.fixins.entity.ghost.ModelGhost;
import com.dyn.fixins.entity.ghost.RenderGhostEntity;
import com.dyn.fixins.reference.Reference;
import com.dyn.render.gui.command.StudentComamndGui;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Client implements Proxy {

	@Override
	public void init() {

	}

	@Override
	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic) {
		Minecraft.getMinecraft().displayGuiScreen(new StudentComamndGui(cmdBlockLogic));
	}

	@Override
	public void preInit() {
		RenderingRegistry.registerEntityRenderingHandler(CrashTestEntity.class, new IRenderFactory<CrashTestEntity>() {
			@Override
			public Render<? super CrashTestEntity> createRenderFor(RenderManager manager) {
				return new RenderCrashTestEntity(manager, new ModelCrashTestEntity(), 0.3F);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(GhostEntity.class, new IRenderFactory<GhostEntity>() {
			@Override
			public Render<? super GhostEntity> createRenderFor(RenderManager manager) {
				return new RenderGhostEntity(manager, new ModelGhost(), 0.0F, 0.65F);
			}
		});
	}

	@Override
	public void registerBlockItem(Block block) {
		String blockName = block.getUnlocalizedName().replace("tile.", "");
		Item blockItem = GameRegistry.findItem(Reference.MOD_ID, blockName);
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MOD_ID + ":" + blockName,
				"inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(blockItem, 0, itemModelResourceLocation);
	}

	@Override
	public void registerItem(Item item, String name, int meta) {
		if (name.contains("item.")) {
			name = name.replace("item.", "");
		}
		ModelResourceLocation location = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, location);
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}
}