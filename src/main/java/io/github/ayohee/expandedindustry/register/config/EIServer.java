package io.github.ayohee.expandedindustry.register.config;

import io.github.ayohee.expandedindustry.register.EIStress;
import net.createmod.catnip.config.ConfigBase;

public class EIServer extends ConfigBase {
    public final EIStress stress = nested(0, EIStress::new, "Stress values and capacities of Create: Expanded Industry's kinetic mechanisms");

    public final ConfigFloat PRESSURISED_TANK_CAPACITY_MULTIPLIER = f(
            1.5f, 1.0f, 32.0f,
            "pressurisedTankCapacityMultiplier",
            "The capacity of pressurised fluid tanks relative to the capacity of normal fluid tanks, expressed as a multiplier"
    );

    @Override
    public String getName() {
        return "server";
    }
}
