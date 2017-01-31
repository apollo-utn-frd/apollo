
import {GenericRVInteraction} from "./genericRVInteraction.vm";

export type Visibilidad = 'PRIVADA' | 'PUBLICA'

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

interface Point {
  longitud: number,
  latitud: number;
}
