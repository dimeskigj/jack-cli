package org.jack.features.net.subcommands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import org.jack.features.net.NET_DNS_COMMAND_NAME
import org.jack.features.net.NET_DNS_DOMAIN_ARG_HELP
import org.jack.features.net.NET_DNS_DOMAIN_ARG_NAME
import org.jack.features.net.NET_DNS_ERROR_PREFIX
import org.jack.features.net.NET_DNS_HELP
import org.jack.features.net.NET_DNS_SEPARATOR
import org.jack.features.net.services.NetService

class DnsCommand(
    private val netService: NetService,
) : CliktCommand(name = NET_DNS_COMMAND_NAME) {
    override fun help(context: Context) = NET_DNS_HELP

    private val domain by argument(name = NET_DNS_DOMAIN_ARG_NAME, help = NET_DNS_DOMAIN_ARG_HELP)

    override fun run() {
        try {
            val addresses = netService.getDns(domain)
            addresses.forEach {
                echo("${it.hostName}$NET_DNS_SEPARATOR${it.hostAddress}")
            }
        } catch (e: Exception) {
            echo("$NET_DNS_ERROR_PREFIX'$domain': ${e.message}", err = true)
        }
    }
}
