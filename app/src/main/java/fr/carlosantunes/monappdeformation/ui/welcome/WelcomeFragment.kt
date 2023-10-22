package fr.carlosantunes.monappdeformation.ui.welcome

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.carlosantunes.monappdeformation.MYAPP
import fr.carlosantunes.monappdeformation.R
import fr.carlosantunes.monappdeformation.databinding.FragmentWelcomeBinding
import fr.carlosantunes.monappdeformation.ui.quiz.QuizFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Fragment d'accueil de l'application qui permet à l'utilisateur de démarrer le quiz.
 * Ce fragment est responsable de la gestion de l'interface utilisateur et de la navigation vers
 * le fragment du quiz.
 *
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Initialisation de vue fragment grace FragmentWelcomeBinding
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Accés aux éléments xml aprés l'initialisation de l'app
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // binding.playButton.setEnabled(true)
        binding.playButton.isEnabled = false

        val usernameText = binding.usernameEditText

        Log.i(MYAPP, "User name : $usernameText")

        // Appel de la méthode lorsque l'utilisateur écrit dans usernameEditText
        this.textChange()

        // Détecter le click sur le boutton L'et's play pour démarrer le quiz
        binding.playButton.setOnClickListener {
            // Navigation vers le fragment quiz
            Log.i(MYAPP, "Click !")

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            // Passer les paramètres appropriés ici ex : param1 param2 etc...
            val quizFragment = QuizFragment.newInstance()
            // Utilisez "replace" au lieu de "add" // container = layout id -> activity_main.xml
            fragmentTransaction.add(R.id.container, quizFragment)
            // Facultatif : ajoutez au back stack si vous souhaitez prendre en charge le retour en arrière
            // fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    // Détecte les changement d'état du usernameEditText
    private fun textChange() {
        /*
            À chaque fois que l'utilisateur saisira une lettre, la méthode afterTextChanged  sera appelée
            Si au moins une lettre a été saisie, alors le bouton doit être actif, sinon il est inactif :
                - Pour activer ou désactiver un bouton, methode (isEnabled)
                - Le texte entré par l'utilisateur est représenté par la variable (s)
                - Pour la convertir en chaîne de caractères methode (toString)
                - Savoir si la chaîne est vide mthode (isEmpty)
        */
        binding.usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ajoutez code ici si nécessaire
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Ajoutez code ici si nécessaire
            }

            override fun afterTextChanged(s: Editable?) {
                /*  Le texte entré par l'utilisateur est représenté par la variable (s)
                    - Il faut convertir en chaîne de caractères methode (toString)
                    - Pour savoir si la chaîne est vide methode (isEmpty)
                    - Modifier l'état du boutton l'orsque le champs texte n'est pas vide
                */
                var text = s.toString()
                var isTextEmpty = text.isEmpty()

                binding.playButton.isEnabled = !isTextEmpty


                Log.i(MYAPP, "text writed is : ($text) count characters is (${text.length}) ")
            }
        })
    }

    override fun onStart() {
        super.onStart()

        // binding.playButton.setEnabled(false)
        binding.playButton.isEnabled = false
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WelcomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}