package example

import com.thoughtworks.gauge.Step
import com.thoughtworks.gauge.Table
import org.assertj.core.api.Assertions.assertThat
import java.util.HashSet

class StepImplementation{

    private var vowels: HashSet<Char>? = null

    @Step("Vowels in English language are <vowelString>.")
    fun setLanguageVowels(vowelString: String) {
        vowels = HashSet()
        for (ch in vowelString.toCharArray()) {
            vowels!!.add(ch)
        }
    }

    @Step("The word <word> has <expectedCount> vowels.")
    fun verifyVowelsCountInWord(word: String, expectedCount: Int) {
        val actualCount = countVowels(word)
        assertThat(expectedCount).isEqualTo(actualCount)
    }

    @Step("Almost all words have vowels <wordsTable>")
    fun verifyVowelsCountInMultipleWords(wordsTable: Table) {
        for (row in wordsTable.tableRows) {
            val word = row.getCell("Word")
            val expectedCount = Integer.parseInt(row.getCell("Vowel Count"))
            val actualCount = countVowels(word)

            assertThat(expectedCount).isEqualTo(actualCount)
        }
    }

    private fun countVowels(word: String): Int {
        var count = 0
        for (ch in word.toCharArray()) {
            if (vowels!!.contains(ch)) {
                count++
            }
        }
        return count
    }
}