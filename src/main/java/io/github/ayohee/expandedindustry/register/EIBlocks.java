package io.github.ayohee.expandedindustry.register;

import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.ayohee.expandedindustry.ErythriteBlock;

import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIBlocks {
    static {
        REGISTRATE.setCreativeTab(EICreativeTabs.MAIN_TAB);
    }

    public static final BlockEntry<ErythriteBlock> ERYTHRITE_BLOCK = REGISTRATE.block("erythrite", ErythriteBlock::new)
            .simpleItem()
            .register();

    public static void register() { }
}
