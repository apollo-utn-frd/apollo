export class User {

  constructor(
    public username: string,
    public password: string,
    public email: string,
    public nombre: string,
    public apellido: string,
    public idGoogle: string,
    public pictureUrl: string,
    public descripcion: string,
    public firstLogin: boolean,
    public enabled: boolean,
    public accountExpired: boolean,
    public accountLocked: boolean,
    public passwordExpired: boolean,
    public seguidos: Seguidor[],
    public seguidores: Seguidor[],
    public rutasViaje: number[],
    public autorizaciones: number[],
    public favoritos: number[],
    public compartidos: number[],
    public comentarios: number[],
    public dateCreated: Date,
    public lastUpdated: Date
            ) { }
}

export interface Seguidor {

}