# CHNoName
Minecraft 1.9.4 CommandHelper Extension CHNoName

Latest release: [download v1.1.0](https://github.com/SilvLightWolf/CHNoName/releases/tag/1.1.0)

##FUNCTIONS :

+ **nn_particle(EnumParticle, x, y, z, x-offset, y-offset, z-offset, speed, particles)** - Create Particle
 EnumParticles : BARRIER, BLOCK_CRACK, BLOCK_DUST, CLOUD, CRIT, CRIT_MAGIC, DRAGON_BREATH,
               DRIP_LAVA, DRIP_WATER, ENCHANTMENT_TABLE, END_ROD, EXPLOSION_HUGE, EXPLOSION_LARGE, EXPLOSION_NORMAL,
               FLAME, FOOTSTEP, HEART, ITEM_CRACK, ITEM_TAKE, LAVA, MOB_APPEARANCE, NOTE, PORTAL, REDSTONE, SLIME,
               SMOKE_LARGE, SMOKE_NORMAL, SNOW_SHOVEL, SNOWBALL, SPELL, SPELL_INSTANCE, SPELL_MOB, SPELL_MOB_AMBIENT,
               SPELL_WITCH, SUSPENDED, SUSPENDED_DEPTH, SWEEP_ATTACK, TOWN_AURA, VILLAGER_ANGRY, VILLAGER_HAPPY,
               WATER_BUBBLE, WATER_DROP, WATER_SPLASH, WATER_WAKE

+ **nn_actmsg(player, msg)** - Send Actionbar Msg

+ **nn_attr(UUID, set/get, AttributeName, Value)** - set Player Attribute
Attributes: GENERIC_ARMOR, HORSE_JUMP_STRENGTH, GENERIC_FOLLOW_RANGE, GENERIC_KNOCKBACK_RESISTANCE,
            GENERIC_LUCK, GENERIC_MAX_HEALTH, GENERIC_MOVEMENT_SPEED, GENERIC_ATTACK_SPEED, ZOMBIE_SPAWN_REINFORCEMENTS
            
+ **nn_get_furnace(LocationArray, World)** - get Furnace Info.
 
+ **nn_set_Furnace(LocationArray, World, Key, Value)** - set Furnace Info. key : burntime, cooktime, fuel, result, smelting
 
+ **nn_get_brew(LocationArray, World)** - get Brewing Stand Info.
 
+ **nn_set_brew(LocationArray, World, Key, Value)** - set Brewing Stand Info. key : keys : brewingtime, fuellevel, fuel, ingredient
 
+ **nn_anvil(player, callback[, Item])** - same to CHExodius's user_input.
 
+ **nn_hopper(player, title[, inventory])** - Hopper GUI. inventory - array(slot, slot, slot, slot, slot) [Empty slot is null].

+ **nn_ping(Type, Key, Value)** - ServerPing. Type : current, max, version. key : set, get. value : integer.
 
##EVENTS
    
###furnace_burn
  
* Event data:
*   burntime - Gets the burn time for this fuel (ticks)
*   fuel - Gets the fuel Item for this event


###furnace_extract

* Event data:
*    player - Get the player that triggered the event
*    material - Get the Material of the item being retrieved
*    amount - Get the item count being retrieved
    
    
    
###furnace_smelt

* Event data:
*    result - Gets the resultant ItemStack for this event
*    source - Gets the smelted ItemStack for this event
    
