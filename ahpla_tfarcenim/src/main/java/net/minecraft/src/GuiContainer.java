package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.PeytonPlayz585.opengl.GL11;
import net.PeytonPlayz585.opengl.GL12;
import net.minecraft.client.Minecraft;

public abstract class GuiContainer extends GuiScreen {
	private static RenderItem itemRenderer = new RenderItem();
	protected int xSize = 176;
	protected int ySize = 166;
	protected List inventorySlots = new ArrayList();

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		int var4 = (this.width - this.xSize) / 2;
		int var5 = (this.height - this.ySize) / 2;
		this.drawGuiContainerBackgroundLayer(var3);
		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef((float)var4, (float)var5, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		for(int var6 = 0; var6 < this.inventorySlots.size(); ++var6) {
			SlotInventory var7 = (SlotInventory)this.inventorySlots.get(var6);
			this.drawSlotInventory(var7);
			if(var7.isAtCursorPos(var1, var2)) {
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				int var8 = var7.xPos;
				int var9 = var7.yPos;
				this.drawGradientRect(var8, var9, var8 + 16, var9 + 16, -2130706433, -2130706433);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}
		}

		InventoryPlayer var10 = this.mc.thePlayer.inventory;
		if(var10.draggingItemStack != null) {
			GL11.glTranslatef(0.0F, 0.0F, 32.0F);
			itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, var10.draggingItemStack, var1 - var4 - 8, var2 - var5 - 8);
			itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var10.draggingItemStack, var1 - var4 - 8, var2 - var5 - 8);
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		this.drawGuiContainerForegroundLayer();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}

	protected void drawGuiContainerForegroundLayer() {
	}

	protected abstract void drawGuiContainerBackgroundLayer(float var1);

	private void drawSlotInventory(SlotInventory var1) {
		IInventory var2 = var1.inventory;
		int var3 = var1.slotIndex;
		int var4 = var1.xPos;
		int var5 = var1.yPos;
		ItemStack var6 = var2.getStackInSlot(var3);
		if(var6 == null) {
			int var7 = var1.func_775_c();
			if(var7 >= 0) {
				GL11.glDisable(GL11.GL_LIGHTING);
				this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/items.png"));
				this.drawTexturedModalRect(var4, var5, var7 % 16 * 16, var7 / 16 * 16, 16, 16);
				GL11.glEnable(GL11.GL_LIGHTING);
				return;
			}
		}

		itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, var6, var4, var5);
		itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var6, var4, var5);
	}

	private Slot getSlotAtPosition(int var1, int var2) {
		for(int var3 = 0; var3 < this.inventorySlots.size(); ++var3) {
			SlotInventory var4 = (SlotInventory)this.inventorySlots.get(var3);
			if(var4.isAtCursorPos(var1, var2)) {
				return var4;
			}
		}

		return null;
	}

	protected void mouseClicked(int var1, int var2, int var3) {
		if(var3 == 0 || var3 == 1) {
			Slot var4 = this.getSlotAtPosition(var1, var2);
			InventoryPlayer var5 = this.mc.thePlayer.inventory;
			int var7;
			if(var4 != null) {
				ItemStack var6 = var4.getStack();
				if(var6 != null || var5.draggingItemStack != null) {
					if(var6 != null && var5.draggingItemStack == null) {
						var7 = var3 == 0 ? var6.stackSize : (var6.stackSize + 1) / 2;
						var5.draggingItemStack = var4.inventory.decrStackSize(var4.slotIndex, var7);
						if(var6.stackSize == 0) {
							var4.putStack((ItemStack)null);
						}

						var4.onPickupFromSlot();
					} else if(var6 == null && var5.draggingItemStack != null && var4.isItemValid(var5.draggingItemStack)) {
						var7 = var3 == 0 ? var5.draggingItemStack.stackSize : 1;
						if(var7 > var4.getSlotStackLimit()) {
							var7 = var4.getSlotStackLimit();
						}

						var4.putStack(var5.draggingItemStack.splitStack(var7));
						if(var5.draggingItemStack.stackSize == 0) {
							var5.draggingItemStack = null;
						}
					} else if(var6 != null && var5.draggingItemStack != null) {
						if(var4.isItemValid(var5.draggingItemStack)) {
							if(var6.itemID != var5.draggingItemStack.itemID) {
								if(var5.draggingItemStack.stackSize <= var4.getSlotStackLimit()) {
									var4.putStack(var5.draggingItemStack);
									var5.draggingItemStack = var6;
								}
							} else if(var6.itemID == var5.draggingItemStack.itemID) {
								if(var3 == 0) {
									var7 = var5.draggingItemStack.stackSize;
									if(var7 > var4.getSlotStackLimit() - var6.stackSize) {
										var7 = var4.getSlotStackLimit() - var6.stackSize;
									}

									if(var7 > var5.draggingItemStack.getMaxStackSize() - var6.stackSize) {
										var7 = var5.draggingItemStack.getMaxStackSize() - var6.stackSize;
									}

									var5.draggingItemStack.splitStack(var7);
									if(var5.draggingItemStack.stackSize == 0) {
										var5.draggingItemStack = null;
									}

									var6.stackSize += var7;
								} else if(var3 == 1) {
									var7 = 1;
									if(var7 > var4.getSlotStackLimit() - var6.stackSize) {
										var7 = var4.getSlotStackLimit() - var6.stackSize;
									}

									if(var7 > var5.draggingItemStack.getMaxStackSize() - var6.stackSize) {
										var7 = var5.draggingItemStack.getMaxStackSize() - var6.stackSize;
									}

									var5.draggingItemStack.splitStack(var7);
									if(var5.draggingItemStack.stackSize == 0) {
										var5.draggingItemStack = null;
									}

									var6.stackSize += var7;
								}
							}
						} else if(var6.itemID == var5.draggingItemStack.itemID && var5.draggingItemStack.getMaxStackSize() > 1) {
							var7 = var6.stackSize;
							if(var7 > 0 && var7 + var5.draggingItemStack.stackSize <= var5.draggingItemStack.getMaxStackSize()) {
								var5.draggingItemStack.stackSize += var7;
								var6.splitStack(var7);
								if(var6.stackSize == 0) {
									var4.putStack((ItemStack)null);
								}

								var4.onPickupFromSlot();
							}
						}
					}
				}

				var4.onSlotChanged();
			} else if(var5.draggingItemStack != null) {
				int var9 = (this.width - this.xSize) / 2;
				var7 = (this.height - this.ySize) / 2;
				if(var1 < var9 || var2 < var7 || var1 >= var9 + this.xSize || var2 >= var7 + this.xSize) {
					EntityPlayerSP var8 = this.mc.thePlayer;
					if(var3 == 0) {
						var8.dropPlayerItem(var5.draggingItemStack);
						var5.draggingItemStack = null;
					}

					if(var3 == 1) {
						var8.dropPlayerItem(var5.draggingItemStack.splitStack(1));
						if(var5.draggingItemStack.stackSize == 0) {
							var5.draggingItemStack = null;
						}
					}
				}
			}
		}

	}

	protected void mouseMovedOrUp(int var1, int var2, int var3) {
		if(var3 == 0) {
		}

	}

	protected void keyTyped(char var1, int var2) {
		if(var2 == 1 || var2 == this.mc.gameSettings.keyBindInventory.keyCode) {
			this.mc.displayGuiScreen((GuiScreen)null);
		}

	}

	public void onGuiClosed() {
		if(this.mc.thePlayer != null) {
			InventoryPlayer var1 = this.mc.thePlayer.inventory;
			if(var1.draggingItemStack != null) {
				this.mc.thePlayer.dropPlayerItem(var1.draggingItemStack);
				var1.draggingItemStack = null;
			}

		}
	}

	public boolean doesGuiPauseGame() {
		return false;
	}
}
