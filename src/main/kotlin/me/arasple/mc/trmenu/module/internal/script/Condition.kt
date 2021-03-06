package me.arasple.mc.trmenu.module.internal.script

import io.izzel.taboolib.kotlin.Mirror
import me.arasple.mc.trmenu.module.display.MenuSession
import me.arasple.mc.trmenu.module.internal.script.js.JavaScriptAgent
import me.arasple.mc.trmenu.module.internal.script.kether.KetherHandler
import me.arasple.mc.trmenu.module.internal.service.Performance
import org.bukkit.entity.Player

/**
 * @author Arasple
 * @date 2021/1/25 11:13
 */
inline class Condition(private val script: String) {

    fun eval(session: MenuSession): EvalResult {
        return if (script.isEmpty()) EvalResult.TRUE
        else eval(session.placeholderPlayer, script)
    }

    companion object {

        fun eval(session: MenuSession, script: String): EvalResult {
            return eval(session.placeholderPlayer, script)
        }

        fun eval(player: Player, script: String): EvalResult {
            Performance.MIRROR.check("Script:evalCondition") {
                val js = script.startsWith("js: ").also { script.removePrefix("js: ") }
                return if (js) JavaScriptAgent.eval(MenuSession.getSession(player), script)
                else KetherHandler.eval(player, script)
            }
            throw Exception()
        }

    }

}