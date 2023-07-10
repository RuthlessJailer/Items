package me.vadim.util.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * A utility class for all things {@link ItemStack item}.
 *
 * @author vadim
 */
public interface ItemBuilder {

	/* factory */

	/**
	 * Constructs a new {@link ItemBuilder} with the initial value of the given {@code material}.
	 *
	 * @param material the starting {@link Material}; may be changed via {@link #material(Material)}
	 *
	 * @return a new {@link ItemBuilder} instance which creates a new {@link ItemStack} upon {@link #build()}
	 */
	static ItemBuilder create(@NotNull final Material material) { return new ItemCreator(material); }

	/**
	 * Constructs a new {@link ItemBuilder} with the initial values copied from the given {@code item}.
	 *
	 * @param item the template {@link ItemStack} from which all values are copied; may be updated again via {@link #item(ItemStack)}
	 *
	 * @return a new {@link ItemBuilder} instance which creates a new {@link ItemStack} upon {@link #build()}
	 */
	static ItemBuilder copy(@NotNull final ItemStack item) { return new ItemCreator(item).meta(item.getItemMeta()); }

	/**
	 * Constructs a new {@link ItemBuilder} for the given {@code item}.
	 *
	 * @param item the {@link ItemStack} whose self or meta will be modified within any method
	 *
	 * @return a new {@link ItemBuilder} instance which mutates the given {@code item} and its meta directly, applying the modified meta upon {@link #build()}
	 */
	static ItemBuilder mutate(@NotNull final ItemStack item) { return new ItemMutator(item); }


	/* builder */


	/**
	 * Updates all available values to values from the {@code meta}, including the current {@code item}'s (if both are non-null) via {@link ItemStack#setItemMeta(ItemMeta)}.
	 *
	 * @param meta the {@link ItemMeta} to update from
	 *
	 * @return this
	 */
	ItemBuilder meta(final ItemMeta meta);

	ItemBuilder item(@NotNull final ItemStack item);

	ItemBuilder material(@NotNull final Material material);

	ItemBuilder displayName(final String displayName);

	ItemBuilder localizedName(final String localizedName);

	ItemBuilder lore(final List<String> lore);

	ItemBuilder lore(final String... lore);

	ItemBuilder addLore(final List<String> lore);

	ItemBuilder addLore(final String... lore);

	ItemBuilder allFlags();

	ItemBuilder flags(final List<ItemFlag> flags);

	ItemBuilder flags(final ItemFlag... flags);

	ItemBuilder flag(final ItemFlag flag);

	ItemBuilder enchantments(final Map<Enchantment, Integer> enchantments);

	ItemBuilder enchantment(final Enchantment enchantment, final int level);

	ItemBuilder attributeModifiers(final Multimap<Attribute, AttributeModifier> attributeModifiers);

	ItemBuilder attributeModifiers(final Attribute attribute, final AttributeModifier... attributeModifiers);

	ItemBuilder attributeModifier(final Attribute attribute, final AttributeModifier attributeModifier);

	ItemBuilder customModelData(final Integer customModelData);

	ItemBuilder unbreakable(final boolean unbreakable);

	ItemBuilder repairCost(final int repairCost);

	ItemBuilder skullOwner(final PlayerProfile playerProfile);

	ItemBuilder skullOwner(final OfflinePlayer owningPlayer);

	/**
	 * @deprecated use {@link #skullOwner(OfflinePlayer)} or {@link #skullOwner(PlayerProfile)}
	 */
	@Deprecated
	ItemBuilder skullOwner(final UUID uniqueId);

	/**
	 * @deprecated see {@link #skullOwner(UUID)}
	 */
	@Deprecated
	ItemBuilder skullOwner(final String playerName);

	ItemBuilder damage(final int damage);

	ItemBuilder amount(final int amount);

	ItemBuilder editMeta(final Consumer<ItemMeta> consumer);

	/**
	 * @return an {@link ItemStack} reflecting all the values in this instance
	 */
	ItemStack build();

}
