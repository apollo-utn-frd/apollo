export class User {

  // TODO
  // se debe agregar el id de usuario? 
  constructor(
    public id: number,
    public firstname: string,
    public lastname: string,
    public username: string,
    public email: string,
    public description: string,
    public firstLogin: string
            ) {}
}
