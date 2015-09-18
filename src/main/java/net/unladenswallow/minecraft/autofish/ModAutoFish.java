package net.unladenswallow.minecraft.autofish;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModAutoFish.MODID, name = ModAutoFish.MODNAME,
version = ModAutoFish.VERSION, useMetadata = true,
guiFactory = "net.unladenswallow.minecraft.autofish.AutoFishGuiFactory")
public class ModAutoFish {
	public static final String MODID = "mod_autofish";
	public static final String MODNAME = "AutoFish Forge Mod";
	public static final String VERSION = "1.8-1.0";
	
	public static Configuration configFile;
	public static boolean config_autofish_enable;
	public static final boolean CONFIG_DEFAULT_AUTOFISH_ENABLE = true;
	public static boolean config_autofish_multirod;
	public static final boolean CONFIG_DEFAULT_AUTOFISH_MULTIROD = false;
	public static boolean config_autofish_preventBreak;
	public static final boolean CONFIG_DEFAULT_AUTOFISH_PREVENTBREAK = false;
	
	@SidedProxy(clientSide="net.unladenswallow.minecraft.autofish.ClientProxy", serverSide="net.unladenswallow.minecraft.autofish.ServerProxy")
	public static CommonProxy proxy;
	
	public static AutoFishEventHandler eventHandler = new AutoFishEventHandler();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent preInitEvent) {
		ModAutoFish.proxy.preInit(preInitEvent);
		configFile = new Configuration(preInitEvent.getSuggestedConfigurationFile());
		syncConfig();
	}
	
	@EventHandler
	public void init (FMLInitializationEvent event) {
		ModAutoFish.proxy.init(event);
		AutoFishLogger.info("Initializing " + ModAutoFish.MODNAME);
	}

	public static void syncConfig() {
		config_autofish_enable = configFile.getBoolean("Enable AutoFish", Configuration.CATEGORY_GENERAL, CONFIG_DEFAULT_AUTOFISH_ENABLE, "Automatically reel in and re-cast when a fish nibbles the hook.");
		config_autofish_multirod = configFile.getBoolean("Enable MultiRod", Configuration.CATEGORY_GENERAL, CONFIG_DEFAULT_AUTOFISH_MULTIROD, "Automatically switch to a new fishing rod when the current rod breaks, if one is available in the hotbar.");
		config_autofish_preventBreak = configFile.getBoolean("Enable Break Protection", Configuration.CATEGORY_GENERAL, CONFIG_DEFAULT_AUTOFISH_PREVENTBREAK, "Stop fishing or switch to a new rod before the current rod breaks.");
		
		if (configFile.hasChanged()) {
			configFile.save();
		}
	}

}
