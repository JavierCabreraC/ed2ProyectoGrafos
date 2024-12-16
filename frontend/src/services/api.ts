import axios from 'axios';

import { NetworkPoint, RouteResponse, NetworkPlan } from '../types/network';


const API_BASE_URL = 'http://localhost:8080/api/network';

export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const networkService = {
  getPoints: () => 
    api.get<NetworkPoint[]>('/points'),
  
  calculateRoute: (startId: number, endId: number) => 
    api.post<RouteResponse>('/route/calculate', { startId, endId }),
  
  generateMST: (algorithm: 'prim' | 'kruskal') => 
    api.post<NetworkPlan>('/mst/generate', { algorithm }),
  
  calculateAllPaths: () => 
    api.post<Record<string, number>>('/floyd/calculate')
};
