export type Coord = {
  lat : number, 
  long : number
}

export type Visibilidad = "PRIVADA" | "PUBLICA"

export class RV {
  nombre : string
  id_usuario : number
  descripcion : string
  marcadores: Marker[]
  ruta: Line[]
  visibilidad : Visibilidad
}

interface Marker {
  lat: number;
  lng: number;
  label?: string;
	draggable?: boolean;
}

interface Point {
  lat: number;
  lng: number;
}

interface Line {
  pointA: Point;
  pointB: Point;
}