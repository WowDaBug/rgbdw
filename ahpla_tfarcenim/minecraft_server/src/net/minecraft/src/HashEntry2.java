package net.minecraft.src;

class HashEntry2 {
	final long field_1025_a;
	Object field_1024_b;
	HashEntry2 field_1027_c;
	final int field_1026_d;

	HashEntry2(int var1, long var2, Object var4, HashEntry2 var5) {
		this.field_1024_b = var4;
		this.field_1027_c = var5;
		this.field_1025_a = var2;
		this.field_1026_d = var1;
	}

	public final long func_736_a() {
		return this.field_1025_a;
	}

	public final Object func_735_b() {
		return this.field_1024_b;
	}

	public final boolean equals(Object var1) {
		if(!(var1 instanceof HashEntry2)) {
			return false;
		} else {
			HashEntry2 var2 = (HashEntry2)var1;
			Long var3 = Long.valueOf(this.func_736_a());
			Long var4 = Long.valueOf(var2.func_736_a());
			if(var3 == var4 || var3 != null && var3.equals(var4)) {
				Object var5 = this.func_735_b();
				Object var6 = var2.func_735_b();
				if(var5 == var6 || var5 != null && var5.equals(var6)) {
					return true;
				}
			}

			return false;
		}
	}

	public final int hashCode() {
		return MCHashTable2.func_674_d(this.field_1025_a);
	}

	public final String toString() {
		return this.func_736_a() + "=" + this.func_735_b();
	}
}
