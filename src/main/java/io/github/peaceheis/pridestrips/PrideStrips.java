package io.github.peaceheis.pridestrips;


import com.ibm.icu.text.PluralSamples;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.worldgen.surface_rule.api.SurfaceRuleContext;
import org.quiltmc.qsl.worldgen.surface_rule.api.SurfaceRuleEvents;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PrideStrips implements SurfaceRuleEvents.OverworldModifierCallback, ModInitializer {
	public static final String MODID = "pridestrips";

	public static final double STRIP_SIZE = 0.17;
	public static final double STRIP_ONE_MIN = -1;
	public static final double STRIP_TWO_MIN = 0;

	public static final ResourceKey<NormalNoise.NoiseParameters> PRIDE_NOISE_KEY = ResourceKey.create(Registry.NOISE_REGISTRY, new ResourceLocation(MODID, "pride"));

	// used for BushBlockMixin, to allow plants to generate on these blocks.
	public static final TagKey<Block> PRIDESTRIPS_USABLE = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(MODID, "flags_usable"));

	public static final BlockState LIGHT_BLUE = Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState();
	public static final BlockState PINK = Blocks.PINK_CONCRETE.defaultBlockState();
	public static final BlockState WHITE = Blocks.WHITE_CONCRETE.defaultBlockState();
	public static final BlockState RED = Blocks.RED_CONCRETE.defaultBlockState();
	public static final BlockState ORANGE = Blocks.ORANGE_CONCRETE.defaultBlockState();
	public static final BlockState YELLOW = Blocks.YELLOW_CONCRETE.defaultBlockState();
	public static final BlockState GREEN = Blocks.LIME_CONCRETE.defaultBlockState();
	public static final BlockState GREEN_TWO = Blocks.GREEN_CONCRETE.defaultBlockState();
	public static final BlockState BLUE = Blocks.BLUE_CONCRETE.defaultBlockState();
	public static final BlockState PURPLE = Blocks.PURPLE_CONCRETE.defaultBlockState();
	public static final BlockState BLACK = Blocks.BLACK_CONCRETE.defaultBlockState();
	public static final BlockState GRAY = Blocks.GRAY_CONCRETE.defaultBlockState();
	public static final BlockState PURPLE_TWO = Blocks.PURPLE_TERRACOTTA.defaultBlockState();
	public static final BlockState CYAN = Blocks.CYAN_CONCRETE.defaultBlockState();
	public static final BlockState ORANGE_TWO = Blocks.ORANGE_TERRACOTTA.defaultBlockState();
	public static final BlockState MAGENTA = Blocks.MAGENTA_CONCRETE.defaultBlockState();



	public static final SurfaceRules.RuleSource FLAG_TRANS = ruleFromBlocks(LIGHT_BLUE, PINK, WHITE, PINK, LIGHT_BLUE);
	public static final SurfaceRules.RuleSource FLAG_ACE = ruleFromBlocks(BLACK, GRAY, WHITE, PURPLE_TWO);
	public static final SurfaceRules.RuleSource FLAG_NB = ruleFromBlocks(YELLOW, WHITE, PURPLE, BLACK);
	public static final SurfaceRules.RuleSource FLAG_BI = ruleFromBlocks(PINK, PINK, PURPLE_TWO, BLUE, BLUE);
	public static final SurfaceRules.RuleSource FLAG_AGENDER = ruleFromBlocks(BLACK, GRAY, WHITE, GREEN, WHITE, GRAY, BLACK);
	public static final SurfaceRules.RuleSource FLAG_ARO = ruleFromBlocks(GREEN, GREEN_TWO, WHITE, GRAY, BLACK);
	public static final SurfaceRules.RuleSource FLAG_GENDERFLUID = ruleFromBlocks(PINK, WHITE, PURPLE, BLACK, BLUE);
	public static final SurfaceRules.RuleSource FLAG_PLURAL = ruleFromBlocks(PURPLE, LIGHT_BLUE, CYAN, BLACK, BLACK, CYAN, LIGHT_BLUE, PURPLE);
	public static final SurfaceRules.RuleSource FLAG_LESBIAN = ruleFromBlocks(RED, ORANGE, ORANGE_TWO, WHITE, PINK, PURPLE_TWO, MAGENTA);
	public static final SurfaceRules.RuleSource FLAG_PAN = ruleFromBlocks(PINK, YELLOW, BLUE);
	public static final SurfaceRules.RuleSource FLAG_DEMIRO = ruleFromBlocks(WHITE, WHITE, GREEN_TWO, GRAY, GRAY);
	public static final SurfaceRules.RuleSource FLAG_PRIDE = ruleFromBlocks(RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE);

	@Override
	public void modifyOverworldRules(SurfaceRuleContext.@NotNull Overworld context) {
		context.materialRules().add(0,
				SurfaceRules.sequence(
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST), FLAG_TRANS),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.SUNFLOWER_PLAINS, Biomes.LUSH_CAVES), FLAG_ACE),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.TAIGA), FLAG_NB),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.DESERT, Biomes.DARK_FOREST), FLAG_BI),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE), FLAG_AGENDER),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.ERODED_BADLANDS), FLAG_ARO),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA), FLAG_GENDERFLUID),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.MEADOW, Biomes.GROVE), FLAG_PLURAL),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS, Biomes.SNOWY_SLOPES), FLAG_LESBIAN),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.DRIPSTONE_CAVES, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.LUKEWARM_OCEAN), FLAG_PAN),
						SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_RIVER), FLAG_DEMIRO),
						FLAG_PRIDE
				)
		);
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
						SurfaceRules.UNDER_FLOOR,
						SurfaceRules.ifTrue(
								SurfaceRules.abovePreliminarySurface(),
								SurfaceRules.ifTrue(
									SurfaceRules.noiseCondition(PRIDE_NOISE_KEY, STRIP_ONE_MIN + sizePerSection*ruleIndex, STRIP_ONE_MIN + sizePerSection*(ruleIndex+1)),
									SurfaceRules.state(blockstates[ruleIndex]))
						)
			);
			ruleSources[ruleIndex+(blockstates.length)] =
					SurfaceRules.ifTrue(
							SurfaceRules.UNDER_FLOOR,
							SurfaceRules.ifTrue(
									SurfaceRules.noiseCondition(PRIDE_NOISE_KEY, STRIP_TWO_MIN + sizePerSection*ruleIndex, STRIP_TWO_MIN + sizePerSection*(ruleIndex+1)),
									SurfaceRules.state(blockstates[ruleIndex]))
					);
		}
		return SurfaceRules.sequence(ruleSources);
	}
}
