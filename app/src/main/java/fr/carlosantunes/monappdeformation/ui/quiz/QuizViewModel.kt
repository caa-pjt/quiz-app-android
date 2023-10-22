package fr.carlosantunes.monappdeformation.ui.quiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.carlosantunes.monappdeformation.MYAPP
import fr.carlosantunes.monappdeformation.data.Question
import fr.carlosantunes.monappdeformation.data.QuestionRepository

/**
 * Chef d'orchestre du quiz qui gère la logique métier entre le fragment et le référentiel de données.
 *
 * Cette classe identifie les états de l'écran de quiz, tels que la question actuelle, le score et
 * s'il s'agit de la dernière question. Elle expose ces états aux observateurs (fragments) via
 * des objets LiveData.
 *
 * @param questionRepository Le référentiel de données qui fournit les questions pour le quiz.
 */
class QuizViewModel(private var questionRepository: QuestionRepository) : ViewModel() {
    private lateinit var questions: List<Question>
    private var currentQuestionIndex: Int = 0

    /**
     * LiveData représentant la question actuelle affichée à l'utilisateur.
     */
    var currentQuestion: MutableLiveData<Question> = MutableLiveData<Question>()

    /**
     * LiveData représentant le score actuel du quiz.
     */
    var score: MutableLiveData<Int> = MutableLiveData<Int>(0)

    /**
     * LiveData indiquant si l'utilisateur est arrivé à la dernière question.
     */
    var isLastQuestion: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    /**
     * Démarre le quiz en obtenant la première question depuis le référentiel de données.
     */
    fun startQuiz() {
        questions = questionRepository.getQuestion()
        currentQuestion.postValue(questions?.get(0))
    }

    /**
     * Passe à la question suivante dans le quiz.
     */
    fun nextQuestion() {
        var nextIndex = currentQuestionIndex + 1

        if (nextIndex >= questions.size) {
            return
        } else if (nextIndex == questions.size - 1) {
            isLastQuestion.postValue(true)
        }
        currentQuestionIndex = nextIndex
        currentQuestion.postValue(questions.get(currentQuestionIndex))
        Log.i(MYAPP, "nextQuestion() : currentQuestionIndex $currentQuestionIndex, questions.size : ${questions.size}")
        Log.i(MYAPP, "${questionRepository.getQuestion()}")

        Log.i(MYAPP, "Next question, nextIndex : $nextIndex")
    }

    /**
     * Vérifie si la réponse sélectionnée par l'utilisateur est valide.
     *
     * @param answerIndex L'index de la réponse sélectionnée.
     * @return True si la réponse est valide, sinon False.
     */
    fun isAnswerValid(answerIndex: Int): Boolean {
        val question = currentQuestion.value
        val isValid = question != null && question.getAnswerIndex() == answerIndex
        val currentScore = score.value
        if (currentScore != null && isValid) {
            score.value = currentScore + 1
        }
        return isValid
    }
}