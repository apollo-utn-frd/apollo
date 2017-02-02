import {Point} from "../../models/point";
import {RVFormVM} from "../../models/rvForm.vm";

export interface MapState {
  center: Point
  points: Point[]
  form: RVFormVM
}

export const INITIAL_MAP_STATE: MapState = {
  center: undefined,
  points: undefined,
  form: undefined
};

export interface UIState {
  mapState: MapState
}

export const INITIAL_UI_STATE: UIState = {
  mapState: INITIAL_MAP_STATE
};
