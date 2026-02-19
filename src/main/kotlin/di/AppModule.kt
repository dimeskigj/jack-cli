package org.jack.di

import org.jack.features.base64.services.impl.Base64ServiceImpl
import org.jack.features.cron.services.impl.CronServiceImpl
import org.jack.features.hash.services.impl.HashServiceImpl
import org.jack.features.json.services.impl.JsonServiceImpl
import org.jack.features.jwt.services.impl.JwtServiceImpl
import org.jack.features.lorem.services.impl.LoremIpsumServiceImpl
import org.jack.features.net.services.impl.NetServiceImpl
import org.jack.features.qr.services.impl.QrCodeWriterServiceImpl
import org.jack.features.timestamp.services.impl.TimestampServiceImpl
import org.jack.features.upgrade.services.impl.UpgradeServiceImpl
import org.jack.features.uuid.services.impl.UuidServiceImpl

object AppModule {
    val uuidService by lazy { UuidServiceImpl() }
    val loremIpsumService by lazy { LoremIpsumServiceImpl() }
    val qrCodeWriterService by lazy { QrCodeWriterServiceImpl() }
    val timestampService by lazy { TimestampServiceImpl() }
    val hashService by lazy { HashServiceImpl() }
    val jwtService by lazy { JwtServiceImpl() }
    val jsonService by lazy { JsonServiceImpl() }
    val upgradeService by lazy { UpgradeServiceImpl() }
    val netService by lazy { NetServiceImpl() }
    val cronService by lazy { CronServiceImpl() }
    val base64Service by lazy { Base64ServiceImpl() }
}
