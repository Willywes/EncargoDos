package cl.alejandroisla.encargodos

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.app_bar_principal.*
import android.R.string.cancel
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.android.synthetic.main.form_pelis.*


class PrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            alertForm("new")
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun alertForm( type : String){

        val builder = AlertDialog.Builder(this@PrincipalActivity)

        val title : String = if (type.equals("new")) "Nueva Pélicula" else "Editar Pélicula"
        builder.setTitle(title)

        val viewInflated = LayoutInflater.from(this@PrincipalActivity).inflate(R.layout.form_pelis, form_pelis, false)
// Set up the input
        //val input = viewInflated.findViewById(R.id.input) as EditText
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated)

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            Toast.makeText(this@PrincipalActivity, "nada", Toast.LENGTH_LONG)
        })
        builder.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }
}
