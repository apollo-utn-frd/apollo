
import {UserMinVM} from "./userMin.vm";
import {RVMinVM} from "./RVMinVM";

export interface Comment {
  id: number
  rutaViaje: RVMinVM
  usuario: UserMinVM
  contenido: string
  dataCreated: Date
}
