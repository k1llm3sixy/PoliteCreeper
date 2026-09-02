package net.k1llm3sixy.politecreeper.event

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.monster.Creeper

sealed interface ServerState
{
    object Empty : ServerState
    data class Active(
        val swell: Boolean,
        val creeper: Creeper,
        val player: ServerPlayer,
        val level: ServerLevel,
    ) : ServerState
}

object ServerEvent : IEvent
{
    var DATA: ServerState = ServerState.Empty
        private set

    override fun register()
    {
        ServerTickEvents.END_SERVER_TICK.register {
            if (!it.isSingleplayer || it.isDedicatedServer || it.isPublished) return@register

            val player = it.playerList.players.first()
            val level = player.level()

            val creeper = level.getEntitiesOfClass(
                Creeper::class.java,
                player.boundingBox.inflate(
                    5.0,
                    5.0,
                    5.0
                )
            ).getOrNull(0)

            if (creeper == null)
            {
                DATA = ServerState.Empty
                return@register
            }

            val swell = creeper.swellDir > 0
            DATA = ServerState.Active(
                swell,
                creeper,
                player,
                level
            )
        }
    }
}