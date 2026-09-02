package net.k1llm3sixy.politecreeper

import net.fabricmc.api.ClientModInitializer
import net.k1llm3sixy.politecreeper.event.ClientEvent
import net.k1llm3sixy.politecreeper.event.ServerEvent

class PoliteCreeper : ClientModInitializer
{
    override fun onInitializeClient()
    {
        ServerEvent.register()
        ClientEvent.register()
    }
}
