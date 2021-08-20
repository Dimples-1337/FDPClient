package net.ccbluex.liquidbounce.ui.ultralight

import com.labymedia.ultralight.UltralightPlatform
import com.labymedia.ultralight.UltralightRenderer
import com.labymedia.ultralight.config.FontHinting
import com.labymedia.ultralight.config.UltralightConfig
import com.labymedia.ultralight.plugin.logging.UltralightLogLevel
import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Listenable
import net.ccbluex.liquidbounce.event.Render2DEvent
import net.ccbluex.liquidbounce.ui.ultralight.support.ClipboardAdapter
import net.ccbluex.liquidbounce.ui.ultralight.support.FileSystemAdapter
import net.ccbluex.liquidbounce.ui.ultralight.view.View
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import org.apache.logging.log4j.LogManager
import java.io.File

object UltralightEngine : Listenable {
    lateinit var platform: UltralightPlatform
    lateinit var renderer: UltralightRenderer

    private val logger = LogManager.getLogger("Ultralight")

    private val ultralightPath = File(LiquidBounce.fileManager.cacheDir, "Ultralight")
    private val resourcePath = File(ultralightPath, "resources")
    private val pagesPath = File(ultralightPath, "pages")

    var width=0
    var height=0
    private val gcTimer=MSTimer()

    private val views=mutableListOf<View>()

    init {
        if(!resourcePath.exists())
            resourcePath.mkdirs()

        if(!pagesPath.exists())
            pagesPath.mkdirs()
    }

    fun init(){
        // load ultralight natives and resources from web
        loadResources()

        platform = UltralightPlatform.instance()
        platform.setConfig(
            UltralightConfig()
                .forceRepaint(false)
                .resourcePath(resourcePath.absolutePath.toString())
                .fontHinting(FontHinting.NORMAL)
        )
        platform.usePlatformFontLoader()
        platform.setFileSystem(FileSystemAdapter())
        platform.setClipboard(ClipboardAdapter())
        platform.setLogger { level, message ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (level) {
                UltralightLogLevel.ERROR -> logger.debug("[Ultralight/ERR] $message")
                UltralightLogLevel.WARNING -> logger.debug("[Ultralight/WARN] $message")
                UltralightLogLevel.INFO -> logger.debug("[Ultralight/INFO] $message")
            }
        }

        renderer = UltralightRenderer.create()
        renderer.logMemoryUsage()

        LiquidBounce.eventManager.registerListener(this)
    }

    private fun loadResources(){

    }

    fun registerView(view: View){
        views.add(view)
    }

    fun unregisterView(view: View){
        views.remove(view)
        view.close()
    }

    @EventTarget
    fun onRender2d(event: Render2DEvent){
        val sr=ScaledResolution(Minecraft.getMinecraft())
        width=sr.scaledWidth
        height=sr.scaledHeight

        this.renderer.update()
        this.renderer.render()

        if(gcTimer.hasTimePassed(1000L)){
            views.forEach { it.gc() }
        }
    }

    override fun handleEvents() = true
}