import {Seguidor} from "./follower";
import {GenericUserInteraction} from "./genericUserInteraction.vm";

export interface PublicUserVM {
  id: string,
  username: string,
  email: string,
  nombre: string,
  apellido: string,
  descripcion: string,
  imagenGoogleUrl: string,
  seguidos: Seguidor[],
  seguidores: Seguidor[],
  rutasViaje: number[],
  comentarios: Comment[],
  favoritos: GenericUserInteraction[],
  compartidos: GenericUserInteraction[],
  dateCreated: Date,
}
