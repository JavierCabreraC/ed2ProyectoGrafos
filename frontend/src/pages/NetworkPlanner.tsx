import React, { useState, useEffect } from 'react';
import { networkService } from '../services/api';
import { GrafoDeRed } from '../components/GrafoDeRed';
import { Connection, NetworkPlan, NetworkPoint, RouteResponse } from '../types/network';


export const NetworkPlanner: React.FC = () => {
    const [points, setPoints] = useState<NetworkPoint[]>([]);
    const [selectedStart, setSelectedStart] = useState<number | null>(null);
    const [selectedEnd, setSelectedEnd] = useState<number | null>(null);
    const [route, setRoute] = useState<RouteResponse | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [connections, setConnections] = useState<Connection[]>([]);
    const [activeView, setActiveView] = useState<'route' | 'mst' | 'allPaths'>('route');
    const [mstPlan, setMstPlan] = useState<NetworkPlan | null>(null);
    const [allPaths, setAllPaths] = useState<Record<string, number>>({});
    const [selectedAlgorithm, setSelectedAlgorithm] = useState<'prim' | 'kruskal'>('prim');

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
            console.log('Datos recibidos - Puntos:', pointsRes.data);
            console.log('Datos recibidos - Conexiones:', connectionsRes.data);
            
            if (pointsRes.data && connectionsRes.data) {
                setPoints(pointsRes.data);
                setConnections(connectionsRes.data);
            } else {
                setError('No se recibieron datos válidos del servidor');
            }
        } catch (err) {
            console.error('Error cargando datos:', err);
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
    
        console.log('Enviando request con:', {
            inicioId: selectedStart,
            finId: selectedEnd
        });
    
        try {
            setLoading(true);
            const response = await networkService.calculateRoute(selectedStart, selectedEnd);
            console.log('Respuesta:', response);
            setRoute(response.data);
        } catch (err) {
            console.error('Error detallado:', err);
            setError('Error calculando la ruta');
        } finally {
            setLoading(false);
        }
    };

    const generateMST = async () => {
        try {
            setLoading(true);
            const response = await networkService.generateMST(selectedAlgorithm);
            setMstPlan(response.data);
        } catch (err) {
            setError('Error generando MST');
        } finally {
            setLoading(false);
        }
    };
    
    const calculateAllPaths = async () => {
        try {
            setLoading(true);
            const response = await networkService.calculateAllPaths();
            setAllPaths(response.data);
        } catch (err) {
            setError('Error calculando caminos');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Planificador de Red</h1>

            {loading && <div className="text-center">Cargando...</div>}
            {error && <div className="text-red-500">{error}</div>}

            {/* Tabs de navegación */}
            <div className="mb-4">
                <div className="flex space-x-4">
                    <button
                        className={`px-4 py-2 rounded ${
                            activeView === 'route' ? 'bg-blue-500 text-white' : 'bg-gray-200'
                        }`}
                        onClick={() => setActiveView('route')}
                    >
                        Calcular Ruta
                    </button>
                    <button
                        className={`px-4 py-2 rounded ${
                            activeView === 'mst' ? 'bg-blue-500 text-white' : 'bg-gray-200'
                        }`}
                        onClick={() => setActiveView('mst')}
                    >
                        Árbol de Expansión Mínima
                    </button>
                    <button
                        className={`px-4 py-2 rounded ${
                            activeView === 'allPaths' ? 'bg-blue-500 text-white' : 'bg-gray-200'
                        }`}
                        onClick={() => setActiveView('allPaths')}
                    >
                        Todos los Caminos
                    </button>
                </div>
            </div>

            {/* Visualización del grafo */}
            <div className="mb-8">
                <h2 className="text-xl mb-4">Red Actual</h2>
                <GrafoDeRed 
                    points={points}
                    connections={connections}
                    selectedPath={activeView === 'route' ? route?.camino : undefined}
                    mstConnections={activeView === 'mst' ? mstPlan?.points : undefined}
                />
            </div>

            {/* Contenido según la vista activa */}
            {activeView === 'route' && (
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
            )}

            {activeView === 'mst' && (
                <div className="grid grid-cols-2 gap-4">
                    <div className="border p-4 rounded">
                        <h2 className="text-xl mb-4">Generar Árbol de Expansión Mínima</h2>
                        <div className="space-y-4">
                            <select
                                className="w-full p-2 border rounded"
                                value={selectedAlgorithm}
                                onChange={(e) => setSelectedAlgorithm(e.target.value as 'prim' | 'kruskal')}
                            >
                                <option value="prim">Algoritmo de Prim</option>
                                <option value="kruskal">Algoritmo de Kruskal</option>
                            </select>
                            <button
                                className="w-full bg-blue-500 text-white p-2 rounded"
                                onClick={generateMST}
                            >
                                Generar MST
                            </button>
                        </div>
                    </div>
                    <div className="border p-4 rounded">
                        <h2 className="text-xl mb-4">Resultados MST</h2>
                        {mstPlan && (
                            <div>
                                <p>Algoritmo: {mstPlan.algoritmo}</p>
                                <p>Costo total: {mstPlan.costoTotal}</p>
                                <div className="mt-4">
                                    <h3 className="font-bold">Puntos en el árbol:</h3>
                                    <ul className="list-disc pl-4">
                                        {mstPlan.points.map((point) => (  // Cambiamos 'puntos' a 'points'
                                            <li key={point.id}>{point.nombre}</li>
                                        ))}
                                    </ul>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            )}

            {activeView === 'allPaths' && (
                <div className="border p-4 rounded">
                    <h2 className="text-xl mb-4">Todos los Caminos Mínimos</h2>
                    <button
                        className="mb-4 bg-blue-500 text-white p-2 rounded"
                        onClick={calculateAllPaths}
                    >
                        Calcular Todos los Caminos
                    </button>
                    {Object.entries(allPaths).length > 0 && (
                        <div className="grid grid-cols-1 gap-2">
                            {Object.entries(allPaths).map(([path, distance]) => (
                                <div key={path} className="p-2 border rounded">
                                    <span className="font-bold">{path}:</span> {distance.toFixed(2)} metros
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};
