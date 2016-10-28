export type Coord = {
  lat: number,
  long: number
}

/**
 * 
 * La visibilidad de una ruta de viaje puede ser solamente
 * 'PRIVADA' o 'PUBLICA'
 */
export type Visibilidad = 'PRIVADA' | 'PUBLICA'

export class RV {
  id: number;
  nombre: string;
  id_usuario: number;
  descripcion: string;
  marcadores: Marker[];
  ruta: Line[];
  visibilidad: Visibilidad;
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