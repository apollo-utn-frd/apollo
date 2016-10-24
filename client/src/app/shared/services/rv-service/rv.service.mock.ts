import { Injectable } from '@angular/core';
import { RV } from '../../models/rv';
import { Service } from '../service.interface';

@Injectable()
export class RVMockService implements Service {

  getByID<RV>(id: number): T {

  }
  get<T>(): T[];
  create<T>(t: T): void;
  delete(id: number): void;
  edit<T>(id: number, t: T): void;
}
