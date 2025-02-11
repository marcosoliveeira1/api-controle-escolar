import { registerEnumType } from '@nestjs/graphql';

export enum StudentLevel {
    ELEMENTARY = 'ELEMENTARY',
    MIDDLE_SCHOOL = 'MIDDLE_SCHOOL',
    HIGH_SCHOOL = 'HIGH_SCHOOL'
}

registerEnumType(StudentLevel, {
    name: 'StudentLevel',
});