package org.factotum.features.lorem.services

interface LoremIpsumService {
    fun generateLoremIpsumText(wordCount: Int): String
}