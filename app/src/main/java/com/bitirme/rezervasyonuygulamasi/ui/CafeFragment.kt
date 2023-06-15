package com.bitirme.rezervasyonuygulamasi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bitirme.rezervasyonuygulamasi.R
import com.bitirme.rezervasyonuygulamasi.adapter.MyAdapter
import com.bitirme.rezervasyonuygulamasi.databinding.FragmentCafeBinding
import com.bitirme.rezervasyonuygulamasi.model.Item
import com.google.firebase.database.*

class CafeFragment : Fragment(R.layout.fragment_cafe) {
    private lateinit var binding: FragmentCafeBinding
    private var database: DatabaseReference? = null

    companion object {
        const val ITEMS = "items"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        fetchDataFromFirebase()
    }


    private fun getDataFromFirebase(callback: (List<Item>) -> Unit) {
        val itemList: MutableList<Item> = mutableListOf()

        val dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Item::class.java)
                    item?.let { itemList.add(it) }
                }
                callback(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                //Hata Mesajı
            }
        }
        database?.child(ITEMS)?.addListenerForSingleValueEvent(dataListener)
    }


    private fun fetchDataFromFirebase() {
        getDataFromFirebase { itemList ->
            val adapter = MyAdapter(itemList, object : MyAdapter.ItemClickListener {
                override fun onItemClick() {
                    Navigation.findNavController(view!!)
                        .navigate(R.id.action_cafeFragment_to_detailFragment)
                }
            })
            binding.recyclerView.adapter = adapter
        }
    }
}




