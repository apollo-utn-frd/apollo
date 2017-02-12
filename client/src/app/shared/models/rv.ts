
import {Point} from "./point";
import {UserMinVM} from "./userMin.vm";

export type Visibilidad = 'PRIVADA' | 'PUBLICA';

export interface RV {
  id?: number
  creador: UserMinVM
  nombre: string
  descripcion: string
  publico: boolean
  imagenUrl: string
  sitios: Point[]
  comentarios: any[]
  favoritos: any[]
  compartidos: any[]
  autorizaciones: any[]
  dateCreated: Date
}

/* interfaz vieja
export interface RV {
  id?: number;
  creador: number,
  nombre: string;
  descripcion: string;
  publico: Visibilidad;
  imagenLocalPath: string,
  comentarios: Comment[],
  favoritos: GenericRVInteraction[],
  compartidos: GenericRVInteraction[],
  autorizaciones: GenericRVInteraction[],
  sitios: Point[];
}
*/
