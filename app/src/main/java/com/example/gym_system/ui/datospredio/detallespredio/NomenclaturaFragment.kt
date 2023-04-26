package com.example.gym_system.ui.datospredio.detallespredio

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.pruebadrawer.R
import com.example.pruebadrawer.databinding.FragmentNomenclaturaBinding
import com.example.gym_system.interfaces.APIServices
import com.example.pruebadrawer.models.detallespredio.nomenclatura.NomenclaturaClass
import com.example.pruebadrawer.interfaces.rutaancj
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class NomenclaturaFragment(iIDPredio: Int) : Fragment(R.layout.fragment_nomenclatura) {


    private val baseurl = rutaancj
    private lateinit var service: com.example.gym_system.interfaces.APIServices

    private val iIDPredio = iIDPredio

    lateinit var title: TextView
    lateinit var llcontenedor: LinearLayout
    private val client = OkHttpClient()
    private val gson: Gson = GsonBuilder().create()
    val gson2 = Gson()
    lateinit var btnRegresa: Button

    private var _binding: FragmentNomenclaturaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNomenclaturaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        llcontenedor = binding.llcontenedor
        conexionApi(iIDPredio)

        return root
    }

    private fun conexionApi(
        iIDPredio: Int,

        ) {
        println("entrado conexion api")
        try {

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            service = retrofit.create(com.example.gym_system.interfaces.APIServices::class.java)
            loadSeguimiento(
                iIDPredio
            )

        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadSeguimiento(
        iIDPredio: Int
    ) {
        var post: NomenclaturaClass
        var cCalle: String
        var cCondominio: String
        var cCruzamiento1: String
        var cCruzamiento2: String
        var cDenominado: String
        var cDepartamento: String
        var cEdificio: String
        var cLote: String
        var cManzana: String
        var cNivel: String
        var cNomenclatura: String
        var cNumero: String
        var cNumeroInterior: String
        var cSeccion: String
        var cTablaje: String
        var cTipo: String
        var cUbicacion: String
        var iIDNodoMunicipio: String

        val txtdinamico = TextView(context)
        val txtNomenclatura = TextView(context)
        val txtTablaje = TextView(context)
        val txtDepartamento = TextView(context)
        val txtEdificio = TextView(context)
        val txtNivel = TextView(context)
        val txtCondominio = TextView(context)
        val txtSeccion = TextView(context)
        val txtManzana = TextView(context)
        val txtLote = TextView(context)

        val edittext = EditText(context)
        val editTextTablaje = EditText(context)
        val editTextDepartamento = EditText(context)
        val editTextEdificio = EditText(context)
        val editTextNivel = EditText(context)
        val editTextCondominio = EditText(context)
        val editTextSeccion = EditText(context)
        val editTextManzana = EditText(context)
        val editTextLote = EditText(context)

        val txtCatastral = TextView(context)
        val CtxtNomenclatura = TextView(context)
        val CtxtTablaje = TextView(context)
        val CtxtDepartamento = TextView(context)
        val CtxtEdificio = TextView(context)
        val CtxtNivel = TextView(context)
        val CtxtCondominio = TextView(context)
        val CtxtSeccion = TextView(context)
        val CtxtManzana = TextView(context)
        val CtxtLote = TextView(context)

        val Cedittext = EditText(context)
        val CeditTextTablaje = EditText(context)
        val CeditTextDepartamento = EditText(context)
        val CeditTextEdificio = EditText(context)
        val CeditTextNivel = EditText(context)
        val CeditTextCondominio = EditText(context)
        val CeditTextSeccion = EditText(context)
        val CeditTextManzana = EditText(context)
        val CeditTextLote = EditText(context)

        val espacio = TextView(context)

        try {
            service.getNomenclaturaPredio(
                iIDPredio
            )
                .enqueue(object : Callback<NomenclaturaClass> {
                    override fun onResponse(
                        call: Call<NomenclaturaClass>,
                        response: Response<NomenclaturaClass>
                    ) {

                        post = response.body()!!

                        if (response.isSuccessful) {

                            val json = gson2.toJson(post)
                            val jsonDecode = gson2.fromJson(json, NomenclaturaClass::class.java)
                            if (jsonDecode.lSuccess) {
                                jsonDecode.data.forEach() {

                                    txtdinamico.textSize = 20F
                                    txtdinamico.setTextColor(Color.parseColor("#000000"))
                                    txtNomenclatura.textSize = 15F
                                    txtNomenclatura.setTextColor(Color.parseColor("#000000"))
                                    txtTablaje.textSize = 15F
                                    txtTablaje.setTextColor(Color.parseColor("#000000"))
                                    txtDepartamento.textSize = 15F
                                    txtDepartamento.setTextColor(Color.parseColor("#000000"))
                                    txtEdificio.textSize = 15F
                                    txtEdificio.setTextColor(Color.parseColor("#000000"))
                                    txtNivel.textSize = 15F
                                    txtNivel.setTextColor(Color.parseColor("#000000"))
                                    txtCondominio.textSize = 15F
                                    txtCondominio.setTextColor(Color.parseColor("#000000"))
                                    txtSeccion.textSize = 15F
                                    txtSeccion.setTextColor(Color.parseColor("#000000"))
                                    txtManzana.textSize = 15F
                                    txtManzana.setTextColor(Color.parseColor("#000000"))
                                    txtLote.textSize = 15F
                                    txtLote.setTextColor(Color.parseColor("#000000"))

                                    txtCatastral.textSize = 20F
                                    txtCatastral.setTextColor(Color.parseColor("#000000"))
                                    CtxtNomenclatura.textSize = 15F
                                    CtxtNomenclatura.setTextColor(Color.parseColor("#000000"))
                                    CtxtTablaje.textSize = 15F
                                    CtxtTablaje.setTextColor(Color.parseColor("#000000"))
                                    CtxtDepartamento.textSize = 15F
                                    CtxtDepartamento.setTextColor(Color.parseColor("#000000"))
                                    CtxtEdificio.textSize = 15F
                                    CtxtEdificio.setTextColor(Color.parseColor("#000000"))
                                    CtxtNivel.textSize = 15F
                                    CtxtNivel.setTextColor(Color.parseColor("#000000"))
                                    CtxtCondominio.textSize = 15F
                                    CtxtCondominio.setTextColor(Color.parseColor("#000000"))
                                    CtxtSeccion.textSize = 15F
                                    CtxtSeccion.setTextColor(Color.parseColor("#000000"))
                                    CtxtManzana.textSize = 15F
                                    CtxtManzana.setTextColor(Color.parseColor("#000000"))
                                    CtxtLote.textSize = 15F
                                    CtxtLote.setTextColor(Color.parseColor("#000000"))

                                    edittext.setTextColor(Color.parseColor("#000000"))
                                    editTextTablaje.setTextColor(Color.parseColor("#000000"))
                                    editTextDepartamento.setTextColor(Color.parseColor("#000000"))
                                    editTextEdificio.setTextColor(Color.parseColor("#000000"))
                                    editTextNivel.setTextColor(Color.parseColor("#000000"))
                                    editTextCondominio.setTextColor(Color.parseColor("#000000"))
                                    editTextSeccion.setTextColor(Color.parseColor("#000000"))
                                    editTextSeccion.setTextColor(Color.parseColor("#000000"))
                                    editTextManzana.setTextColor(Color.parseColor("#000000"))
                                    editTextLote.setTextColor(Color.parseColor("#000000"))

                                    Cedittext.setTextColor(Color.parseColor("#000000"))
                                    CeditTextTablaje.setTextColor(Color.parseColor("#000000"))
                                    CeditTextDepartamento.setTextColor(Color.parseColor("#000000"))
                                    CeditTextEdificio.setTextColor(Color.parseColor("#000000"))
                                    CeditTextNivel.setTextColor(Color.parseColor("#000000"))
                                    CeditTextCondominio.setTextColor(Color.parseColor("#000000"))
                                    CeditTextSeccion.setTextColor(Color.parseColor("#000000"))
                                    CeditTextSeccion.setTextColor(Color.parseColor("#000000"))
                                    CeditTextManzana.setTextColor(Color.parseColor("#000000"))
                                    CeditTextLote.setTextColor(Color.parseColor("#000000"))

                                    txtdinamico.setBackgroundResource(R.drawable.font)

                                    cNomenclatura = (it.cNomenclatura)
                                    cTablaje = (it.cTablaje)
                                    cDepartamento = (it.cDepartamento)
                                    cEdificio = (it.cEdificio)
                                    cNivel = (it.cNivel)
                                    cCondominio = (it.cCondominio)
                                    cSeccion = (it.cSeccion)
                                    cManzana = (it.cManzana)
                                    cLote = (it.cLote)

                                    llcontenedor.setBackgroundResource(R.drawable.bordecuadro)
                                    if (cNomenclatura.isEmpty()) {
                                        txtNomenclatura.isVisible = false
                                        edittext.isVisible = false
                                    } else if (cTablaje.isEmpty()) {
                                        txtTablaje.isVisible = false
                                        editTextTablaje.isVisible = false
                                    }
                                    if (cDepartamento.isEmpty()) {
                                        txtDepartamento.isVisible = false
                                        editTextDepartamento.isVisible = false
                                    }
                                    if (cEdificio.isEmpty()) {
                                        txtEdificio.isVisible = false
                                        editTextEdificio.isVisible = false
                                    }
                                    if (cNivel.isEmpty()) {
                                        txtNivel.isVisible = false
                                        editTextNivel.isVisible = false
                                    }
                                    if (cCondominio.isEmpty()) {
                                        txtCondominio.isVisible = false
                                        editTextCondominio.isVisible = false
                                    }
                                    if (cSeccion.isEmpty()) {
                                        txtSeccion.isVisible = false
                                        editTextSeccion.isVisible = false
                                    }
                                    if (cManzana.isEmpty()) {
                                        txtManzana.isVisible = false
                                        editTextManzana.isVisible = false
                                    }
                                    if (cLote.isEmpty()) {
                                        txtLote.isVisible = false
                                        editTextLote.isVisible = false
                                    }


                                    if (it.cTipo == "R") {
                                        txtdinamico.append("Registral\n")
                                        txtNomenclatura.append("Nomenclatura\n")
                                        edittext.append(cNomenclatura)
                                        edittext.isEnabled = false
                                        txtTablaje.append("Tablaje\n")
                                        editTextTablaje.append(cTablaje)
                                        editTextTablaje.isEnabled = false
                                        txtDepartamento.append("Departamento\n")
                                        editTextDepartamento.append(cDepartamento)
                                        editTextDepartamento.isEnabled = false
                                        txtEdificio.append("Edificio\n")
                                        editTextEdificio.append(cEdificio)
                                        editTextEdificio.isEnabled = false
                                        txtNivel.append("Nivel\n")
                                        editTextNivel.append(cNivel)
                                        editTextNivel.isEnabled = false
                                        txtCondominio.append("Condominio\n")
                                        editTextCondominio.append(cCondominio)
                                        editTextCondominio.isEnabled = false
                                        txtSeccion.append("Seccion\n")
                                        editTextSeccion.append(cSeccion)
                                        editTextSeccion.isEnabled = false
                                        txtManzana.append("Manzana\n")
                                        editTextManzana.append(cManzana)
                                        editTextManzana.isEnabled = false
                                        txtLote.append("Lote\n")
                                        editTextLote.append(cLote)
                                        editTextLote.isEnabled = false
                                    } else if (it.cTipo == "C") {
                                        if (cNomenclatura.isEmpty()) {
                                            CtxtNomenclatura.isVisible = false
                                            Cedittext.isVisible = false
                                        } else if (cTablaje.isEmpty()) {
                                            CtxtTablaje.isVisible = false
                                            CeditTextTablaje.isVisible = false
                                        }
                                        if (cDepartamento.isEmpty()) {
                                            CtxtDepartamento.isVisible = false
                                            CeditTextDepartamento.isVisible = false
                                        }
                                        if (cEdificio.isEmpty()) {
                                            CtxtEdificio.isVisible = false
                                            CeditTextEdificio.isVisible = false
                                        }
                                        if (cNivel.isEmpty()) {
                                            CtxtNivel.isVisible = false
                                            CeditTextNivel.isVisible = false
                                        }
                                        if (cCondominio.isEmpty()) {
                                            CtxtCondominio.isVisible = false
                                            CeditTextCondominio.isVisible = false
                                        }
                                        if (cSeccion.isEmpty()) {
                                            CtxtSeccion.isVisible = false
                                            CeditTextSeccion.isVisible = false
                                        }
                                        if (cManzana.isEmpty()) {
                                            CtxtManzana.isVisible = false
                                            CeditTextManzana.isVisible = false
                                        }
                                        if (cLote.isEmpty()) {
                                            CtxtLote.isVisible = false
                                            CeditTextLote.isVisible = false
                                        }

                                        txtCatastral.append("Catastral\n")
                                        CtxtNomenclatura.append("Nomenclatura\n")
                                        Cedittext.append(cNomenclatura)
                                        Cedittext.isEnabled = false
                                        CtxtTablaje.append("Tablaje\n")
                                        CeditTextTablaje.append(cTablaje)
                                        CeditTextTablaje.isEnabled = false
                                        CtxtDepartamento.append("Departamento\n")
                                        CeditTextDepartamento.append(cDepartamento)
                                        CeditTextDepartamento.isEnabled = false
                                        CtxtEdificio.append("Edificio\n")
                                        CeditTextEdificio.append(cEdificio)
                                        CeditTextEdificio.isEnabled = false
                                        CtxtNivel.append("Nivel\n")
                                        CeditTextNivel.append(cNivel)
                                        CeditTextNivel.isEnabled = false
                                        CtxtCondominio.append("Condominio\n")
                                        CeditTextCondominio.append(cCondominio)
                                        CeditTextCondominio.isEnabled = false
                                        CtxtSeccion.append("Seccion\n")
                                        CeditTextSeccion.append(cSeccion)
                                        CeditTextSeccion.isEnabled = false
                                        CtxtManzana.append("Manzana\n")
                                        CeditTextManzana.append(cManzana)
                                        CeditTextManzana.isEnabled = false
                                        CtxtLote.append("Lote\n")
                                        CeditTextLote.append(cLote)
                                        CeditTextLote.isEnabled = false
                                    }


                                }

                                llcontenedor.addView(txtdinamico)
                                llcontenedor.addView(txtNomenclatura)
                                llcontenedor.addView(edittext)
                                llcontenedor.addView(txtTablaje)
                                llcontenedor.addView(editTextTablaje)
                                llcontenedor.addView(txtDepartamento)
                                llcontenedor.addView(editTextDepartamento)
                                llcontenedor.addView(txtEdificio)
                                llcontenedor.addView(editTextEdificio)
                                llcontenedor.addView(txtNivel)
                                llcontenedor.addView(editTextNivel)
                                llcontenedor.addView(txtCondominio)
                                llcontenedor.addView(editTextCondominio)
                                llcontenedor.addView(txtSeccion)
                                llcontenedor.addView(editTextSeccion)
                                llcontenedor.addView(txtManzana)
                                llcontenedor.addView(editTextManzana)
                                llcontenedor.addView(txtLote)
                                llcontenedor.addView(editTextLote)
                                llcontenedor.addView(espacio)

                                llcontenedor.addView(txtCatastral)
                                llcontenedor.addView(CtxtNomenclatura)
                                llcontenedor.addView(Cedittext)
                                llcontenedor.addView(CtxtTablaje)
                                llcontenedor.addView(CeditTextTablaje)
                                llcontenedor.addView(CtxtDepartamento)
                                llcontenedor.addView(CeditTextDepartamento)
                                llcontenedor.addView(CtxtEdificio)
                                llcontenedor.addView(CeditTextEdificio)
                                llcontenedor.addView(CtxtNivel)
                                llcontenedor.addView(CeditTextNivel)
                                llcontenedor.addView(CtxtCondominio)
                                llcontenedor.addView(CeditTextCondominio)
                                llcontenedor.addView(CtxtSeccion)
                                llcontenedor.addView(CeditTextSeccion)
                                llcontenedor.addView(CtxtManzana)
                                llcontenedor.addView(CeditTextManzana)
                                llcontenedor.addView(CtxtLote)
                                llcontenedor.addView(CeditTextLote)

                            } else {
                                SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("No se encontraron datos de esa solicitud")
                                    .show()
                            }
                        } else {
                            throw Exception("No se pudo establecer conexión con la api de Datos Inscripcion")
                            SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("No es posible acceder a los datos en este momento")
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<NomenclaturaClass>, t: Throwable) {
                        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No se encontraron datos en Nomenclatura")
                            .show()
                        t.printStackTrace()
                    }

                })


        } catch (e: Exception) {
            println(e.message)
        }

    }
}