package fr.carlosantunes.monappdeformation.data

/*
    Model de données atendu pour chaque question du quiz
 */
class Question(
    private val question: String,
    private val choiceList: List<String>,
    private val answerIndex: Int
) {
    fun getQuestion(): String {
        return question
    }

    fun getChoiceList(): List<String> {
        return choiceList
    }

    fun getAnswerIndex(): Int {
        return answerIndex
    }
}