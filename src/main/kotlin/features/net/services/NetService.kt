package org.jack.features.net.services

import java.net.InetAddress

interface NetService {
    fun getLocalIp(): InetAddress

    fun getDns(domain: String): List<InetAddress>
}
