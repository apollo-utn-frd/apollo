
import {Visibilidad} from "./rv";
import {Point} from "./point";

export interface RVFormVM {
  nombre: string
  creador: number
  descripcion: string
  sitios: Point[]
  publico: Visibilidad
}
