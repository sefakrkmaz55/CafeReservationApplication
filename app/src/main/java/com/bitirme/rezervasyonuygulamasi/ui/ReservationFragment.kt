package com.bitirme.rezervasyonuygulamasi.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bitirme.rezervasyonuygulamasi.R
import com.bitirme.rezervasyonuygulamasi.databinding.FragmentReservationBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class ReservationFragment : Fragment() {
    private lateinit var binding: FragmentReservationBinding
    private var selectedActivity: String = " "
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().reference

        binding.btnTable.setOnClickListener {
            showTable()
        }

        binding.btnCalendar.setOnClickListener {
            showDatePicker()
        }

        binding.btnActivity.setOnClickListener {
            showDialog()
        }

        binding.btnReservation.setOnClickListener {
            saveReservationData()
        }
    }

    private fun saveReservationData() {
        val table = binding.tvChoosetable.text.toString()
        val activity = binding.tvChooseactivity.text.toString()
        val extra = binding.tvExtra.text.toString()
        val calendar = binding.tvCalendar.text.toString()
        val name = binding.etName.text.toString()
        val phone = binding.etNumber.text.toString()

        val isValidPhoneNumber = phone.length == 11

        val isTableEmpty = table.isBlank()
        val isActivityEmpty = activity.isBlank()
        val isCalendarEmpty = calendar.isBlank()
        val isNameEmpty = name.isBlank()

        if (isValidPhoneNumber && !isTableEmpty && !isActivityEmpty && !isCalendarEmpty && !isNameEmpty) {
            val reservationData = hashMapOf(
                "table" to table,
                "activity" to activity,
                "extra" to extra,
                "calendar" to calendar,
                "name" to name,
                "phone" to phone
            )

            // Veriyi "reservations" adlı veritabanına kaydeder
            database.child("reservations").push().setValue(reservationData)
                .addOnSuccessListener {
                    showSuccessPopup()
                }
                .addOnFailureListener { error ->
                    requireActivity().runOnUiThread {
                        showFailurePopup()
                    }
                }
        } else {
            requireActivity().runOnUiThread {
                showFailurePopup()
            }
        }
    }


    private fun showSuccessPopup() {
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.popup_succes, null)
        val alertDialogBuilder =
            AlertDialog.Builder(requireContext()).setView(dialogView).setTitle("Kayıt Başarılı")

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showFailurePopup() {
        requireActivity().runOnUiThread {
            val inflater = LayoutInflater.from(requireContext())
            val dialogView = inflater.inflate(R.layout.popup_error, null)
            val alertDialogBuilder =
                AlertDialog.Builder(requireContext()).setView(dialogView)

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }


    private fun shouldShowFailurePopup(error: Exception): Boolean {
        val table = binding.tvChoosetable.text.toString()
        val activity = binding.tvChooseactivity.text.toString()
        val extra = binding.tvExtra.text.toString()
        val calendar = binding.tvCalendar.text.toString()
        val name = binding.etName.text.toString()
        val phone = binding.etNumber.text.toString()

        val isValidPhoneNumber = phone.length == 11

        val isTableEmpty = table.isBlank()
        val isActivityEmpty = activity.isBlank()
        val isCalendarEmpty = calendar.isBlank()
        val isNameEmpty = name.isBlank()

        return !isValidPhoneNumber || isTableEmpty || isActivityEmpty || isCalendarEmpty || isNameEmpty
    }


    private fun showTable() {
        val dialogView = layoutInflater.inflate(R.layout.fragment_table, null)
        val alertDialogBuilder =
            AlertDialog.Builder(requireContext()).setView(dialogView).setTitle("Masa Dağılımı")

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        val textViews = arrayOf<TextView>(
            dialogView.findViewById(R.id.tv_one),
            dialogView.findViewById(R.id.tv_two),
            dialogView.findViewById(R.id.tv_three),
            dialogView.findViewById(R.id.tv_four),
            dialogView.findViewById(R.id.tv_five),
            dialogView.findViewById(R.id.tv_six),
            dialogView.findViewById(R.id.tv_seven),
            dialogView.findViewById(R.id.tv_eight),
            dialogView.findViewById(R.id.tv_nine),
            dialogView.findViewById(R.id.tv_ten)
        )

        for (textView in textViews) {
            textView.setOnClickListener {
                val selectedText = textView.text.toString()
                val fragmentDetail = requireActivity().findViewById<TextView>(R.id.tv_choosetable)
                fragmentDetail.text = selectedText
                alertDialog.dismiss()
            }
        }
    }


    private fun showDialog() {
        val options = arrayOf("Doğum Günü", "Evlilik Teklifi", "İş Yemeği", "Standart Oturum")

        AlertDialog.Builder(requireContext()).setTitle("Etkinlik Seçiniz")
            .setItems(options) { dialog, which ->
                dialog.dismiss()
                selectedActivity = options[which]
                showExtraComponents()
            }.show()
    }

    private fun showExtraComponents() {
        if (selectedActivity == "İş Yemeği" || selectedActivity == "Standart Oturum") {
            binding.btnExtra.visibility = View.GONE
            binding.tvExtra.visibility = View.GONE
        } else {
            binding.btnExtra.visibility = View.VISIBLE
            binding.tvExtra.visibility = View.VISIBLE

            binding.btnExtra.setOnClickListener {
                showExtraDialog()
            }
        }
        binding.tvChooseactivity.text = selectedActivity
    }

    private fun showExtraDialog() {
        val options = when (selectedActivity) {
            "Doğum Günü" -> arrayOf("Palyaço", "Süsleme")
            "Evlilik Teklifi" -> arrayOf("Keman", "Mum", "Çiçek")
            else -> emptyArray()
        }

        val checkedItems = BooleanArray(options.size)
        val selectedExtras = ArrayList<String>()

        AlertDialog.Builder(requireContext()).setTitle("Ekstraları Seçin")
            .setMultiChoiceItems(options, checkedItems) { _, which, isChecked ->
                checkedItems[which] = isChecked
            }.setPositiveButton("Tamam") { dialog, _ ->
                for (i in options.indices) {
                    if (checkedItems[i]) {
                        selectedExtras.add(options[i])
                    }
                }
                dialog.dismiss()
                saveSelectedExtras(selectedExtras)
            }.show()
    }

    private fun saveSelectedExtras(selectedExtras: List<String>) {
        val selectedExtrasText = selectedExtras.joinToString(", ")
        binding.tvExtra.text = selectedExtrasText
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, selectedYear, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, monthOfYear, dayOfMonth)
                val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormatter.format(selectedDate.time)
                binding.tvCalendar.text = formattedDate
            }, year, month, day
        )
        datePickerDialog.show()
    }
}



