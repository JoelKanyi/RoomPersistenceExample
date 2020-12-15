package com.kanyideveloper.roompersistenceexample.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.roompersistenceexample.R
import com.kanyideveloper.roompersistenceexample.model.User
import com.kanyideveloper.roompersistenceexample.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_add, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val firstName = updateFirstName.text.toString()
        val lastName = updateLastName.text.toString()
        val age = updateAge.text

        if(inputsCheck(firstName,lastName,age)){
            //Create user object
            val user = User(0,firstName,lastName,Integer.parseInt(age.toString()))

            //Add user to the database
            userViewModel.addUser(user)

            Toast.makeText(requireContext(),"Data added Successfully",Toast.LENGTH_LONG).show()

            //Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(),"Please fill out your details",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputsCheck(fname: String, lname: String, age: Editable) : Boolean {
        return !(TextUtils.isEmpty(fname) && TextUtils.isEmpty(lname) && age.isEmpty())
    }
}