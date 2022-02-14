package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //get the employeeDao variable through the application class
        val employeeDao = (application as EmployeeApp).db.employeeDao()
        binding.btnAdd.setOnClickListener {
            addRecord(employeeDao)
        }
    }

    private fun setupListOfDataIntoRecyclerView(employeesList:ArrayList<EmployeeEntity>,
                                                employeeDao: EmployeeDao) {

        if (employeesList.isNotEmpty()) {
            // Adapter class is initialized and list is passed in the param.

        } else {

            binding.rvItemsList.visibility = View.GONE
            binding.tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }


    private fun addRecord(employeeDao: EmployeeDao) {
        val name = binding.etName.text.toString()
        val email = binding.etEmailId.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty()) {
            lifecycleScope.launch {
                employeeDao.insert(EmployeeEntity(name = name, email = email))
                //因在lifecycleScope，使用applicationContext，而非this
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                binding.etName.text?.clear()
                binding.etEmailId.text?.clear()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Name or Email cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}