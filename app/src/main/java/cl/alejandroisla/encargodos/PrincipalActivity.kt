package cl.alejandroisla.encargodos

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.app_bar_principal.*
import kotlinx.android.synthetic.main.content_principal.*
import kotlinx.android.synthetic.main.form_pelis.*


class PrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var listPelicula = ArrayList<Pelicula>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            alertForm("new", null)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        listPelicula.add(Pelicula(1, "Avengers Infinity War", "127 Minutos", "+TE"))
        listPelicula.add(Pelicula(2, "Pie Pequeño", "96 Minutos","+14"))
        listPelicula.add(Pelicula(3, "Halloween", "106 Minutos","+18"))
        listPelicula.add(Pelicula(4, "Corazón Valiene", "167 Minutos","+21"))

        updateListView()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.principal, menu)
        return true
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.nav_pelis -> {
                lvPelis.invalidateViews()
            }
            R.id.nav_about -> {
                about()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun alertForm( type : String, position : Int?){

        val builder = AlertDialog.Builder(this)

        val title : String = if (type.equals("new")) "Nueva Película" else "Editar Película"
        builder.setTitle(title)

        val viewInflated = LayoutInflater.from(this).inflate(R.layout.form_pelis, form_pelis, false)
        builder.setView(viewInflated)

        if (!type.equals("new")){

            var Pelicula = listPelicula[position!!]

            val nombre = viewInflated.findViewById(R.id.txtNombre) as TextView
            val duracion = viewInflated.findViewById(R.id.txtDuracion) as TextView
            val edad = viewInflated.findViewById(R.id.spnEdad) as Spinner

            nombre.text = Pelicula.titulo
            duracion.text = Pelicula.duracion


            val compareValue = Pelicula.edad
            val adapter = ArrayAdapter.createFromResource(this, R.array.array_items, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            edad.setAdapter(adapter)
            if (compareValue != null) {
                val spinnerPosition = adapter.getPosition(compareValue)
                edad.setSelection(spinnerPosition)
            }

        }
        builder.setPositiveButton("Guardar") { dialog, whichButton ->
            if (type.equals("new")){
                addPelicula(viewInflated)
            } else {
                editPelicula(viewInflated, position)
            }

            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, whichButton ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun addPelicula(view : View){

        val nombre = view.findViewById(R.id.txtNombre) as TextView
        val duracion = view.findViewById(R.id.txtDuracion) as TextView
        val edad = view.findViewById(R.id.spnEdad) as Spinner

        val id = listPelicula.size + 1
        listPelicula.add(Pelicula(id, nombre.text.toString(), duracion.text.toString(), edad.selectedItem.toString()))
        lvPelis.invalidateViews()
        Toast.makeText(this@PrincipalActivity, "Película agregada", Toast.LENGTH_SHORT).show()
    }

    private fun editPelicula(view : View, position: Int?){

        val nombre = view.findViewById(R.id.txtNombre) as TextView
        val duracion = view.findViewById(R.id.txtDuracion) as TextView
        val edad = view.findViewById(R.id.spnEdad) as Spinner

        val pelicula = listPelicula[position!!]

        pelicula.titulo = nombre.text.toString()
        pelicula.duracion = duracion.text.toString()
        pelicula.edad = edad.selectedItem.toString()

        lvPelis.invalidateViews()

        Toast.makeText(this@PrincipalActivity, "Película editada", Toast.LENGTH_SHORT).show()
    }

    private fun updateListView(){
        var notesAdapter = NotesAdapter(this, listPelicula)
        lvPelis.adapter = notesAdapter
    }


    inner class NotesAdapter : BaseAdapter {

        private var pelisList = ArrayList<Pelicula>()
        private var context: Context? = null

        constructor(context: Context, pelisList: ArrayList<Pelicula>) : super() {
            this.pelisList = pelisList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val vh: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.pelicula, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            vh.tvTitulo.text = pelisList[position].titulo
            vh.tvDuracion.text = pelisList[position].duracion
            vh.tvEdad.text = pelisList[position].edad


            val ivDelete = view?.findViewById(R.id.ivDelete) as ImageView
            val ivEdit = view?.findViewById(R.id.ivEdit) as ImageView

            ivDelete.setOnClickListener {
                listPelicula.removeAt(position )
                lvPelis.invalidateViews()
                Toast.makeText(this@PrincipalActivity, "Película eliminada", Toast.LENGTH_SHORT).show()
            }
            ivEdit.setOnClickListener {

                alertForm("edit", position)
                lvPelis.invalidateViews()
            }


            return view
        }

        override fun getItem(position: Int): Any {
            return pelisList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return pelisList.size
        }
    }

    private class ViewHolder(view: View?) {

        val tvTitulo: TextView
        val tvDuracion: TextView
        val tvEdad: TextView

        init {
            this.tvTitulo = view?.findViewById(R.id.tvTitulo) as TextView
            this.tvDuracion = view?.findViewById(R.id.tvDuracion) as TextView
            this.tvEdad = view?.findViewById(R.id.tvEdad) as TextView
        }

    }


    private fun about(){

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Acerca de ")
        builder.setMessage("Desarrollado por Alejandro Isla, aplicación para la gestión de peliculas de cinema, ale.isla@alumnos.duoc.cl")

        builder.setPositiveButton("OK") { dialog, whichButton ->

            dialog.dismiss()
        }


        builder.show()
    }
}
