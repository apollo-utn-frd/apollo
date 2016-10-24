export interface Service {

  getByID<T>(id: number): T;
  get<T>(): T[];
  create<T>(t: T): void;
  delete(id: number): void;
  edit<T>(id: number, t: T): void;
  
}