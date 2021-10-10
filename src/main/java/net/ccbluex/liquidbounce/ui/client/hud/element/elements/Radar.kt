/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.ui.client.hud.element.elements

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.vertex.IVertexBuffer
import net.ccbluex.liquidbounce.features.module.modules.render.ESP
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.utils.EntityUtils
import net.ccbluex.liquidbounce.utils.render.MiniMapRegister
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowShader
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import org.lwjgl.opengl.GL11.*
import org.lwjgl.util.vector.Vector2f
import java.awt.Color
import kotlin.math.*

@ElementInfo(name = "Radar", blur = true)
class Radar(x: Double = 5.0, y: Double = 130.0) : Element(x, y) {

    companion object {
        private val SQRT_OF_TWO = sqrt(2f)
    }

    private val sizeValue = FloatValue("Size", 90f, 30f, 500f)
    private val viewDistanceValue = FloatValue("View Distance", 4F, 0.5F, 32F)
    private val playerShapeValue = ListValue("Player Shape", arrayOf("Triangle", "Rectangle", "Circle"), "Triangle")
    private val playerSizeValue = FloatValue("Player Size", 2.0F, 0.5f, 20F)
    private val useESPColorsValue = BoolValue("Use ESP Colors", true)
    private val fovSizeValue = FloatValue("FOV Size", 10F, 0F, 50F)
    private val fovAngleValue = FloatValue("FOV Angle", 70F, 30F, 160F)
    private val lineValue = BoolValue("Line", false)
    private val rainbowList = ListValue("Line-Rainbow", arrayOf("Custom", "Random", "Rainbow", "AnotherRainbow", "Slowly", "SkyRainbow"), "Rainbow")
    private val backgroundRedValue = IntegerValue("Background Red", 0, 0, 255)
    private val backgroundGreenValue = IntegerValue("Background Green", 0, 0, 255)
    private val backgroundBlueValue = IntegerValue("Background Blue", 0, 0, 255)
    private val backgroundAlphaValue = IntegerValue("Background Alpha", 50, 0, 255)
    private val minimapValue = BoolValue("Minimap", true)
    private val distanceValue = IntegerValue("Line-Distance", 0, 0, 400)
    private val gradientAmountValue = IntegerValue("Gradient-Amount", 25, 1, 50)
    private val cRainbowSecValue = IntegerValue("Seconds", 2, 1, 10)
    private val brightnessValue = FloatValue("Brightness", 1f, 0f, 1f)
    private val saturationValue = FloatValue("Saturation", 0.9f, 0f, 1f)
    private val backgroundRedValue = IntegerValue("Background Red", 0, 0, 255)
    private val backgroundGreenValue = IntegerValue("Background Green", 0, 0, 255)
    private val backgroundBlueValue = IntegerValue("Background Blue", 0, 0, 255)
    private val backgroundAlphaValue = IntegerValue("Background Alpha", 50, 0, 255)
    private val borderStrengthValue = FloatValue("Border Strength", 2F, 1F, 5F)
    private val borderRedValue = IntegerValue("Border Red", 0, 0, 255)
    private val borderGreenValue = IntegerValue("Border Green", 0, 0, 255)
    private val borderBlueValue = IntegerValue("Border Blue", 0, 0, 255)
    private val borderAlphaValue = IntegerValue("Border Alpha", 150, 0, 255)

    private var fovMarkerVertexBuffer: IVertexBuffer? = null
    private var lastFov = 0f

    override fun drawElement(): Border? {
        MiniMapRegister.updateChunks()

        val fovAngle = fovAngleValue.get()

        if (lastFov != fovAngle || fovMarkerVertexBuffer == null) {

            fovMarkerVertexBuffer?.deleteGlBuffers()

            fovMarkerVertexBuffer = createFovIndicator(fovAngle)
            lastFov = fovAngle
        }

        val renderViewEntity = mc.renderViewEntity!!

        val size = sizeValue.get()

        if (!minimapValue.get()) {
            RenderUtils.drawRect(0F, 0F, size, size, Color(backgroundRedValue.get(), backgroundGreenValue.get(),
                    backgroundBlueValue.get(), backgroundAlphaValue.get()).rgb)
        }

        val viewDistance = viewDistanceValue.get() * 16.0F

        val maxDisplayableDistanceSquare = ((viewDistance + fovSizeValue.get().toDouble()) *
                (viewDistance + fovSizeValue.get().toDouble()))
        val halfSize = size / 2f

        RenderUtils.makeScissorBox(x.toFloat(), y.toFloat(), x.toFloat() + ceil(size), y.toFloat() + ceil(size))

        glEnable(GL_SCISSOR_TEST)

        glPushMatrix()

        glTranslatef(halfSize, halfSize, 0f)
        glRotatef(renderViewEntity.rotationYaw, 0f, 0f, -1f)

        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        glColor4f(1.0f, 1.0f, 1.0f, 1.0f)

        if (minimapValue.get()) {
            glEnable(GL_TEXTURE_2D)

            val chunkSizeOnScreen = size / viewDistanceValue.get()
            val chunksToRender = max(1, ceil((SQRT_OF_TWO * (viewDistanceValue.get() * 0.5f))).toInt())

            val currX = renderViewEntity.posX / 16.0
            val currZ = renderViewEntity.posZ / 16.0

            for (x in -chunksToRender..chunksToRender) {
                for (z in -chunksToRender..chunksToRender) {
                    val currChunk = MiniMapRegister.getChunkTextureAt(floor(currX).toInt() + x, floor(currZ).toInt() + z)

                    if (currChunk != null) {
                        val sc = chunkSizeOnScreen.toDouble()

                        val onScreenX = (currX - floor(currX).toLong() - 1 - x) * sc
                        val onScreenZ = (currZ - floor(currZ).toLong() - 1 - z) * sc

                        classProvider.getGlStateManager().bindTexture(currChunk.texture.glTextureId)

                        glBegin(GL_QUADS)

                        glTexCoord2f(0f, 0f)
                        glVertex2d(onScreenX, onScreenZ)
                        glTexCoord2f(0f, 1f)
                        glVertex2d(onScreenX, onScreenZ + chunkSizeOnScreen)
                        glTexCoord2f(1f, 1f)
                        glVertex2d(onScreenX + chunkSizeOnScreen, onScreenZ + chunkSizeOnScreen)
                        glTexCoord2f(1f, 0f)
                        glVertex2d(onScreenX + chunkSizeOnScreen, onScreenZ)

                        glEnd()
                    }

                }
            }

            classProvider.getGlStateManager().bindTexture(0)

            glDisable(GL_TEXTURE_2D)
        }

        glDisable(GL_TEXTURE_2D)
        glEnable(GL_LINE_SMOOTH)

        val triangleMode = playerShapeValue.get().equals("triangle", true)
        val circleMode = playerShapeValue.get().equals("circle", true)

        val tessellator = classProvider.tessellatorInstance
        val worldRenderer = tessellator.worldRenderer

        if (circleMode) {
            glEnable(GL_POINT_SMOOTH)
        }

        var playerSize = playerSizeValue.get()

        glEnable(GL_POLYGON_SMOOTH)

        if (triangleMode) {
            playerSize *= 2
        } else {
            worldRenderer.begin(GL_POINTS, classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION))
            glPointSize(playerSize)
        }

        for (entity in mc.theWorld!!.loadedEntityList) {
            if (entity != mc.thePlayer && EntityUtils.isSelected(entity, false)) {
                val positionRelativeToPlayer = Vector2f((renderViewEntity.posX - entity.posX).toFloat(),
                        (renderViewEntity.posZ - entity.posZ).toFloat())

                if (maxDisplayableDistanceSquare < positionRelativeToPlayer.lengthSquared())
                    continue

                val transform = triangleMode || fovSizeValue.get() > 0F

                if (transform) {
                    glPushMatrix()

                    glTranslatef((positionRelativeToPlayer.x / viewDistance) * size,
                            (positionRelativeToPlayer.y / viewDistance) * size, 0f)
                    glRotatef(entity.rotationYaw, 0f, 0f, 1f)
                }

                if (fovSizeValue.get() > 0F) {
                    glPushMatrix()
                    glRotatef(180.0f, 0f, 0f, 1f)
                    val sc = (fovSizeValue.get() / viewDistance) * size
                    glScalef(sc, sc, sc)

                    glColor4f(1.0f, 1.0f, 1.0f, if (minimapValue.get()) 0.75f else 0.25f)

                    val vbo = fovMarkerVertexBuffer!!

                    vbo.bindBuffer()

                    glEnableClientState(GL_VERTEX_ARRAY)
                    glVertexPointer(3, GL_FLOAT, 12, 0L)

                    vbo.drawArrays(GL_TRIANGLE_FAN)
                    vbo.unbindBuffer()

                    glDisableClientState(GL_VERTEX_ARRAY)

                    glPopMatrix()
                }

                if (triangleMode) {
                    if (useESPColorsValue.get()) {
                        val color = (LiquidBounce.moduleManager[ESP::class.java] as ESP).getColor(entity)

                        glColor4f(color.red / 255.0f, color.green / 255.0f, color.blue / 255.0f, 1.0f)
                    } else {
                        glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
                    }

                    glBegin(GL_TRIANGLES)

                    glVertex2f(-playerSize * 0.25f, playerSize * 0.5f)
                    glVertex2f(playerSize * 0.25f, playerSize * 0.5f)
                    glVertex2f(0f, -playerSize * 0.5f)

                    glEnd()
                } else {
                    val color = (LiquidBounce.moduleManager[ESP::class.java] as ESP).getColor(entity)

                    worldRenderer.pos(((positionRelativeToPlayer.x / viewDistance) * size).toDouble(), ((positionRelativeToPlayer.y / viewDistance) * size).toDouble(), 0.0)
                            .color(color.red / 255.0f, color.green / 255.0f,
                                    color.blue / 255.0f, 1.0f).endVertex()
                }

                if (transform) {
                    glPopMatrix()
                }

            }
        }

        if (!triangleMode)
            tessellator.draw()

        if (circleMode) {
            glDisable(GL_POINT_SMOOTH)
        }

        glDisable(GL_POLYGON_SMOOTH)

        glEnable(GL_TEXTURE_2D)
        glDisable(GL_BLEND)
        glDisable(GL_LINE_SMOOTH)

        glDisable(GL_SCISSOR_TEST)

        glPopMatrix()

         if (lineValue.get()) {
            val barLength = size.toDouble()

            for (i in 0..(gradientAmountValue.get()-1)) {
                val barStart = i.toDouble() / gradientAmountValue.get().toDouble() * barLength
                val barEnd = (i + 1).toDouble() / gradientAmountValue.get().toDouble() * barLength
                RenderUtils.drawGradientSideways(barStart, -1.0, barEnd, 0.0, 
                when (rainbowType) {
                    "rainbow" -> ColorUtils.hslRainbow(index+1,indexOffset=100*rainbowSpeed.get()).rgb
                    "random" -> moduleColor
                    "skyrainbow" -> ColorUtils.skyRainbow(index, saturationValue.get(), brightnessValue.get(), rainbowSpeed.get().toDouble()).rgb
                    "slowly" -> ColorUtils.slowlyRainbow(System.nanoTime(), index * 30 * rainbowSpeed.get(), saturationValue.get(), brightnessValue.get()).rgb
                    "anotherrainbow" -> ColorUtils.fade(customColor,100,index+1).rgb
                    else -> customColor.rgb
                },
                when (rainbowType) {
                    "rainbow" -> ColorUtils.hslRainbow(index+1,indexOffset=100*rainbowSpeed.get()).rgb
                    "random" -> moduleColor
                    "skyrainbow" -> ColorUtils.skyRainbow(index, saturationValue.get(), brightnessValue.get(), rainbowSpeed.get().toDouble()).rgb
                    "slowly" -> ColorUtils.slowlyRainbow(System.nanoTime(), index * 30 * rainbowSpeed.get(), saturationValue.get(), brightnessValue.get()).rgb
                    "anotherrainbow" -> ColorUtils.fade(customColor,100,index+1).rgb
                    else -> customColor.rgb
                })
            }
        }
         
            glEnable(GL_BLEND)
            glDisable(GL_TEXTURE_2D)
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
            glEnable(GL_LINE_SMOOTH)

            RenderUtils.glColor(borderRedValue.get(), borderGreenValue.get(), borderBlueValue.get(), borderAlphaValue.get())
            glLineWidth(borderStrengthValue.get())

            glBegin(GL_LINES)

            glVertex2f(halfSize, 0f)
            glVertex2f(halfSize, size)

            glVertex2f(0f, halfSize)
            glVertex2f(size, halfSize)

            glEnd()

            glEnable(GL_TEXTURE_2D)
            glDisable(GL_BLEND)
            glDisable(GL_LINE_SMOOTH)
        }

        glColor4f(1.0f, 1.0f, 1.0f, 1.0f)

        return Border(0F, 0F, size, size)
    }

    private fun createFovIndicator(angle: Float): IVertexBuffer {

        val worldRenderer = classProvider.tessellatorInstance.worldRenderer

        worldRenderer.begin(GL_TRIANGLE_FAN, classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION))

        val start = (90.0f - (angle * 0.5f)) / 180.0f * Math.PI.toFloat()
        val end = (90.0f + (angle * 0.5f)) / 180.0f * Math.PI.toFloat()

        var curr = end
        val radius = 1.0

        worldRenderer.pos(0.0, 0.0, 0.0).endVertex()

        while (curr >= start) {
            worldRenderer.pos(cos(curr) * radius, sin(curr) * radius, 0.0).endVertex()

            curr -= 0.15f
        }

        // Uploading to VBO

        val safeVertexBuffer = classProvider.createSafeVertexBuffer(worldRenderer.vertexFormat)

        worldRenderer.finishDrawing()
        worldRenderer.reset()
        safeVertexBuffer.bufferData(worldRenderer.byteBuffer)

        return safeVertexBuffer
    }

}
