package ca.tnoah.quicktpll.events;

import ca.tnoah.quicktpll.commands.TpllCommand;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {

    private static KeyBinding tpll;
    private TpllCommand tpllCommand;

    public KeyHandler() {
        initCommands();
        initBindings();
        initHandler();
    }

    private void initCommands() {
        tpllCommand = new TpllCommand();
    }

    private void initBindings() {

        tpll = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.quicktpll.tpll",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT,
                "category.quicktpll.commands"
        ));

    }

    private void initHandler() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (tpll.wasPressed()) {
                tpllCommand.run();
            }
        });
    }

}
