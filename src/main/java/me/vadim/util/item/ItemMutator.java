package me.vadim.util.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * This class directly modifies {@link ItemMeta} and {@link ItemStack}.
 * The {@link #build()} method still needs to be called to apply the {@code meta} to the {@code item}.
 *
 * @author vadim
 * @see ItemMeta
 * @see ItemStack
 */
@SuppressWarnings("Deprecation,ConstantValue")
final class ItemMutator implements ItemBuilder {

	private ItemStack item;
	private ItemMeta meta;

	ItemMutator(final ItemStack item) {
		if (item == null) { throw new NullPointerException("item"); }

		this.item = item;
		this.meta = item.getItemMeta();
	}

	@Override
	public ItemBuilder meta(final ItemMeta meta) {
		this.item.setItemMeta(meta);
		this.meta = meta;
		return this;
	}

	@Override
	public ItemBuilder item(final @NotNull ItemStack item) {
		if (item == null) { throw new NullPointerException("item"); }

		ItemStack old = this.item;

		this.item = item;

		return meta(old.getItemMeta());
	}

	@Override
	public ItemBuilder material(final @NotNull Material material) {
		if (material == null) { throw new NullPointerException("material"); }

		item.setType(material);

		return this;
	}

	@Override
	public ItemBuilder displayName(final String displayName) {
		if (meta != null) { meta.setDisplayName(Text.colorize(Text.CHAT_COLOR_CLEAR + displayName)); }
		return this;
	}

	@Override
	public ItemBuilder localizedName(final String localizedName) {
		if (meta != null) { meta.setLocalizedName(localizedName); }
		return this;
	}

	@Override
	public ItemBuilder lore(final List<String> lore) {
		if (meta != null) { meta.setLore(Text.colorize(lore.stream().map(it -> Text.CHAT_COLOR_CLEAR + it).toList())); }
		return this;
	}

	@Override
	public ItemBuilder lore(final String... lore) {
		return lore(Arrays.asList(lore));
	}

	@Override
	public ItemBuilder addLore(final List<String> lore) {
		if (meta != null) {
			List<String> append = meta.getLore();
			if(append == null)
				append = new ArrayList<>();
			append.addAll(Text.colorize(lore.stream().map(it -> Text.CHAT_COLOR_CLEAR + it).toList()));
			meta.setLore(append);
		}
		return this;
	}

	@Override
	public ItemBuilder addLore(final String... lore) {
		return addLore(Arrays.asList(lore));
	}

	@Override
	public ItemBuilder allFlags() {
		if (meta != null) {
			meta.addItemFlags(ItemFlag.values());
		}
		return this;
	}

	@Override
	public ItemBuilder flags(final List<ItemFlag> flags) {
		if (meta != null) {
			meta.removeItemFlags(ItemFlag.values());
			meta.addItemFlags(flags.toArray(ItemFlag[]::new));
		}
		return this;
	}

	@Override
	public ItemBuilder flags(final ItemFlag... flags) {
		if (meta != null) {
			meta.removeItemFlags(ItemFlag.values());
			meta.addItemFlags(flags);
		}
		return this;
	}

	@Override
	public ItemBuilder flag(final ItemFlag flag) {
		if (meta != null) { meta.addItemFlags(flag); }
		return this;
	}

	@Override
	public ItemBuilder enchantments(final Map<Enchantment, Integer> enchantments) {
		item.addUnsafeEnchantments(enchantments);
		return this;
	}

	@Override
	public ItemBuilder enchantment(final Enchantment enchantment, final int level) {
		item.addUnsafeEnchantment(enchantment, level);
		return this;
	}

	@Override
	public ItemBuilder attributeModifiers(final Multimap<Attribute, AttributeModifier> attributeModifiers) {
		if (meta != null) { meta.setAttributeModifiers(attributeModifiers); }
		return this;
	}

	@Override
	public ItemBuilder attributeModifiers(final Attribute attribute, final AttributeModifier... attributeModifiers) {
		if (meta != null) { if (meta.hasAttributeModifiers()) { meta.getAttributeModifiers().get(attribute).addAll(Arrays.asList(attributeModifiers)); } }
		return this;
	}

	@Override
	public ItemBuilder attributeModifier(final Attribute attribute, final AttributeModifier attributeModifier) {
		if (meta != null) { if (meta.hasAttributeModifiers()) { meta.getAttributeModifiers().put(attribute, attributeModifier); } }
		return this;
	}

	@Override
	public ItemBuilder customModelData(final Integer customModelData) {
		if (meta != null) { meta.setCustomModelData(customModelData); }
		return this;
	}

	@Override
	public ItemBuilder unbreakable(final boolean unbreakable) {
		if (meta != null) { meta.setUnbreakable(unbreakable); }
		return this;
	}

	@Override
	public ItemBuilder repairCost(final int repairCost) {
		if (meta != null && meta instanceof final Repairable repairable) { repairable.setRepairCost(repairCost); }
		return this;
	}

	@Override
	public ItemBuilder skullOwner(final PlayerProfile playerProfile) {
		if (meta != null && meta instanceof final SkullMeta skullMeta) { skullMeta.setPlayerProfile(playerProfile); }
		return this;
	}

	@Override
	public ItemBuilder skullOwner(final OfflinePlayer owningPlayer) {
		if (meta != null && meta instanceof final SkullMeta skullMeta) { skullMeta.setOwningPlayer(owningPlayer); }
		return this;
	}

	@Override
	public ItemBuilder skullOwner(final UUID uniqueId) {
		return skullOwner(Bukkit.getOfflinePlayer(uniqueId));
	}

	@Override
	public ItemBuilder skullOwner(final String playerName) {
		if (meta != null && meta instanceof final SkullMeta skullMeta) { skullMeta.setOwner(playerName); }
		return this;
	}

	@Override
	public ItemBuilder damage(final int damage) {
		if (meta != null && meta instanceof final Damageable damageable) { damageable.setDamage(damage); }
		return this;
	}

	@Override
	public ItemBuilder amount(final int amount) {
		item.setAmount(amount);
		return this;
	}

	@Override
	public ItemBuilder editMeta(Consumer<ItemMeta> consumer) {
		item.editMeta(consumer);
		return this;
	}

	@Override
	public ItemStack build() {
		this.item.setItemMeta(meta);
		return this.item;
	}

}