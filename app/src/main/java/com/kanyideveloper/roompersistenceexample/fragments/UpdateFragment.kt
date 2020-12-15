package com.kanyideveloper.roompersistenceexample.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kanyideveloper.roompersistenceexample.R
import com.kanyideveloper.roompersistenceexample.model.User
import com.kanyideveloper.roompersistenceexample.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.view.updateAge
import kotlinx.android.synthetic.main.fragment_add.view.updateFirstName
import kotlinx.android.synthetic.main.fragment_add.view.updateLastName
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mUserViewModel : UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.updateFirstName.setText(args.currentUser.firstName)
        view.updateLastName.setText(args.currentUser.lastName)
        view.updateAge.setText(args.currentUser.age.toString())

        view.btnUpdate.setOnClickListener {
            updateUser()
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun updateUser(){
        val firstName = updateFirstName.text.toString()
        val lastName = updateLastName.text.toString()
        val age = Integer.parseInt(updateAge.text.toString())

        if (inputsCheck(firstName,lastName,updateAge.text)){

            val updatedUser = User(args.currentUser.id,firstName,lastName,age)

            mUserViewModel.updateUser(updatedUser)

            Toast.makeText(requireContext(),"User updated Successfully",Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }
        else{
            Toast.makeText(requireContext(),"It seems your field are empty",Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputsCheck(fname: String, lname: String, age: Editable) : Boolean {
        return !(TextUtils.isEmpty(fname) && TextUtils.isEmpty(lname) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.delete){
            val builder = AlertDialog.Builder(requireContext())

            builder.setPositiveButton("Yes") { _, _ ->
                mUserViewModel.deleteUser(args.currentUser)
                Toast.makeText(requireContext(),"${args.currentUser.firstName} deleted successfully",Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("${args.currentUser.firstName}")
            builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
            builder.create().show()
        }
        return super.onOptionsItemSelected(item)
    }
}