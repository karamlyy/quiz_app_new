package com.karamlyy.myquizapp

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var answerButtons: Array<Button>
    private lateinit var currentQuestion: Question
    private lateinit var questionList: ArrayList<Question>
    private var questionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.questionText)
        answerButtons = arrayOf(
            findViewById(R.id.answer_button1),
            findViewById(R.id.answer_button2),
            findViewById(R.id.answer_button3),
            findViewById(R.id.answer_button4)
        )

        questionList = arrayListOf(
            Question("What is the capital of France?", arrayOf("Paris", "Berlin", "Rome", "Madrid"), 0),
            Question("What is the largest planet in our solar system?", arrayOf("Earth", "Jupiter", "Mars", "Venus"), 1),
            Question("Who wrote the Harry Potter book series?", arrayOf("J.K. Rowling", "Stephen King", "Dan Brown", "George R.R. Martin"), 0),
            Question("What is the largest ocean on Earth?", arrayOf("Indian Ocean", "Pacific Ocean", "Atlantic Ocean", "Arctic Ocean"), 1),
            Question("What is the world's tallest mountain?", arrayOf("Mount Everest", "K2", "Kangchenjunga", "Lhotse"), 0),
            Question("2 + 3 = ?", arrayOf("4", "2", "1", "5"), 3),

        )

        showQuestion(questionList[questionIndex])

        for (i in answerButtons.indices) {
            answerButtons[i].setOnClickListener {
                checkAnswer(i)
                if (questionIndex < questionList.size - 1) {
                    questionIndex++
                    showQuestion(questionList[questionIndex])
                } else {
                    showScore()
                }
            }
        }
    }

    private fun showQuestion(question: Question) {
        currentQuestion = question
        questionTextView.text = question.text
        for (i in answerButtons.indices) {
            answerButtons[i].text = question.answers[i]
        }
    }

    private fun checkAnswer(answerIndex: Int) {
        if (answerIndex == currentQuestion.correctAnswerIndex) {
            score++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showScore() {
        val result = "You scored $score out of ${questionList.size}"
        AlertDialog.Builder(this)
            .setTitle("Quiz Result")
            .setMessage(result)
            .setPositiveButton("Restart Quiz") { _, _ ->
                restartQuiz()
            }
            .setNegativeButton("Exit Quiz") { _, _ ->
                finish()
            }
            .show()
    }

    private fun restartQuiz() {
        questionIndex = 0
        score = 0
        showQuestion(questionList[questionIndex])
    }
}

data class Question(val text: String, val answers: Array<String>, val correctAnswerIndex: Int)
