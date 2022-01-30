package com.codingblocksmodules.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.codingblocksmodules.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var board : Array<Array<Button>>
    private var player = true
    private var turnCount = 0
    private var boardStatus = Array(3){IntArray(3)}
    private var name1 : String? = "X"
    private var name2:String? = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        name1 = intent.getStringExtra("Name1")
        name2 = intent.getStringExtra("Name2")

        binding.apply{
            //initializing board of buttons once this activity is created
            board = arrayOf(arrayOf(button1 , button2 , button3),
                            arrayOf(button4 , button5 , button6),
                            arrayOf(button7 , button8 , button9))

            //resetting every parameter and re-initializing the board if reset button is clicked
            resetBtn.setOnClickListener {
                turnCount = 0
                player = true
                initialiseBoardStatus()
            }
        }

        //to handle click if any button in "board" matrix is clicked
        for(row in board){
            for(button in row){
                button.setOnClickListener(this)
            }
        }

        initialiseBoardStatus()

    }

    /**handle clicks according to the button on "board" clicked and then updating other parameters:-
     * changing the state of player variable i.e true to false or false to true
     * incrementing the turn count by 1 to keep track of no of moves played up till now
       */
    override fun onClick(v: View) {
        when(v.id){
            R.id.button1->{
                updateBoard(row = 0 , col = 0 , player = player)
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
        turnCount++

        /** if player value is true i.e., player 1 then update the activity display heading by his name,
           else update it with other player's name*/
        if(player){
            updateDisplay("Player $name1 turn" , R.color.green)
        }else{
            updateDisplay("Player $name2 turn" , R.color.yellow)
        }

        //it total turn count becomes 9 that means nobody won and game is draw
        if(turnCount == 9){
            updateDisplay("Game Draw" , R.color.black)
        }

        //check winner each time a button on the board is pressed
        checkWinner()

    }

    //to check the winner with the help of "boardStatus" matrix
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

    /**initializing the board by setting the display to default one, setting all the values on "boardStatus"
       back to -1 and enabling all the buttons on the board along with their text set as empty
       and background color as default one*/
    private fun initialiseBoardStatus() {
        binding.displayTv.text = getString(R.string.application_name)
        binding.displayTv.setTextColor(ContextCompat.getColor(this, R.color.black))
        for(i in 0..2){
            for(j in 0..2){
                boardStatus[i][j] = -1
            }
        }

        for(row in board){
            for(button in row){
                button.isEnabled = true
                button.text =""
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            }
        }
    }

    //to set the text above the board in order to update the user about the game's status
    private fun updateDisplay(displayText: String , color: Int) {
        binding.displayTv.text = displayText
        binding.displayTv.setTextColor(ContextCompat.getColor(this, color))
        if(displayText.contains("Winner")){
            disableButton()
        }
    }

    //disable button once it has been clicked while the game is being played
    private fun disableButton(){
        for(row in board){
            for(button in row){
                button.isEnabled = false
            }
        }
    }

    //update the appearance and boardStatus according to which user has clicked which button
    private fun updateBoard(row: Int, col: Int, player: Boolean) {
        val text  = if (player)  "X" else "0"
        val value  = if(player) 1 else 0
        val color = if(player) ContextCompat.getColor(this, R.color.green) else ContextCompat.getColor(this, R.color.yellow)
        board[row][col].apply {
            isEnabled = false
            setText(text)
            setBackgroundColor(color)
        }
        boardStatus[row][col] = value
    }
}