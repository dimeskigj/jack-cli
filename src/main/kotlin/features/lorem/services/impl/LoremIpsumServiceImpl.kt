package org.jack.features.lorem.services.impl

import org.jack.features.lorem.services.LoremIpsumService
import kotlin.random.Random

val loremIpsumWords = listOf(
    "lorem", "ipsum", "dolor", "sit", "amet", "consectetur",
    "adipiscing", "elit", "sed", "do", "eiusmod", "tempor",
    "incididunt", "ut", "labore", "et", "dolore", "magna",
    "aliqua", "enim", "minim", "veniam", "quis", "nostrud",
    "exercitation", "ullamco", "laboris", "nisi", "aliquip",
    "ex", "ea", "commodo", "consequat", "duis", "aute",
    "irure", "in", "reprehenderit", "voluptate", "velit",
    "esse", "cillum", "dolore", "eu", "fugiat", "nulla",
    "pariatur", "excepteur", "sint", "occaecat", "cupidatat",
    "non", "proident", "sunt", "culpa", "qui", "officia",
    "deserunt", "mollit", "anim", "id", "est", "laborum"
)


class LoremIpsumServiceImpl : LoremIpsumService {
    override fun generateLoremIpsumText(wordCount: Int): String {
        val result = StringBuilder()
        var wordsAdded = 0

        while (wordsAdded < wordCount) {
            val sentenceLength = Random.nextInt(3, 13).coerceAtMost(wordCount - wordsAdded)

            for (i in 0 until sentenceLength) {
                val word = loremIpsumWords.random()
                if (i == 0) {
                    result.append(word.replaceFirstChar { it.uppercase() })
                } else {
                    result.append(word)
                }
                if (i < sentenceLength - 1) {
                    result.append(" ")
                }
            }
            result.append(". ")
            wordsAdded += sentenceLength
        }

        return result.toString().trim()
    }
}