package fr.carlosantunes.monappdeformation.data

/*
    Liste des questions du quiz
 */
class QuestionBank {
    fun getQuestions(): List<Question> {
        return listOf(
            Question(
                "Who is the creator of Android?",
                listOf(
                    "Andy Rubin",
                    "Steve Wozniak",
                    "Jake Wharton",
                    "Paul Smith"
                ),
                0
            ),
            Question(
                "When did the first man land on the moon?",
                listOf(
                    "1958",
                    "1962",
                    "1967",
                    "1969"
                ),
                3
            ),
            Question(
                "What is the house number of The Simpsons?",
                listOf(
                    "42",
                    "101",
                    "666",
                    "742"
                ),
                3
            ),
            Question(
                "Who painted the Mona Lisa?",
                listOf(
                    "Michelangelo",
                    "Leonardo Da Vinci",
                    "Raphael",
                    "Pablo Picasso"
                ),
                1
            )
        )
    }

    companion object {
        private var instance: QuestionBank? = null
        fun getInstance(): QuestionBank {
            if (instance == null) {
                instance = QuestionBank()
            }
            return instance as QuestionBank
        }
    }
}