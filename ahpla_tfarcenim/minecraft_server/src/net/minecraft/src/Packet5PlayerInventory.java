package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet5PlayerInventory extends Packet {
	public int type;
	public ItemStack[] stacks;

	public Packet5PlayerInventory() {
	}

	public Packet5PlayerInventory(int var1, ItemStack[] var2) {
		this.type = var1;
		this.stacks = new ItemStack[var2.length];

		for(int var3 = 0; var3 < this.stacks.length; ++var3) {
			this.stacks[var3] = var2[var3] == null ? null : var2[var3].copy();
		}

	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.type = var1.readInt();
		short var2 = var1.readShort();
		this.stacks = new ItemStack[var2];

		for(int var3 = 0; var3 < var2; ++var3) {
			short var4 = var1.readShort();
			if(var4 >= 0) {
				byte var5 = var1.readByte();
				short var6 = var1.readShort();
				this.stacks[var3] = new ItemStack(var4, var5, var6);
			}
		}

	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeInt(this.type);
		var1.writeShort(this.stacks.length);

		for(int var2 = 0; var2 < this.stacks.length; ++var2) {
			if(this.stacks[var2] == null) {
				var1.writeShort(-1);
			} else {
				var1.writeShort((short)this.stacks[var2].itemID);
				var1.writeByte((byte)this.stacks[var2].stackSize);
				var1.writeShort((short)this.stacks[var2].itemDamage);
			}
		}

	}

	public void processPacket(NetHandler var1) {
		var1.handlePlayerInventory(this);
	}

	public int getPacketSize() {
		return 6 + this.stacks.length * 5;
	}
}
