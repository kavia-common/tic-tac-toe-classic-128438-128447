package org.example.app

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

/**
 * PUBLIC_INTERFACE
 * MainActivity
 * This activity hosts a minimalistic two-player local Tic Tac Toe game.
 * - Interactive 3x3 board
 * - Displays current player, win/draw status
 * - Restart/reset controls
 * - Simple onboarding/instructions dialog on first launch
 */
class MainActivity : Activity() {

    private lateinit var statusText: TextView
    private lateinit var boardGrid: GridLayout
    private lateinit var restartBtn: Button
    private lateinit var resetBtn: Button

    // Game state: 0..8 cells, values: ' ', 'X', 'O'
    private var board: CharArray = CharArray(9) { ' ' }
    private var currentPlayer: Char = 'X'
    private var gameOver: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.tvStatus)
        boardGrid = findViewById(R.id.boardGrid)
        restartBtn = findViewById(R.id.btnRestart)
        resetBtn = findViewById(R.id.btnReset)

        setupBoardButtons()
        updateStatus()

        restartBtn.setOnClickListener { newRound() }
        resetBtn.setOnClickListener { fullReset() }

        if (savedInstanceState == null) {
            showOnboarding()
        } else {
            restoreState(savedInstanceState)
            syncBoardUI()
            updateStatus()
        }
    }

    private fun setupBoardButtons() {
        for (i in 0 until boardGrid.childCount) {
            val cell = boardGrid.getChildAt(i) as Button
            cell.setOnClickListener { onCellClicked(i, cell) }
            styleCell(cell)
        }
    }

    @Suppress("DEPRECATION")
    private fun styleCell(cell: Button) {
        cell.typeface = Typeface.MONOSPACE
        cell.textSize = 28f
        cell.isAllCaps = false
        cell.setTextColor(resources.getColor(R.color.primaryColor))
        cell.background = resources.getDrawable(R.drawable.bg_cell)
    }

    private fun onCellClicked(index: Int, cell: Button) {
        if (gameOver || board[index] != ' ') return

        board[index] = currentPlayer
        cell.text = currentPlayer.toString()

        val winner = checkWinner()
        if (winner != null) {
            gameOver = true
            statusText.text = getString(R.string.status_win, winner)
            highlightWin(winner)
            return
        }
        if (board.all { it != ' ' }) {
            gameOver = true
            statusText.text = getString(R.string.status_draw)
            return
        }

        // Next turn
        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
        updateStatus()
    }

    private fun updateStatus() {
        if (!gameOver) {
            statusText.text = getString(R.string.status_turn, currentPlayer)
        }
    }

    private fun newRound() {
        board = CharArray(9) { ' ' }
        currentPlayer = 'X'
        gameOver = false
        syncBoardUI()
        updateStatus()
        clearHighlights()
    }

    private fun fullReset() {
        // Same as newRound for now
        newRound()
    }

    private fun syncBoardUI() {
        for (i in 0 until boardGrid.childCount) {
            val cell = boardGrid.getChildAt(i) as Button
            cell.text = if (board[i] == ' ') "" else board[i].toString()
        }
    }

    @Suppress("DEPRECATION")
    private fun clearHighlights() {
        for (i in 0 until boardGrid.childCount) {
            val cell = boardGrid.getChildAt(i) as Button
            cell.background = resources.getDrawable(R.drawable.bg_cell)
        }
    }

    @Suppress("DEPRECATION")
    private fun highlightWin(winner: Char) {
        val winLines = winningLines()
        val line = winLines.firstOrNull { (a, b, c) ->
            board[a] == winner && board[b] == winner && board[c] == winner
        } ?: return

        val accent = resources.getDrawable(R.drawable.bg_cell_win)
        listOf(line.first, line.second, line.third).forEach { idx ->
            val cell = boardGrid.getChildAt(idx) as Button
            cell.background = accent
        }
    }

    private fun winningLines(): List<Triple<Int, Int, Int>> = listOf(
        Triple(0, 1, 2), Triple(3, 4, 5), Triple(6, 7, 8), // rows
        Triple(0, 3, 6), Triple(1, 4, 7), Triple(2, 5, 8), // cols
        Triple(0, 4, 8), Triple(2, 4, 6) // diagonals
    )

    private fun checkWinner(): Char? {
        for ((a, b, c) in winningLines()) {
            val v = board[a]
            if (v != ' ' && v == board[b] && v == board[c]) return v
        }
        return null
    }

    private fun showOnboarding() {
        AlertDialog.Builder(this)
            .setTitle(R.string.onboarding_title)
            .setMessage(R.string.onboarding_message)
            .setPositiveButton(R.string.onboarding_ok) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharArray("board", board)
        outState.putChar("currentPlayer", currentPlayer)
        outState.putBoolean("gameOver", gameOver)
    }

    private fun restoreState(savedInstanceState: Bundle) {
        savedInstanceState.getCharArray("board")?.let { board = it }
        currentPlayer = savedInstanceState.getChar("currentPlayer", 'X')
        gameOver = savedInstanceState.getBoolean("gameOver", false)
    }
}
