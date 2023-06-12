package me.vadim.util.item

import com.destroystokyo.paper.profile.PlayerProfile
import com.google.common.collect.Multimap
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.*
import kotlin.reflect.KProperty

/**
 * Delegate to make [ItemBuilderKt] cleaner.
 *
 * @author vadim
 */
internal class Unreadable(private val name: String = "", private val list: String = "", private val int: String = "") {

	operator fun getValue(thisRef: ItemBuilderKt, property: KProperty<*>): Nothing = throw UnsupportedOperationException("get")

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, material: Material) {
		thisRef material material
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, item: ItemStack) {
		thisRef item item
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, meta: ItemMeta) {
		thisRef meta meta
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, name: String) {
		when (this.name) {
			"display"   -> thisRef displayName name
			"localized" -> thisRef localizedName name
		}
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, list: List<*>) {
		when (this.list) {
			"lore"  -> thisRef lore list as List<String>
			"flags" -> thisRef flags list as List<ItemFlag>
		}
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, map: Map<*, *>) {
		thisRef enchantments map as Map<Enchantment, Int>
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, map: Multimap<*, *>) {
		thisRef attributeModifiers map as Multimap<Attribute, AttributeModifier>
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, playerProfile: PlayerProfile) {
		thisRef skullOwner playerProfile
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, owningPlayer: OfflinePlayer) {
		thisRef skullOwner owningPlayer
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, uniqueId: UUID) {
		thisRef.owningPlayer = Bukkit.getOfflinePlayer(uniqueId)
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, int: Int) {
		when (this.int) {
			"customModelData" -> thisRef customModelData int
			"repairCost"      -> thisRef repairCost int
			"damage"          -> thisRef damage int
			"amount"          -> thisRef amount int
		}
	}

	operator fun setValue(thisRef: ItemBuilderKt, property: KProperty<*>, unbreakable: Boolean) {
		thisRef unbreakable unbreakable
	}

}