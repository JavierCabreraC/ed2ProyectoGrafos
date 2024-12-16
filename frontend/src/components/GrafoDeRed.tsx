import React from 'react';
import ReactFlow, { Node, Edge, Background, Controls } from 'reactflow';
import 'reactflow/dist/style.css';
import { Connection, NetworkPoint } from '../types/network';


interface GrafoDeRedProps {
    points: NetworkPoint[];
    connections: Connection[];
    selectedPath?: NetworkPoint[];
}

export const GrafoDeRed: React.FC<GrafoDeRedProps> = ({ 
    points, 
    connections,
    selectedPath 
}) => {
    const nodes: Node[] = points.map(point => ({
        id: point.id.toString(),
        data: { label: point.nombre },
        position: {
            x: (point.longitud + 63.2) * 10000,
            y: (point.latitud + 17.8) * 10000
        }
    }));

    const edges: Edge[] = connections.map(conn => ({
        id: `${conn.point_a_id}-${conn.point_b_id}`,
        source: conn.point_a_id.toString(),
        target: conn.point_b_id.toString(),
        label: `${conn.distancia.toFixed(1)}m`,
        style: selectedPath?.some(p => 
            [conn.point_a_id, conn.point_b_id].includes(p.id)
        ) ? { stroke: '#ff0072', strokeWidth: 3 } : {}
    }));

    return (
        <div style={{ height: 500 }}>
            <ReactFlow
                nodes={nodes}
                edges={edges}
                fitView
            >
                <Background />
                <Controls />
            </ReactFlow>
        </div>
    );
};
