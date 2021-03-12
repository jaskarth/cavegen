package supercoder79.cavegenmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.HorizontalVoronoiBiomeAccessType;
import net.minecraft.world.biome.source.VoronoiBiomeAccessType;

@Mixin(HorizontalVoronoiBiomeAccessType.class)
public class MixinHorizontalVoronoiBiomeAccessType {

	/**
	 * @author SuperCoder79
	 */
	@Overwrite
	public Biome getBiome(long seed, int x, int y, int z, BiomeAccess.Storage storage) {
		return VoronoiBiomeAccessType.INSTANCE.getBiome(seed, x, y, z, storage);
	}
}
