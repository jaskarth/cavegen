package supercoder79.cavegenmod.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.gen.SimpleRandom;

@Mixin(VanillaLayeredBiomeSource.class)
public class MixinVanillaLayeredBiomeSource {

	@Shadow @Final private BiomeLayerSampler biomeSampler;

	@Shadow @Final private Registry<Biome> biomeRegistry;

	@Unique
	private PerlinNoiseSampler caveBiomeNoise;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void makeNoise(long seed, boolean legacyBiomeInitLayer, boolean largeBiomes, Registry<Biome> biomeRegistry, CallbackInfo ci) {
		this.caveBiomeNoise = new PerlinNoiseSampler(new SimpleRandom(seed));
	}

	/**
	 * @author SuperCoder79
	 */
	@Overwrite
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
		if (biomeY < 14) {
			if (this.caveBiomeNoise.sample(biomeX / 60.0, 0, biomeZ / 60.0) > 0) {
				return this.biomeRegistry.get(BiomeKeys.LUSH_CAVES);
			} else {
				return this.biomeRegistry.get(BiomeKeys.DRIPSTONE_CAVES);
			}
		}

		return this.biomeSampler.sample(this.biomeRegistry, biomeX, biomeZ);
	}
}
