package tech.marlonr.collectitemchallenge

import net.axay.kspigot.chat.KColors
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.commands.runs
import net.axay.kspigot.event.listen
import net.axay.kspigot.main.KSpigot
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.EntityPickupItemEvent
import tech.marlonr.collectitemchallenge.timer.Timer
import java.util.concurrent.ThreadLocalRandom

class Manager: KSpigot() {

    companion object {
        val timer = Timer()
    }

    override fun startup() {
        timer.init()

        command("start") {
            requiresPermission("collectitemchallenge.start")

            runs {
                timer.started = true
                player.sendMessage(Component.text("Du hast das Spiel gestartet!").color(KColors.RED))
            }
        }

        listen<EntityPickupItemEvent> {
            if (!timer.started) return@listen
            if (it.entity.type != EntityType.PLAYER) return@listen

            val x = ThreadLocalRandom.current().nextInt(1, 25 + 1)
            val z = ThreadLocalRandom.current().nextInt(1, 25 + 1)

            it.entity.teleport(Location(it.entity.location.world, x.toDouble().plus(it.entity.location.x), it.entity.world.getHighestBlockAt(x, z).y.toDouble(), z.toDouble().plus(it.entity.location.z)))
        }
    }
}