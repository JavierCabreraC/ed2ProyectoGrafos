import React from 'react';
import ReactFlow, { Node, Edge, Background, Controls } from 'reactflow';
import 'reactflow/dist/style.css';
import { Connection, NetworkPoint } from '../types/network';
import { getNodeColorByType } from '../utils/networkUtils';


interface GrafoDeRedProps {
    points: NetworkPoint[];
    connections: Connection[];
    selectedPath?: NetworkPoint[];
    mstConnections?: NetworkPoint[];
    highlightedPoints?: NetworkPoint[];
    onNodeClick?: (point: NetworkPoint) => void;
}

export const GrafoDeRed: React.FC<GrafoDeRedProps> = ({ 
    points = [], 
    connections = [],
    selectedPath 
}) => {
    const nodes: Node[] = points.map(point => ({
        id: point.id?.toString() || '',
        data: { 
            label: point.nombre || '',
            type: point.type
        },
        position: {
            x: (point.longitud + 63.2) * 10000,
            y: (point.latitud + 17.8) * 10000
        },
        style: {
            background: getNodeColorByType(point.type),
            padding: 10,
            borderRadius: 5
        }
    }));

    const edges: Edge[] = connections.map(conn => ({
        id: `${conn.pointAId}-${conn.pointBId}`,
        source: conn.pointAId.toString(),
        target: conn.pointBId.toString(),
        label: `${conn.distance.toFixed(1)}m`,
        style: selectedPath?.some(p => 
            [conn.pointAId, conn.pointBId].includes(p.id)
        ) ? { stroke: '#ff0072', strokeWidth: 3 } : {}
    }));

    if (points.length === 0 || connections.length === 0) {
        return <div>Cargando grafo...</div>;
    }

    return (
        <div className="relative">
            <div style={{ height: 500, border: '1px solid #ccc' }}>
                <ReactFlow
                    nodes={nodes}
                    edges={edges}
                    fitView
                >
                    <Background />
                    <Controls />
                </ReactFlow>
            </div>
            
            {/* Leyenda de colores */}
            <div className="absolute top-4 right-4 bg-white p-4 rounded shadow-lg">
                <h3 className="text-sm font-bold mb-2">Tipos de Puntos</h3>
                <div className="space-y-2">
                    {['comercial', 'salud', 'educacion', 'industrial', 'tecnologia'].map(type => (
                        <div key={type} className="flex items-center space-x-2">
                            <div 
                                className="w-4 h-4 rounded"
                                style={{ backgroundColor: getNodeColorByType(type) }}
                            />
                            <span className="text-sm capitalize">{type}</span>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};
