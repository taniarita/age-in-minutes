package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    //O conteúdo dessas views muda
    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Referência das views do layout
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        //Seta o onclicklistener parão o botão
        btnDatePicker.setOnClickListener {
            //Chama a função clickDatePicker quando botão é clicado
            clickDatePicker()

        }
    }

    //Função para mostrar o calendário (date picker)
    private fun clickDatePicker() {
        //Instancia um calendário com fuso e localidade padrão
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        //Cria o datepicker
        val dpd = DatePickerDialog(
            this,
            //O listener indica que o usuário acabou de selecionar uma data. Aqui, quando clica no botão, o calendário já aparece
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->

                //Passo 2: selecionar a data e faze-la aparecer na textview
                Toast.makeText(
                    this,
                    "Year was $selectedYear, month was ${selectedMonth + 1}, day of month was $selectedDayOfMonth",
                    Toast.LENGTH_LONG
                ).show()

                //Aqui a data selecionada é definida em um formato dia/mês/ano
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

                //Data selecionada é definida para o TextView e fica visível ao usuário.
                tvSelectedDate?.text = selectedDate

                /** Passo 3: Aqui nós pegamos uma instância do Date Formatter
                 * pois ele irá formatar nossa data selecionada no formato que nós
                 * passamos como parâmetro e Locale. Aqui eu passei o formato como dd/MM/yyyy.*/
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                /** O formatador analisará a data selecionada no objeto Date para que possamos
                 * simplesmente obter a data em milissegundos.*/
                val theDate = sdf.parse(selectedDate)

                //usar safe call operator com .let para evitar o crash caso theDate seja null
                theDate?.let {

                    val selectedDateInMinutes = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {

                        val currenteDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currenteDateInMinutes - selectedDateInMinutes

                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }

                }

            },
            year,
            month,
            day
        )

        /** Define a data máxima suportada em milissegundos desde 1º de janeiro de 1970 00:00:00 no fuso horário.*/
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }
}

