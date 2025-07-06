package io.github.ayohee.expandedindustry.register;

import com.simibubi.create.AllTags;
import com.simibubi.create.AllTags.AllItemTags;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static com.simibubi.create.AllTags.commonItemTag;
import static io.github.ayohee.expandedindustry.CreateExpandedIndustry.REGISTRATE;

public class EIItems {
    static {
        REGISTRATE.setCreativeTab(EICreativeTabs.MAIN_TAB);
    }

    public static final ItemEntry<Item> CRUSHED_RAW_COBALT = REGISTRATE.item("crushed_raw_cobalt", Item::new)
            .tag(AllItemTags.CRUSHED_RAW_MATERIALS.tag)
            .register();

    public static final ItemEntry<Item> COBALT_INGOT = REGISTRATE.item("cobalt_ingot", Item::new)
            .tag(commonItemTag("ingots/cobalt"))
            .register();

    public static final ItemEntry<Item> COBALT_SHEET = REGISTRATE.item("cobalt_sheet", Item::new)
            .tag(AllItemTags.PLATES.tag, commonItemTag("plates/cobalt"))
            .register();


    public static void register() { }
}
