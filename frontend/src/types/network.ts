export interface NetworkPoint {
    id: number;
    nombre: string;
    latitud: number;
    longitud: number;
}

export interface RouteResponse {
    camino: NetworkPoint[];
    distancia: number;
}

export interface NetworkPlan {
    id: number;
    algoritmo: string;
    costoTotal: number;
    puntos: NetworkPoint[];
}

export interface Connection {
    id: number;
    point_a_id: number;
    point_b_id: number;
    distancia: number;
    costo: number;
    estado: string;
}
