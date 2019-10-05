package com.example.cvapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.example.cvapp.R
import com.example.cvapp.models.CVResponse
import com.example.cvapp.models.PastExperience
import com.example.cvapp.viewmodels.ViewModelsProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_cv.*
import kotlinx.android.synthetic.main.past_experience.view.*
import javax.inject.Inject

class CVActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelsProvidersFactory
    @Inject
    lateinit var requestManager: RequestManager

    lateinit var viewModel: CVViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(CVViewModel::class.java)
        viewModel.response().observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.AuthStatus.LOADING -> {
                        showProgressBar(true)
                    }

                    Resource.AuthStatus.SUCCESS -> {
                        pastExperienceContainer.removeAllViews()
                        showProgressBar(false)
                        populateData(it.data)
                    }

                    Resource.AuthStatus.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        showProgressBar(false)
                    }
                }
            }
        })
        viewModel.getCV()
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun populateData(data: CVResponse?) {
        name.text = data?.name
        email.text = data?.email
        phoneNo.text = data?.phones
        profSummaryContent.text = data?.professionalSummary?.stringToList()
        var expList = data?.pastExperience
        expList?.let { it -> addExperience(it) }
    }

    private fun addExperience(list: List<PastExperience>) {
        var linf: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        for (i in list) {
            var v = linf.inflate(R.layout.past_experience, null)
            pastExperienceContainer.addView(v)
            v.role.text = i.role
            v.companyName.text = i.name
            v.skillsContent.text = i.skills
            requestManager.load(i.url)
                .override(resources.getDimension(R.dimen.width).toInt(), resources.getDimension(R.dimen.height).toInt())
                .into(v.imageView)
        }
    }

     fun String.stringToList(): String? {
        var stringArray = this.split(". ",".")
        var convertedString: String? = ""
        stringArray.forEach { i ->
            if (i.isNotEmpty()) {
                convertedString = "$convertedString$i.\n\n"
            }
        }
        return convertedString
    }
}
