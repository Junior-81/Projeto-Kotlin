package com.example.coursestrack.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coursestrack.data.model.Institution
import com.example.coursestrack.databinding.ItemSimpleCardBinding


class InstitutionAdapter : RecyclerView.Adapter<InstitutionViewHolder>() {
    private var institutions = mutableListOf<Institution>()
    fun setInstitutionList(institutions: List<Institution>) {
        this.institutions = institutions.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstitutionViewHolder {
        val binding =
            ItemSimpleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InstitutionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InstitutionViewHolder, position: Int) {
        val institution = institutions[position]
        holder.bind(institution)
    }

    override fun getItemCount(): Int = institutions.size

}

class InstitutionViewHolder(private val binding: ItemSimpleCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(institution: Institution) {
        binding.productName.text = institution.name

        // Configurar listeners para os botões, se necessário
        binding.createMatterBtn.setOnClickListener {
            // Handle edit button click
        }
        binding.deleteMatterBtn.setOnClickListener {
            // Handle delete button click
        }
    }
}
