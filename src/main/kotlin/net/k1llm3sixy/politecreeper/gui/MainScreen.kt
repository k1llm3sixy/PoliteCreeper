package net.k1llm3sixy.politecreeper.gui

import net.k1llm3sixy.politecreeper.manager.ServerManager
import net.minecraft.client.gui.components.ImageWidget
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.layouts.FrameLayout
import net.minecraft.client.gui.layouts.LinearLayout
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier

class MainScreen : Screen(Component.empty())
{
    companion object
    {
        private val choices = listOf(
            { ServerManager.removeCreeper() },
            { ServerManager.explodeCreeper() })

        private val CREEPER_IMG = Identifier.fromNamespaceAndPath(
            "politecreeper",
            "textures/gui/creeper.png"
        )
    }

    override fun init()
    {
        val mainLayout = LinearLayout.horizontal()
        mainLayout.defaultCellSetting().alignVerticallyMiddle().padding(4)

        val leftLayout = LinearLayout.vertical()
        leftLayout.defaultCellSetting().alignHorizontallyCenter().padding(4)

        val titleWidget = StringWidget(
            Component.translatable("politecreeper.title"),
            font
        )

        val creeperWidget = ImageWidget.texture(
            128,
            128,
            CREEPER_IMG,
            128,
            128
        )

        leftLayout.addChild(titleWidget)
        leftLayout.addChild(creeperWidget)

        val subLayout = LinearLayout.vertical()
        subLayout.defaultCellSetting().alignHorizontallyCenter().padding(4)

        val allowBtn = CustomButton(
            Component.translatable("politecreeper.allow"),
            Component.translatable("politecreeper.allow_tooltip")
        ) {
            onClose()
            ServerManager.explodeCreeper()
        }

        val denyBtn = CustomButton(
            Component.translatable("politecreeper.deny"),
            Component.translatable("politecreeper.deny_tooltip"),
        ) {
            onClose()
            ServerManager.removeCreeper()
        }

        val randomBtn = CustomButton(
            Component.translatable("politecreeper.random"),
            Component.translatable("politecreeper.random_tooltip")
        ) {
            onClose()
            choices.random()()
        }

        subLayout.addChild(allowBtn)
        subLayout.addChild(denyBtn)
        subLayout.addChild(randomBtn)

        mainLayout.addChild(leftLayout)
        mainLayout.addChild(subLayout)

        mainLayout.arrangeElements()
        FrameLayout.alignInRectangle(
            mainLayout,
            0,
            0,
            width,
            height,
            0.5F,
            0.5F
        )
        mainLayout.visitWidgets(::addRenderableWidget)
    }
}