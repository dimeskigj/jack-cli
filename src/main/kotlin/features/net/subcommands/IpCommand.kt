package org.jack.features.net.subcommands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import org.jack.features.net.NET_HOSTNAME_OUTPUT_PREFIX
import org.jack.features.net.NET_IP_COMMAND_NAME
import org.jack.features.net.NET_IP_HELP
import org.jack.features.net.NET_IP_OUTPUT_PREFIX
import org.jack.features.net.services.NetService

class IpCommand(
    private val netService: NetService,
) : CliktCommand(name = NET_IP_COMMAND_NAME) {
    override fun help(context: Context) = NET_IP_HELP

    override fun run() {
        val localIp = netService.getLocalIp()
        echo("$NET_IP_OUTPUT_PREFIX${localIp.hostAddress}")
        echo("$NET_HOSTNAME_OUTPUT_PREFIX${localIp.hostName}")
    }
}
