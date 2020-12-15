package com.kanyideveloper.roompersistenceexample.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.delete){
            val builder = AlertDialog.Builder(requireContext())

            builder.setPositiveButton("Yes") { _, _ ->
                userViewModel.deleteAllUsers()
                Toast.makeText(requireContext(),"Deleted everything successfully", Toast.LENGTH_LONG).show()

            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete Everything")
            builder.setMessage("Are you sure you want to delete everything?")
            builder.create().show()
        }
        return super.onOptionsItemSelected(item)
    }
}