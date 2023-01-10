/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/UnlegitMC/FDPClient/
 */
package net.ccbluex.liquidbounce.utils.misc

import net.ccbluex.liquidbounce.utils.MinecraftInstance
import java.awt.Desktop
import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.util.function.Consumer
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JOptionPane
import net.minecraft.util.MinecraftError
import net.minecraft.world.MinecraftException
import net.minecraft.util.ReportedException
import net.minecraft.util.ChatComponentTranslationFormatException
import net.minecraft.network.ThreadQuickExitException
import net.minecraft.nbt.NBTException
import net.minecraft.command.WrongUsageException
import net.minecraft.command.SyntaxErrorException
import net.minecraft.command.PlayerNotFoundException
import net.minecraft.command.NumberInvalidException
import net.minecraft.command.EntityNotFoundException
import net.minecraft.command.CommandNotFoundException
import net.minecraft.command.CommandException
import net.minecraft.client.util.JsonException
import net.minecraft.client.resources.ResourcePackFileNotFoundException
import net.minecraft.client.renderer.StitcherException
import net.minecraft.client.AnvilConverterException
import net.minecraft.crash.CrashReport
import net.minecraft.crash.CrashReportCategory
import net.minecraft.init.Bootstrap

object MiscUtils : MinecraftInstance() {
    fun showErrorPopup(message: String) {
        JOptionPane.showMessageDialog(null, message, "Alert", JOptionPane.ERROR_MESSAGE)
    }

    fun showErrorPopup(title: String, message: String) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE)
    }

    fun showURL(url: String) {
        try {
            Desktop.getDesktop().browse(URI(url))
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }
    fun everyExceptionsWith1StringArgCanThrowInMC() {
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw IllegalStateException("TickNextTick list out of synch")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException("Failed to instantiate "+">_<")
        throw RuntimeException("Failed to check session lock, aborting")
        throw MinecraftException("The save is being accessed from another location, aborting")
        throw MinecraftException("Failed to check session lock, aborting")
        throw RuntimeException("Old Chunk Storage is no longer supported.")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw IndexOutOfBoundsException("The coordinate is out of range")
        throw IndexOutOfBoundsException("The coordinate is out of range")
        throw IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: "+">_<")
        throw AnvilConverterException("Unable to read or access folder where game worlds are saved!")
        throw RuntimeException("Already decorating")
        throw IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow")
        throw Error("Biome \""+">_<"+" and "+">_<")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw NoSuchElementException()
        throw EntityNotFoundException()
        repeat(9) {throw UnsupportedOperationException()}
        throw IllegalArgumentException("Don\'t know how to search for "+">_<")
        throw IllegalStateException("Unable to get CW facing for axis "+">_<")
        throw IllegalStateException("Unable to get Y-rotated facing of "+">_<")
        throw IllegalStateException("Unable to get X-rotated facing of "+">_<")
        throw IllegalStateException("Unable to get Z-rotated facing of "+">_<")
        throw IllegalStateException("Unable to get CCW facing of "+">_<")
        throw IllegalArgumentException("No such direction: "+">_<"+" "+">_<")
        throw Error("Someone\'s been tampering with the universe!")
        throw IOException("Filesize is bigger than maximum allowed (file is "+">_<"+")")
        throw IOException("Filesize was bigger than maximum allowed (got >= "+">_<"+")")
        throw JsonParseException("Don\'t know how to turn "+">_<"+" into a Component")
        throw JsonParseException("A score component needs a least a name and an objective")
        throw JsonParseException("Don\'t know how to turn "+">_<"+" into a Component")
        throw JsonParseException("Unexpected empty array of components")
        throw IllegalArgumentException("Don\'t know how to serialize "+">_<"+" as a Component")
        throw JsonSyntaxException("Expected "+">_<"+" to be a string, was "+">_<")
        throw JsonSyntaxException("Missing "+">_<"+", expected to find a string")
        throw JsonSyntaxException("Expected "+">_<"+" to be a Boolean, was "+">_<")
        throw JsonSyntaxException("Missing "+">_<"+", expected to find a Boolean")
        throw JsonSyntaxException("Expected "+">_<"+" to be a Float, was "+">_<")
        throw JsonSyntaxException("Missing "+">_<"+", expected to find a Float")
        throw JsonSyntaxException("Expected "+">_<"+" to be a Int, was "+">_<")
        throw JsonSyntaxException("Missing "+">_<"+", expected to find a Int")
        throw JsonSyntaxException("Expected "+">_<"+" to be a JsonObject, was "+">_<")
        throw JsonSyntaxException("Missing "+">_<"+", expected to find a JsonObject")
        throw JsonSyntaxException("Expected "+">_<"+" to be a JsonArray, was "+">_<")
        throw JsonSyntaxException("Missing "+">_<"+", expected to find a JsonArray")
        throw NoSuchElementException()
        throw RuntimeException("Something went wrong when converting from HSV to RGB. Input was "+">_<"+", "+">_<")
        throw IOException("Bad packet id "+">_<")
        throw IOException("Packet "+">_<"+" bytes extra whilst reading packet "+">_<")
        throw CorruptedFrameException("length wider than 21-bit")
        throw IOException("Can\'t serialize unregistered packet")
        throw IllegalArgumentException("unable to fit "+">_<"+" into "+">_<")
        throw IllegalArgumentException()
        throw IllegalArgumentException("Duplicate id: "+">_<")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException("Duplicate stat id: \""+">_<"+"\" at id "+">_<")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw UnsupportedOperationException("Invalid intention "+">_<")
        throw IllegalStateException("Invalid nonce!")
        throw IllegalStateException("Cannot modify read-only score")
        throw IllegalStateException("Cannot modify read-only score")
        throw IllegalStateException("Cannot modify read-only score")
        throw IllegalArgumentException("The objective name \'"+">_<                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+"\' is too long!")
        throw IllegalArgumentException("An objective with the name \'"+">_<"+"\' already exists!")
        throw IllegalArgumentException("The player name \'"+">_<                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "+"\' is too long!")
        throw IllegalArgumentException("The team name \'"+">_<                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+"\' is too long!")
        throw IllegalArgumentException("A team with the name \'"+">_<"+"\' already exists!")
        throw IllegalArgumentException("The player name \'"+">_<                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          "+"\' is too long!")
        throw IllegalStateException("Player is either on another team or not on any team. Cannot remove from team \'"+">_<"+"\'.")
        throw IllegalArgumentException("Name cannot be null")
        throw IllegalArgumentException("Prefix cannot be null")
        throw IllegalArgumentException()
        throw IllegalStateException("OW KNOWS!")
        throw IllegalArgumentException()
        throw Error("Invalid protocol ID "+">_<")
        throw Error("Packet "+">_<"+" - can\'t reassign to "+">_<")
        throw Error("Packet "+">_<"+" fails instantiation checks! "+">_<")
        throw IllegalArgumentException("Invalid player action")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw IllegalArgumentException("Invalid client command!")
        throw IOException("Invalid book tag!")
        throw IOException("Invalid book tag!")
        throw DecoderException("Badly compressed packet - size of "+">_<"+" is below server threshold of "+">_<")
        throw DecoderException("Badly compressed packet - size of "+">_<"+" is larger than protocol maximum of "+">_<")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException("VarInt too big")
        throw RuntimeException("VarLong too big")
        throw DecoderException("The received encoded string buffer length is longer than maximum allowed ("+">_<                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              "+")")
        throw DecoderException("The received encoded string buffer length is less than zero! Weird string!")
        throw DecoderException("The received string length is longer than maximum allowed ("+">_<                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            "+")")
        throw EncoderException("String too big (was "+">_<                                                                                                                                                                                                                                                                                                                                                                                                                                                                         "+")")
        throw IllegalArgumentException("Method must be join or leave for player constructor")
        throw IllegalArgumentException("Players cannot be null/empty")
        throw IllegalArgumentException("Payload may not be larger than 1048576 bytes")
        throw IOException("Payload may not be larger than 1048576 bytes")
        throw IllegalArgumentException("Hash is too long (max 40, was "+">_<                                                                                                                                                                               "+")")
        throw IllegalArgumentException("Payload may not be larger than 32767 bytes")
        throw IOException("Payload may not be larger than 32767 bytes")
        throw IOException("Failed to delete "+">_<")
        throw IOException("Root tag must be a named compound tag")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw NBTException("Invalid tag encountered, expected \'{\' as first char.")
        throw NBTException("Encountered multiple top tags, only one expected")
        throw NBTException("Illegal use of \\\": "+">_<")
        throw NBTException("Unbalanced curly brackets {}: "+">_<")
        throw NBTException("Unbalanced square brackets []: "+">_<")
        throw NBTException("Unbalanced quotation: "+">_<")
        throw NBTException("Unbalanced brackets: "+">_<")
        throw NBTException("Unexpected token \'"+">_<"+"\' at: "+">_<")
        throw NBTException("Unexpected token \'"+">_<"+"\' at: "+">_<")
        throw NBTException("Unable to locate name/value separator for string: "+">_<")
        throw NBTException("Name error at: "+">_<")
        throw NBTException("Illegal use of \\\": "+">_<")
        throw NBTException("Unbalanced curly brackets {}: "+">_<")
        throw NBTException("Unbalanced square brackets []: "+">_<")
        throw NBTException("Unable to locate name/value separator for string: "+">_<")
        throw NBTException("Unable to locate name/value separator for string: "+">_<")
        throw RuntimeException("Tried to read NBT tag that was too big; tried to allocate: "+">_<"+"bytes where max allowed: "+">_<")
        throw RuntimeException("Tried to read NBT tag with too high complexity, depth > 512")
        throw UnsupportedOperationException()
        throw UnsupportedOperationException()
        throw UnsupportedOperationException()
        throw UnsupportedOperationException()
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException("Tried to read NBT tag with too high complexity, depth > 512")
        throw RuntimeException("Missing type on ListTag")
        throw IllegalArgumentException("Empty string not allowed")
        throw UnsupportedOperationException("Can\'t dye non-leather!")
        throw IllegalArgumentException("Invalid shapeless recipe: unknown type "+">_<"+"!")
        throw IllegalArgumentException("Listener already listening")
        throw RuntimeException("Accessed Blocks before Bootstrap!")
        throw RuntimeException("Accessed Items before Bootstrap!")
        throw IllegalArgumentException("Unknown data type: "+">_<")
        throw IllegalArgumentException("Data value id is too big with "+">_<"+")")
        throw IllegalArgumentException("Duplicate id value for "+">_<"+"!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw IllegalArgumentException("ID is already registered: "+">_<")
        throw IllegalArgumentException("ID is already registered: "+">_<")
        throw IllegalArgumentException("Cannot register to reserved id: "+">_<")
        throw IllegalArgumentException("Cannot register null clazz for id: "+">_<")
        throw IllegalStateException("Entity is already tracked!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw IllegalArgumentException("Don\'t know how to add "+">_<"+"!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob")
        throw IllegalArgumentException("Unsupported mob type for DoorInteractGoal")
        throw IllegalArgumentException("Unsupported mob type for FollowOwnerGoal")
        throw IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal")
        throw IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal")
        throw IllegalArgumentException("Unsupported mob type for TemptGoal")
        throw IllegalArgumentException("Name cannot be null!")
        throw IllegalArgumentException("Attribute is already registered!")
        throw IllegalArgumentException("Modifier is already applied on this attribute!")
        throw IllegalArgumentException("Minimum value cannot be bigger than maximum value!")
        throw IllegalArgumentException("Default value cannot be lower than minimum value!")
        throw IllegalArgumentException("Default value cannot be bigger than maximum value!")
        throw IllegalArgumentException("Duplicate enchantment id!")
        repeat(12) {throw UnsupportedOperationException()}
        throw PlayerNotFoundException()
        throw UnsupportedOperationException()
        throw EntityNotFoundException()
        throw PlayerNotFoundException()
        repeat(4) {throw UnsupportedOperationException()}
        val crashreport = CrashReport.makeCrashReport(UnsupportedOperationException(), ">_<ing game")
        crashreport.makeCategory(">_<ion")
        val crashreport2 = mc.addGraphicsAndWorldToCrashReport(crashreport).getCompleteReport()
        repeat(176) {
        Bootstrap.printToSYSOUT(crashreport2)
        Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#")
        }
        throw UnsupportedOperationException()
        throw CommandNotFoundException()
        repeat(13) {throw UnsupportedOperationException()}
        throw PlayerNotFoundException()
        repeat(4) {throw UnsupportedOperationException()}
        throw EntityNotFoundException()
        throw PlayerNotFoundException()
        throw EntityNotFoundException()
        repeat(50) {
        throw UnsupportedOperationException()}
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        repeat(4) {throw UnsupportedOperationException()}
        repeat(310) {
        Bootstrap.printToSYSOUT(crashreport2)
        Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#")
        }
        repeat(3) {throw UnsupportedOperationException()}
        throw MinecraftError()
        throw MinecraftError()
        throw MinecraftError()
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw UnsupportedOperationException()
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw IllegalArgumentException("Metadata payload is full, cannot add more to it!")
        throw IllegalArgumentException("Metadata payload key cannot be null!")
        throw IllegalArgumentException("Metadata payload key is too long!")
        throw IllegalArgumentException("Metadata payload value cannot be null!")
        throw IllegalArgumentException("Metadata payload value is too long!")
        throw RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT")
        throw RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT")
        throw RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER")
        throw RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER")
        throw RuntimeException("glCheckFramebufferStatus returned unknown status:"+">_<")
        throw JsonException(">_< is already defined")
        throw JsonException("Input target \'"+">_<"+"\' does not exist")
        throw JsonException("Output target \'"+">_<"+"\' does not exist")
        throw JsonException("Render target or texture \'"+">_<"+"\' does not exist")
        throw JsonException("Uniform \'"+">_<"+"\' does not exist")
        throw JsonException("Could not create shader program (returned program ID "+">_<"+")")
        throw JsonException("Invalid amount of values specified (expected "+">_<"+")")
        throw RuntimeException("Encountered an exception when loading model definition of \'"+">_<"+"\'", exception)
        throw RuntimeException("Encountered an exception when loading model definition of model "+">_<")
        throw RuntimeException("Missing particle!")
        throw JsonParseException("Invalid animation->frames: expected array, was "+">_<")
        throw JsonParseException("Invalid font->characters: expected object, was "+">_<")
        throw JsonParseException("Invalid font->characters->default: expected object, was "+">_<")
        throw IllegalArgumentException("Metadata section name cannot be null")
        throw IllegalArgumentException("Invalid metadata for \'"+">_<"+"\' - expected object, found "+">_<")
        throw IllegalArgumentException("Don\'t know how to handle metadata section \'"+">_<"+"\'")
        throw JsonParseException("Invalid language->\'"+">_<"+"\'->region: empty value")
        throw JsonParseException("Invalid language->\'"+">_<"+"\'->name: empty value")
        throw JsonParseException("Duplicate language->\'"+">_<"+"\' defined")
        throw JsonParseException("Invalid/missing description!")
        throw JsonParseException("Invalid texture->mipmap->"+">_<"+": expected number, was "+">_<")
        throw JsonParseException("Invalid texture->mipmap->"+">_<"+": expected number, was "+">_<")
        throw JsonParseException("Invalid texture->mipmaps: expected array, was "+">_<")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw IllegalStateException("glGenLists returned an ID of 0 for a count of "+">_<"+"): "+">_<")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw IllegalStateException("Already building!")
        throw IllegalStateException("Not building!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw UnsupportedOperationException()
        throw RuntimeException("broken aspect ratio and not an animation")
        throw RuntimeException("invalid frameindex "+">_<")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException("Unable to load extra miplevels, source-texture is not power of two")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw IllegalArgumentException("Location cannot be null!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw UnsupportedOperationException()
        throw UnsupportedOperationException()
        throw UnsupportedOperationException()
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw UnsupportedOperationException()
        throw UnsupportedOperationException()
        throw NullPointerException("uvs")
        throw NullPointerException("uvs")
        throw JsonParseException("Invalid rotation "+">_<"+" found, only 0/90/180/270 allowed")
        throw JsonParseException("Expected 4 uv values, found: "+">_<")
        throw NullPointerException()
        throw JsonParseException("Expected shade to be a Boolean")
        throw JsonParseException("Invalid rotation "+">_<"+" found, only -45/-22.5/0/22.5/45 allowed")
        throw JsonParseException("Invalid rotation axis: "+">_<")
        throw JsonParseException("Expected between 1 and 6 unique faces, got 0")
        throw JsonParseException("Unknown facing: "+">_<")
        throw JsonParseException("\'to\' specifier exceeds the allowed boundaries: "+">_<")
        throw JsonParseException("\'from\' specifier exceeds the allowed boundaries: "+">_<")
        throw JsonParseException("Expected 3 "+">_<"+" values, found: "+">_<")
        throw JsonParseException("Expected 3 "+">_<"+" values, found: "+">_<")
        throw ModelBlock.LoopException()
        throw JsonParseException("BlockModel requires either elements or parent, found neither")
        throw JsonParseException("BlockModel requires either elements or parent, found both")
        throw JsonParseException("Invalid BlockModelRotation x: "+">_<"+", y: "+">_<")
        throw UnsupportedOperationException()
        throw UnsupportedOperationException()
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException(">_< is missing a mapping! This is a bug!")
        throw RuntimeException("Invalid call to Particle.setTex, use coordinate methods")
        throw RuntimeException("Invalid call to Particle.setMiscTex")
        throw IllegalStateException("Server tried to update attributes of a non-living entity (actually: "+">_<"+")")
        throw Error("Clash in Sound Category ID & Name pools! Cannot insert "+">_<")
        throw IllegalStateException("IN YOU FACE")
        throw Error("TODO: Sanely handle url exception! :D")
        throw IllegalArgumentException("Don\'t know how to convert "+">_<"+" back into data...")
        throw IllegalArgumentException("Invalid entityFacing "+">_<"+" for facing "+">_<")
        throw IllegalArgumentException("Invalid entityFacing "+">_<"+" for facing "+">_<")
        throw IllegalArgumentException("Invalid facing: "+">_<")
        throw IllegalArgumentException("Invalid material")
        throw IllegalArgumentException("Invalid material")
        throw IllegalArgumentException(">_< is an invalid choice")
        throw IllegalArgumentException("Cannot get property "+">_<"+" as it does not exist in "+">_<")
        throw IllegalArgumentException("Cannot set property "+">_<"+" as it does not exist in "+">_<")
        throw IllegalArgumentException("Cannot set property "+">_<"+", it is not an allowed value")
        throw IllegalStateException()
        throw IllegalArgumentException("Invalid forwards & up combination")
        throw IllegalArgumentException(">_< cannot support property "+">_<")
        throw IllegalArgumentException("Expected aisle with height of "+">_<"+")")
        throw IllegalArgumentException("Not all rows in the given aisle are the correct width (expected "+">_<"+")")
        throw IllegalArgumentException("Empty pattern for aisle")
        throw IllegalStateException("Predicates for character(s) "+">_<"+" are missing")
        throw IllegalArgumentException("Multiple values have the same name \'"+">_<"+"\'")
        throw IllegalArgumentException("Min value of "+">_<"+" must be 0 or greater")
        throw IllegalArgumentException("Max value of "+">_<"+")")
        throw IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)")
    }
    fun openFileChooser(): File? {
        if (mc.isFullScreen) mc.toggleFullscreen()
        val fileChooser = JFileChooser()
        val frame = JFrame()
        fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY
        frame.isVisible = true
        frame.toFront()
        frame.isVisible = false
        val action = fileChooser.showOpenDialog(frame)
        frame.dispose()
        return if (action == JFileChooser.APPROVE_OPTION) fileChooser.selectedFile else null
    }

    fun saveFileChooser(): File? {
        if (mc.isFullScreen) mc.toggleFullscreen()
        val fileChooser = JFileChooser()
        val frame = JFrame()
        fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY
        frame.isVisible = true
        frame.toFront()
        frame.isVisible = false
        val action = fileChooser.showSaveDialog(frame)
        frame.dispose()
        return if (action == JFileChooser.APPROVE_OPTION) fileChooser.selectedFile else null
    }

    fun <T> make(`object`: T, consumer: Consumer<T>): T {
        consumer.accept(`object`)
        return `object`
    }
}