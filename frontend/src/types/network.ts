export interface NetworkPoint {
    id: number;
    nombre: string;
    latitud: number;
    longitud: number;
    type: string;
}

export interface RouteResponse {
    camino: NetworkPoint[];
    distancia: number;
}

export interface NetworkPlan {
    id: number;
    algoritmo: string;
    costoTotal: number;
    points: NetworkPoint[];
}

export interface Connection {
    id: number;
    pointAId: number;
    pointBId: number;
    distance: number;
    cost: number;
    status: string;
}
