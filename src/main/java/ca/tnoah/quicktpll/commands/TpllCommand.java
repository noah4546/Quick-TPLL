package ca.tnoah.quicktpll.commands;

import ca.tnoah.quicktpll.QuickTpll;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.regex.Pattern;

public class TpllCommand {

    private final Pattern googleMaps = Pattern.compile("((-?|\\+?)?\\d+(\\.\\d+)?),\\s*((-?|\\+?)?\\d+(\\.\\d+)?)$");
    private final Pattern googleEarth = Pattern.compile("(\\d+°\\d+'\\d+\\.\\d+\"\\s?[NESW])\\s+(\\d+°\\d+'\\d+\\.\\d+\"\\s?[NESW])$");

    public TpllCommand() {
    }

    public void run() {
        assert MinecraftClient.getInstance().player != null;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;;

        //MinecraftClient.getInstance().player.networkHandler.sendChatCommand("hello world!");

        String clip = MinecraftClient.getInstance().keyboard.getClipboard();

        if (googleMaps.matcher(clip).find())
            sendTPLLCommand(player, clip);
        else if (googleEarth.matcher(clip).find())
            sendTPLLCommand(player, convertToGoogleMaps(clip));
        else {
            QuickTpll.LOGGER.info("Clipboard was not a valid lat,lng");
            player.sendMessage(Text.translatable("error.quicktpll.invalid_lat_lng"));
        }
    }

    private void sendTPLLCommand(ClientPlayerEntity player, String coords) {
        player.networkHandler.sendChatCommand("/tpll " + coords);
    }

    private String convertToGoogleMaps(String coords) {
        String[] split = coords.split("[NESW]");
        String lat = convertDMStoLatLng(split[0]);
        String lng = convertDMStoLatLng(split[1]);

        if (coords.contains("S"))
            lat = "-" + lat;

        if (coords.contains("W"))
            lng = "-" + lng;

        String latLng = lat + ", " + lng;
        QuickTpll.LOGGER.info(latLng);

        return latLng;
    }

    private String convertDMStoLatLng(String coord) {
        String[] split = coord.split("[°'.\"]");
        double degree = Double.parseDouble(split[0]);
        double minute = Double.parseDouble(split[1]);
        double second = Double.parseDouble(split[2]);
        double ms = Double.parseDouble(split[3]);

        degree += (minute / 60.0) + (second / 3600.0) + (ms / 60000.0);

        return String.valueOf(degree);
    }

}
