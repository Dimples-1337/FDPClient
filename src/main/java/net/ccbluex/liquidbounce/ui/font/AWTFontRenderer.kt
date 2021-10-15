/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/Project-EZ4H/FDPClient/
 */
package net.ccbluex.liquidbounce.ui.font

import net.ccbluex.liquidbounce.utils.render.RenderUtils
import org.lwjgl.opengl.GL11
import java.awt.Canvas
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

/**
 * 矢量字体渲染器
 * @author liulihaocai
 */
class AWTFontRenderer(val font: Font) {

//    private val chars=HashMap<String, FontChar>()

    private val fontMetrics: FontMetrics = Canvas().getFontMetrics(font)
    private val fontHeight: Int = if (fontMetrics.height <= 0){ font.size }else{ fontMetrics.height + 3 }

    val height: Int
        get() = (fontHeight - 8) / 2

    /**
     * Allows you to draw a string with the target font
     *
     * @param text  to render
     * @param x     location for target position
     * @param y     location for target position
     * @param color of the text
     */
    fun drawString(text: String, x: Double, y: Double, color: Int) {
        val scale = 0.25

        GL11.glPushMatrix()
        GL11.glScaled(scale, scale, scale)
        GL11.glTranslated(x * 2F, y * 2.0 - 2.0, 0.0)

        val red = (color shr 16 and 0xff) / 255F
        val green = (color shr 8 and 0xff) / 255F
        val blue = (color and 0xff) / 255F
        val alpha = (color shr 24 and 0xff) / 255F

        GL11.glColor4f(red, green, blue, alpha)

        var isLastUTF16=false
        var highSurrogate='\u0000'
        for(char in text.toCharArray()){
            if(char in '\ud800'..'\udfff'){
                if(isLastUTF16){
                    val utf16Char="$highSurrogate$char"
                    val singleWidth=drawChar(utf16Char, 0f, 0f)
                    GL11.glTranslatef(singleWidth-8f,0f,0f)
                }else{
                    highSurrogate=char
                }
                isLastUTF16 = !isLastUTF16
            }else{
                val singleWidth=drawChar("$char", 0f, 0f)
                GL11.glTranslatef(singleWidth-8f,0f,0f)
                isLastUTF16=false
            }
        }

        GL11.glPopMatrix()
    }

    /**
     * Draw char from texture to display
     *
     * @param char target font char to render
     * @param x        target position x to render
     * @param y        target position y to render
     */
    private fun drawChar(char: String, x: Float, y: Float): Int {
        RenderUtils.drawAWTShape(font.createGlyphVector(FontRenderContext(AffineTransform(), true, false), char).getOutline(x + 3, y + 1f + fontMetrics.ascent))

        return getCharWidth(char)
    }

    /**
     * Calculate the string width of a text
     *
     * @param text for width calculation
     * @return the width of the text
     */
    fun getStringWidth(text: String): Int {
        var width=0

        var isLastUTF16=false
        var highSurrogate='\u0000'
        for(char in text.toCharArray()){
            if(char in '\ud800'..'\udfff'){
                if(isLastUTF16){
                    val utf16Char="$highSurrogate$char"

                    width+=getCharWidth(utf16Char)-8
                }else{
                    highSurrogate=char
                }
                isLastUTF16 = !isLastUTF16
            }else{
                width+=getCharWidth("$char")-8
                isLastUTF16=false
            }
        }

        return width/2
    }

    fun getCharWidth(char: String): Int {
        var width=fontMetrics.stringWidth(char) + 8
        if(width <= 0)
            width = 7

        return width
    }
}