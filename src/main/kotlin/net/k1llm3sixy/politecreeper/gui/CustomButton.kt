package net.k1llm3sixy.politecreeper.gui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component

class CustomButton(
    message: Component,
    tooltip: Component,
    private val onPress: () -> Unit,
) : AbstractWidget(
    0,
    0,
    150,
    20,
    message
)
{
    init
    {
        setTooltip(Tooltip.create(tooltip))
    }

    override fun extractWidgetRenderState(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        a: Float,
    )
    {
        val font = Minecraft.getInstance().font

        val bgColor = if (isHovered) 0xCC2A2D32.toInt() else 0xCC1A1C20.toInt()
        val borderColor = if (isHovered) 0xFF5A606C.toInt() else 0xFF3D414B.toInt()
        val textColor = if (isHovered) 0xFFFFFFFF.toInt() else 0xFFD0D3DC.toInt()

        graphics.fill(
            x,
            y,
            x + width,
            y + height,
            bgColor
        )
        graphics.outline(
            x,
            y,
            width,
            height,
            borderColor
        )
        graphics.centeredText(
            font,
            message,
            x + width / 2,
            y + (height - 8) / 2,
            textColor
        )
    }

    override fun onClick(event: MouseButtonEvent, doubleClick: Boolean)
    {
        onPress()
    }

    override fun updateWidgetNarration(output: NarrationElementOutput)
    {
        defaultButtonNarrationText(output)
    }
}