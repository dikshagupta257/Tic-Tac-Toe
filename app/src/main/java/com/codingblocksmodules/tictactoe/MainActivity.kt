package com.codingblocksmodules.tictactoe

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.codingblocksmodules.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    lateinit var board : Array<Array<Button>>
    var player = true
    var turn_count = 0
    var boardStatus = Array(3){IntArray(3)}
    var name1 : String? = "X"
    var name2:String? = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        val i: Intent = getIntent()
        name1 = i.getStringExtra("Name1")
        name2 = i.getStringExtra("Name2")


        Log.d("TAG", "in MainActivity onCreate: $name1 and $name2")

        binding.apply{
            board = arrayOf(arrayOf(button1 , button2 , button3),
                            arrayOf(button4 , button5 , button6),
                            arrayOf(button7 , button8 , button9))

            resetBtn.setOnClickListener {
                turn_count = 0
                player = true
                initialiseBoardStatus()
            }
        }

        for(row in board){
            for(button in row){
                button.setOnClickListener(this)
            }
        }

        initialiseBoardStatus()

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button1->{
                updateBoard(row = 0 , col = 0 , player = player)
                //binding.button1.setBackgroundColor(R.color.)
            }
            R.id.button2->{
                updateBoard(row = 0 , col = 1 , player = player)
            }
            R.id.button3->{
                updateBoard(row = 0 , col = 2 , player = player)
            }
            R.id.button4->{
                updateBoard(row = 1 , col = 0 , player = player)
            }
            R.id.button5->{
                updateBoard(row = 1 , col = 1 , player = player)
            }
            R.id.button6->{
                updateBoard(row = 1 , col = 2 , player = player)
            }
            R.id.button7->{
                updateBoard(row = 2 , col = 0 , player = player)
            }
            R.id.button8->{
                updateBoard(row = 2 , col = 1 , player = player)
            }
            R.id.button9->{
                updateBoard(row = 2 , col = 2 , player = player)
            }
        }
        player = !(player)
        turn_count++
        if(player){
            updateDisplay("Player $name1 turn" , R.color.green)
        }else{
            updateDisplay("Player $name2 turn" , R.color.yellow)
        }
        if(turn_count == 9){
            updateDisplay("Game Draw" , R.color.black)
        }

        checkWinner()

    }

    private fun checkWinner() {
        //horizontal rows
        for(i in 0..2){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][1] == boardStatus[i][2]){
                if(boardStatus[i][0] == 1){
                    updateDisplay("Player ${name1?.uppercase()} is Winner" , R.color.black)
                    break
                }else if(boardStatus[i][0] == 0){
                    updateDisplay("Player ${name2?.uppercase()} is Winner" , R.color.black)
                    break
                }
            }
        }

        //vertical rows
        for(i in 0..2){
            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[1][i] == boardStatus[2][i]){
                if(boardStatus[0][i] == 1){
                    updateDisplay("Player ${name1?.uppercase()} is Winner", R.color.black)
                    break
                }else if(boardStatus[0][i] == 0){
                    updateDisplay("Player ${name2?.uppercase()} is Winner", R.color.black)
                    break
                }
            }
        }

        //Diagonal 1

        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][2]){
            if(boardStatus[0][0] == 1){
                    updateDisplay("Player ${name1?.uppercase()} is Winner",R.color.black)

            }else if(boardStatus[0][0] == 0){
                    updateDisplay("Player ${name2?.uppercase()} is Winner",R.color.black)

            }
        }

        //Diagonal 2

        if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]){
            if(boardStatus[0][2] == 1){
                updateDisplay("Player ${name1?.uppercase()} is Winner",R.color.black)

            }else if(boardStatus[0][2] == 0){
                updateDisplay("Player ${name2?.uppercase()}is Winner",R.color.black)

            }
        }

    }

    private fun disableButton(){
        for(row in board){
            for(button in row){
                button.isEnabled = false
            }
        }
    }

    private fun initialiseBoardStatus() {
        binding.displayTv.text = "Tic-Tac-Toe"
        for(i in 0..2){
            for(j in 0..2){
                boardStatus[i][j] = -1
            }
        }

        for(row in board){
            for(button in row){
                button.isEnabled = true
                button.text =""
                button.setBackgroundColor(resources.getColor(R.color.grey))
            }
        }
    }

    private fun updateDisplay(displayText: String , color: Int) {
        binding.displayTv.text = displayText
        binding.displayTv.setTextColor(ContextCompat.getColor(this, color))
        if(displayText.contains("Winner")){
            disableButton()
        }
    }

    private fun updateBoard(row: Int, col: Int, player: Boolean) {
        var text  = if (player)  "X" else "0"
        var value  = if(player) 1 else 0
        var color = if(player) resources.getColor(R.color.green) else resources.getColor(R.color.yellow)
        board[row][col].apply {
            isEnabled = false
            setText(text)
            setBackgroundColor(color)
        }
        boardStatus[row][col] = value
    }
}