package com.sogeti.inno.cherry.activities.ui.choregraphy

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.media.Image
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.MainActivity
import com.sogeti.inno.cherry.activities.models.Choregraphy
import com.sogeti.inno.cherry.activities.models.ChoregraphyMove
import com.sogeti.inno.cherry.comm.*
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.ChoregraphiesHolder
import com.sogeti.inno.cherry.utils.IdleHandler
import com.sogeti.inno.cherry.utils.ToolbarHandler
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.choregraphy_main_layout.*
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

class ChoregraphyMainActivity: AppCompatActivity() {
    var adapter: ChoregraphyMovesAdapter? = null
    private lateinit var currentChoregraphy: Choregraphy
    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choregraphy_main_layout)
        ToolbarHandler.initToolbar(this)

        ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Voici le jeu de la chorégraphie. Commence d'abord par choisir les 4 mouvements que tu préfères !")))

        initButtons()
        getChoregraphy()

        adapter = ChoregraphyMovesAdapter(this, currentChoregraphy.moves)
        choregraphy_moves_grid_view.adapter = adapter

        currentChoregraphy.name="poppyDance"
    }

    private fun initButtons() {

        // init buttons
        val playBtn = findViewById<ImageButton>(R.id.play_choregraphy_btn)
        playBtn.setBackgroundColor(Color.parseColor("#ffffff"))
        val linearLayout = findViewById<LinearLayout>(R.id.musicLayout)
        val linearPlay = findViewById<LinearLayout>(R.id.mainPlay)

        val popBtn = findViewById<ImageButton>(R.id.choregraphy_pop_button)
        val rockBtn = findViewById<ImageButton>(R.id.choregraphy_rock_button)
        val electroBtn = findViewById<ImageButton>(R.id.choregraphy_electro_button)
        val moveTable = findViewById<TableLayout>(R.id.add_move_table)

        val imageView = findViewById<ImageView>(R.id.imageNote)

        val textAccueil = findViewById<TextView>(R.id.choregraphy_main_title)

        popBtn.setBackgroundColor(Color.parseColor("#ffffff"))
        rockBtn.setBackgroundColor(Color.parseColor("#ffffff"))
        electroBtn.setBackgroundColor(Color.parseColor("#ffffff"))

        // cache buttons
        showHide(linearLayout)
        showHide(linearPlay)
        imageView.visibility = View.GONE

        // on click play
        playBtn.setOnClickListener {
            //ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Et c'est partiii !")))
            linearLayout.visibility = View.GONE
            linearPlay.visibility = View.GONE
            moveTable.visibility = View.GONE
            textAccueil.visibility = View.GONE

            ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Et c'est parti !")))

            Glide.with(this).asGif().load(R.drawable.cat).into(imageView)
            imageView.visibility = View.VISIBLE
            Handler().postDelayed(
                {
                    // This method will be executed once the timer is over
                    val movesList = ArrayList<String>()
                    for (move in currentChoregraphy.moves) {
                        movesList.add(move.name)
                    }
                    if (! currentChoregraphy.isSaved)
                        ControlServer.sendCommand(CommandBuilder.toJson(AddChoregraphy(movesList, currentChoregraphy.name, currentChoregraphy.music)))
                    ControlServer.sendCommand(CommandBuilder.toJson(PlayChoregraphy(currentChoregraphy.name)))
                    if (! currentChoregraphy.isSaved)
                        ControlServer.sendCommand(CommandBuilder.toJson(RemoveChoregraphy(currentChoregraphy.name)))
                    Handler().postDelayed(
                        {
                            Glide.with(this).asGif().load(R.drawable.pug).into(imageView)
                            Handler().postDelayed(
                                {
                                    ControlServer.sendCommand(CommandBuilder.toJson(ResetPoppy()))
                                    currentChoregraphy.moves.removeAll(currentChoregraphy.moves)
                                    for (i in 0 until moveTable.childCount) {
                                        val row = moveTable.getChildAt(i) as TableRow
                                        for (j in 0 until row.childCount) {
                                            val button = row.getChildAt(j) as Button
                                            button.setBackgroundColor(Color.parseColor("#ffffff"))
                                            button.isSelected = false
                                        }
                                    }

                                    popBtn.setBackgroundColor(Color.parseColor("#ffffff"))
                                    rockBtn.setBackgroundColor(Color.parseColor("#ffffff"))
                                    electroBtn.setBackgroundColor(Color.parseColor("#ffffff"))
                                    ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("J'espère que ça t'as plu ! Tu peux recommencer si tu veux !")))
                                    linearLayout.visibility = View.VISIBLE
                                    linearPlay.visibility = View.VISIBLE
                                    moveTable.visibility = View.VISIBLE
                                    textAccueil.visibility = View.VISIBLE
                                    imageView.visibility = View.GONE
                                },
                                20000 // value in milliseconds
                            )
                        }
                        ,10000)

                },
                5000 // value in milliseconds
            )


        }

        for (i in 0 until moveTable.childCount) {
            val row = moveTable.getChildAt(i) as TableRow
            for (j in 0 until row.childCount) {
                val button = row.getChildAt(j) as Button
                button.setBackgroundColor(Color.parseColor("#ffffff"))
                button.setOnClickListener {
                    val tag = button.tag.toString()
                    val id = resources.getIdentifier(tag, "drawable", packageName)
                    if(!button.isSelected) {
                        if(currentChoregraphy.moves.count() < 4){
                            button.setBackgroundColor(Color.CYAN)
                            button.isSelected = true
                            currentChoregraphy.moves.add(ChoregraphyMove(button.text.toString(), id, getImageName(id)))
                            if(currentChoregraphy.moves.count() == 4) {
                                ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Choisis maintenant la musique que tu préfères !")))
                                linearLayout.visibility = View.VISIBLE
                            }
                            if(currentChoregraphy.moves.count() == 2) {
                                //ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Plus que 2 !")))
                            }
                        }else{
                            ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Tu as déja 4 mouvements ! Choisis une musique ou supprime des mouvements")))
                        }
                    }else {
                        button.setBackgroundColor(Color.parseColor("#ffffff"))
                        button.isSelected = false
                        currentChoregraphy.moves.remove(ChoregraphyMove(button.text.toString(), id, getImageName(id)))
                    }
                    adapter!!.notifyDataSetChanged()
                }

            }
        }

        popBtn.setOnClickListener { changeMusic("pop") }
        rockBtn.setOnClickListener { changeMusic("rock") }
        electroBtn.setOnClickListener { changeMusic("electro") }

    }
    fun showHide(view:View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.INVISIBLE
        } else{
            View.VISIBLE
        }
    }
    private fun deleteChoregraphy() {
        val deleteDialog = AlertDialog.Builder(this)
            .setTitle("Confirmation de suppression")
            .setMessage("Voulez vous vraiment supprimer cette chorégraphie?")
            .setPositiveButton("Oui") { _, _ ->
                ChoregraphiesHolder.instance.choregraphiesList.remove(currentChoregraphy)
                ControlServer.sendCommand(CommandBuilder.toJson(RemoveChoregraphy(currentChoregraphy.name)))
                startActivity(Intent(this, ChoregraphyChoiceActivity::class.java))
            }
            .setNegativeButton("Non") { _, _ ->
                Toasty.info(this, "Suppression annulée", Toast.LENGTH_SHORT).show()
            }
            .create()
        deleteDialog.show()
    }

    private fun changeMusic(music: String, isOriginalMusic: Boolean = false) {
        val originalBtn = getMusicButton(currentChoregraphy.music)
        originalBtn.setBackgroundColor(Color.parseColor("#ffffff"))
        currentChoregraphy.music = music
        val newBtn = when (music) {
            "rock" -> findViewById<ImageButton>(R.id.choregraphy_rock_button)
            "electro" -> findViewById<ImageButton>(R.id.choregraphy_electro_button)
            "pop" -> findViewById<ImageButton>(R.id.choregraphy_pop_button)
            else -> null
        }
        if(newBtn != null) {

            newBtn.setBackgroundColor(Color.CYAN)
            ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("C'est bon, tu peux envoyer la sauce !")))

        }
        //val playBtn = findViewById<Button>(R.id.play_choregraphy_btn)
        //playBtn.setBackgroundColor(Color.WHITE)
        //playBtn.isEnabled = true;
        //playBtn.visibility = View.VISIBLE
        val linearPlay = findViewById<LinearLayout>(R.id.mainPlay)
        linearPlay.visibility = View.VISIBLE

    }

    private fun getMusicButton(music: String): ImageButton {
        return when (music) {
            "rock" -> findViewById(R.id.choregraphy_rock_button)
            "electro" -> findViewById(R.id.choregraphy_electro_button)
            else -> findViewById(R.id.choregraphy_pop_button)
        }
    }

    /*
    private fun saveChoregraphy() {
        if (currentChoregraphy.name == "") {
            val name = findViewById<EditText>(R.id.choregraphy_name).text.toString()
            if (name == "") {
                Toasty.error(this, "Veuillez entrer un nom pour la chorégraphie", Toast.LENGTH_LONG).show()
                return
            }
            currentChoregraphy.name = name
        }
        ChoregraphiesHolder.instance.choregraphiesList[index] = currentChoregraphy
        Toasty.success(this, "Chorégraphie sauvegardée").show()
        val movesList = ArrayList<String>()
        for (move in currentChoregraphy.moves) {
            movesList.add(move.name)
        }
        if (currentChoregraphy.isSaved)
            ControlServer.sendCommand(CommandBuilder.toJson(RemoveChoregraphy(currentChoregraphy.name)))
        ControlServer.sendCommand(CommandBuilder.toJson(AddChoregraphy(movesList, currentChoregraphy.name, currentChoregraphy.music)))
        currentChoregraphy.isSaved = true
        Log.d("ChoregraphyActivity", "Choregraphy save cmd sent")
    }
*/

    private fun getChoregraphy() {
        val currentIndex = intent.getIntExtra("choregraphy_index", 0)
        index = currentIndex
        currentChoregraphy = ChoregraphiesHolder.instance.choregraphiesList[currentIndex]
    }

    private fun getImageName(imageId: Int): String {
        return when (imageId) {
            R.drawable.dab -> "dab"
            R.drawable.twist -> "twist"
            R.drawable.balancing -> "balancing"
            R.drawable.shaking -> "shaking"
            R.drawable.r_arm_fwd -> "r_arm_fwd"
            R.drawable.l_arm_fwd -> "l_arm_fwd"
            R.drawable.r_arm_back -> "r_arm_bwd"
            R.drawable.l_arm_back -> "l_arm_bwd"
            R.drawable.r_arm_up -> "r_elbow_up"
            R.drawable.l_arm_up -> "l_elbow_up"
            else -> {
                Log.d("Name getter", "Name not found for $imageId")
                return ""
            }
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        IdleHandler.resetCount()
    }

}