package net.ccbluex.liquidbounce.ui.ultralight.view

import com.labymedia.ultralight.UltralightView
import com.labymedia.ultralight.bitmap.UltralightBitmapSurface
import com.labymedia.ultralight.config.UltralightViewConfig
import net.ccbluex.liquidbounce.ui.ultralight.UltralightEngine
import net.ccbluex.liquidbounce.ui.ultralight.listener.TheLoadListener
import net.ccbluex.liquidbounce.ui.ultralight.listener.TheViewListener
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.OpenGlHelper
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*
import java.nio.ByteBuffer

class View {
    private val view: UltralightView
    private val viewListener: TheViewListener
    private val loadListener: TheLoadListener

    private var glTexture = -1

    init {
        view = UltralightEngine.renderer.createView(
            UltralightEngine.width.toLong(), UltralightEngine.height.toLong(),
            UltralightViewConfig()
                .initialDeviceScale(1.0)
                .isTransparent(true)
        )
        viewListener = TheViewListener()
        view.setViewListener(viewListener)
        loadListener = TheLoadListener(view)
        view.setLoadListener(loadListener)
    }

    fun loadURL(url: String){
        view.loadURL(url)
    }

    fun resize(width: Int, height: Int){
        view.resize(width.toLong(), height.toLong())
    }

    fun render(){
        if (glTexture == -1) {
            createTexture()
        }

        // As we are using the CPU renderer, draw with a bitmap (we did not set a custom surface)
        val surface = view.surface() as UltralightBitmapSurface
        val bitmap = surface.bitmap()
        val width = view.width().toInt()
        val height = view.height().toInt()

        // Prepare OpenGL for 2D textures and bind our texture
        GlStateManager.enableTexture2D()
        GlStateManager.bindTexture(glTexture)
//        RenderSystem.enableTexture()
//        RenderSystem.bindTexture(glTexture)

        val dirtyBounds = surface.dirtyBounds()

        if (dirtyBounds.isValid) {
            val imageData = bitmap.lockPixels()

            glPixelStorei(GL_UNPACK_SKIP_ROWS, 0)
            glPixelStorei(GL_UNPACK_SKIP_PIXELS, 0)
            glPixelStorei(GL_UNPACK_SKIP_IMAGES, 0)
            glPixelStorei(GL_UNPACK_ROW_LENGTH, bitmap.rowBytes().toInt() / 4)

            if (dirtyBounds.width() == width && dirtyBounds.height() == height) {
                // Update full image
                glTexImage2D(
                    GL_TEXTURE_2D,
                    0,
                    GL_RGBA8,
                    width,
                    height,
                    0,
                    GL_BGRA,
                    GL_UNSIGNED_INT_8_8_8_8_REV,
                    imageData
                )
            } else {
                // Update partial image
                val x = dirtyBounds.x()
                val y = dirtyBounds.y()
                val dirtyWidth = dirtyBounds.width()
                val dirtyHeight = dirtyBounds.height()
                val startOffset = (y * bitmap.rowBytes() + x * 4).toInt()

                glTexSubImage2D(
                    GL_TEXTURE_2D,
                    0,
                    x, y, dirtyWidth, dirtyHeight,
                    GL_BGRA, GL_UNSIGNED_INT_8_8_8_8_REV,
                    imageData.position(startOffset) as ByteBuffer
                )
            }
            glPixelStorei(GL_UNPACK_ROW_LENGTH, 0)

            bitmap.unlockPixels()
            surface.clearDirtyBounds()
        }

        glDisable(GL_DEPTH_TEST)
        glEnable(GL_BLEND)
        glDepthMask(false)
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO)
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f)

        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0f, 0f, UltralightEngine.scaledWidth, UltralightEngine.scaledHeight, UltralightEngine.scaledWidth.toFloat(), UltralightEngine.scaledHeight.toFloat())
        glDepthMask(true)
        glDisable(GL_BLEND)
        glEnable(GL_DEPTH_TEST)

//        val tessellator = Tessellator.getInstance()
//        val bufferBuilder = tessellator.buffer
//        val scaleFactor = mc.window.scaleFactor.toFloat()
//
//        RenderSystem.setShader { GameRenderer.getPositionTexColorShader() }
//        RenderSystem.setShaderTexture(0, glTexture)
//        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
//        RenderSystem.enableBlend()
//        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR)
//
//        bufferBuilder
//            .vertex(0.0, height.toDouble(), 0.0)
//            .texture(0f, scaleFactor)
//            .color(255, 255, 255, 255)
//            .next()
//        bufferBuilder
//            .vertex(width.toDouble(), height.toDouble(), 0.0)
//            .texture(scaleFactor, scaleFactor)
//            .color(255, 255, 255, 255)
//            .next()
//        bufferBuilder
//            .vertex(width.toDouble(), 0.0, 0.0)
//            .texture(scaleFactor, 0.0f)
//            .color(255, 255, 255, 255)
//            .next()
//
//        bufferBuilder
//            .vertex(0.0, 0.0, 0.0)
//            .texture(0.0f, 0.0f)
//            .color(255, 255, 255, 255)
//            .next()
//
//        tessellator.draw()
//        RenderSystem.disableBlend()
    }

    fun deleteTexture() {
        glDeleteTextures(glTexture)
        glTexture = -1
    }

    fun createTexture() {
        glTexture = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, glTexture)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    fun gc(){
        val lock=view.lockJavascriptContext() // idk why use{} not working
        try {
            lock.context.garbageCollect()
        }catch (t: Throwable){
            // ignored
        }
    }

    fun close(){
        view.unfocus()
        view.stop()
        deleteTexture()
    }
}