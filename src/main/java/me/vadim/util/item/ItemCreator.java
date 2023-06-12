package me.vadim.util.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * This class was mostly generated via a builder class generation IDE plugin which creates setters from fields in the class.
 * Most of the fields were copied from {@link ItemMeta} and {@link ItemStack} implementations.
 *
 * @author vadim
 * @see ItemStack
 * @see ItemMeta
 */
final class ItemCreator implements ItemBuilder {

	private Material                               material;
	private ItemStack                              item;
	private String                                 displayName;
	private String                                 localizedName;
	private List<String>                           lore               = new ArrayList<>();
	private List<ItemFlag>                         flags              = new ArrayList<>();
	private Map<Enchantment, Integer>              enchantments       = new HashMap<>();
	private Multimap<Attribute, AttributeModifier> attributeModifiers = HashMultimap.create();
	private PlayerProfile                          playerProfile;
	private Integer                                customModelData;
	private boolean                                unbreakable        = false;
	private int                                    repairCost         = 0;
	private int                                    damage             = 0;
	private int                                    amount             = 1;

	ItemCreator(final Material material) {
		if (material == null) { throw new NullPointerException("material"); }

		this.item     = null;
		this.material = material;
	}

	ItemCreator(final ItemStack item) {
		if (item == null) { throw new NullPointerException("item"); }

		this.item     = item;
		this.material = null;
	}

	@Override
	public ItemBuilder meta(final ItemMeta meta) {
		if (meta == null) { return this; }

		if(meta.hasDisplayName()) displayName(meta.getDisplayName());
		if(meta.hasLocalizedName()) localizedName(meta.getLocalizedName());
		if(meta.hasLore()) lore(meta.getLore());
		flags(new ArrayList<>(meta.getItemFlags()));
		if(meta.hasEnchants()) enchantments(meta.getEnchants());
		if(meta.hasAttributeModifiers()) attributeModifiers(meta.getAttributeModifiers());
		if (meta.hasCustomModelData()) customModelData(meta.getCustomModelData());
		unbreakable(meta.isUnbreakable());

		if (meta instanceof Repairable && ((Repairable) meta).hasRepairCost()) { repairCost(((Repairable) meta).getRepairCost()); }
		if (meta instanceof SkullMeta && ((SkullMeta) meta).getOwningPlayer() != null) { skullOwner(((SkullMeta) meta).getOwningPlayer().getUniqueId()); }

		if (this.item != null) { this.item.setItemMeta(meta); }

		return this;
	}

	@Override
	public ItemBuilder material(final @NotNull Material material) {
		if (material == null) { throw new NullPointerException("material"); }

		this.material = material;
		return this;
	}

	@Override
	public ItemBuilder item(final @NotNull ItemStack item) {
		if (item == null) { throw new NullPointerException("item"); }

		this.item = item;
		return meta(item.getItemMeta());
	}

	@Override
	public ItemBuilder displayName(final String displayName) {
		this.displayName = displayName;
		return this;
	}

	@Override
	public ItemBuilder localizedName(final String localizedName) {
		this.localizedName = localizedName;
		return this;
	}

	@Override
	public ItemBuilder lore(final List<String> lore) {
		this.lore = lore;
		return this;
	}

	@Override
	public ItemBuilder lore(final String... lore) {
		this.lore = Arrays.asList(lore);
		return this;
	}

	@Override
	public ItemBuilder allFlags() {
		return flags(ItemFlag.values());
	}

	@Override
	public ItemBuilder flags(final List<ItemFlag> flags) {
		this.flags = flags;
		return this;
	}

	@Override
	public ItemBuilder flags(final ItemFlag... flags) {
		this.flags = Arrays.asList(flags);
		return this;
	}

	@Override
	public ItemBuilder flag(final ItemFlag flag) {
		this.flags.add(flag);
		return this;
	}

	@Override
	public ItemBuilder enchantments(final Map<Enchantment, Integer> enchantments) {
		this.enchantments = enchantments;
		return this;
	}

	@Override
	public ItemBuilder enchantment(final Enchantment enchantment, final int level) {
		this.enchantments.put(enchantment, level);
		return this;
	}

	@Override
	public ItemBuilder attributeModifiers(final Multimap<Attribute, AttributeModifier> attributeModifiers) {
//		this.attributeModifiers = attributeModifiers;
		this.attributeModifiers.clear();
		this.attributeModifiers.putAll(attributeModifiers);
		return this;
	}

	@Override
	public ItemBuilder attributeModifiers(final Attribute attribute, final AttributeModifier... attributeModifiers) {
		this.attributeModifiers.get(attribute).addAll(Arrays.asList(attributeModifiers));
		return this;
	}

	@Override
	public ItemBuilder attributeModifier(final Attribute attribute, final AttributeModifier attributeModifier) {
		this.attributeModifiers.get(attribute).add(attributeModifier);
		return this;
	}

	@Override
	public ItemBuilder customModelData(final Integer customModelData) {
		this.customModelData = customModelData;
		return this;
	}

	@Override
	public ItemBuilder unbreakable(final boolean unbreakable) {
		this.unbreakable = unbreakable;
		return this;
	}

	@Override
	public ItemBuilder repairCost(final int repairCost) {
		this.repairCost = repairCost;
		return this;
	}

	@Override
	public ItemBuilder skullOwner(final PlayerProfile playerProfile) {
		this.playerProfile = playerProfile;
		return this;
	}

	@Override
	public ItemBuilder skullOwner(final OfflinePlayer owningPlayer) {
		this.playerProfile = Bukkit.createProfile(owningPlayer.getUniqueId(), owningPlayer.getName());
		return this;
	}

	@Override
	@Deprecated
	public ItemBuilder skullOwner(final UUID uniqueId) {
		this.playerProfile = Bukkit.createProfile(uniqueId);
		return this;
	}

	@Override
	@Deprecated
	public ItemBuilder skullOwner(final String playerName) {
		this.playerProfile = Bukkit.createProfile(playerName);
		return this;
	}

	@Override
	public ItemBuilder damage(final int damage) {
		this.damage = damage;
		return this;
	}

	@Override
	public ItemBuilder amount(final int amount) {
		this.amount = amount;
		return this;
	}

	@Override
	public ItemBuilder editMeta(Consumer<ItemMeta> consumer) {
		ItemStack item = build();
		ItemMeta meta = item.getItemMeta();
		if(meta == null) return this;
		consumer.accept(meta);
		item.setItemMeta(meta);
		return item(item);
	}

	@Override
	@SuppressWarnings("deprecation")
	public ItemStack build() {
		if (this.material == null && this.item == null) throw new NullPointerException("Material or item must be set.");

		final ItemStack item = this.material == null ? this.item : new ItemStack(this.material, this.amount, (short) this.damage);
		final ItemMeta  meta = item.getItemMeta();

		if (meta == null) return item;

		meta.setDisplayName(Text.colorize(this.displayName));
		meta.setLocalizedName(this.localizedName);
		meta.setLore(Text.colorize(this.lore));
		meta.addItemFlags(this.flags.toArray(new ItemFlag[this.flags.size()]));
		meta.setAttributeModifiers(this.attributeModifiers);
		meta.setCustomModelData(this.customModelData);
		meta.setUnbreakable(this.unbreakable);

		if (meta instanceof final Repairable repairable) repairable.setRepairCost(this.repairCost);
		if (meta instanceof final SkullMeta skullMeta) skullMeta.setPlayerProfile(this.playerProfile);

		item.setItemMeta(meta);
		item.addUnsafeEnchantments(this.enchantments);

		return item;
	}
}