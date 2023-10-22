package fr.carlosantunes.monappdeformation.ui.quiz

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.carlosantunes.monappdeformation.MYAPP
import fr.carlosantunes.monappdeformation.R
import fr.carlosantunes.monappdeformation.databinding.FragmentQuizBinding
import fr.carlosantunes.monappdeformation.ui.welcome.WelcomeFragment


/**
 * Un fragment qui affiche un quiz avec des questions et des réponses.
 */
class QuizFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private lateinit var binding: FragmentQuizBinding

    /**
     * Appelé lorsque le fragment est créé. Il est généralement utilisé pour initialiser
     * les ressources et les composants du fragment.
     *
     * @param savedInstanceState Les données de l'état précédent du fragment, s'il y en a.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, ViewModelFactory.getInstance())[QuizViewModel::class.java]
    }

    /**
     * Crée et configure la vue du fragment. Cela inclut généralement l'inflation de la
     * mise en page et l'initialisation des éléments de l'interface utilisateur.
     *
     * @param inflater Le service d'inflation pour la mise en page.
     * @param container Le conteneur parent dans lequel la vue du fragment doit être attachée.
     * @param savedInstanceState Les données de l'état précédent du fragment, s'il y en a.
     * @return La vue racine du fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Appelé lorsque la vue associée au fragment est créée. Cette méthode est appelée après
     * que la vue a été créée et peut être utilisée pour initialiser et configurer les composants
     * de l'interface utilisateur.
     *
     * @param view La vue racine du fragment.
     * @param savedInstanceState Les données de l'état précédent du fragment, s'il y en a.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Démarre le quiz
        viewModel.startQuiz()

        // Observer la question en cours
        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer { question ->
            // Met à jour les éléments de l'interface utilisateur avec la nouvelle question
            binding.question.text = question.getQuestion()
            binding.answer1.text = question.getChoiceList().get(0)
            binding.answer2.text = question.getChoiceList().get(1)
            binding.answer3.text = question.getChoiceList().get(2)
            binding.answer4.text = question.getChoiceList().get(3)
            binding.next.visibility = View.INVISIBLE
        })

        // Ecouteur d'événement (click) sur chaque réponse
        binding.answer1.setOnClickListener {
            updateAnswer(binding.answer1, 0)
        }
        binding.answer2.setOnClickListener {
            updateAnswer(binding.answer2, 1)
        }
        binding.answer3.setOnClickListener {
            updateAnswer(binding.answer3, 2)
        }
        binding.answer4.setOnClickListener {
            updateAnswer(binding.answer4, 3)
        }

        // Changer de question aprés avoir donner la réponse
        binding.next.setOnClickListener(View.OnClickListener() {
            val islastQuestion: Boolean = viewModel.isLastQuestion.value == true
            if (islastQuestion) {
                displayResultDialog()
                Log.i(MYAPP, "Dernière question affichage de la boite de dialogue")
            } else {
                Log.i(MYAPP, "Bouton next, affichage de la question suivante")
                viewModel.nextQuestion()
                Log.i(MYAPP, "${viewModel.currentQuestion}")
                resetQuestion()
            }
        })

        // Observer l’état isLastQuestion
        viewModel.isLastQuestion.observe(viewLifecycleOwner, Observer { isLastQuestion ->
            if (isLastQuestion) {
                binding.next.setText(R.string.quiz_fragment_next)
            } else {
                binding.next.setText(R.string.quiz_fragment_finish)
            }
        })
    }

    /**
     * Met à jour la réponse sélectionnée par l'utilisateur et affiche la validité de la réponse.
     *
     * @param button Le bouton de réponse sélectionné.
     * @param index L'index de la réponse sélectionnée.
     */
    private fun updateAnswer(button: Button, index: Int) {
        // Validité de la réponse sélectionnée
        showAnswerValidity(button, index)
        // Désactive tous les boutons de réponse
        enableAllAnswers(false)
        // Affiche le boutton next
        binding.next.visibility = View.VISIBLE

        Log.i(MYAPP, "updateAnswer() : button : ${button.text}, index : $index")
    }

    /**
     * Affiche la validité de la réponse sélectionnée par l'utilisateur.
     *
     * @param button Le bouton de réponse sélectionné.
     * @param index L'index de la réponse sélectionnée.
     */
    private fun showAnswerValidity(button: Button, index: Int) {
        val isValid = viewModel.isAnswerValid(index)
        if (isValid) {
            button.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            ) // vert foncé
            binding.validityText.text = getString(R.string.good_answer)
        } else {
            //button.setBackgroundColor(Color.RED)
            button.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            ) // rouge foncé
            binding.validityText.text = getString(R.string.bad_answer)
        }
        // Affichage du message dans le layout
        binding.validityText.visibility = View.VISIBLE
        Log.i(MYAPP, "showAnswerValidity()")
        // Log.i(MYAPP, "button : ${button.setText("Boutton cliqué")}")
    }

    /**
     * Active ou désactive tous les boutons de réponse.
     *
     * @param enable True pour activer les boutons, False pour les désactiver.
     */
    private fun enableAllAnswers(enable: Boolean) {
        val allAnswers = listOf(binding.answer1, binding.answer2, binding.answer3, binding.answer4)
        for (answer in allAnswers) {
            answer.isEnabled = enable
        }
    }

    /**
     *   Remise à zéro de tous les paramètres pour la question suivante
     *
     *   1. remise à zéro de la couleur de background des boutton
     *   2. Rendre le texte de la réponse invisible
     *   3. Rendre le boutton next invisible
     */
    @SuppressLint("ResourceAsColor")
    private fun resetQuestion() {
        var allAnswers: List<Button> =
            listOf(binding.answer1, binding.answer2, binding.answer3, binding.answer4)
        // remise à zéro des couleurs
        for (answer in allAnswers) {
            answer.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimary
                )
            )
        }
        // texte de validation invisible
        binding.validityText.visibility = View.INVISIBLE

        // boutton next invisible
        // binding.next.visibility = View.INVISIBLE

        // Bouttons de réponse actifs
        enableAllAnswers(true)
    }

    /**
     * Affiche la boîte de dialogue de résultat du quiz.
     */
    private fun displayResultDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())

        builder.setTitle("Finished !")
        val score = viewModel.score.value
        builder.setMessage(getString(R.string.quiz_fragment_final_score) + score)
        builder.setPositiveButton("Quit") { dialog, _ ->
            goToWelcomeFragment()
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Navigue vers le fragment d'accueil (WelcomeFragment).
     */
    private fun goToWelcomeFragment(){
        val welcomeFragment : WelcomeFragment = WelcomeFragment.newInstance("1", "2")
        val fragmentManager : FragmentManager = getParentFragmentManager()
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.container, welcomeFragment).commit()

    }

    /**
     * Crée une nouvelle instance de [QuizFragment].
     */
    companion object {
        fun newInstance() = QuizFragment()
    }
}