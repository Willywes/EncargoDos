package cl.alejandroisla.encargodos

class Pelicula {

    var id: Int? = null
    var titulo: String? = null
    var duracion: String? = null
    var edad: String? = null

    constructor(id: Int, titulo: String, duracion: String, edad: String) {
        this.id = id
        this.titulo = titulo
        this.duracion = duracion
        this.edad = edad
    }
}