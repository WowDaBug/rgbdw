package net.minecraft.src;

import net.PeytonPlayz585.input.Mouse;

public class MouseHelper {
	public int field_1114_a;
	public int field_1113_b;

	public MouseHelper() {
	}

	public void func_774_a() {
		Mouse.setGrabbed(true);
		this.field_1114_a = 0;
		this.field_1113_b = 0;
	}

	public void func_773_b() {
		Mouse.setGrabbed(false);
	}

	public void func_772_c() {
		this.field_1114_a = Mouse.getDX();
		this.field_1113_b = Mouse.getDY();
	}
}
