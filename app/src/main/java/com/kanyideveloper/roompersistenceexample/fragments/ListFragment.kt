package com.kanyideveloper.roompersistenceexample.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kanyideveloper.roompersistenceexample.ListAdapter
import com.kanyideveloper.roompersistenceexample.R
import com.kanyideveloper.roompersistenceexample.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        //Setting up the recyclerView
        val adapter = ListAdapter()
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //ViewModel
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //Reading data from the database
        userViewModel.readAllData.observe(viewLifecycleOwner, { user->
            adapter.setData(user)
        })

        val add : FloatingActionButton = view.findViewById(R.id.floatingActionButton)

        add.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view
    }
}