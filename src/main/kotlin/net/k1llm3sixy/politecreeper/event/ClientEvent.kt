package net.k1llm3sixy.politecreeper.event

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.k1llm3sixy.politecreeper.event.ServerEvent.DATA
import net.k1llm3sixy.politecreeper.gui.MainScreen
import java.util.*

object ClientEvent : IEvent
{
    private var creeperUUID: UUID? = null

    override fun register()
    {
        ClientTickEvents.END_CLIENT_TICK.register {
            when (val state = DATA)
            {
                is ServerState.Active ->
                {
                    if (!state.swell)
                    {
                        creeperUUID = null
                        return@register
                    }

                    if (state.creeper.uuid == creeperUUID) return@register

                    if (it.gui.screen() !is MainScreen)
                    {
                        creeperUUID = state.creeper.uuid
                        it.gui.setScreen(MainScreen())
                    }
                }

                is ServerState.Empty  ->
                {
                }
            }
        }
    }
}