package org.jack.features.net

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import org.jack.features.net.services.NetService

const val NET_COMMAND_NAME = "net"
const val NET_HELP_TEXT = "Network utilities"
const val NET_HELP_DESC = "Network utilities for IP and DNS lookup"

const val NET_IP_COMMAND_NAME = "ip"
const val NET_IP_HELP = "Get local IP address"

const val NET_DNS_COMMAND_NAME = "dns"
const val NET_DNS_HELP = "DNS lookup"
const val NET_DNS_DOMAIN_ARG_NAME = "domain"
const val NET_DNS_DOMAIN_ARG_HELP = "Domain name to lookup"

const val NET_IP_OUTPUT_PREFIX = "Local IP: "
const val NET_HOSTNAME_OUTPUT_PREFIX = "Hostname: "
const val NET_DNS_SEPARATOR = " -> "
const val NET_DNS_ERROR_PREFIX = "Error looking up domain "

class NetCommand(
    private val netService: NetService,
) : CliktCommand(name = NET_COMMAND_NAME) {
    override fun help(context: Context) = NET_HELP_DESC

    override fun run() = Unit
}
