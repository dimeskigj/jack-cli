package org.jack.features.net.services.impl

import org.jack.features.net.services.NetService
import java.net.InetAddress

class NetServiceImpl : NetService {
    override fun getLocalIp(): InetAddress = InetAddress.getLocalHost()

    override fun getDns(domain: String): List<InetAddress> = InetAddress.getAllByName(domain).toList()
}
