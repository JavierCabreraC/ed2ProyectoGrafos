export interface NetworkPoint {
    id: number;
    name: string;
    latitude: number;
    longitude: number;
}

export interface RouteResponse {
  path: NetworkPoint[];
  distance: number;
}

export interface NetworkPlan {
  id: number;
  algorithm: string;
  totalCost: number;
  points: NetworkPoint[];
}
