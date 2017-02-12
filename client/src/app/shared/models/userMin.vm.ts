// buscar un nombre copado
export interface UserMinVM {
  id: string
  username: string
  email: string
  nombre: string
  apellido: string
  descripcion: string
  imagenUrl: string
  seguidos: any[]
  seguidores: any[]
  rutasViaje: {id:number}[]
  comentarios: any[]
  favoritos: any[]
  compartidos: any[]
  autorizaciones: any[]
  dateCreated: Date
}
