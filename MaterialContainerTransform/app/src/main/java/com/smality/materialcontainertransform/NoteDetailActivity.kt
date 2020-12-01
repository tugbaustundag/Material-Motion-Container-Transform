package com.smality.materialcontainertransform

import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.smality.materialcontainertransform.databinding.NoteDetailActivityBinding

class NoteDetailActivity : AppCompatActivity() {
    private lateinit var binding: NoteDetailActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        binding = NoteDetailActivityBinding.inflate(layoutInflater)
        //MainActivity sınıfından gelen noteId değerini aldık
        val noteId = intent.getIntExtra("noteId", 0)
        //Id bilgisine göre Note data class daki not içeriğini alıp, note nesnesine atadık
        val note = notes.find { it.id == noteId }
        binding.note = note
        //
        binding.coordinator.transitionName = noteId.toString()
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        //buildContainerTransform metodunda oluturulan özelliklerin geciş esnasında uygulanması için
        //yapılan atama
        window.sharedElementEnterTransition = buildContainerTransform()
        window.sharedElementReturnTransition = buildContainerTransform()
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
    //RecyclerView nesnesinden MaterialTextView nesnesine geciş anının süresini belirkeyen metod
    private fun buildContainerTransform() =
        MaterialContainerTransform().apply {
            addTarget(binding.coordinator)
            duration = 300
            //geçiş anındaki animasyon hızını ayarlayan metod
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_IN
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
}
