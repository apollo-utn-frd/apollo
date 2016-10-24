import { Injectable } from '@angular/core';
import { RV } from '../../models/rv';
import { Service } from '../service.interface';

@Injectable()
export class RVMockService implements Service {

  getByID<RV>(id: number): RV {
      return null;
  }
  get<RV>(): RV[] {
      return null;
  }
  create<RV>(rv: RV): void {
      return null;
  }
  delete(id: number): void {
      return null;
  }
  edit<RV>(id: number, rv: RV): void{
      return null;
  }
}
