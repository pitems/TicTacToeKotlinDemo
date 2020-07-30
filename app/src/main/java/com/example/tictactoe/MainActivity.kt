package com.example.tictactoe


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


open class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    var finish = false
    fun btnClick(view:View){
        val btnSelected:Button = view as Button

        var cellID = btnSelected.context.resources.getResourceEntryName(btnSelected.id).replace(Regex("[^0-9]"), "")

        PlayGame(cellID = cellID.toInt(),btnSelected = btnSelected)
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var ActivePlayer=1

    fun PlayGame(cellID:Int,btnSelected:Button)
    {
        if(ActivePlayer==1)
        {
            btnSelected.text="X"
            btnSelected.setBackgroundResource(R.color.green)
            player1.add(cellID)
            ActivePlayer=2
            AutoPlay()
        }else{
            btnSelected.text="O"
            btnSelected.setBackgroundResource(R.color.blue)
            player2.add(cellID)
            ActivePlayer=1
        }

        btnSelected.isEnabled=false
        CheckWinner()
    }

    fun CheckWinner() {
        var winner = -1
        if(finish==false)
        {
            //row 1 && Col 1
            if (player1.contains(1) && player1.contains(2) && player1.contains(3) || player1.contains(1) && player1.contains(4) && player1.contains(7)) {
                winner=1
            }else if(player2.contains(1) && player2.contains(2) && player2.contains(3) || player2.contains(1) && player2.contains(4) && player2.contains(7)){
                winner=2
            }

            //row 2 && Col 2
            if (player1.contains(4) && player1.contains(5) && player1.contains(6) || player1.contains(2) && player1.contains(5) && player1.contains(8)) {
                winner=1
            }else if(player2.contains(4) && player2.contains(5) && player2.contains(6) || player2.contains(2) && player2.contains(5) && player2.contains(8)){
                winner=2
            }

            //row 3 && Col 3
            if (player1.contains(7) && player1.contains(8) && player1.contains(9) || player1.contains(3) && player1.contains(6) && player1.contains(9)) {
                winner=1
            }else if(player2.contains(7) && player2.contains(8) && player2.contains(9) || player2.contains(3) && player2.contains(6) && player2.contains(9)){
                winner=2
            }

            //Diagonal 1
            if (player1.contains(1) && player1.contains(5) && player1.contains(9) || player1.contains(7) && player1.contains(5) && player1.contains(3)) {
                winner=1
            }else if(player2.contains(1) && player2.contains(5) && player2.contains(9) || player2.contains(7) && player2.contains(5) && player2.contains(3)){
                winner=2
            }

            if(winner!= -1){

                if(winner==1)
                {
                    winner=-1
                    MaterialDialog(this).show {
                        title(text = "Victory")
                        message(text = "You WON")
                    }
                    finish=true
                    CleanPlay()
                }else {
                    winner=-1
                    MaterialDialog(this).show {
                        title(text = "Defeat")
                        message(text = "You LOST")
                    }
                    finish=true
                    CleanPlay()
                }
            }
        }


    }

    fun CleanPlay(){
        player1.clear()
        player2.clear()
        emptyCells.clear()
        val layout = findViewById<ViewGroup>(R.id.mainMenu)
        for (i in 0 until layout.childCount) {
            Log.d("Clean PLay", "Inside")
            val child = layout.getChildAt(i)
            if (child is TableRow) {
                val insiderow = findViewById<ViewGroup>(child.id)
                for (i in 0 until insiderow.childCount) {
                    val child = insiderow.getChildAt(i)
                    if (child is Button) {
                        Log.d("Value", child.context.resources.getResourceEntryName(child.id))
                        child.text=""
                        child.setBackgroundResource(R.color.white)
                        child.isEnabled=true

                    }
                }
            }
        }
        MaterialDialog(this).show {
            title(text = "Ready")
            message(text = "Start Playing")
        }
        finish=false
    }
    var emptyCells= ArrayList<Int>()
    fun AutoPlay(){
        if(finish==false){
            emptyCells.clear()
            for (cellID in 1..9){
                if(!(player1.contains(cellID) || player2.contains(cellID))){
                    emptyCells.add(cellID)
                }
            }

            val r= Random
            val randIndex= r.nextInt(emptyCells.size-0)+0

            val cellID = emptyCells[randIndex]// emptyCells.get(randIndex) is the same
            var btnSelect:Button?
            when(cellID){
                1-> btnSelect=btn1
                2-> btnSelect=btn2
                3-> btnSelect=btn3
                4-> btnSelect=btn4
                5-> btnSelect=btn5
                6-> btnSelect=btn6
                7-> btnSelect=btn7
                8-> btnSelect=btn8
                9-> btnSelect=btn9
                else->{
                    btnSelect=btn1
                }
            }

            PlayGame(cellID,btnSelect)
        }

    }
}