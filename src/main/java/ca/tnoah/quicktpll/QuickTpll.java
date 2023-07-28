package ca.tnoah.quicktpll;

import ca.tnoah.quicktpll.events.KeyHandler;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickTpll implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("qucktpll");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		new KeyHandler();
	}
}