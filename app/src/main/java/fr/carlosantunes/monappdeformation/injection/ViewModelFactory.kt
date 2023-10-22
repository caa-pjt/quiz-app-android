import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.carlosantunes.monappdeformation.data.QuestionBank
import fr.carlosantunes.monappdeformation.data.QuestionRepository
import fr.carlosantunes.monappdeformation.ui.quiz.QuizViewModel

class ViewModelFactory private constructor(private val questionRepository: QuestionRepository) :
    ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var factory: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory {
            return factory ?: synchronized(this) {
                factory ?: ViewModelFactory(QuestionRepository(QuestionBank.getInstance())).also {
                    factory = it
                }
            }
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(questionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
