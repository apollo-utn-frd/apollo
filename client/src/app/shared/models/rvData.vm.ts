
import {Point} from "./point";
import {Visibilidad} from "./rv";

export interface RVDataVM {
  nombre: string
  creador: number
  descripcion: string
  publico: Visibilidad
  sitios: Point[]
}
