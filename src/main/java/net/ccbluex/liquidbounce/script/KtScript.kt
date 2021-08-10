package net.ccbluex.liquidbounce.script

import com.sun.jna.Platform.RESOURCE_PREFIX
import gnu.trove.THashSet
import net.minecraft.client.Minecraft
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileSystemUtil
import java.io.File
import javax.script.ScriptEngineManager
import kotlin.script.experimental.host.ScriptingHostConfiguration

/**
 * @author Liulihaocai
 * Load kotlin scripts dynamically
 */
class KtScript(val scriptFile: File) {

    private val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")
    private val scriptText = scriptFile.readText(Charsets.UTF_8)

    init {
        scriptEngine.put("mc", Minecraft.getMinecraft())

        scriptEngine.eval(scriptText)
    }
}