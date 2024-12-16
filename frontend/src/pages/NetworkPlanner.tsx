import React, { useState, useEffect } from 'react';
import { networkService } from '../services/api';
import { Connection, NetworkPoint, RouteResponse } from '../types/network';
import { GrafoDeRed } from '../components/GrafoDeRed';


export const NetworkPlanner: React.FC = () => {
    const [points, setPoints] = useState<NetworkPoint[]>([]);
    const [selectedStart, setSelectedStart] = useState<number | null>(null);
    const [selectedEnd, setSelectedEnd] = useState<number | null>(null);
    const [route, setRoute] = useState<RouteResponse | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [connections, setConnections] = useState<Connection[]>([]);

    useEffect(() => {
        loadInitialData();
    }, []);
    
    const loadInitialData = async () => {
        try {
            setLoading(true);
            const [pointsRes, connectionsRes] = await Promise.all([
                networkService.getPoints(),
                networkService.getConnections()
            ]);
            console.log('Points:', pointsRes.data);
            console.log('Connections:', connectionsRes.data);
            setPoints(pointsRes.data);
            setConnections(connectionsRes.data);
        } catch (err) {
            console.error('Error:', err);
            setError('Error cargando datos de red');
        } finally {
            setLoading(false);
        }
    };

    const calcularRuta = async () => {
        if (!selectedStart || !selectedEnd) {
            setError('Selecciona puntos de inicio y fin');
            return;
        }

        try {
            setLoading(true);
            const response = await networkService.calculateRoute(selectedStart, selectedEnd);
            setRoute(response.data);
        } catch (err) {
            setError('Error calculando la ruta');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Planificador de Red</h1>

            {loading && <div className="text-center">Cargando...</div>}
            {error && <div className="text-red-500">{error}</div>}

            <div className="mb-8">
                <h2 className="text-xl mb-4">Red Actual</h2>
                <GrafoDeRed 
                    points={points}
                    connections={connections}
                    selectedPath={route?.camino}
                />
            </div>

            <div className="grid grid-cols-2 gap-4">
                {/* Panel de Selección */}
                <div className="border p-4 rounded">
                    <h2 className="text-xl mb-4">Puntos de Red</h2>
                    <div className="space-y-4">
                        <select
                            className="w-full p-2 border rounded"
                            value={selectedStart || ''}
                            onChange={(e) => setSelectedStart(Number(e.target.value))}
                        >
                            <option value="">Selecciona punto inicial</option>
                            {points.map(point => (
                                <option key={point.id} value={point.id}>
                                    {point.nombre}
                                </option>
                            ))}
                        </select>
                        
                        <select
                            className="w-full p-2 border rounded"
                            value={selectedEnd || ''}
                            onChange={(e) => setSelectedEnd(Number(e.target.value))}
                        >
                            <option value="">Selecciona punto final</option>
                            {points.map(point => (
                                <option key={point.id} value={point.id}>
                                    {point.nombre}
                                </option>
                            ))}
                        </select>
                        
                        <button
                            className="w-full bg-blue-500 text-white p-2 rounded"
                            onClick={calcularRuta}
                            disabled={!selectedStart || !selectedEnd}
                        >
                            Calcular Ruta
                        </button>
                    </div>
                </div>
                    
                {/* Panel de Resultados */}
                <div className="border p-4 rounded">
                    <h2 className="text-xl mb-4">Resultados</h2>
                    {route && (
                        <div>
                            <p>Distancia total: {route.distancia} metros</p>
                            <div className="mt-4">
                                <h3 className="font-bold">Ruta:</h3>
                                <ul className="list-disc pl-4">
                                    {route.camino.map((point, index) => (
                                        <li key={index}>{point.nombre}</li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};
