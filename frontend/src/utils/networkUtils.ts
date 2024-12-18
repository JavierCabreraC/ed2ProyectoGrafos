export const getNodeColorByType = (type: string | undefined): string => {
    if (!type) return '#757575';
    
    switch (type.toLowerCase()) {
        case 'comercial':
            return '#4CAF50';
        case 'salud':
            return '#F44336';
        case 'educacion':
            return '#2196F3';
        case 'industrial':
            return '#FF9800';
        case 'tecnologia':
            return '#9C27B0';
        default:
            return '#757575';
    }
};
