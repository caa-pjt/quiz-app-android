package fr.carlosantunes.monappdeformation.ui.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.carlosantunes.monappdeformation.data.Question
import fr.carlosantunes.monappdeformation.data.QuestionRepository

/*
    Chef d'orchestre qui reçois les données depuis le data/repository et le renvoie ves le fragment
    et à l'inversse reçois les retour depuis la vue et effectue les traitements qui en désoulent

     # Identifiez les états de l’écran de quiz
        - la question en cours ;
        - savoir si l’utilisateur est arrivé à la dernière question, pour afficher le bouton “Finish” ;
        - le score en particulier pour afficher le score final.
 */
class QuizViewModel(private val questionRepository: QuestionRepository) : ViewModel() {
    private lateinit var questions: List<Question>
    private var currentQuestionIndex: Int = 0

    /* 1. Exposez les états grâce aux LiveData (MutableLiveData) */

    /*
       # pattern Observer
        - Un observable émet des informations. Un ou des observateurs reçoivent ces informations.
     */
    var currentQuestion: MutableLiveData<Question> = MutableLiveData<Question>()

    // Score du quiz
    var score: MutableLiveData<Int> = MutableLiveData<Int>(0)

    // Boolean est-ce que l’utilisateur est arrivé à la dernière question
    var isLastQuestion: MutableLiveData<Boolean> = MutableLiveData<Boolean>()


    /* 2. Identifiez les événements de l’écran de quiz */
    fun startQuiz() {
        var question = questionRepository.getQuestion()
        currentQuestion.postValue(question?.get(0))
    }

    fun nextQuestion() {
        val nextIndex = currentQuestionIndex + 1
        if (nextIndex >= questions.size){
            return
        }
        else if (nextIndex == questions.size -1){
            isLastQuestion.postValue(true)
        }
        currentQuestionIndex = nextIndex
        currentQuestion.postValue(questions[currentQuestionIndex])
    }

    fun isAnswerValid(answerIndex: Int): Boolean {
        val question = currentQuestion.value
        val isValid = question != null && question.getAnswerIndex() == answerIndex //question?.answerIndex == answerIndex
        val currentScore = score.value
        if (currentScore != null && isValid) {
            score.value = currentScore + 1
        }
        return isValid
    }


}