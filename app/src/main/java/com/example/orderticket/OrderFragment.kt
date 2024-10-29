package com.example.orderticket

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.orderticket.databinding.FragmentOrderBinding
import java.text.SimpleDateFormat
import java.util.Locale

class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            jenisTiket.setOnClickListener {
                val action = OrderFragmentDirections.actionOrderFragmentToCheckoutFragment()
                findNavController().navigate(action)
            }

            // Retrieve ticket type from CheckoutFragment
            findNavController().currentBackStackEntry
                ?.savedStateHandle?.let { handle ->
                    handle.getLiveData<String>("jenistiket")
                        .observe(viewLifecycleOwner) { res ->
                            jenisTiket.setText(res)
                        }
                }

            btnBuy.setOnClickListener {
                val ticketType = jenisTiket.text.toString()

                if (ticketType.isEmpty()) {
                    // Display toast if no ticket type is selected
                    Toast.makeText(context, "Pilih tipe tiket terlebih dahulu", Toast.LENGTH_SHORT).show()
                } else {
                    // Display confirmation toast with ticket type and current date and time
                    val currentTime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis())
                    Toast.makeText(context, "Tiket dengan tipe $ticketType berhasil dipesan pada $currentTime", Toast.LENGTH_LONG).show()

                    // Navigate back
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
