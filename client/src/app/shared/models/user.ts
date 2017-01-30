export interface User {
  username: string,
  password: string,
  email: string,
  nombre: string,
  apellido: string,
  idGoogle: string,
  pictureGoogleUrl: string,
  descripcion: string,
  firstLogin: boolean,
  enabled: boolean,
  accountExpired: boolean,
  accountLocked: boolean,
  passwordExpired: boolean,
  seguidos: Seguidor[],
  seguidores: Seguidor[],
  rutasViaje: number[],
  autorizaciones: number[],
  favoritos: number[],
  compartidos: number[],
  comentarios: number[],
  dateCreated: Date,
  lastUpdated: Date
}

export interface Seguidor {

}
