package net.minecraft.src;

import net.minecraft.client.Minecraft;

class ThreadConnectToServer extends Thread {
	final Minecraft mc;
	final String uri;
	final GuiConnecting connectingGui;

	ThreadConnectToServer(GuiConnecting var1, Minecraft var2, String var3) {
		this.connectingGui = var1;
		this.mc = var2;
		this.uri = var3;
	}

	public void run() {
		try {
			GuiConnecting.setNetClientHandler(this.connectingGui, new NetClientHandler(this.mc, this.uri));
			if(GuiConnecting.isCancelled(this.connectingGui)) {
				return;
			}

			GuiConnecting.getNetClientHandler(this.connectingGui).addToSendQueue(new Packet2Handshake(this.mc.field_6320_i.inventory));
		} catch (Exception var4) {
			if(GuiConnecting.isCancelled(this.connectingGui)) {
				return;
			}

			var4.printStackTrace();
			this.mc.displayGuiScreen(new GuiConnectFailed("Failed to connect to the server", var4.toString()));
		}

	}
}