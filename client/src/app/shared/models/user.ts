import {Seguidor} from "./follower";
import {GenericUserInteraction} from "./genericUserInteraction.vm";

export interface User {
  id: string,
  username: string,
  email: string,
  nombre: string,
  apellido: string,
  descripcion: string,
  firstLogin: boolean,
  accountLocked: boolean
  imagenUrl: string,
  seguidos: Seguidor[],
  seguidores: Seguidor[],
  rutasViaje: number[],
  comentarios: Comment[],
  favoritos: GenericUserInteraction[],
  compartidos: GenericUserInteraction[],
  autorizaciones: GenericUserInteraction[],
  dateCreated: Date,
}
