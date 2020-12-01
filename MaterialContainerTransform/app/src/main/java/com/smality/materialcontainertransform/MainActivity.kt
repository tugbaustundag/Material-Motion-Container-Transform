package com.smality.materialcontainertransform
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecyclerView()
    }
    private fun setRecyclerView() {
        //Note data class da tanımlanan başlık ve içerikler NotesAdapter sınıfına gönderiliyor
        val adapter = NotesAdapter(notes)
        //RecyclerView içeriğine yani not kutularına tıklanma eventi
        adapter.noteClickListener = object : NotesAdapter.NoteClickListener {
            override fun onNoteClick(id: Int, noteCard: MaterialCardView) {

                val intent = Intent(this@MainActivity, NoteDetailActivity::class.java)
                //Not kutusuna tıkladığı anda yapılan geçiş animasyonunu tetiklediğimiz bölüm
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MainActivity, noteCard, id.toString()
                )
                //İlgili içeriğin id'si NoteDetailActivity sınıfına gönderiliyor
                intent.putExtra("noteId", id)
                startActivity(intent, options.toBundle())
            }
        }
        //Notları grid şeklinde gösterilmesi sağlayan bölüm
        recycler_view.layoutManager = GridLayoutManager(this, 2)
        recycler_view.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                true
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}