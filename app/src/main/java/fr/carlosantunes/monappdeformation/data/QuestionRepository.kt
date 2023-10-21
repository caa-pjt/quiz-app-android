package fr.carlosantunes.monappdeformation.data

/*
    Réqupération des données depuis QuestionBank

    C'est depuis cette class qu'il faut géréer les appels DB
        API
        SQLite
        class de données
        Shared Préférences (clé/valeur au format XML)
 */
class QuestionRepository(private val questionBank: QuestionBank) {

    /*
        C'est dans cette fonction que l'on doit faire appel au repository
     */
    fun getQuestion(): List<Question> {
        return questionBank.getQuestions()
    }
}