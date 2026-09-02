package net.k1llm3sixy.politecreeper.manager

import net.k1llm3sixy.politecreeper.event.ServerEvent.DATA
import net.k1llm3sixy.politecreeper.event.ServerState
import net.k1llm3sixy.politecreeper.mixin.CreeperAccessor
import net.minecraft.core.component.DataComponents
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.network.chat.Component
import net.minecraft.server.network.Filterable
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.WrittenBookContent

object ServerManager
{

    fun explodeCreeper()
    {
        when (val state = DATA)
        {
            is ServerState.Active ->
            {
                (state.creeper as CreeperAccessor).`politecreeper$setSwell`(30)
                state.player.sendSystemMessage(Component.translatable("politecreeper.sorry"))
            }

            is ServerState.Empty  ->
            {
            }
        }
    }

    fun removeCreeper()
    {
        when (val state = DATA)
        {
            is ServerState.Active ->
            {
                state.creeper.remove(Entity.RemovalReason.DISCARDED)
                spawnParticles(
                    state,
                    ParticleTypes.HEART
                )
                dropBook(state)
            }

            is ServerState.Empty  ->
            {
            }
        }
    }

    private fun spawnParticles(state: ServerState.Active, particle: SimpleParticleType)
    {
        state.level.sendParticles(
            particle,
            state.creeper.x,
            state.creeper.y,
            state.creeper.z,
            15,
            1.0,
            3.0,
            1.0,
            0.0
        )
    }

    private fun dropBook(state: ServerState.Active)
    {
        val pos = state.creeper.position()
        val book = createBook()

        val itemEntity = ItemEntity(
            state.level,
            pos.x + 0.5,
            pos.y,
            pos.z + 0.5,
            book
        )

        state.level.addFreshEntity(itemEntity)
    }

    private fun createBook(): ItemStack
    {
        val book = ItemStack(Items.WRITTEN_BOOK)
        val content = WrittenBookContent(
            Filterable.passThrough("..."),
            Component.translatable("politecreeper.book_author").string,
            0,
            listOf(Filterable.passThrough(Component.translatable("politecreeper.book_text"))),
            false
        )

        book.set(
            DataComponents.WRITTEN_BOOK_CONTENT,
            content
        )

        return book
    }
}