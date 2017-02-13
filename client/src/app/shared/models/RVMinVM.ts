
import {Point} from "./point";

export interface RVMinVM {
  id: number
  creador: {id: number}
  nombre: string
  descripcion: string
  publico: boolean
  imagenUrl: string
  sitios: Point[]
  comentarios: {id:number}[]
  favoritos: action_usuario[]
  compartidos: action_usuario[]
  autorizaciones: action_usuario[]
  dateCreated: Date
}

export interface action_usuario {
  id: number
  dateCreated: Date
}
