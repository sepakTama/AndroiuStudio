package com.example.sendmessage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ----- 以下を追加する -----
        // Buttonへの参照を取得する
        val button = findViewById<Button>(R.id.button)
        // ボタンタップ時の処理を指定する
        button.setOnClickListener {
            // EditTextへの参照を取得する
            val editText = findViewById<EditText>(R.id.editText)
            // メッセージを取り出す
            val message = editText.text.toString()

            // Intentを作成する
            val intent = Intent(this, ResultActivity::class.java)
            // パラメータをセットする
            intent.putExtra("message", message)
            // 画面を遷移させる
            startActivity(intent)
        }
        // ------------------------
    }
}
