package net.ccbluex.liquidbounce.ui.ultralight.view

import com.labymedia.ultralight.UltralightView
import com.labymedia.ultralight.config.UltralightViewConfig
import net.ccbluex.liquidbounce.ui.ultralight.UltralightEngine
import net.ccbluex.liquidbounce.ui.ultralight.listener.UltralightLoadListener
import net.ccbluex.liquidbounce.ui.ultralight.listener.UltralightViewListener

class View {
    private val view: UltralightView
    private val viewListener: UltralightViewListener
    private val loadListener: UltralightLoadListener

    init {
        view = UltralightEngine.renderer.createView(
            UltralightEngine.width.toLong(), UltralightEngine.height.toLong(),
            UltralightViewConfig()
                .initialDeviceScale(1.0)
                .isTransparent(true)
        )
        viewListener = UltralightViewListener()
        view.setViewListener(viewListener)
        loadListener = UltralightLoadListener(view)
        view.setLoadListener(loadListener)
    }

    fun loadURL(url: String){
        view.loadURL(url)
    }

    fun render(){

    }

    fun gc(){
        view.lockJavascriptContext().use { lock -> lock.context.garbageCollect() }
    }

    fun close(){
        view.unfocus()
        view.stop()
    }
}