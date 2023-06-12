package me.vadim.util.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vadim
 */
public final class Text {

	public static String flatten(Component component) { return LegacyComponentSerializer.legacySection().serialize(component); }
	public static String plain(Component component) { return PlainTextComponentSerializer.plainText().serialize(component); }

	public static String colorize(@Nullable final String text) { return text == null ? null : ChatColor.translateAlternateColorCodes('&', text); }
	public static List<String> colorize(@Nullable final List<String> texts) { return texts == null ? null : texts.stream().map(Text::colorize).collect(Collectors.toList()); }

}
