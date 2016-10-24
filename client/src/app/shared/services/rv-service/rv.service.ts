import { Injectable } from '@angular/core';
import { RV } from '../../models/index';
import { Http, Response } from '@angular/http';
import { Service } from '../service.interface';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class RVService implements Service {
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
