package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet28 extends Packet {
	public int field_6040_a;
	public int field_6039_b;
	public int field_6042_c;
	public int field_6041_d;

	public Packet28() {
	}

	public Packet28(Entity var1) {
		this(var1.field_331_c, var1.motionX, var1.motionY, var1.motionZ);
	}

	public Packet28(int var1, double var2, double var4, double var6) {
		this.field_6040_a = var1;
		double var8 = 3.9D;
		if(var2 < -var8) {
			var2 = -var8;
		}

		if(var4 < -var8) {
			var4 = -var8;
		}

		if(var6 < -var8) {
			var6 = -var8;
		}

		if(var2 > var8) {
			var2 = var8;
		}

		if(var4 > var8) {
			var4 = var8;
		}

		if(var6 > var8) {
			var6 = var8;
		}

		this.field_6039_b = (int)(var2 * 8000.0D);
		this.field_6042_c = (int)(var4 * 8000.0D);
		this.field_6041_d = (int)(var6 * 8000.0D);
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.field_6040_a = var1.readInt();
		this.field_6039_b = var1.readShort();
		this.field_6042_c = var1.readShort();
		this.field_6041_d = var1.readShort();
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeInt(this.field_6040_a);
		var1.writeShort(this.field_6039_b);
		var1.writeShort(this.field_6042_c);
		var1.writeShort(this.field_6041_d);
	}

	public void processPacket(NetHandler var1) {
		var1.func_6002_a(this);
	}

	public int getPacketSize() {
		return 10;
	}
}
