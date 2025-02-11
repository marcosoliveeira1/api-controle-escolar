import { registerEnumType } from '@nestjs/graphql';

export enum StudentLevel {
    ELEMENTARY = 'ELEMENTARY',
    MIDDLE = 'MIDDLE',
    HIGH = 'HIGH'
}

registerEnumType(StudentLevel, {
    name: 'StudentLevel',
});