export class User {

  constructor(
    public id: number,
    public firstname: string,
    public lastname: string,
    public username: string,
    public email: string,
    public pictureUrl: string,
    public seguidos: number[],
    public seguidores: number[],
    public rutasViaje: number[],
    public comentarios: number[],
    public favoritos: number[],
    public compartidos: number[],
    public autorizaciones: number[],
    public dateCreated: string, // cambiar a Date?
    public description: string,
    public firstLogin: string
            ) {}
}
