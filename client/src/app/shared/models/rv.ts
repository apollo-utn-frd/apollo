/**
 * 
 * La visibilidad de una ruta de viaje puede ser solamente
 * 'PRIVADA' o 'PUBLICA'
 */
export type Visibilidad = 'PRIVADA' | 'PUBLICA'

export class RV {
  id: number;
  titulo: string;
  id_usuario: number;
  descripcion: string;
  marcadores: Point[];
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
  latitud: number;
  longitud: number;
}

interface Line {
  pointA: Point;
  pointB: Point;
}
