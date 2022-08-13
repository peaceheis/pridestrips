package io.github.peaceheis.pridestrips;


import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.worldgen.surface_rule.api.SurfaceRuleContext;
import org.quiltmc.qsl.worldgen.surface_rule.api.SurfaceRuleEvents;
import org.slf4j.LoggerFactory;

public class PrideStrips implements SurfaceRuleEvents.OverworldModifierCallback, ModInitializer {
	public static final String MODID = "pridestrips";

	public static final double STRIP_SIZE = 1;
	public static final double STRIP_ONE_MIN = -1;
	public static final double STRIP_TWO_MIN = 0;

	public static final ResourceKey<NormalNoise.NoiseParameters> PRIDE_NOISE_KEY = Noises.CALCITE;

	// i'm lazy. like, really lazy. and i don't like my code to extend far off my screen.
	public static final BlockState LIGHT_BLUE = Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState();
	public static final BlockState PINK = Blocks.PINK_CONCRETE.defaultBlockState();
	public static final BlockState WHITE = Blocks.WHITE_CONCRETE.defaultBlockState();
	public static final BlockState RED = Blocks.RED_CONCRETE.defaultBlockState();
	public static final BlockState ORANGE = Blocks.ORANGE_CONCRETE.defaultBlockState();
	public static final BlockState YELLOW = Blocks.YELLOW_CONCRETE.defaultBlockState();
	public static final BlockState GREEN = Blocks.GREEN_CONCRETE.defaultBlockState();
	public static final BlockState BLUE = Blocks.BLUE_CONCRETE.defaultBlockState();
	public static final BlockState PURPLE = Blocks.PURPLE_CONCRETE.defaultBlockState();
	public static final BlockState BLACK = Blocks.BLACK_CONCRETE.defaultBlockState();
	public static final BlockState GRAY = Blocks.GRAY_CONCRETE.defaultBlockState();
	public static final BlockState PURPLE_TWO = Blocks.PURPLE_TERRACOTTA.defaultBlockState();

	public static final SurfaceRules.RuleSource FLAG_TRANS = ruleFromBlocks(LIGHT_BLUE, PINK, WHITE, PINK, LIGHT_BLUE);
	public static final SurfaceRules.RuleSource FLAG_ACE = ruleFromBlocks(BLACK, GRAY, WHITE, PURPLE_TWO);
	public static final SurfaceRules.RuleSource FLAG_NB = ruleFromBlocks(YELLOW, WHITE, PURPLE, BLACK);
	public static final SurfaceRules.RuleSource FLAG_BI = ruleFromBlocks(PINK, PINK, PURPLE_TWO, BLUE, BLUE);
	public static final SurfaceRules.RuleSource FLAG_AGENDER = ruleFromBlocks(BLACK, GRAY, WHITE, GREEN, WHITE, GRAY, BLACK);
	public static final SurfaceRules.RuleSource FLAG_PRIDE = ruleFromBlocks(RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE);

	@Override
	public void modifyOverworldRules(SurfaceRuleContext.@NotNull Overworld context) {
		context.materialRules().add(0,
				SurfaceRules.sequence(
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.BIRCH_FOREST, Biomes.FLOWER_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST), FLAG_TRANS),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.SUNFLOWER_PLAINS, Biomes.LUSH_CAVES), FLAG_ACE),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA), FLAG_NB),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.DESERT, Biomes.DARK_FOREST), FLAG_BI),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.GROVE, Biomes.JUNGLE), FLAG_AGENDER),
						FLAG_PRIDE
				)
		);
		LoggerFactory.getLogger(PrideStrips.class).info("THING" + context.materialRules());
	}

	@Override
	public void onInitialize(ModContainer mod) {
		LoggerFactory.getLogger(PrideStrips.class).info("Your Ground Just Got Prided :^)");
	}

	public static SurfaceRules.RuleSource ruleFromBlocks(BlockState... blockstates) {
		SurfaceRules.RuleSource[] ruleSources = new SurfaceRules.RuleSource[blockstates.length*2];

		for(int ruleIndex = 0; ruleIndex < blockstates.length; ruleIndex++) {
			double sizePerSection = STRIP_SIZE/blockstates.length;
			ruleSources[ruleIndex] =
				SurfaceRules.ifTrue(
						SurfaceRules.ON_FLOOR,
						SurfaceRules.ifTrue(
								SurfaceRules.abovePreliminarySurface(),
								SurfaceRules.ifTrue(
									SurfaceRules.noiseCondition(PRIDE_NOISE_KEY, STRIP_ONE_MIN + sizePerSection*ruleIndex, STRIP_ONE_MIN + sizePerSection*(ruleIndex+1)),
									SurfaceRules.state(blockstates[ruleIndex]))
						)
			);
			ruleSources[ruleIndex+(blockstates.length)] =
					SurfaceRules.ifTrue(
							SurfaceRules.ON_FLOOR,
							SurfaceRules.ifTrue(
									SurfaceRules.noiseCondition(PRIDE_NOISE_KEY, STRIP_TWO_MIN + sizePerSection*ruleIndex, STRIP_TWO_MIN + sizePerSection*(ruleIndex+1)),
									SurfaceRules.state(blockstates[ruleIndex]))
					);
		}
		return SurfaceRules.sequence(ruleSources);
	}
}
