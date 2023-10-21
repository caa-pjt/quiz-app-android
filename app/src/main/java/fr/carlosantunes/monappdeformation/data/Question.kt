package fr.carlosantunes.monappdeformation.data

/*
    Model de données atendu pour chaque question du quiz
 */
class Question(
    val question: String,
    val choiceList: List<String>,
    val answerIndex: Int
)