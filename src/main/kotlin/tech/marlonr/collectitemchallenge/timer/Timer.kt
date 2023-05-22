package tech.marlonr.collectitemchallenge.timer

import net.axay.kspigot.extensions.bukkit.actionBar
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.Component
import java.util.*

class Timer {
    var started = false
    var currentTime: Long = 0

    fun init() {
        task(sync = true, period = 20) {
            onlinePlayers.forEach {
                it.actionBar(currentTime.formatAsClockTime)
            }
            if (started) currentTime++
        }
    }

    val Long.formatAsClockTime /*Ich weiß dummer Name*/: String get() {
        return String.format(Locale.GERMAN, "%02d:%02d:%02d", this / 3600, this % 3600 / 60, this % 60)
    }
}