
import {GenericRVInteraction} from "./genericRVInteraction.vm";
import {Point} from "./point";

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
