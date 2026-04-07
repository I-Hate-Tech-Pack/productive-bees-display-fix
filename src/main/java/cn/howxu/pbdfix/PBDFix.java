package cn.howxu.pbdfix;

import net.neoforged.api.distmarker.Dist;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(value = PBDFix.MODID,dist = Dist.CLIENT)
public class PBDFix {
    public static final String MODID = "pbdfix";
    public static final Logger LOGGER = LogUtils.getLogger();
    public PBDFix(IEventBus modEventBus, ModContainer modContainer) {

    }

}
