package hu.bme.aut.android.qnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.qnotes.data.AppDatabase
import hu.bme.aut.android.qnotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_QNotes)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(applicationContext)
    }
}