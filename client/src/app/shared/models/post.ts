
import {RV} from "./rv";
import {UserMinVM} from "./userMin.vm";

export interface Post {
  rutaViaje: RV
  creador: UserMinVM
  compartidos: number[]
  dateCreated: Date
}
