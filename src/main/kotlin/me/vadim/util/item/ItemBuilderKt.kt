package me.vadim.util.item

import com.destroystokyo.paper.profile.PlayerProfile
import com.google.common.collect.Multimap
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.*
import java.util.function.Consumer

/**
 * Kotlin DSL wrapper for [ItemBuilder]. This class simply encapsulates the superinterface, providing syntactic sugar for Kotlin, whilst retaining a clean Java implementation.
 *
 * @author vadim
 */
class ItemBuilderKt(material: Material? = null, item: ItemStack? = null, mutate: Boolean = false) : ItemBuilder {

	init {
		if (material == null && item == null) throw IllegalArgumentException("Either material or item must be supplied.")
	}

	internal val builder =
		if (material == null && item != null)
			if (mutate)
				ItemBuilder.mutate(item)
			else
				ItemBuilder.copy(item)
		else if (material != null && item == null)
			ItemBuilder.create(material)
		else
			throw IllegalArgumentException("Either material or item must be supplied.")

	var material: Material by Unreadable()

	var item: ItemStack by Unreadable()

	var meta: ItemMeta by Unreadable()

	var displayName: String by Unreadable(name = "display")

	var localizedName: String by Unreadable(name = "localized")

	var lore: List<String> by Unreadable(list = "lore")

	var flags: List<ItemFlag> by Unreadable(list = "flags")

	var enchantments: Map<Enchantment, Int> by Unreadable()

	var attributeModifiers: Multimap<Attribute, AttributeModifier> by Unreadable()

	var playerProfile: PlayerProfile by Unreadable()

	var owningPlayer: OfflinePlayer by Unreadable()

	@Deprecated("use playerProfile or owningPlayer", ReplaceWith("owningPlayer = Bukkit.getOfflinePlayer(uuid)"))
	var skullOwner: UUID by Unreadable()

	var customModelData: Int by Unreadable(int = "customModelData")

	var unbreakable: Boolean by Unreadable()

	var repairCost: Int by Unreadable(int = "repairCost")

	var damage: Int by Unreadable(int = "damage")

	var amount: Int by Unreadable(int = "amount")

	override infix fun meta(meta: ItemMeta): ItemBuilderKt {
		builder.meta(meta)
		return this
	}

	override infix fun item(item: ItemStack): ItemBuilderKt {
		builder.item(item)
		return this
	}

	override infix fun material(material: Material): ItemBuilderKt {
		builder.material(material)
		return this
	}

	override infix fun displayName(displayName: String): ItemBuilderKt {
		builder.displayName(displayName)
		return this
	}

	override infix fun localizedName(localizedName: String): ItemBuilderKt {
		builder.localizedName(localizedName)
		return this
	}

	override infix fun lore(lore: List<String>): ItemBuilderKt {
		builder.lore(lore)
		return this
	}

	override fun lore(vararg lore: String): ItemBuilderKt {
		builder.lore(*lore)
		return this
	}

	override fun allFlags(): ItemBuilderKt {
		builder.allFlags()
		return this
	}

	override infix fun flags(flags: List<ItemFlag>): ItemBuilderKt {
		builder.flags(flags)
		return this
	}

	override fun flags(vararg flags: ItemFlag): ItemBuilderKt {
		builder.flags(*flags)
		return this
	}

	override infix fun flag(flag: ItemFlag): ItemBuilderKt {
		builder.flag(flag)
		return this
	}

	override infix fun enchantments(enchantments: Map<Enchantment, Int>): ItemBuilderKt {
		builder.enchantments(enchantments)
		return this
	}

	override fun enchantment(enchantment: Enchantment, level: Int): ItemBuilderKt {
		builder.enchantment(enchantment, level)
		return this
	}

	override infix fun attributeModifiers(attributeModifiers: Multimap<Attribute, AttributeModifier>): ItemBuilderKt {
		builder.attributeModifiers(attributeModifiers)
		return this
	}

	override fun attributeModifiers(attribute: Attribute, vararg attributeModifiers: AttributeModifier): ItemBuilderKt {
		builder.attributeModifiers(attribute, *attributeModifiers)
		return this
	}

	override fun attributeModifier(attribute: Attribute, attributeModifier: AttributeModifier): ItemBuilderKt {
		builder.attributeModifier(attribute, attributeModifier)
		return this
	}

	override infix fun customModelData(customModelData: Int): ItemBuilderKt {
		builder.customModelData(customModelData)
		return this
	}

	override infix fun unbreakable(unbreakable: Boolean): ItemBuilderKt {
		builder.unbreakable(unbreakable)
		return this
	}

	override infix fun repairCost(repairCost: Int): ItemBuilderKt {
		builder.repairCost(repairCost)
		return this
	}

	override infix fun skullOwner(playerProfile: PlayerProfile): ItemBuilderKt {
		builder.skullOwner(playerProfile)
		return this
	}

	override infix fun skullOwner(owningPlayer: OfflinePlayer): ItemBuilderKt {
		builder.skullOwner(owningPlayer)
		return this
	}

	@Deprecated("use owningPlayer: OfflinePlayer, or playerProfile: PlayerProfile", ReplaceWith("owningPlayer = Bukkit.getOfflinePlayer(uuid)"))
	override infix fun skullOwner(uniqueId: UUID): ItemBuilderKt {
		builder.skullOwner(uniqueId)
		return this
	}

	@Deprecated("use skullOwner: UUID", ReplaceWith("skullOwner = Bukkit.getOfflinePlayer(name).getUniqueId()"))
	override infix fun skullOwner(playerName: String): ItemBuilderKt {
		builder.skullOwner(playerName)
		return this
	}

	override infix fun damage(damage: Int): ItemBuilderKt {
		builder.damage(damage)
		return this
	}

	override infix fun amount(amount: Int): ItemBuilderKt {
		builder.amount(amount)
		return this
	}

	override fun editMeta(consumer: Consumer<ItemMeta>): ItemBuilder {
		builder.editMeta(consumer)
		return this
	}

	override fun build(): ItemStack = builder.build()

	operator fun invoke(): ItemStack = build()

}

inline fun createItem(material: Material, builder: ItemBuilderKt.() -> Unit) = ItemBuilderKt(material = material).apply(builder).build()
inline fun copyItem(item: ItemStack, builder: ItemBuilderKt.() -> Unit) = ItemBuilderKt(item = item).apply(builder).build()
inline fun mutateItem(item: ItemStack, builder: ItemBuilderKt.() -> Unit) = ItemBuilderKt(item = item, mutate = true).apply(builder).build()
fun createItem(material: Material) = ItemBuilderKt(material = material)
fun copyItem(item: ItemStack) = ItemBuilderKt(item = item)
fun mutateItem(item: ItemStack) = ItemBuilderKt(item = item, mutate = true)